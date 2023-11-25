
import breeze.numerics.constants.e
import breeze.util.BloomFilter
import org.apache.log4j.BasicConfigurator
import org.apache.log4j.varia.NullAppender
import org.apache.spark.{SparkConf, SparkContext}
object BloomFilterDocuments {
  def main(args: Array[String]): Unit = {
    val nullAppender = new NullAppender
    BasicConfigurator.configure(nullAppender)

    val configuration = new SparkConf()
      .setAppName("Read Documents")
      .setMaster("local")
    val sparkContext = new SparkContext(configuration)

    val textFilesFromDirectory = "src/main/resources/NumberOfDocuments/*"
    val rddText = sparkContext.textFile(textFilesFromDirectory).flatMap(word => word .split(" "))
//   rddText.collect().foreach(f => {
//     println(f)
//   })
val numberOfWords = rddText.count() // m
  //  println(numberOfWords)

    val bloomFilter = rddText.mapPartitions { iter =>
      val bloomFilter = BloomFilter.optimallySized[String](numberOfWords, 0.001)
      iter.foreach(i => bloomFilter += i)
      Iterator(bloomFilter)
    }.reduce(_ | _)

    println(bloomFilter.contains("algorithm")) // true
    println(bloomFilter.contains("Parquet")) // true
    println(bloomFilter.contains("ahed")) //false

    val rddTextCollect = rddText.collect()

        val resultOfBloomFilter = rddTextCollect.map { word =>
          val isInBloomFilter= bloomFilter.contains(word)
          (word,isInBloomFilter)
        }
    println("The result of Bloom filter for each  words: ")
    resultOfBloomFilter.foreach(println)

    val resultOfActualRdd  = rddTextCollect.map { word =>
      val isInOfActualRdd = rddText.collect().contains(word)
      (word ,isInOfActualRdd)
    }

    println("The result of Actual Rdd for each words  : ")
    resultOfActualRdd.foreach(println)


    val comparisonResult = resultOfBloomFilter.zip(resultOfActualRdd).map {
      case ((wordBf, isInBF), (wordRdd, isInRDD)) =>
        val isEqual =  isInBF == isInRDD
        val result = if (isEqual) 1.0 else 0.0
        (wordBf, result)

    }

    println("The result of comparison between bloom filter and Actual rdd   : ")
    comparisonResult.foreach(println)

    val totalComparisonResultMatches = comparisonResult.map(_._2).sum
    val errorRate = 1.0 - (totalComparisonResultMatches / comparisonResult.length)

    println(s"Error Rate is : $errorRate")

    // test data
    //val ListOfWordsToTest = Seq("ahed", "Parquet", "sensitive", "libraries", "collections", "sources") // Words for testing


  }

}

import breeze.util.BloomFilter
import org.apache.log4j.BasicConfigurator
import org.apache.log4j.varia.NullAppender
import org.apache.spark.sql.catalyst.dsl.expressions.longToLiteral
import org.apache.spark.{SparkConf, SparkContext}
object BloomFilter {
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

    val bf = rddText.mapPartitions { iter =>
      val bf = 

  }

}

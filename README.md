# Bloom Filter Implementation with Apache Spark

This Scala application demonstrates the implementation and evaluation of a Bloom Filter using Apache Spark for word filtering.

## Overview

The program aims to showcase the use of a Bloom Filter for efficient word containment checking within a large dataset. It utilizes Apache Spark's functionality for distributed computing.

## Setup

Ensure you have the necessary dependencies installed and Apache Spark environment configured to run the program.

## Functionality

- The program reads text files from the specified directory (`src/main/resources/NumberOfDocuments/*`) and processes them to create an RDD (Resilient Distributed Dataset).

- It calculates the total number of words (m) within the RDD.

- Creates a Bloom Filter optimized for the number of words found in the documents with a given error rate (0.001 in this case).

- Inserts words from the RDD into the Bloom Filter and checks if certain words exist in the Bloom Filter (`bloomFilter.contains(word)`).

- Compares the Bloom Filter results with the actual RDD to evaluate the false-positive rate of the Bloom Filter.

- Outputs the results of the Bloom Filter, actual RDD, comparison between them, and the calculated error rate.

## Output Explanation

- The program prints the existence of specific words within the Bloom Filter and the RDD.
- It compares the Bloom Filter's results against the actual RDD to evaluate their equality (1.0 for match, 0.0 for mismatch) and computes the error rate.
- The printed output shows the comparison result between the Bloom Filter and the RDD for each word.

## Note
- Adjustments might be necessary based on specific use cases or requirements.
- Error rate calculation depends on the comparison between Bloom Filter and RDD results.


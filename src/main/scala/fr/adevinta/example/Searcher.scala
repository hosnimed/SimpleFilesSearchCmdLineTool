package fr.adevinta.example

import java.io.File
import java.math.MathContext

import scala.io.{Source, StdIn}

object Searcher extends App {

  def indexFile(file: File): Map[String, Int] = {
    val source = Source.fromFile(file)
    val wordsOccurences: Map[String, Int] = source.getLines()
      .toStream
      .par
      .flatMap(_.split("\\W+")) // lazy to not load the whole file into memory
      .map(_.toLowerCase)
      .foldLeft(Map.empty[String, Int].withDefaultValue(0)) {
        (acc, word) => acc + (word -> (acc(word) + 1))
      }
    source.close
    wordsOccurences
  }

  //  calculate the occurrence of a word in one file
  def caclOcc(word: String, indexedFile: Map[String, Int]) = indexedFile.get(word)


  override def main(args: Array[String]): Unit = {
    if (args.isEmpty) throw new IllegalArgumentException("No directory given to index.")
    val indexableDirectory = new File(args(0));
    if (!indexableDirectory.isDirectory) throw new IllegalArgumentException("The given argument must be a directory")
    val files: Array[File] = listTextFiles(indexableDirectory)
    val totalFiles = files.length
    println(s"$totalFiles files read in directory ${indexableDirectory.getAbsolutePath}")

    //  Index all files in indexableDirectory
    // Todo: execute in another thread : future & exec ctx
    val indexedFiles: List[(File, Map[String, Int])] = files.par
      .map { file => (file, indexFile(file)) }
      .toList
    //    Read words
    print("search> ")
    var line: String = StdIn.readLine()
    while (!line.equalsIgnoreCase(":quit") && totalFiles > 0) {
      print("result> ")
      val words: Array[String] = wordsTokenizer(line)
      val totalWords = words.length
      //    Search indexed files for words in line
      val top10 = takeTop10(words, indexedFiles) // take top 10
      // filter empty results
      if (top10.filter { case (_, acc) => acc > 0 }.isEmpty) {
        println("No matches found.")
      } else { top10.foreach { case (file, acc) => {
        if (acc == 0) println(s"${file.getName} : 0%")}}}
      //deals with non empty
      top10
        .filter { case (_, acc) => acc > 0 }
        .foreach {
          case (file, totalOcc) =>
            val avg = calculateAverage(totalWords, totalOcc)
//            println(s"(file, totalWords : totalOcc)=>(avg) : (${file.getName}, $totalWords : $totalOcc)=>$avg")
            println(s"${file.getName} : ${avg} %")
        }

      print("search> ")
      line = StdIn.readLine()
    }

  }

  def calculateAverage(totalWords: Int, totalOcc: Int) = {
    BigDecimal((totalOcc.toDouble / totalWords.toDouble) * 100).setScale(0, BigDecimal.RoundingMode.DOWN).round(new MathContext(0)).toInt
  }

  def takeTop10(words: Array[String], indexedFiles: List[(File, Map[String, Int])]) = {
    indexedFiles.map {
      case (file, fileMap) =>
        //          println(s"${file.getName} ==> ${fileMap}")
        var acc = 0
        words //.toStream.par
          .map {
          word => {
            if (caclOcc(word, fileMap).isDefined) {
              acc += 1
            } // is word exist in file => increment acc
          }
        }
        (file, acc)
    }.sortBy(_._2) //sort by existing words
      .reverse
      .take(10)
  }

  def wordsTokenizer(line: String) = {
    line.split("\\W+").map(_.toLowerCase).distinct
  }

  def listTextFiles(indexableDirectory: File): Array[File] = {
    val files: Array[File] = indexableDirectory
      .listFiles()
      .filter(_.getName.endsWith(".txt"))
    files
  }

}
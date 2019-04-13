import java.io.{BufferedWriter, FileWriter}
import java.nio.file.{Files, Paths}

import fr.adevinta.example.Searcher
import org.scalatest._

class SearcherTest extends FunSuiteLike with Matchers with BeforeAndAfterAll {
  val testDir = Paths.get("testDir")

  override protected def beforeAll(): Unit = {
    val path = Files.createDirectory(testDir)
    for (file <- 'A' to 'L') {
      val path = Paths.get(s"testDir/$file.txt")
      val p = Files.createFile(path)
      println(s"File created @ $p")
    }
  }

  override protected def afterAll(): Unit = {
    testDir.toFile.listFiles().foreach { f => Files.deleteIfExists(Paths.get(f.getAbsolutePath)) }
    Files.delete(testDir)
  }

  test("should list files in dir") {
    assert(!Searcher.listTextFiles(testDir.toFile).isEmpty)
    Searcher.listTextFiles(testDir.toFile).length shouldEqual (12)
  }

  test("should index file") {
    val file = testDir.toFile.listFiles().head
    val writer = new BufferedWriter(new FileWriter(file))
    val text = StringBuilder.newBuilder
      .append("to")
      .append(System.lineSeparator())
      .append("be or not")
      .append(System.lineSeparator())
      .append("TO BE")
    writer.write(text.toString())
    writer.close()
    var expected = Map.empty[String, Int]
    expected ++=(expected.+("to" -> 2).+("be" -> 2).+("or" -> 1).+("not" -> 1))
    Searcher.indexFile(file) shouldEqual(expected)
  }

  test("should calculate occurence of a word in file"){
    var wordsMap = Map.empty[String, Int]
    wordsMap ++=(wordsMap.+("to" -> 2).+("be" -> 2).+("or" -> 1).+("not" -> 1))
    Searcher.caclOcc("to", wordsMap) === 2
    Searcher.caclOcc("none", wordsMap) === 0
  }

  test("should split words and filter duplicated ones"){
     Searcher.wordsTokenizer("to be or not TO BE") === Array("to", "be", "or", "not")
  }

  test("should get top 10 ranked file") {
    val file = testDir.toFile.listFiles()(0)
    val file2 = testDir.toFile.listFiles()(2)
    val writer = new BufferedWriter(new FileWriter(file))
    val writer2 = new BufferedWriter(new FileWriter(file2))
    val text = StringBuilder.newBuilder
      .append("to")
      .append(System.lineSeparator())
      .append("be or not")
      .append(System.lineSeparator())
      .append("TO BE")
    writer.write(text.toString())
    writer.close()
    writer2.write("or not")
    writer2.close()

    var wordsMap = Map.empty[String, Int]
    wordsMap ++=(wordsMap.+("to" -> 2).+("be" -> 2).+("or" -> 1).+("not" -> 1))
    var wordsMap2 = Map.empty[String, Int]
    wordsMap2 ++=(wordsMap2.+("or" -> 1).+("not" -> 1))

    Searcher.takeTop10(Array("to", "be", "or", "not"), List( (file, wordsMap), (file2, wordsMap2) ) ) shouldEqual (List( (file, 4), (file2, 2) ))
  }

}
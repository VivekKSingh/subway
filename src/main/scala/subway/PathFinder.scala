package subway

/**
  * Created by Vivek Singh on 7/31/16.
  *
  * Scala Object which implements a bunch of test cases
  */
object PathFinder {

  private val line1Stops: List[String] = List("Canal", "Houston", "Christopher", "14th")
  private val line2Stops: List[String] = List("Spring", "West 4th", "14th", "23rd")

  private def testSystemWithWeights(): Unit = {
    println("\nWeighted Graph Test")
    println("************************")
    val g: SubwaySystem = new SubwaySystem()
    val line1Edges: List[StopConnection] = List(StopConnection("Canal", "Houston", 3), StopConnection("Houston", "Christopher", 7), StopConnection("Christopher", "14th", 2))
    val line2Edges: List[StopConnection] = List(StopConnection("Spring", "West 4th", 1), StopConnection("West 4th", "14th", 5), StopConnection("14th", "23rd", 2))
    g.addTrainLine(line1Stops, line1Edges, "line1")
    g.addTrainLine(line2Stops, line2Edges, "line2")
    assertEquals((List("Houston", "Christopher", "14th", "23rd"), 11), g.takeTrain("Houston", "23rd"), "Houston -> Christopher weighted")
    assertEquals((List("Christopher", "Houston", "Canal"), 10), g.takeTrain("Christopher", "Canal"), "Christopher -> Canal weighted")
    assertEquals((List("Christopher"), 0), g.takeTrain("Christopher", "Christopher"), "Christopher -> Christopher weighted")
    assertEquals((List(), 0), g.takeTrain("Christo", "Christopher"), "Non-Existent source")
    assertEquals((List(), 0), g.takeTrain("Christopher", "C"), "Non-Existent destination")

  }

  private def testSystemWithoutWeights(): Unit = {
    println("\nUn-Weighted Graph Test")
    println("************************")
    val g: SubwaySystem = new SubwaySystem(true)
    val line1EdgesNoWeight: List[StopConnection] = List(StopConnection("Canal", "Houston"), StopConnection("Houston", "Christopher"), StopConnection("Christopher", "14th"))
    val line2EdgesNoWeight: List[StopConnection] = List(StopConnection("Spring", "West 4th"), StopConnection("West 4th", "14th"), StopConnection("14th", "23rd"))
    g.addTrainLine(line1Stops, line1EdgesNoWeight, "line1")
    g.addTrainLine(line2Stops, line2EdgesNoWeight, "line2")
    assertEquals(List("Houston", "Christopher", "14th", "23rd"), g.takeTrain("Houston", "23rd")._1, "Houston -> Christopher unweighted")
    assertEquals(List("Christopher", "Houston", "Canal"), g.takeTrain("Christopher", "Canal")._1, "Christopher -> Canal unweighted")
    assertEquals(List("Christopher"), g.takeTrain("Christopher", "Christopher")._1, "Christopher -> Christopher unweighted")
    assertEquals(List(), g.takeTrain("Christo", "Christopher")._1, "Non-Existent source")
    assertEquals(List(), g.takeTrain("Christo", "C")._1, "Non-Existent destination")
  }

  def main(args: Array[String]) {
    testSystemWithWeights()
    testSystemWithoutWeights()
  }

  // Helper methods to Validate results returned by tests
  private def assertEquals(expected: List[String], actual: List[String], testName: String): Unit = {
    assertEquals(expected == actual, expected, actual, testName)
  }

  private def assertEquals(expected: (List[String], Int),
                           actual: (List[String], Int),
                           testName: String): Unit = {
    assertEquals((expected._1 == actual._1) && (expected._2 == actual._2), expected, actual, testName)
  }

  private def assertEquals(result: Boolean,
                           expected: Any,
                           actual: Any,
                           testName: String): Unit = {
    if (result) println(s"${testName}: Passed")
    else {
      println(s"${testName}: Failed")
      println(s"Expected: ${expected}")
      println(s"Actual: ${actual}")
    }
  }
}

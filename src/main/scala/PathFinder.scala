/**
  * Created by Vivek Singh on 7/31/16.
  */
object PathFinder {
  private val g: Graph = new Graph()

  // Allow adding train lines
  def addTrainLine(stops: List[String], edges: List[Edge], lineName: String) {
    g.addStops(stops, lineName)
    g.addRouteDistances(edges)
  }

  // Fetch the shortest path between stations
  def takeTrain(source: String, destination: String) {
    g.buildPaths(source)
    g.prettyPrintPath(destination)
  }

  def main(args: Array[String]) {

    println("With Weights")
    println("-----------")
    val line1Stops: List[String] = List("Canal", "Houston", "Christopher", "14th")
    val line2Stops: List[String] = List("Spring", "West 4th", "14th", "23rd")
    val line1Edges: List[Edge] = List(Edge("Canal", "Houston", 3), Edge("Houston", "Christopher", 7), Edge("Christopher", "14th", 2))
    val line2Edges: List[Edge] = List(Edge("Spring", "West 4th", 1), Edge("West 4th", "14th", 5), Edge("14th", "23rd", 2))

    addTrainLine(line1Stops, line1Edges, "line1")
    addTrainLine(line2Stops, line2Edges, "line2")
    takeTrain("West 4th", "Houston")

    println("\nNo Weights")
    println("-----------")

    // No Weights
    val line1EdgesNoWeight: List[Edge] = List(Edge("Canal", "Houston"), Edge("Houston", "Christopher"), Edge("Christopher", "14th"))
    val line2EdgesNoWeight: List[Edge] = List(Edge("Spring", "West 4th"), Edge("West 4th", "14th"), Edge("14th", "23rd"))

    addTrainLine(line1Stops, line1EdgesNoWeight, "line1")
    addTrainLine(line2Stops, line2EdgesNoWeight, "line2")
    takeTrain("West 4th", "Houston")
  }
}

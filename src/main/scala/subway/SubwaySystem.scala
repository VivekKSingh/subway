package subway

import java.util

import scala.collection.mutable.{ListBuffer}
import scala.util.control.Breaks._

case class StopConnection(vertex1: String, vertex2: String, distance: Int = 1)

/**
  * Created by Vivek Singh on 7/31/16.
  */
class SubwaySystem(isUnweighted: Boolean = false) {
  private val graph = new util.HashMap[String, SubwayStop]


  // Allow adding train lines
  def addTrainLine(stops: List[String], edges: List[StopConnection], lineName: String) {
    addStops(stops, lineName)
    addRouteDistances(edges)
  }

  // Fetch the shortest path between stations
  def takeTrain(source: String, destination: String): (List[String], Int) = {
    if(!isValidVertex(source) || !isValidVertex(destination)) return (List(), 0)
    buildPaths(source)
    prettyPrintPath(destination)
  }


  private def addStops(destinations: List[String], lineName: String) {
    for (destination <- destinations) {
      if (!(graph.keySet contains destination)) graph.put(destination, new SubwayStop(destination, lineName, isUnweighted))
    }
  }

  private def addRouteDistances(edges: List[StopConnection]) {
    for (e <- edges) {
      //Make sure the edge is between valid stops
      if((graph.keySet contains e.vertex1) && (graph.keySet contains e.vertex2)) {
        graph.get(e.vertex1).neighbours.put(graph.get(e.vertex2), e.distance)
        graph.get(e.vertex2).neighbours.put(graph.get(e.vertex1), e.distance)
      }
    }
  }

  private def buildPaths(startStopName: String) {
    // Get the Object representing the start "Stop"
    val startStop: SubwayStop = graph.get(startStopName)
    val heap = new util.TreeSet[SubwayStop]

    // Initialize our heap
    val iter = graph.values().iterator()
    while(iter.hasNext) {
      val v = iter.next()
      v.previous = if (v == startStop) startStop else null
      v.dist = if (v == startStop) 0 else Integer.MAX_VALUE
      heap.add(v)
    }

    dijkstra(heap)
  }

  // Run the Dijkstra's algorithm to calculate paths
  private def dijkstra(heap: util.TreeSet[SubwayStop]) {
    var u: SubwayStop = null
    var v: SubwayStop = null
    breakable {
      while (!heap.isEmpty) {
        u = heap.pollFirst()
        if (u.dist == Integer.MAX_VALUE) break

        import scala.collection.JavaConversions._
        for (a <- u.neighbours.entrySet) {
          v = a.getKey
          val alternateDist: Int = u.dist + a.getValue
          if (alternateDist < v.dist) {
            heap.remove(v)
            v.dist = alternateDist
            v.previous = u
            heap.add(v)
          }
        }
      }
    }
  }

  private def isValidVertex(vertexValue: String): Boolean = {
    if (!(graph.keySet contains vertexValue)) {
      return false
    }
    true
  }

  private def prettyPrintPath(endName: String): (List[String], Int) = {
    if (!(graph.keySet() contains endName)) {
      print(s"""No such stop found: "$endName"\n""")
      List[String]()
    }
    val path = new ListBuffer[String]()
    val distance = new ListBuffer[Int]()
    graph.get(endName).getPath(path, distance)
    (path.toList, if (distance.size > 0) distance.last else 0)
  }
}

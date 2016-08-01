import java.util

import scala.collection.mutable.HashMap
import scala.util.control.Breaks._

case class Edge(vertex1: String, vertex2: String, distance: Int = 1)

/**
  * Created by Vivek Singh on 7/31/16.
  */
class Graph {
  private val graph = new HashMap[String, Vertex]

  // A
  def addStops(destinations: List[String], lineName: String) {
    for (destination <- destinations) {
      if (!(graph.keySet contains destination)) graph += (destination -> new Vertex(destination, lineName))
    }
  }

  def addRouteDistances(edges: List[Edge]) {
    for (e <- edges) {
      graph(e.vertex1).neighbours.put(graph(e.vertex2) ,e.distance)
      graph(e.vertex2).neighbours.put(graph(e.vertex1) ,  e.distance)
    }
  }

  def buildPaths(startStopName: String) {
    if (!(graph.keySet contains startStopName)) {
      print(s"${startStopName} is not a valid stop!")
      return
    }
    // Get the Object representing the start "Stop"
    val startStop: Vertex = graph(startStopName)
    val heap = new util.TreeSet[Vertex]

    // Initialize our heap
    for (v <- graph.values) {
      v.previous = if (v == startStop) startStop else null
      v.dist = if (v == startStop) 0 else Integer.MAX_VALUE
      heap.add(v)
    }

    dijkstra(heap)
  }

  // Run the Dijkstra's algorithm to calculate paths
  private def dijkstra(heap: util.TreeSet[Vertex]) {
    var u: Vertex = null
    var v: Vertex = null
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

  def prettyPrintPath(endName: String) {
    if (!(graph contains endName)) {
      print(s"""No such stop found: "$endName"\n""")
      return
    }
    graph(endName).getPath()
  }
}

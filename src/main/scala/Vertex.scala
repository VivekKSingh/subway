import java.util

import scala.collection.mutable.ListBuffer

/**
  * Created by Vivek Singh on 7/31/16.
  */
class Vertex(data: String, lineName: String) extends Ordered[Vertex]{
  val name: String = data
  //Infinity
  var dist: Int = Integer.MAX_VALUE
  var previous: Vertex = null

  // Maintain a map of neighbors and their distances
  var neighbours = new util.HashMap[Vertex, Integer]

  // Maintain a list of names for train lines in ase we need in future
  val lineNames =  new ListBuffer[String]
  lineNames += lineName

  // Implement equals so we can compare two Vertex types
  override def equals(obj: scala.Any): Boolean = {
    if (this eq obj.asInstanceOf[Vertex]) return true
    if (obj == null || (getClass ne obj.getClass)) return false
    val other = obj.asInstanceOf[Vertex]
    other.name eq this.name
  }

  override def hashCode(): Int = {
    if(name != null) name.hashCode else 0
  }


  def getPath() {
    if (this == this.previous) print(this.name)
    else if (this.previous == null) print(s"${this.name} is not reachable")
    else {
      this.previous.getPath()
      print(s" -> ${this.name} (${this.dist})")
    }
  }

  override def compare(that: Vertex): Int = Integer.compare(dist, that.dist)
}

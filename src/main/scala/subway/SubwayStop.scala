package subway

import java.util

import scala.collection.mutable.ListBuffer

/**
  * Created by Vivek Singh on 7/31/16.
  */
class SubwayStop(data: String, lineName: String, isUnweighted: Boolean = false) extends Ordered[SubwayStop]{
  val name: String = data
  //Infinity
  var dist: Int = Integer.MAX_VALUE
  var previous: SubwayStop = null

  // Maintain a map of neighbors and their distances
  var neighbours = new util.HashMap[SubwayStop, Integer]

  // Maintain a list of names for train lines in ase we need in future
  val lineNames =  new ListBuffer[String]
  lineNames += lineName

  // Implement equals so we can compare two Vertex types
  override def equals(obj: scala.Any): Boolean = {
    if (this eq obj.asInstanceOf[SubwayStop]) return true
    if (obj == null || (getClass ne obj.getClass)) return false
    val other = obj.asInstanceOf[SubwayStop]
    other.name eq this.name
  }

  override def hashCode(): Int = {
    if(name != null) name.hashCode else 0
  }


  def getPath(path: ListBuffer[String], distance: ListBuffer[Int]) {
    if (this == this.previous) {
      //print(this.name)
      path += this.name
      distance += this.dist
    }
    else if (this.previous == null) print(s"${this.name} is not reachable")
    else {
      this.previous.getPath(path, distance)
      path += this.name
      distance += this.dist
      //print(s" -> ${this.name} (${this.dist})")
    }
  }

  override def compare(that: SubwayStop): Int = {
    if(isUnweighted && this.dist == 1 && that.dist == 1) return -1
    Integer.compare(dist, that.dist)
  }
}

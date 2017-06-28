package com.persist.uw.examples

import scala.annotation.tailrec
import scala.io.Source
import scala.concurrent.duration._

case class Station(name: String)

case class Connection(from: Station, to: Station, time: FiniteDuration)

case class Path(connections: Seq[Connection]) {
  val time: FiniteDuration = connections.map(_.time).fold(0 seconds) { case (t1, t2) => t1 + t2 }

  def stations: Seq[Station] = connections.map(_.from)
}

case class Info(path: Option[Path]){
def better(other:Info):Info ={
val path1 = (this.path,other.path) match{
case (None,None)=>None
case (None,p)=>p
case (p,None) =>p
case (Some(c1).Some(c2))=>
if(c1.time)<=c2.time) Some(c1) else Some(c2)}
Info(path1)
}
def add(connection:Connection):Info ={
path match{
case Some(p) => Info(Some(Path(p.connections:+connection)))
case None => Info(None)
}
}
}

class Trains {
def type State = Map[Station,Info]
private def toFiniteDuration(s:String): FiniteDuration ={
val parts = s,split(":")
val hours = parts(0).toInt.hours
val minutes = parts(1).toInt.minutes
hours+minutes
}
private val lines = Source.fromFile("data/graphs/trains.txt").get
private val connections = 
lines
.map(_.split(","))
.map {fields =>(Station(fields(0),Station(fields(1)),to
toSet
private val neighbors:Map[Station,Set[Connection] = 
connections.groupby(_.from)
}
private def start : State = stations.map{
}
def Paris(): Seq[(Station, Info)] = ???
}

object Trains {

  def main(args: Array[String]): Unit = {
    val t = new Trains
    val state = t.toParis()
    println("")
    for ((station, info) <- state) {
      val (p, t) = info.path match {
        case Some(p) =>
          val h = p.time.toHours
          val m = (p.time - h.hours).toMinutes
          val ts = f"$h%d:$m%02d"
          (p.stations.reverse.:+(Station("Paris")).map(_.name).mkString("(", ",", ")"), ts)
        case None => ("no path", "")
      }
      println(s"${station.name} to Paris $t: $p")
    }
    println("")
  }
}

package com.persist.uw.examples

import scala.annotation.tailrec
import scala.io.Source
import scala.concurrent.duration._
import scala.language.postfixOps

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
      case (Some(c1),Some(c2))=>
    if(c1.time <= c2.time) Some(c1) else Some(c2)
    }
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
  private type State = Map[Station,Info]
  private def toFiniteDuration(s:String): FiniteDuration ={
    val parts = s.split(":")
    val hours = parts(0).toInt.hours
    val minutes = parts(1).toInt.minutes
    hours+minutes
  }
  private val lines = Source.fromFile("data/graphs/trains.txt").getLines()
  private val connections =
    lines
      .map(_.split(","))
      .map {fields =>(Station(fields(0)),Station(fields(1)),toFiniteDuration(fields(2)))}
      .flatMap {case (from,to,t) =>Seq(Connection(from,to,t),Connection(to,from,t))}
      .toSet

  private val stations = connections.flatMap(c => Seq(c.from,c.to))
  private val neighbors:Map[Station,Set[Connection]] =
        connections.groupBy(_.from)

  private def start:State = stations.map {
        case s@Station(name) =>
          val p = if(name == "Paris") Some(Path(Seq.empty[Connection])) else None
          (s,Info(p))
      }.toMap

  private def best(state:State,station: Station,info:Info): Info={
        val infos = neighbors(station).map(connection => state(connection.to) .add(connection))
        infos.fold[Info](state(station)){case (a,b) =>a.better(b)}
      }
  private def next(in:State):State =
        {
          in.map {
            case (station, info) => (station, best(in, station, info))
          }

        }
  @tailrec private def step(in:State):State ={
    val in1 = next(in)
    if(in == in1) in else step(in1)
  }

  def  toParis(): Seq[(Station,Info)] = step(start).toSeq.sortBy(_._1.name)
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

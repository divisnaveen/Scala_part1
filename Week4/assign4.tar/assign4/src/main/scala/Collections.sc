import scala.collection.immutable.{BitSet, HashMap, HashSet, TreeMap, TreeSet}

val s = Seq.empty[Int]
val t1 = Seq(1,2,3)
val t2 = List(3,4,6)
val t3 = Vector(4,6,7)

val s1 = Set.empty[Int]
val s3 = Set(2,4,6)
val s2 = HashSet(5,7,9)
val s5 = TreeSet(2,3,4)
val s6 = BitSet(4,6,7)

val m = Map.empty[Int,String]
val m1 = Map(1->"foo",2->"sam")
val m2 = HashMap(2->"Dam",5 ->"dad")
val m3 = TreeMap(1->"Gam", 4 -> "fas")

val z = t1.toSet
val z1 = m3.toList

val fruits = Seq("banana","apple","Grapes")
val map = fruits.map(_.toUpperCase())
val flatmap = fruits.flatMap(_.toUpperCase())
val string = Seq("1","2","3","4")
string.map(_.toInt)

string.map(_.toInt).sum
//filter method
//The Scala List class filter method implicitly loops
// over the List/Seq you supply, tests each element of the List
// with the function you supply.
// Your function must return true or false, and filter
// returns
// the list elements where your function returns true.
val num = List(1,3,5,7,9)
num.filter(_>2)
num.filter(_ >3)
num.filter(_%3 ==0)
val strings = List("banana","apple","kiwi")
strings.filter(_.length>5)
val colors = Map("red" -> "#FF0000", "azure" -> "#F0FFFF", "peru" -> "#CD853F")
val nums: Map[Int, Int] = Map()

println( "Keys in colors : " + colors.keys )
println( "Values in colors : " + colors.values )
println( "Check if colors is empty : " + colors.isEmpty )
println( "Check if nums is empty : " + nums.isEmpty )
def toInt(s: String): Option[Int] = {
  try {
    Some(Integer.parseInt(s.trim))
  } catch {
    case e: Exception => None
  }
}
val int = toInt("1")
val strin = toInt("foo")
val x = toInt("1").getOrElse(0)
toInt("1").foreach{ i =>
  println(s"Got an int: $i")
}
toInt("1") match {
  case Some(i) => println(i)
  case None => println("That didn't work.")
}
toInt("dfg") match {
  case Some(i) => println(i)
  case None => println("That didn't work.")
}
















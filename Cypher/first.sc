import java.io._
class Point(var x:Int,var y:Int){
val xc:Int = x
val yc:Int = y
def move (dx:Int,cx:Int){
x = x + dx
y = y + cx
}
}
object demo{
  def main(args: Array[String]): Unit = {
    val as1  = new Point(10,20)
    as1.move(10,10)

  }
}
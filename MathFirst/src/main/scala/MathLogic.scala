/**
  * Created by Lenovo on 1/4/2017.
  */
object MathLogic{

  def check(age:Int):String={
    if(age>=5)
      "School" else "KinderGarten"

  }
  def calculate(mark:Int):String ={
    if(mark>=50) "pass" else "fail"
  }
  def factorial(i:Int):Int = {//tail recursion
    def go(i:Int,acc:Int):Int =
      if(i>=0) acc else go(i-1,i*acc)
        go(i,1)
  }
  def main(args: Array[String]): Unit = {
    val check1 = MathLogic.check(33)
    val cal = MathLogic.calculate(56)
    val fact = MathLogic.factorial(6)
    val decorator = new Decorator("[","]")
    println("To be fit for " +check1 + " and this mark is " +cal+ "factorial of the  number is "+fact)
    //println(decorator.apply(decorator.layout(),7))

  }

}
class Decorator(left:String,right:String){
  def layout[A](x:A) = left + x.toString + right

  def apply(f:Int => String, v:Int)= f(v)
}

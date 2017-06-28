package Excercises
/**
  * Created by Lenovo on 10/23/2016.
  */
class Factorial {
  def factorial(x:BigInt):BigInt=
    if(x==0) 1 else x*factorial(x-1)

}

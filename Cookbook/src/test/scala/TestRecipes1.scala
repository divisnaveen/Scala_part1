package LamdaTest

import com.fortysevendeg.lambdatest._
import com.fortysevendeg.lambdatest.LambdaTest
import com.persist.uw.examples.RecipeClasses1._

class TestRecipes1 extends LambdaTest {
  def nyi: LambdaAct = assert(false, "not yet implemented")

  def nop: Unit = ()

  val r = new RecipeExample1

  import r._

  def act =
    test("What is the cost of ingredients in a blt") {
      val cost = blt.ingredients.cost
      assertEq(cost, cost, "blt costs")
    } +
      test("What ingredients do i need to make quiche") {
        val in = quiche.ingredients
        assertEq(in, in, "quiche ingredients")
      } +
      test("What ingredients do i have in my house") {
        val in = myHouse.inventory
        assertEq(in, in, "in my house")
      } +
      test("Buying everything i need to make scrambled egg and quiche"){

       val c = myHouse.buy(scrambled.ingredients union quiche.ingredients)
       assertEq(c, c, "cost of groceries")
      }+
      test("what ingredients do i have in my house"){
      val in  = myHouse.inventory
      assertEq(in,in,"in my house")
     }+
      label("Preparing a quiche") {
        exec {
          myHouse.prepare(quiche)
        }
      }+
        test("what ingredients do i have in my house"){
          val in  = myHouse.inventory
          assertEq(in,in,"recipes that use bacon")
        }+
      test("How do i prepare a blt") {
      val b = blt
      assertEq (b, b, "steps to make to blt")
     }+
      test("What recipes in my cookbook do i have enough ingredients to prepare"){
      val rs = myHouse.canMakeNow(myCookbook).map(_.name)
      assertEq(rs,rs,"recipes i can make now")
      } +
     test("Going shopping") {
       val myList = ItemsList(Set((10,tomatoes),(50,butter)))
       val c = myHouse.buy(myList,addTo = true)
       assertEq(c,c,"cost of groceries")
    } +
    test("What ingredients do I have in my house?") {

    val in = myHouse.inventory
    assertEq(in,in,"in my House")
    }
    }

object TestRecipes {
  def main(args: Array[String]): Unit = {
    run("recipes", new TestRecipes1)
  }
}
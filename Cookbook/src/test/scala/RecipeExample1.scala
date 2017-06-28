package LamdaTest

import com.persist.uw.examples.RecipeClasses1._

case class RecipeExample1() {
val egg = Cold("eggs","egg",12,DollarCents(3))
val cream = Cold("cream","T",10,DollarCents(2))
val butter = Cold("butter","T",12,DollarCents(2))
val salt  = Warm("salt","pinch",100,DollarCents(1))
val pepper = Warm("pepper","pinch",100,DollarCents(1))
val bacon  = Cold("bacon","strip",30,DollarCents(4))
val swiss = Cold("swiss cheese","slice",20,DollarCents(2))
val lettuce = Cold("lettuce","leaf",30,DollarCents(2))
val tomatoes = Warm("tomatoes","tomatoes",1,DollarCents(1))
val bread = Warm("bread","slice",20,DollarCents(2))

 val scrambled = {
   val step1  = Step(1,"whisk",ingredients = Set((3,egg),(1,salt),(1,pepper),(3,cream)))
   val step2  = Step(2,"cook",ingredients = Set((1,butter)),uses = Set(1))
   val step3  = Step(3,"rest 1 minute",uses = Set(2))
   Recipe("scrambled eggs",Seq(step1,step2,step3))

 }
 val quiche ={
   val step1 = Step(1,"cook",ingredients = Set((3,bacon)))
   val step2 = Step(2,"shred",ingredients = Set((3,swiss)))
   val step3 = Step(3,"combine",ingredients = Set((3,egg),(10,cream)),uses = Set(1,2))
   val step4 = Step(4,"bake",uses = Set((3)))
   val step5 = Step(5,"cool",uses = Set((4)))
   Recipe("quiche",Seq(step1,step2,step3,step4,step5))

 }

 val blt ={
   val step1 = Step(1,"slice",ingredients =Set((1,tomatoes)))
   val step2 = Step(2,"cook",ingredients = Set((3,bacon)))
   val step3 = Step(3,"combine",ingredients = Set((2,bread),(2,lettuce)),uses = Set(1,2))
   Recipe("blt sandwich",Seq(step1,step2,step3))
 }

 val myCatalog = Catalog("All",Set(egg,cream,butter,salt,pepper,bacon,swiss,lettuce,tomatoes,bread))
 val myCookbook = Cookbook("My Recipes",Set(scrambled,quiche,blt))
 val myHouse = House("My House",Set.empty,Set.empty)
}



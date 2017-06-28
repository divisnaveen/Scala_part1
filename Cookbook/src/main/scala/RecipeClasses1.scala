package com.persist.uw.examples

object RecipeClasses1 {

  object Util {
    def add[T](l1: Set[(Int, T)], l2: Set[(Int, T)]): Set[(Int, T)] = {
      val both: Seq[(Int, T)] = l1.toSeq ++ l2.toSeq
      val m = both
        .groupBy { case (cnt, in) => in }
        .map { case (in, seq) => (in, seq.map { case (cnt, in) => cnt }.sum) }
        .toList.map { case (in, cnt) => (cnt, in) }
        .toSet
      m
    }

    def sub[T](l1: Set[(Int, T)], l2: Set[(Int, T)]): Set[(Int, T)] = {
      val sub = l2.map { case (cnt, in) => (in, cnt) }.toMap
      l1.flatMap {
        case (cnt, in) =>
          val used = sub.getOrElse(in, 0)
          if (cnt > used) {
            Seq((cnt - used, in))
          } else {
            Seq()
          }
      }
    }
  }

  def DollarCents(dollars: Int = 0, cents: Int = 0) = Cost(dollars * 100 + cents)

  case class Cost(cents: Int) {
    def *(i: Int) = Cost(i * cents)

    def /(i: Int) = Cost((cents + i - 1) / i)

    private def D = "$"

    override def toString: String = f"$D${cents / 100}.${cents % 100}%02d"
  }

  case class Catalog(name: String, ingredients: Set[Ingredient]) {
    def ingredient(name: String): Option[Ingredient] = {
      ingredients.filter(_.name == name).headOption
    }

    override def toString = {
      ingredients
        .toSeq
        .sortBy(_.name)
        .map(_.toString)
        .mkString("\n")
    }
  }

  case class Cookbook(name: String, recipes: Set[Recipe]){
    def uses(ingredients: Set[Ingredient]): Set[Recipe] = {
      recipes.filter(_.uses(ingredients))
    }

    def recipe(name: String): Option[Recipe] = {
      recipes.filter(_.name == name).headOption
    }

    override def toString = {
      val s = recipes
        .map(_.name)
        .mkString("\n")
      s"Cookbook:$name\n$s"
    }
  }

  case class House(name: String, var cabinet: Set[(Int, Warm)], var fridge: Set[(Int, Cold)]) {
    def inventory: ItemsList = {
      ItemsList(cabinet.toSet[(Int, Ingredient)]) union ItemsList(fridge.toSet[(Int, Ingredient)])
    }

    def buy(list: ItemsList, addTo: Boolean = false): Cost = {
      def roundUp(cnt: Int, pack: Int): Int = {
        return (cnt + pack) / pack * pack
      }
      val items = list.items.map {
        case (cnt, in) => (roundUp(cnt, in.pack), in)
      }
      val need = if (addTo) ItemsList(items) else ItemsList(items) minus inventory
      val needWarm = need.items.collect { case (cnt, w: Warm) => (cnt, w) }
      val needCold = need.items.collect { case (cnt, c: Cold) => (cnt, c) }
      cabinet = Util.add(cabinet, needWarm)
      fridge = Util.add(fridge, needCold)
      need.cost
    }

    def prepare(recipe: Recipe): Unit = {
      val need = recipe.ingredients
      assert(inventory.contains(need))
      val useWarm = need.items.collect { case (cnt, w: Warm) => (cnt, w) }
      val useCold = need.items.collect { case (cnt, c: Cold) => (cnt, c) }
      cabinet = Util.sub(cabinet, useWarm)
      fridge = Util.sub(fridge, useCold)
    }

    def canMakeNow(cookbook: Cookbook): Set[Recipe] = {
      def in = inventory
      cookbook.recipes.filter { case r => in.contains(r.ingredients) }
    }
  }

  trait Ingredient {
    val name: String
    val unit: String
    val pack: Int
    val price: Cost
  }

  case class Warm(name: String, unit: String, pack: Int, price: Cost) extends Ingredient

  case class Cold(name: String, unit: String, pack: Int, price: Cost) extends Ingredient

  case class Recipe(name: String, steps: Seq[Step]) {
    def ingredients: ItemsList = {
      steps
        .map {case step => ItemsList(step.ingredients)}
          .fold(ItemsList(Set.empty)) {case (s1, s2) => s1 union s2 }
        }

      def uses(ingredients: Set[Ingredient]): Boolean = {
        ingredients
          .forall { ingredient =>
            steps
              .exists(_.uses(ingredient))
          }
      }

      override def toString = {
        val s = steps
          .map(_.toString)
          .mkString("\n")
        s"Recipe:$name\n$s"
      }
      }


    case class Step(index: Int, action: String, ingredients: Set[(Int, Ingredient)] = Set.empty, uses: Set[Int] = Set.empty) {

    def uses(ingredient: Ingredient): Boolean = {

      ingredients.map { case (i, in) => in }.contains(ingredient)
    }

    override def toString = {
      val i = ingredients
        .map { case (cnt, in) => s"$action $cnt ${in.name}" }
      val u = uses
        .map(i => s"Step $i")
      val iu = (i union u)
        .mkString(",")
      s"$index.$iu"
    }
  }


  case class ItemsList(items: Set[(Int, Ingredient)]) {
    def   cost: Cost = {
      items.map {
        case (cnt, in) => in.price * cnt / in.pack
      }.fold(Cost(0)) {
        case (c1, c2) => Cost(c1.cents + c2.cents)
      }
    }

    def union(list: ItemsList): ItemsList = {
      ItemsList(Util.add(this.items, list.items))
    }

    def minus(list: ItemsList): ItemsList = {
      ItemsList(Util.sub(this.items, list.items))
    }

    def contains(list: ItemsList): Boolean = {
      val has = this.items.map { case (cnt, in) => (in, cnt) }.toMap
      list.items.forall {
        case (cnt, in) => cnt <= has.getOrElse(in, 0)
      }
    }

    override def toString = {
      items
        .map {
          case (cnt, in) => s"$cnt ${in.unit} ${in.name}"
        }
        .mkString("\n")

    }
  }

}






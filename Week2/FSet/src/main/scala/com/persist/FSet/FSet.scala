package com.persist.uw.examples

import scala.annotation.tailrec

// make it similar to FQueue
// tests must pass
// use only immutable data
// if using recursion make sure its tail recursive
// use case classes and objects, not Scala collection types
// correctness not performance is the goal
object FSet {

    private case class NonEmptyFSet(i: Int, next: FSet) extends FSet

    private case object EmptyFSet extends FSet
    def apply(): FSet = EmptyFSet

  }

  sealed trait FSet {

    import FSet._

    //def insert(i: Int): FSet = NonEmptyFSet(i, this)


    def contains(i: Int): Boolean = {

      @tailrec def apply1(items: FSet): Boolean = {
        items match {
          case NonEmptyFSet(i1, next) =>
            if (i == i1) true else apply1(next)
          case EmptyFSet => false
        }
      }
      apply1(this)
    }

    //if i1 contains in FSet  true else next

    def add(i: Int): FSet = {
      if (contains(i)) this else NonEmptyFSet(i, this)
    }

    //checks i already exists else add this to NonEmptySet

    def delete(i: Int): FSet = {
      @tailrec def delete1(items: FSet, accum: FSet): FSet = {
        items match {
          case NonEmptyFSet(i1, next) =>
            if (i == i1)
              delete1(next, accum)
            else delete1(next, accum.add(i1))
          case EmptyFSet => accum
        }
      }
      delete1(this, FSet())
    }


    def union(set1: FSet): FSet = {
      @tailrec def union1(items: FSet, accum: FSet): FSet = {
        items match {
          case NonEmptyFSet(i1, next) => union1(next, accum.add(i1))
          case EmptyFSet => accum
        }
      }
      union1(this, set1)
    }

    def intersect(set1: FSet): FSet = {
      @tailrec def intersect1(items: FSet, accum: FSet): FSet ={
        items match {
        case NonEmptyFSet(i1, next) =>
          if(set1.contains(i1)) intersect1(next, accum.add(i1)) else intersect1(next,accum)

        case EmptyFSet => accum
      }
      }
      intersect1(this, EmptyFSet)
    }

    //def intersect(s: FSet, p: Int => Boolean): Set = {(i: Int) => s(i) && p(i)}


    def subset(set1: FSet): Boolean = {
      @tailrec def subset1(items: FSet): Boolean = {
        items match {
          case NonEmptyFSet(i1, next) =>
            if (set1.contains(i1))
              subset1(next)
            else false

          case EmptyFSet => true
        }
      }
      subset1(this)

    }


    def equals(set1: FSet): Boolean = set1.subset(set1) && set1.subset(this)


    def size: Int = {
      @tailrec def size1(items: FSet, accum: Int = 0): Int = {
        items match {
          case NonEmptyFSet(i, next) => size1(next, accum + 1)
          case EmptyFSet => accum
        }
      }
      size1(this)

    }
  }







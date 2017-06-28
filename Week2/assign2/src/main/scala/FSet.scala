package com.persist.uw.examples

import scala.annotation.tailrec


// make it similar to FQueue
// tests must pass
// use only immutable data
// if using recursion make sure its tail recursive
// use case classes and objects, not Scala collection types
// correctness not performance is the goal
object FSet {
  def main(args: Array[String]) {


  private case class NonEmptyFSet(set: Int) extends FSet

  private case object EmptyFSet extends FSet
  def init():FSet = EmptyFSet

   }

sealed trait FSet {

  import FSet._

  def insert(i: Int): FSet = {
    val s= 3
    val newNum = s+i
    insert(newNum)
     }

  def contains(i: Int): Boolean = {

   def contain(i:Int,newNum:Int):Int = i match {
     case (i,s) => contain(insert(i))
     case _ => false
   }
    contains(i)
    }

    def add(i: Int): FSet = {
      i match {
        case i=>insert()



    }}

    def delete(i: Int): FSet = {
      i match {
        case (i,next) =>NonEmptyFSet(i,this).delete(i)
        case _ => EmptyFSet
      }
    }

      def union(set1: FSet): Unit = {
        def union(set1:FSet,set2:FSet): Unit = set1 match {
          case (set1, set2) => NonEmptyFSet(set1,this).union(set2)
          case _ => EmptyFSet
        }

     }

      def intersect(set1: FSet): Unit = {
        def intersect(set1: FSet, set2: FSet): Unit = set1 match {
          case (set1,set2) =>NonEmptyFSet(set1,this).intersect(set2)
          case _ => EmptyFSet

        }
      }


        def subset(set1: FSet): Boolean = {
          set1 match {
            case NonEmptyFSet(i, next) => Some(next, set1)
          }
          subset(set1)
        }


        def equals(set1: FSet): Boolean = {
          def equality(obj:FSet):Boolean= {
            set1 match {
              case that: NonEmptyFSet => that.set1.equals(this.obj)
              case _ => false
            }
          }

        }




          def size: Int = {
            @tailrec
            def size1(items: FSet, accum: Int = 0): Int = {
              items match {
                case NonEmptyFSet(i, next) => size1(next, accum + 1)
                case EmptyFSet => accum
              }

            }
            size1(this)
          }
        }
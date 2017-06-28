package com.persist.uw.examples

import scala.annotation.tailrec


object FQueue {
  private case class NonEmptyFQueue(i: Int, next: FQueue) extends FQueue

  private case object EmptyFQueue extends FQueue

  def apply():FQueue = EmptyFQueue
}

sealed trait FQueue {
  import FQueue._

  def insert(i: Int): FQueue = NonEmptyFQueue(i, this)

  private def reverse(items: FQueue): FQueue = {
    @tailrec def reverse1(items: FQueue, accum: FQueue = EmptyFQueue): FQueue = {
      items match {
        case NonEmptyFQueue(i, next) => reverse1(next, NonEmptyFQueue(i, accum))
        case EmptyFQueue => accum
      }
    }
    reverse1(items)
  }

  def last: Option[Int] = {
    reverse(this) match {
      case NonEmptyFQueue(i, next) => Some(i)
      case EmptyFQueue => None
    }

  }

  def remove(): FQueue = {
    reverse(this) match {
      case NonEmptyFQueue(i, next) => reverse(next)
      case EmptyFQueue => EmptyFQueue
    }
  }

  def size: Int = {
    @tailrec def size1(items: FQueue, accum: Int = 0): Int = {
      items match {
        case NonEmptyFQueue(i, next) => size1(next, accum + 1)
        case EmptyFQueue => accum
      }
    }
    size1(this)
  }

}

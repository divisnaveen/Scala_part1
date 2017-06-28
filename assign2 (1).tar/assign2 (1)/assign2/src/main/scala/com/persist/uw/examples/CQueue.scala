package com.persist.uw.examples

import scala.annotation.tailrec

object CQueue {

  case class Cell(i: Int, var prev: Option[Cell], var next: Option[Cell])

  case class Ends(first: Cell, last: Cell, cnt: Int = 0)

  def apply() = new CQueue()
}

class CQueue() {

  import CQueue._

  private var root: Option[Ends] = None

  def copy(): CQueue = {
    val q = CQueue()
    @tailrec def copy1(c: Cell): Unit = {
      q.insert(c.i)
      c.prev match {
        case Some(c1) => copy1(c1)
        case None =>
      }
    }
    root match {
      case Some(e) => copy1(e.last)
      case None =>
    }
    q
  }

  override def equals(other: Any): Boolean = {
    @tailrec def equals2(cell1: Cell, cell2: Cell): Boolean = {
      cell1.i == cell2.i && {
        (cell1.next, cell2.next) match {
          case (None, None) => true
          case (Some(cell1a), Some(cell2a)) => equals2(cell1a, cell2a)
          case _ => false
        }
      }
    }
    def equals1(c1: CQueue): Boolean = {
      (root, c1.root) match {
        case (None, None) => true
        case (Some(e1), Some(e2)) => equals2(e1.first, e2.first)
        case _ => false
      }
      true
    }
    other match {
      case c1: CQueue => equals1(c1)
      case None => false
    }
  }

  def last: Option[Int] = {
    root match {
      case Some(c) => Some(c.last.i)
      case None => None
    }
  }


  def insert(i: Int): Unit = {
    root match {
      case Some(e@Ends(first, last, cnt)) =>
        val cell = Cell(i, None, Some(first))
        first.prev = Some(cell)
        root = Some(Ends(cell, last, cnt + 1))
      case None =>
        val cell = Cell(i, None, None)
        root = Some(Ends(cell, cell, 1))
    }
  }

  def remove(): Option[Int] = {
    root match {
      case Some(Ends(first, last, cnt)) =>
        root = if (first == last) None
        else {
          val newLast = last.prev.get
          newLast.next = None
          Some(Ends(first, newLast, cnt - 1))
        }
        Some(last.i)
      case None => None
    }
  }

  def size: Int = {
    root match {
      case Some(Ends(first, last, cnt)) => cnt
      case None => 0
    }
  }

}

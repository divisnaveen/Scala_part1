package com.persist.uw.examples

case class SQueue() {

  private case class Cell(i: Int, var prev: Option[Cell], var next: Option[Cell])

  private case class Ends(first: Cell, Last: Cell, cnt: Int = 0)

  private var root: Option[Ends] = None

  def insert(i: Int): Unit = {
    root.synchronized {
      root match {
        case Some(e@Ends(first, last, cnt)) =>
          val cell = Cell(i, None, Some(first))
          first.prev = Some(cell)
          root = Some(Ends(cell, last, cnt + 1))
        case None =>
          val cell = Cell(i, None, None)
          root.synchronized {
            root = Some(Ends(cell, cell, 1))
          }
      }
    }
  }

  def remove(): Option[Int] = {
    root.synchronized {
      root match {
        case Some(Ends(first, last, cnt)) =>
          val newRoot = if (first == last) None
          else {
            val newLast = last.prev.get
            newLast.next = None
            Some(Ends(first, newLast, cnt - 1))
          }
          root = newRoot
          Some(last.i)
        case None => None
      }
    }
  }

  def size: Int = {
    val r = root.synchronized(root)
    r match {
      case Some(Ends(first, last, cnt)) => cnt
      case None => 0
    }
  }

}

package com.persist.uw.examples

object FCQueue {

  private case class CQ(c: CQueue) extends FCQueue

  def apply(): FCQueue = CQ(CQueue())
}

sealed trait FCQueue {

  import FCQueue._

  val c: CQueue

  def insert(i: Int): FCQueue = {
    val c1 = c.copy()
    c1.insert(i)
    CQ(c1)
  }

  def remove(): FCQueue = {
    val c1 = c.copy()
    c1.remove()
    CQ(c1)
  }

  def last: Option[Int] = {
    c.last
  }

  def size: Int = {
    c.size
  }

}

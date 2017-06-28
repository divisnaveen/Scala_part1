package com.persist.uw.examples

case class CFQueue() {

  var q = FQueue()

  def insert(i: Int): Unit = {
    q = q.insert(i)
  }

  def remove(): Option[Int] = {
    val result = q.last
    q = q.remove()
    result
  }

  def size: Int = q.size

}

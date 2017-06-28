package com.persist.uw.examples

import org.specs2._

class TestFCQueue extends mutable.Specification {
  sequential

  val emptyQ = FCQueue()
  var q = emptyQ

  "init" >> empty
  "insert" >> {
    q = q.insert(10).insert(20).insert(30)
    q.size mustEqual 3
  }
  "remove" >> {
    val t1 = q.last mustEqual Some(10)
    q = q.remove
    val t2 = q.last mustEqual Some(20)
    q = q.remove
    val t3 = q.last mustEqual Some(30)
    q = q.remove
    t1 and t2 and t3
  }
  "final" >> empty

  def empty = {
    (q.size mustEqual 0) and
    (q.last mustEqual None) and
    (q.remove() mustEqual emptyQ)
  }

}

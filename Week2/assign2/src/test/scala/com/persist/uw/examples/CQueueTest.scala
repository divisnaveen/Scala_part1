package com.persist.uw.examples

import org.specs2._

class TestCQueue extends mutable.Specification {
  sequential

  val q = CQueue()

  "init" >> empty
  "insert" >> {
    q.insert(10)
    q.insert(20)
    q.insert(30)
    q.size mustEqual 3
  }
  "remove" >> {
    (q.remove() mustEqual Some(10)) and
      (q.remove() mustEqual Some(20)) and
      (q.remove() mustEqual Some(30))
  }
  "final" >> empty

  def empty = (q.size mustEqual 0) and (q.remove mustEqual None)

}

package com.persist.uw.examples

import org.specs2._

class TestFSet extends mutable.Specification {

  "init" >> {
    val s = FSet()
    s.size mustEqual 0
  }

  "insert" >> {
    val s = FSet().add(1).add(2).add(1)
    (s.size mustEqual 2) and
      (s.contains(1) mustEqual true) and
      (s.contains(2) mustEqual true) and
      (s.contains(0) mustEqual false)
  }

  "delete" >> {
    val s = FSet().add(1).add(2).delete(1)
    val s0 = s.delete(2)
    (s.size mustEqual 1) and
      (s.contains(1) mustEqual false) and
      (s.contains(2) mustEqual true) and
      (s0.size mustEqual 0)
  }

  "equals" >> {
    val s1 = FSet().add(1).add(2)
    val s2 = FSet().add(2).add(1).add(2)
    val s3 = FSet().add(1).add(2).add(3)
    (s1.equals(s2) mustEqual true) and
      (s1.equals(s3) mustEqual false)
  }

  "subset" >> {
    val s1 = FSet().add(1).add(2)
    val s2 = s1.add(3)
    (s1.subset(s1) mustEqual true) and
      (s1.subset(s2) mustEqual true) and
      (s2.subset(s1) mustEqual false)
  }

  "intersect" >> {
    val s1 = FSet().add(1).add(2).add(3)
    val s2 = FSet().add(4).add(3).add(2)
    val s = FSet().add(2).add(3)
    s1.intersect(s2).equals(s) mustEqual true
  }

  "union" >> {
    val s1 = FSet().add(1).add(2).add(3)
    val s2 = FSet().add(4).add(3).add(2)
    val s = FSet().add(1).add(2).add(3).add(4)
    s1.union(s2).equals(s) mustEqual true
  }

}

package com.persist.uw.examples

import org.specs2.mutable.Specification


class TestTree1 extends Specification {
  val int1 = Int1(1)
  val intm1 = Int1(-1)

  val myadd = Add1(int1,intm1)
  val t1 = If1(myadd, Name1("a"), Name1("b"))
  val t2 = Add1(Multiply1(Int1(2), Add1(Int1(3), Name1("c"))), Int1(4))

  "size" >> {
    (t1.size shouldEqual 6) and
      (t2.size shouldEqual 7)
  }

  "depth" >> {
    (t1.depth shouldEqual 3) and
      (t2.depth shouldEqual 4)
  }


}

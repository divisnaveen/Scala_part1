package com.persist.uw.examples

import org.specs2.mutable.Specification

/**
  * Created by Lenovo on 11/5/2016.
  */
class TestTree2 extends Specification {
  val t1 = If2(Add2(Int2(1), Int2(-1)), Name2("a"), Name2("b"))
  val t2 = Add2(Multiply2(Int2(2), Add2(Int2(3), Name2("c"))), Int2(4))

  "size" >> {
    (t1.size shouldEqual 6) and
      (t2.size shouldEqual 7)
  }

  "depth" >> {
    (t1.depth shouldEqual 3) and
      (t2.depth shouldEqual 4)
  }


}

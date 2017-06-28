package com.persist.uw.examples

import org.specs2._

class TestEaster extends mutable.Specification{

  "2016" >> (Easter.easter(2016) mustEqual Day(3, 27))
  "1761" >> (Easter.easter(1761) mustEqual Day(3, 22))
  "2038" >> (Easter.easter(2038) mustEqual Day(4, 25))

  }


package com.persist.uw.examples
// replace ???
// don't use any mutable data
// don't use var
// don't use return
// tests must pass

case class Day(month:Int,day:Int)

  object Easter {

    def easter(year: Int): Day = {
      val c = year / 100
      val n = year - 19 * ( year / 19 )
      val k = ( c - 17 ) / 25
      val i1 = c - c / 4 - ( c - k ) / 3 + 19 * n + 15
      val i2 = i1 - 30 * ( i1 / 30 )
      val i3 = i2 - ( i2 / 28 ) * ( 1 - ( i2 / 28 ) * ( 29 / ( i2 + 1 ) )
        * ( ( 21 - n ) / 11 ) )
      val j1 = year + year / 4 + i3 + 2 - c + c / 4
      val j2 = j1 - 7 * ( j1 / 7 )
      val l = i3 - j2
      val month = 3 + ( l + 40 ) / 44
      val day = l + 28 - 31 * ( month / 4 )
      Day(month,day)

    }

    def main(args: Array[String]): Unit = {
    }
  }





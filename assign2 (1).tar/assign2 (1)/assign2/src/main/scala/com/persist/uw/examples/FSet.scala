package com.persist.uw.examples

import scala.annotation.tailrec

// make it similar to FQueue
// tests must pass
// use only immutable data
// if using recursion make sure its tail recursive
// use case classes and objects, not Scala collection types
// correctness not performance is the goal

object FSet {

  ???
    
  def apply(): FSet = ???
}

sealed trait FSet {

  import FSet._

  def contains(i: Int): Boolean = ???

  def add(i: Int): FSet = ???

  def delete(i: Int): FSet = ???

  def union(set1: FSet): FSet = ???

  def intersect(set1: FSet): FSet = ???

  def subset(set1: FSet): Boolean = ???

  def equals(set1: FSet): Boolean = ???

  def size: Int = ???
}

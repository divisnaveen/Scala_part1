package com.persist.uw.cake

import scala.collection.Map

class PMap[K,V] extends scala.collection.Ma
p[K,V] {

  override def get(key: K) : Option[V] = ???

  override def iterator : Iterator[(K,V)] = ???

  override def +[B1 >: V](kv:(K,B1)):Map[K,B1] = ???

  override  def -(key:K):Map[K,V] = ???



}

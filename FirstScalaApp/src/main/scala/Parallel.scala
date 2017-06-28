package com.persist.uw.examples

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

// Introduce parallelism using futures
// Not required (but desirable) try to maximize parallelism
// Use only immutable data
// Do not use blocking (don't call Await)
// Don't worry about the cost of concatenating lists
// If you use recursion, to keep it simpler you are not required to
//    make every function tail recursive
// Hint: use flatMap rather than map to avoid Future[Future[...]]
//
// REQUIRED: parMap, parFilter and parFold (partial credit if you only do some)
// OPTIONAL: parSortBy and/or parGroupBy for extra credit

object Parallel {

  implicit class ParallelList[A](flist: Future[List[A]]) {

    private def split[B](l: List[B]): (List[B], List[B]) = {
      l.splitAt(l.size / 2)
    }

    // One possible approach (you are free to pick a different approach)
    //   Run f on each element in its own Future and use Future.sequence to combine
    def parMap[B](f: A => B): Future[List[B]] = {
      flist flatMap {
        list =>
          val fs = list.map(i => Future(f(i)))
          Future.sequence(fs)
      }
    }

    // One possible approach (you are free to pick a different approach)
    //   Split list into two parts, in parallel recursively filter each half
    //      and then combine the results
    def parFilter(f: A => Boolean): Future[List[A]] = {
      flist flatMap {
        list =>
          if (list.size == 0) {
            Future(list)
          } else if (list.size == 1) {
            if (f(list.head)) {
              Future(list)
            } else {
              Future(list.tail)
            }
          } else {
            val (l1, l2) = split(list)
            val f1 = Future(l1).parFilter(f)
            val f2 = Future(l2).parFilter(f)
            (f1 zip f2) map {
              case (v1, v2) => v1 ++ v2
            }
          }
      }
    }


    // One possible approach (you are free to pick a different approach)
    //   Split list into two parts, in parallel recursively fold each half
    //      and then combine the results
    def parFold(init: A)(f: (A, A) => A): Future[A] = {
      flist flatMap {
        case list =>
          if (list.size == 0) {
            Future(init)
          }
          else if (list.size == 1) {
            Future(list.head)
          }
          else {
            val (l1, l2) = split(list)
            val f1 = Future(l1).parFold(init)(f)
            val f2 = Future(l2).parFold(init)(f)
            (f1 zip f2) map {
              case (v1, v2) => f(v1, v2)
            }
          }
      }

    }

    // One possible approach (you are free to pick a different approach)
    //   Split the list, recursively sort each half and then merge the result
    def parSortBy[B](f: A => B)(implicit order: Ordering[B]): Future[List[A]] = {
      import order._
      def merge(l1: List[A], l2: List[A]): List[A] = {
        (l1.headOption, l2.headOption) match {
          case (None, _) => l2
          case (_, None) => l1
          case (Some(h1), Some(h2)) if f(h1) <= f(h2) => h1 +: merge(l1.tail, l2)
          case (Some(h1), Some(h2)) if f(h2) <= f(h1) => h2 +: merge(l1, l2.tail)
        }
      }
      flist flatMap {
        case list =>
          if (list.size <= 1) {
            Future(list)
          } else {
            val (l1, l2) = split(list)
            val s1 = Future(l1).parSortBy(f)
            val s2 = Future(l2).parSortBy(f)
            (s1 zip s2) map {
              case (l1, l2) => merge(l1, l2)
            }
          }
      }
    }
          // One possible approach (you are free to pick a different approach)
          //   Use parMap to produce pair (f(i),i)
          //   Pick an f(i) and split list of pairs into left(<) middle(=) right(>) parts
          //       using parFilter
          //   Use recursion to group the left and right parts in parallel
          def parGroupBy[B](f: A => B)(implicit order: Ordering[B]): Future[Map[B, List[A]]] = {
            import order._
            def pgp(flist: Future[List[(B, A)]]): Future[Map[B, List[A]]] = {
              flist flatMap {
                case list =>
                  if (list.size <= 1) {
                    Future(list.map(p => (p._1, List(p._2))).toMap)
                  } else {
                    val (k, v) = list.head
                    val f = Future(list)
                    val left = f.parFilter(_._1 < k)
                    val middle = f.parFilter(_._1 == k).parMap(i => i._2)
                    val right = f.parFilter(_._1 > k)
                    val middlePair = middle.map(l => (k, l))
                    val fleft = pgp(left)
                    val fright = pgp(right)
                    val ends = (fleft zip fright) map {
                      case (l, r) => l ++ r

                    }
                    (middlePair zip ends) map {
                      case (m, e) => e + m

                    }
                  }
              }
            }
            val list1 = parMap(i => (f(i), i))
            pgp(list1)


          }
      }
    }

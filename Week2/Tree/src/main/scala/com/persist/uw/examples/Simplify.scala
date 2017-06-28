package com.persist.uw.examples

// Created by Lenovo on 11/5/2016.
object Simplify {

    // use pattern matching
    // no vars or mutable data
    // pass all tests

   private def simplify1(t: Tree3): Tree3 = {
      t match {
        case Add3(a,b) => Add3(simplify(a),simplify(b))
        case Subtract3(a, b) => Subtract3(simplify(a),simplify(b))
        case Multiply3(a, b) => Multiply3(simplify(a),simplify(b))
        case Divide3(a, b) => Divide3(simplify(a),simplify(b))
        case If3(test, t, f) => If3(simplify(test),simplify(t),simplify(f))
        case _ => t
      }
    }
   private def simplify2(t:Tree3):Tree3 = {
      t match{
    case Add3(Int3(a),Int3(b))=> Int3(a + b)
    case Subtract3(Int3(a), Int3(b))=> Int3(a-b)
    case Multiply3(Int3(a),Int3(b))=> Int3(a*b)
    case Divide3(Int3(a), Int3(b))=> Int3(a/b)

    case Add3(a,Int3(0)) => a
    case Add3(Int3(0),a) => a
    case Subtract3(a,Int3(0)) => a
    case Multiply3(a,Int3(1)) => a
    case Multiply3(Int3(1),a) => a
    case Multiply3(a,Int3(0)) => Int3(0)
    case Multiply3(Int3(0),a) => Int3(0)
    case Divide3(a,Int3(1)) => a
    case Subtract3(a,b) if a==b => Int3(0)
    case Divide3(a,b) if a==b => Int3(1)
    case If3(Int3(i),a,b) if i > 0 => a
    case If3(Int3(_),a,b) => b
    case Add3(Multiply3(a,b),Multiply3(c,d)) if a==c => simplify2(Multiply3(a,simplify2(Add3(b,d))))
    case Subtract3(Multiply3(a,b),Multiply3(c,d)) if a == c => simplify2(Multiply3(a,simplify2(Subtract3(b,d))))
    case _ => t


      }
}
def simplify(t:Tree3):Tree3 = simplify2(simplify1(t))

}

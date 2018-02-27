package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = for{
    i <- arbitrary[Int]
    h <- oneOf(const(empty), genHeap)
  }yield insert(i, h)


  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("min 1") = forAll{ (a: Int) =>
    val h = insert(a, empty)
    findMin(h) == a
  }
  property("min 2") = forAll{(a: Int, b: Int) =>
    val h = insert(a, insert(b, empty))
    findMin(h) == a.min(b)
  }

  property("min 3") = forAll{ (a: Int, b: Int, c: Int) =>
    val h = insert(a, insert(b, insert(c, empty)))
    findMin(h) == a.min(b.min(c))
  }

  property("empty") = forAll{ (a: Int) =>
    isEmpty(empty)
  }

  property("not empty") = forAll{ (a: Int) =>
    isEmpty(insert(a, empty))
  }

  property("delete min 1") = forAll{(a: Int) =>
    val h = insert(a, empty)
    deleteMin(h) == empty
  }

  property("delete min 2") = forAll{(a: Int, b:Int) =>
    val h = insert(a, insert(b, empty))
    deleteMin(h) == insert(a max b, empty)
  }

  property("meldMin") = forAll{ (h1: H, h2: H) =>
    val one = findMin(h1)
    val two = findMin(h2)
    val h3 = meld(h1, h2)
    val three = findMin(h3)
    three == one || three == two
  }

  property("remMin") = forAll{(h: H) =>
    def res(h: H, lst: List[Int]): List[Int] = {
      if(isEmpty(h)) lst
      else findMin(h) :: res(deleteMin(h), lst)
    } 
    val t = res(h, Nil)
    t == t.sorted
  }

  property("meldMinMove") = forAll{(h1: H, h2: H) =>
    def res(h: H, lst: List[Int]): List[Int] = {
      if(isEmpty(h)) lst
      else findMin(h) :: res(deleteMin(h), lst)
    }

    val meldOne = meld(h1, h2)
    val minOne = findMin(h1)
    val meldTwo = meld(deleteMin(h1), insert(minOne, h2))
    res(meldOne, Nil) == res(meldTwo, Nil)
  }

}

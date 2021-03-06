package reductions

import scala.annotation._
import org.scalameter._
import common._


object ParallelParenthesesBalancingRunner {

  @volatile var seqResult = false

  @volatile var parResult = false

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 40,
    Key.exec.maxWarmupRuns -> 80,
    Key.exec.benchRuns -> 120,
    Key.verbose -> true
  ) withWarmer (new Warmer.Default)

  def main(args: Array[String]): Unit = {
    val length = 100000000
    val chars = new Array[Char](length)
    val threshold = 10000
    val seqtime = standardConfig measure {
      seqResult = ParallelParenthesesBalancing.balance(chars)
    }
    println(s"sequential result = $seqResult")
    println(s"sequential balancing time: $seqtime ms")

    val fjtime = standardConfig measure {
      parResult = ParallelParenthesesBalancing.parBalance(chars, threshold)
    }
    println(s"parallel result = $parResult")
    println(s"parallel balancing time: $fjtime ms")
    println(s"speedup: ${seqtime / fjtime}")
  }
}

object ParallelParenthesesBalancing {

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
    */
  def balance(chars: Array[Char]): Boolean = {
    @tailrec
    def solve(chars: Array[Char], count: Int): Boolean = {
      if (chars.isEmpty || count < 0) count == 0
      else if (chars.head == '(') solve(chars.tail, count + 1)
      else if (chars.head == ')') solve(chars.tail, count - 1)
      else solve(chars.tail, count)
    }

    solve(chars, 0)
  }

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
    */
  def parBalance(chars: Array[Char], threshold: Int): Boolean = {

    def traverse(idx: Int, until: Int, arg1: Int, arg2: Int): (Int, Int) = {
      if (idx >= until) (arg1, arg2)
      else chars(idx) match {
        case '(' => traverse(idx + 1, until, arg1 + 1, arg2 - 1)
        case ')' => traverse(idx + 1, until, arg1 - 1, arg2 + 1)
        case _ => traverse(idx + 1, until, arg1, arg2)
      }

    }


    def reduce(from: Int, until: Int): (Int, Int) = {
      if (until - from <= threshold)
        traverse(from, until, 0, 0)
      else {
        val mid = from + (until - from) / 2;
        val ((lo, lc), (ro, rc)) = parallel(reduce(from, mid), reduce(mid, until))

        (lo + ro, lc + rc)
      }
    }

    reduce(0, chars.length) == (0, 0)
  }
}

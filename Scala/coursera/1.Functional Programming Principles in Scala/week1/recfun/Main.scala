//Programming Assignment: Recursion

package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  def pascal(c: Int, r: Int): Int = {
        if(c == 0 || r == c)    1
        else    pascal(c, r-1) + pascal(c-1, r-1)
    }

    def balance(chars: List[Char]): Boolean = {
       def solve(chars:List[Char], stack:Int):Boolean = {
            if(chars.isEmpty) return stack == 0
            else if(chars(0) == '(' ) solve(chars.tail, stack+1)
            else if(chars(0) == ')' ) solve(chars.tail, stack-1)
            else solve(chars.tail, stack)
        }

       if(chars.isEmpty)
           return true
       return solve(chars, 0)
    }

    def countChange(money: Int, coins: List[Int]): Int = {
        def solve(money:Int, coins:List[Int]) :Int = {
            if(money < 0 || coins.isEmpty) 0
            else if(money == 0) 1
            else solve(money, coins.tail) + solve(money - coins.head, coins)
         }
        solve(money, coins)
    }
  }

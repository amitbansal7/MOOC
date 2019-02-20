package calculator

object Polynomial {
  def computeDelta(a: Signal[Double], b: Signal[Double],
      c: Signal[Double]): Signal[Double] = {
        Signal(b()*b() - 4*a()*c())
  }

  def computeSolutions(a: Signal[Double], b: Signal[Double],
      c: Signal[Double], delta: Signal[Double]): Signal[Set[Double]] = {
        Signal{
          var res = Set[Double]()
          val rootD = computeDelta(a, b, c)

          if(rootD() > 0){
            res += (-b() + math.sqrt(rootD()))/(2*a())
            res += (-b() - math.sqrt(rootD()))/(2*a())
          }else if(rootD() == 0){
            res += (-b()/(2*a()))
          }

          res
        }
  }
}

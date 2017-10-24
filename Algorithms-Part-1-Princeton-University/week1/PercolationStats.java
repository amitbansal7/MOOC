import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {
   private double[] results;
   private double mean;
   private double sd;
   private double sum;
   private int T;

   public PercolationStats(int n, int trials) {  // perform trials independent experiments on an n-by-n grid
      if (n <= 0 || trials <= 0)
         throw new IllegalArgumentException();
      this.T = trials;
      results = new double[trials];
      for (int i = 0; i < trials; i++) {
         Percolation pr = new Percolation(n);
         int count = 0;

         while (!pr.percolates()) {
            int a = StdRandom.uniform(1, n + 1);
            int b = StdRandom.uniform(1, n + 1);
            if (!pr.isOpen(a, b)) {
               count++;
               pr.open(a, b);
            }
         }
         results[i] = (double)count / (n * n);
      }
   }
   public double mean() {                        // sample mean of percolation threshold
      return StdStats.mean(results);
   }
   public double stddev() {                      // sample standard deviation of percolation threshold
      return StdStats.stddev(results);
   }
   public double confidenceLo() {                // low  endpoint of 95% confidence interval
      return mean() - ((1.96 * stddev()) / Math.sqrt(T));
   }
   public double confidenceHi() {                // high endpoint of 95% confidence interval
      return mean() + ((1.96 * stddev()) / Math.sqrt(T));
   }

   public static void main(String[] args) {      // test client (described below)
      PercolationStats p = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
      System.out.println("mean\t\t\t= " + p.mean());
      System.out.println("stddev\t\t\t= " + p.stddev());
      System.out.println("95% confidence interval = [" + p.confidenceLo() + ", " + p.confidenceHi() + "]");
   }
}

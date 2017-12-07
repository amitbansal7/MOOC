import java.lang.Math;
import java.util.List;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;


public class SAP {

   // constructor takes a digraph (not necessarily a DAG)
   private final Digraph graph;
   private int V;

   public SAP(Digraph G) {
      graph = G;
   }

   // length of shortest ancestral path between v and w; -1 if no such path
   public int length(int v, int w) {
      int length = -1;

      BreadthFirstDirectedPaths one = new BreadthFirstDirectedPaths(graph, v);
      BreadthFirstDirectedPaths two = new BreadthFirstDirectedPaths(graph, w);

      for(int i=0;i<graph.V();i++){
         if(one.hasPathTo(i) && two.hasPathTo(i)){
            int t = one.distTo(i) + two.distTo(i);
            if(t < length || length == -1)
               length = t;
         }
      }
      return length;
   }

   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w) {
      int ancestor = -1;
      int length = -1;

      BreadthFirstDirectedPaths one = new BreadthFirstDirectedPaths(graph, v);
      BreadthFirstDirectedPaths two = new BreadthFirstDirectedPaths(graph, w);

      for(int i=0;i<graph.V();i++){
         if(one.hasPathTo(i) && two.hasPathTo(i)){
            int t = one.distTo(i) + two.distTo(i);
            if(t < length || length == -1)
            {
               length = t;
               ancestor = i;
            }
         }
      }
      return ancestor;

   }

   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
   public int length(Iterable<Integer> v, Iterable<Integer> w) {
      int res = Integer.MAX_VALUE;
      for (int a : v) {
         for (int b : w)
            res = Math.min(length(a, b), res);
      }
      if (res == Integer.MAX_VALUE)
         return -1;
      return res;
   }

   // a common ancestor that participates in shortest ancestral path; -1 if no such path
   public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
      int res = Integer.MAX_VALUE;
      int ans = -1;
      for (int a : v) {
         for (int b : w) {
            int t = Math.min(length(a, b), res);
            if (res > t) {
               res = t;
               ans = ancestor(a, b);
            }
         }
      }
      return ans;
   }

   // do unit testing of this class
   public static void main(String[] args) {
      In in = new In(args[0]);
      Digraph G = new Digraph(in);
      SAP sap = new SAP(G);
      while (!StdIn.isEmpty()) {
         int v = StdIn.readInt();
         int w = StdIn.readInt();
         int length   = sap.length(v, w);
         StdOut.printf("ancestor ");
         int ancestor = sap.ancestor(v, w);
         StdOut.printf("length = %d ancestor = %d\n", length,  ancestor);
      }
   }
}

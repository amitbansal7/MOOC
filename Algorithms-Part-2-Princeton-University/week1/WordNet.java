import java.lang.Math;
import java.util.List;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.DirectedCycle;

public class WordNet {

   private ST<String, Integer> nounTable;
   private ST<Integer, String> synset;
   private Digraph adj;
   private SAP sap;
   // constructor takes the name of the two input files
   public WordNet(String synsets, String hypernyms) {
      this.nounTable = new ST<String, Integer>();
      this.synset = new ST<Integer, String>();

      In inp = new In(synsets);
      int n = 0;
      while (inp.hasNextLine()) {
         n++;

         String line = inp.readLine();

         String[] argsSynsets = line.split(",");
         int id = Integer.parseInt(argsSynsets[0]);

         synset.put(id, argsSynsets[1]);
         String nouns[] = argsSynsets[1].split(" ");
         for (String no : nouns) {
            nounTable.put(no, id);
         }
      }

      this.adj = new Digraph(n);
      inp = new In(hypernyms);

      while (inp.hasNextLine()) {
         String line = inp.readLine();
         String[] all = line.split(",");

         int u = Integer.parseInt(all[0]);

         for (int i = 1; i < all.length; i++) {
            Integer v = Integer.parseInt(all[i]);

            if (v < 0 || v > n)
               throw new java.lang.IllegalArgumentException();

            adj.addEdge(u, v);
         }
      }
      if (!isRootedDag(adj))
         throw new java.lang.IllegalArgumentException();

      sap = new SAP(adj);
   }
   private boolean isRootedDag(Digraph adj) {
      DirectedCycle cycleFinder = new DirectedCycle(adj);
      if (cycleFinder.hasCycle())
         return false;
      int num = 0;
      for (int i = 0; i < adj.V(); i++) {
         int outEdge = 0;
         for(int o:adj.adj(i))
            outEdge++;

         if (outEdge == 0)
            num++;

         if (num > 1)
            return false;
      }
      return true;
   }
   // returns all WordNet nouns
   public Iterable<String> nouns() {
      return this.nounTable;
   }

   // is the word a WordNet noun?
   public boolean isNoun(String word) {
      return nounTable.contains(word);
   }

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB) {
      if (!isNoun(nounA) || !isNoun(nounB))
         throw new java.lang.IllegalArgumentException();

      return sap.length(nounTable.get(nounA), nounTable.get(nounB));
   }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB) {
      if (!isNoun(nounA) || !isNoun(nounB))
         throw new java.lang.IllegalArgumentException();

      return synset.get(sap.ancestor(nounTable.get(nounA), nounTable.get(nounB)));
   }

   // do unit testing of this class
   public static void main(String[] args) {

   }
}

import java.lang.Math;
import java.util.List;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
	private WordNet wordnet;
	public Outcast(WordNet wordnet) {
		this.wordnet = wordnet;
	}
	public String outcast(String[] nouns) {
		int max = 0;
		String res = "";

		for (String noun : nouns) {
			int current = 0;
			for (String tnoun : nouns) {
				current += wordnet.distance(noun, tnoun);
			}

			if (max < current) {
				max = current;
				res = noun;
			}
		}
		return res;
	}

}

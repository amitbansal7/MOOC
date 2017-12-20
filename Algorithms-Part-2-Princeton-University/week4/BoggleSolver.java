import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

/*
	Correctness:  13/13 tests passed
	Memory:       3/3 tests passed
	Timing:       9/9 tests passed

	Score :- 100/100
*/
public class BoggleSolver {

	private class Node {
		public boolean isLeaf = false;
		public String word = null;
		public Node[] next = new Node[26];
	};

	private int rows, cols;
	private Node root;

	public BoggleSolver(String[] dictionary) {
		root = new Node();
		for (String word : dictionary) {
			if (word.length() < 3)
				continue;
			Node crawl = root;
			for (char c : word.toCharArray()) {
				if (crawl.next[c - 'A'] == null) {
					crawl.next[c - 'A'] = new Node();
				}
				crawl = crawl.next[c - 'A'];
			}
			crawl.word = word;
			crawl.isLeaf = true;
		}
	}
	private boolean find(String word) {
		Node crawl = root;
		for (char c : word.toCharArray()) {
			if (crawl.next[c - 'A'] == null)
				return false;

			crawl = crawl.next[c - 'A'];
		}
		return crawl.isLeaf;
	}
	private void solve(char[][] board, int i, int j, Node root, HashSet<String> res) {
		if (i < 0 ||
		        i >= rows ||
		        j < 0 ||
		        j >= cols ||
		        board[i][j] == '$' ||
		        root.next[board[i][j] - 'A'] == null) {
			return;
		}

		char c = board[i][j];
		root = root.next[c - 'A'];
		if (c == 'Q') {
			if (root.next['U' - 'A'] != null) {
				root = root.next['U' - 'A'];
			} else
				return;
		}

		if (root.word != null) {
			res.add(root.word);
		}
		board[i][j] = '$';

		solve(board, i + 1, j, root, res);
		solve(board, i - 1, j, root, res);
		solve(board, i, j + 1, root, res);
		solve(board, i, j - 1, root, res);
		solve(board, i + 1, j + 1, root, res);
		solve(board, i + 1, j - 1, root, res);
		solve(board, i - 1, j + 1, root, res);
		solve(board, i - 1, j - 1, root, res);

		board[i][j] = c;
	}

	public Iterable<String> getAllValidWords(BoggleBoard board) {

		char[][] brd = new char[board.rows()][board.cols()];
		for (int i = 0; i < board.rows(); i++) {
			for (int j = 0; j < board.cols(); j++) {
				brd[i][j] = board.getLetter(i, j);
			}
		}
		rows = brd.length;
		cols = brd[0].length;
		HashSet<String> res = new HashSet<>();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				solve(brd, i, j, root, res);
			}
		}
		List<String> res2 = new ArrayList<>(res);
		return res2;
	}

	public int scoreOf(String word) {
		int l = word.length();
		if (l <= 2)
			return 0;
		if (find(word) == false)
			return 0;
		else if (l <= 4)
			return 1;
		else if (l == 5)
			return 2;
		else if (l == 6)
			return 3;
		else if (l == 7)
			return 5;

		return 11;
	}

	public static void main(String[] args) {
		In in = new In(args[0]);
		String[] dictionary = in.readAllStrings();
		BoggleSolver solver = new BoggleSolver(dictionary);
		BoggleBoard board = new BoggleBoard(args[1]);
		int score = 0;
		for (String word : solver.getAllValidWords(board)) {
			StdOut.println(word);
			score += solver.scoreOf(word);
		}
		StdOut.println("Score = " + score);
	}
}

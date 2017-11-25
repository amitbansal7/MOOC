import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;


public class Solver {
	private Node result = null;

	private class Node {
		public Board board;
		public int moves;
		public Node prev;
	}

	public Solver(Board initial) {
		if (initial == null)
			throw new java.lang.IllegalArgumentException();

		MinPQ<Node> pq = new MinPQ<Node>(new BoardComp());


		Node init = new Node();
		init.board = initial;
		init.moves = 0;
		init.prev = null;

		pq.insert(init);

		while (!pq.isEmpty()) {
			Node crnt = pq.delMin();
			int mov = crnt.moves;

			if(mov > 100)
				break;
			if (crnt.board.isGoal()) {
				result = crnt;
				break;
			}

			for (Board brd : crnt.board.neighbors()) {
				Node brdNode = new Node();
				brdNode.board = brd;
				brdNode.moves = mov + 1;
				brdNode.prev = crnt;

				if (crnt.prev != null && brd.equals(crnt.prev.board))
					continue;

				pq.insert(brdNode);
			}
		}
	}
	private class BoardComp implements Comparator<Node> {
		public int compare(Node a, Node b) {
			int mv1 = a.moves + a.board.manhattan();
			int mv2 = b.moves + b.board.manhattan();

			if (mv1 < mv2)
				return -1;
			else if (mv1 == mv2)
				return 0;
			return 1;
		}
	}
	public boolean isSolvable() {
		if (result != null)
			return true;
		return false;

	}
	public int moves() {
		if (!this.isSolvable())
			return -1;
		return result.moves;
	}
	public Iterable<Board> solution() {
		if (!isSolvable())
			return null;

		Stack<Board> res = new Stack<Board>();
		Node crnt = result;

		while (crnt != null) {
			res.push(crnt.board);
			crnt = crnt.prev;
		}
		Stack<Board> mainres = new Stack<Board>();
		while (!res.empty()) {
			Board b = res.pop();
			mainres.push(b);
		}
		return mainres;
	}
	public static void main(String[] args) {

		// create initial board from file
		In in = new In(args[0]);
		int n = in.readInt();
		int[][] blocks = new int[n][n];

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				blocks[i][j] = in.readInt();

		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable()) {

			StdOut.println("No solution possible");
		} else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}
}

import java.util.*;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
	final private int[][] board;
	final private int n;

	public Board(int[][] blocks) {
		n = blocks.length;
		board = new int[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				board[i][j] = blocks[i][j];
			}
		}
	}

	public int dimension() {
		return n;
	}
	public int hamming() {
		int oop = 0;
		if (board[n - 1][n - 1] != 0)
			oop++;

		int c = 1;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				if (c == n*n)
					break;

				if (board[i][j] != c && board[i][j] != 0)
					oop++;

				c++;
			}
		return oop;
	}
	public int manhattan() {
		int manhattanDis = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				final int b = board[i][j];

				if (b != 0) {
					int bx = (b - 1) / n;
					int by = (b - 1) - (bx * n);
					manhattanDis += (Math.abs(bx - i) + Math.abs(by - j));
				}

			}
		}
		return manhattanDis;
	}
	public boolean isGoal() {
		int c = 1;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
			{
				if(i == n-1 && j == n-1)
					continue;
				if (board[i][j] != c++)
					return false;
			}
		return true;
	}
	public Board twin() {
		Board brd = new Board(board);

		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
				if(board[i][j]!=0 && board[i][j+1]!=0)
				{
					int t = brd.board[i][j];
					brd.board[i][j] = brd.board[i][j+1];
					brd.board[i][j+1] = t;
					return brd;
				}
		return brd;
	}
	public boolean equals(Object y) {
		if (y == null)
			return false;
		if (this == y)
			return true;
		if (getClass() != y.getClass())
			return false;

		Board obj = (Board)y;
		if (this.n != obj.dimension())
			return false;

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if (this.board[i][j] != obj.board[i][j])
					return false;
		return true;
	}
	public Iterable<Board> neighbors() {
		Stack<Board> res = new Stack<Board>();
		int i0 = -1;
		int j0 = -1;
		int [][]tempB = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				tempB[i][j] = board[i][j];
				if (board[i][j] == 0) {
					i0 = i;
					j0 = j;
				}
			}
		}

		if (i0 > 0) {
			int t = tempB[i0 - 1][j0];
			tempB[i0 - 1][j0] = 0;
			tempB[i0][j0] = t;
			res.push(new Board(tempB));
			tempB[i0][j0] = 0;
			tempB[i0 - 1][j0] = t;
		}

		if (j0 > 0) {
			int t = tempB[i0][j0 - 1];
			tempB[i0][j0 - 1] = 0;
			tempB[i0][j0] = t;
			res.push(new Board(tempB));
			tempB[i0][j0] = 0;
			tempB[i0][j0 - 1] = t;
		}

		if (i0 + 1  < n) {
			int t = tempB[i0 + 1][j0];
			tempB[i0 + 1][j0] = 0;
			tempB[i0][j0] = t;
			res.push(new Board(tempB));
			tempB[i0][j0] = 0;
			tempB[i0 + 1][j0] = t;
		}

		if (j0 + 1 < n) {
			int t = tempB[i0][j0 + 1];
			tempB[i0][j0 + 1] = 0;
			tempB[i0][j0] = t;
			res.push(new Board(tempB));
			tempB[i0][j0] = 0;
			tempB[i0][j0 + 1] = t;
		}
		return res;
	}
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(n + "\n");

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				str.append(String.format("%2d ", board[i][j]));
			}
			str.append("\n");
		}
		return str.toString();
	}
}

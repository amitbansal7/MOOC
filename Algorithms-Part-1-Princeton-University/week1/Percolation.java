import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private int N;
	private WeightedQuickUnionUF uf;
	private int src;
	private int sink;
	private boolean[][] open;
	private int numberOfOpensites;

	public Percolation(int n) { // create n-by-n grid, with all sites blocked
		if (n <= 0)
			throw new java.lang.IllegalArgumentException();

		this.N = n;
		src = index(n, n) + 1;
		sink = index(n, n) + 2;;
		uf = new WeightedQuickUnionUF(n * n + 2);
		open = new boolean[n + 1][n + 1];
		numberOfOpensites = 0;
	}
	private int index(int i, int j) {
		return (N * (i - 1) + j - 1);
	}
	public	void open(int row, int col) {  // open site (row, col) if it is not open already
		if (row < 1 || row > N || col < 1 || col > N)
			throw new java.lang.IllegalArgumentException();

		if (open[row][col])
			return;

		open[row][col] = true;
		numberOfOpensites++;

		if (row == 1)
			uf.union(index(row, col), src);

		if (row == N)
			uf.union(index(row, col), sink);

		if (row > 1 && open[row - 1][col])
			uf.union(index(row, col), index(row - 1, col));

		if (row < N && open[row + 1][col])
			uf.union(index(row, col), index(row + 1, col));

		if (col > 1 && open[row][col - 1])
			uf.union(index(row, col), index(row, col - 1));

		if (col < N && open[row][col + 1])
			uf.union(index(row, col), index(row, col + 1));
	}
	public boolean isOpen(int row, int col) { // is site (row, col) open?
		if (row < 1 || row > N || col < 1 || col > N)
			throw new java.lang.IllegalArgumentException();

		return open[row][col] == true;

	}
	public boolean isFull(int row, int col) { // is site (row, col) full?
		if (row < 1 || col < 1 || row > N || col > N)
			throw new java.lang.IllegalArgumentException();

		return uf.connected(src, index(row, col));
	}
	public	int numberOfOpenSites() {     // number of open sites
		return numberOfOpensites;
	}
	public boolean percolates() {            // does the system percolate?
		return uf.connected(src, sink);
	}
}

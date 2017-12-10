import java.awt.Color;
import edu.princeton.cs.algs4.Picture;

/*
	Score : 82/100
	Correctness:  21/31 tests passed
	Memory:       6/6 tests passed
	Timing:       18/17 tests passed
*/

public class SeamCarver {
	private Picture picture;
	private final double maxEng = 255 * 255 + 255 * 255 + 255 * 255;
	private double[][] edgeTo;
	private double[][] distTo;
	private double[][] energies;


	public SeamCarver(Picture picture) {
		if(picture == null)
			throw new java.lang.IllegalArgumentException();

		this.picture = picture;
	}
	public Picture picture() {
		return new Picture(this.picture);
	}
	public int width() {
		return picture.width();
	}
	public int height() {
		return picture.height();
	}
	public double energy(int x, int y)  {
		if (x < 0 || x >= width()  || y < 0 || y >= height() )
			throw new java.lang.IllegalArgumentException();

		if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1)
			return 1000.0;

		return Math.sqrt(sqg(picture.get(x - 1, y), picture.get(x + 1, y)) +
		                 sqg(picture.get(x, y - 1), picture.get(x, y + 1)));
	}

	private double sqg(Color one, Color two) {
		double r = Math.abs(one.getRed() - two.getRed());
		double g = Math.abs(one.getGreen() - two.getGreen());
		double b = Math.abs(one.getBlue() - two.getBlue());

		return r * r + g * g + b * b;
	}

	public int[] findHorizontalSeam() {
		distTo = new double[height()][width()];
		edgeTo = new double[height()][width()];
		energies = new double[height()][width()];

		for (int i = 0; i < height() ; i++) {
			for (int j = 0; j < width() ; j++) {

				if (j == 0) {
					distTo[i][j] = 0;
				} else
					distTo[i][j] = Double.POSITIVE_INFINITY;

				edgeTo[i][j] = -1;
				energies[i][j] = energy(j, i);
			}
		}

		for (int i = 0; i < width()  - 1; i++) {
			for (int j = 0; j < height() ; j++) {

				if (j - 1 >= 0) {
					relax(j, i, j - 1, i + 1);
				}

				relax(j, i, j, i + 1);

				if (j + 1 < height() ) {
					relax(j, i, j + 1, i + 1);
				}
			}
		}

		double min = Double.POSITIVE_INFINITY;
		int end = 0;

		for (int i = 0; i < height() ; i++) {
			if (distTo[i][width()  - 1] < min) {
				min = distTo[i][width()  - 1];
				end = i;
			}
		}

		int[] res = new int[width()];
		int len = width()  - 1;
		int t = end;

		while (len > 0) {
			res[len] = t;

			if (t - 1 >= 0 && edgeTo[t - 1][len - 1] == t) {
				t = t - 1;
			} else if (t + 1 < height() && edgeTo[t + 1][len - 1] == t)
				t = t + 1;

			len--;
		}
		res[len] = t;

		// while (len >= 0) {
		// 	res[len--] = t;

		// 	if (t - 1 >= 0 && edgeTo[t - 1][len - 1] == t) {
		// 		t = t - 1;
		// 	} else if (t + 1 < height()  && edgeTo[t + 1][len - 1] == t) {
		// 		t = t + 1;
		// 	}
		// }
		return res;
	}
	private void relax(int i, int j, int x, int y) {
		if (distTo[x][y] > distTo[i][j] + energies[x][y]) {
			distTo[x][y] = distTo[i][j] + energies[x][y];
			edgeTo[x][y] = j;
		}
	}
	public int[] findVerticalSeam() {
		distTo = new double[width()][height()];
		edgeTo = new double[width()][height()];
		energies = new double[width()][height()];

		for (int i = 0; i < width() ; i++) {
			for (int j = 0; j < height() ; j++) {

				if (i == 0) {
					distTo[i][j] = 0;
				} else
					distTo[i][j] = Double.POSITIVE_INFINITY;

				edgeTo[i][j] = -1;
				energies[i][j] = energy(i, j);
			}
		}

		for (int i = 0; i < height()  - 1; i++) {
			for (int j = 0; j < width() ; j++) {

				if (j - 1 >= 0) {
					relax(j, i, j - 1, i + 1);
				}

				relax(j, i, j, i + 1);

				if (j + 1 < width() ) {
					relax(j, i, j + 1, i + 1);
				}
			}
		}

		double min = Double.POSITIVE_INFINITY;
		int end = 0;

		for (int i = 0; i < width() ; i++) {
			if (distTo[i][height()  - 1] < min) {
				min = distTo[i][height()  - 1];
				end = i;
			}
		}

		int[] res = new int[height() ];
		int h = height()  - 1;
		int t = end;

		while (h > 0) {
			res[h] = t;

			if (t - 1 >= 0 && edgeTo[t - 1][h - 1] == t)
				t = t - 1;
			else if (t + 1 < width() && edgeTo[t + 1][h - 1] == t)
				t = t + 1;
			h--;
		}
		res[h] = t;

		// while (h >= 0) {
		// 	res[h--] = t;

		// 	if (t - 1 >= 0 && edgeTo[h - 1][t - 1] == t) {
		// 		t = t - 1;
		// 	} else if (t + 1 < width() && edgeTo[h - 1][t + 1] == t) {
		// 		t = t + 1;
		// 	}
		// }
		return res;
	}
	public void removeHorizontalSeam(int[] seam) {
		if (height()  <= 1)
			throw new java.lang.IllegalArgumentException();

		if (seam.length != width())
			throw new java.lang.IllegalArgumentException();

		Picture res = new Picture(width(), height() - 1);
		int prev = seam[0];

		for (int i = 0; i < width(); i++) {

			if (seam[i] < 0 || seam[i] >=  height())
				throw new java.lang.IllegalArgumentException();

			if (seam[i] < prev - 1 || seam[i] > prev + 1)
				throw new java.lang.IllegalArgumentException();

			prev = seam[i];
			for (int j = 0; j < height() - 1; j++) {
				if (j < prev) {
					res.set(i, j, picture.get(i, j));
				} else {
					res.set(i, j, picture.get(i, j + 1));
				}
			}
		}
		picture = res;
		edgeTo = null;
		distTo = null;
		energies = null;

	}
	public void removeVerticalSeam(int[] seam) {
		if (width()  <= 1)
			throw new java.lang.IllegalArgumentException();

		if (seam.length != height() )
			throw new java.lang.IllegalArgumentException();

		Picture res = new Picture(width() - 1, height());
		int prev = seam[0];

		for (int i = 0; i < height(); i++) {
			if (seam[i] < 0 || seam[i] >=  width())
				throw new java.lang.IllegalArgumentException();

			if (seam[i] < prev - 1 || seam[i] > prev + 1)
				throw new java.lang.IllegalArgumentException();

			prev = seam[i];
			for (int j = 0; j < width() - 1; j++) {
				if (j < prev) {
					res.set(j, i, picture.get(j, i));
				} else {
					res.set(j, i, picture.get(j + 1, i));
				}
			}
		}
		picture = res;
		edgeTo = null;
		distTo = null;
		energies = null;
	}
}

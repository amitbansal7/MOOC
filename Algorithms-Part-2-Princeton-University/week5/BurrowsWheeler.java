import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
	// apply Burrows-Wheeler transform, reading from standard input and writing to standard output
	public static void transform() {
		String str = BinaryStdIn.readString();

		CircularSuffixArray csa = new CircularSuffixArray(str);

		for (int i = 0; i < str.length(); i++) {
			if (csa.index(i) == 0) {
				BinaryStdOut.write(i);
				break;
			}
		}

		for (int i = 0; i < str.length(); i++) {
			int idx = csa.index(i);
			if (idx == 0) {
				BinaryStdOut.write(str.charAt(str.length() - 1));
			} else {
				BinaryStdOut.write(str.charAt(idx - 1));
			}
		}
		BinaryStdOut.flush();
	}

	// apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
	public static void inverseTransform() {
		int first = BinaryStdIn.readInt();
		String str = BinaryStdIn.readString();

		int R = 256;
		int N = str.length();

		int[] count = new int[R + 1];
		char[] temp = new char[N];
		int[] next = new int[N];

		for (int i = 0; i < N; i++) {
			count[str.charAt(i) + 1]++;
		}
		for (int i = 0; i < R; i++) {
			count[i + 1] += count[i];
		}
		for (int i = 0; i < N; i++) {
			int t = count[str.charAt(i)]++;
			temp[t] = str.charAt(i);
			next[t] = i;
		}

		for (int i = 0; i < N; i++) {
			BinaryStdOut.write(temp[first]);
			first = next[first];
		}
		BinaryStdOut.flush();
	}

	// if args[0] is '-', apply Burrows-Wheeler transform
	// if args[0] is '+', apply Burrows-Wheeler inverse transform
	public static void main(String[] args) {

		if (args[0].equals("-"))
			BurrowsWheeler.transform();
		else
			BurrowsWheeler.inverseTransform();
	}
}

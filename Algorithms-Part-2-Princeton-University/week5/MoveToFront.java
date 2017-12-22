import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

/*
	Correctness:  43/64 tests passed
	Memory:       10/10 tests passed
	Timing:       157/159 tests passed
*/
public class MoveToFront {
	// apply move-to-front encoding, reading from standard input and writing to standard output
	public static void encode() {
		// System.out.println("encode");
		int R = 256;
		char[] chars = new char[R];

		for (int i = 0; i < R; i++) {
			chars[i] = (char)i;
		}

		while (!BinaryStdIn.isEmpty()) {
			char c = BinaryStdIn.readChar();
			int idx = 0;
			char front = chars[0];
			if (front == c) {
				BinaryStdOut.write((char)idx);
			} else {
				for (int i = 0; i < R; i++) {
					char next = chars[i + 1];
					if (next == c) {
						chars[i + 1] = front;
						chars[0] = next;
						idx = i + 1;
						break;
					} else {
						char t = front;
						front = chars[i + 1];
						chars[i + 1] = t;
					}
				}
			}
			BinaryStdOut.write((char)idx);
		}
		BinaryStdOut.flush();
	}

	// apply move-to-front decoding, reading from standard input and writing to standard output
	public static void decode() {
		// System.out.println("decode");
		int R = 256;
		char[] chars = new char[R];

		for (int i = 0; i < R; i++) {
			chars[i] = (char)i;
		}

		while (!BinaryStdIn.isEmpty()) {
			int idx = (int)BinaryStdIn.readChar();
			BinaryStdOut.write(chars[idx]);
			int t = chars[0];

			for (int i = 0; i < idx; i++) {
				chars[i] = chars[i + 1];
			}
			chars[idx] = chars[0];
		}
		BinaryStdOut.flush();
	}

	// if args[0] is '-', apply move-to-front encoding
	// if args[0] is '+', apply move-to-front decoding
	public static void main(String[] args) {

		if (args[0].equals("-")) {
			MoveToFront.encode();
		} else if (args[0].equals("+")) {
			MoveToFront.decode();
		} else {
			throw new java.lang.IllegalArgumentException();
		}
	}
}

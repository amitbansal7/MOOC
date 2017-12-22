import java.util.Arrays;

public class CircularSuffixArray {

	private final String str;
	private class Node implements Comparable<Node> {
		// private String str;
		private int idx;

		public Node(int idx) {
			this.idx = idx;
		}

		public int compareTo(Node that) {
			String one = str;
			int len = str.length();
			for (int i = 0; i < len; i++) {
				char a  = one.charAt((i + this.idx) % len);
				char b = one.charAt((i + that.idx) % len);

				if (a > b)
					return 1;

				else if (a < b)
					return -1;

				else
					continue;
			}

			return 0;
		}

		public String getStr() {
			return str;
		}

		public int getIdx() {
			return idx;
		}
	}

	private int[] indexes;
	private int len;
	public CircularSuffixArray(String s) {
		if (s == null)
			throw new java.lang.IllegalArgumentException();
		this.str = s;
		this.len = s.length();
		indexes = new int[len];
		Node[] subStrings = new Node[len];

		for (int i = 0; i < len; i++) {
			subStrings[i] = new Node(i);
		}

		Arrays.sort(subStrings);

		for (int i = 0; i < len; i++) {
			indexes[i] = subStrings[i].getIdx();
			// System.out.println(i + " " + indexes[i] + " " + subStrings[i].getStr());
		}
	}
	public int length() {
		return len;
	}
	public int index(int i) {
		if (i >= len || i < 0)
			throw new java.lang.IllegalArgumentException();
		return indexes[i];
	}
	public static void main(String[] args ) {
		CircularSuffixArray n = new CircularSuffixArray(args[0]);
	}
}

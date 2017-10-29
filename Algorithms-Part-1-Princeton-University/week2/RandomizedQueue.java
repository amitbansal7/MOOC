import java.util.*;
import java.lang.*;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private int N;
	private Item[] arr;

	// construct an empty randomized queue
	public RandomizedQueue() {
		N = 0;
		arr = (Item []) new Object[1];
	}
	// is the randomized queue empty?
	public boolean isEmpty() {
		return N == 0;
	}
	// return the number of items on the randomized queue
	public int size() {
		return N;
	}

	private void resize(int newsize) {
		Item[] t = (Item []) new Object[newsize];
		for (int i = 0; i < N; i++)
			t[i] = arr[i];

		arr = t;
	}

	// add the item
	public void enqueue(Item item) {
		if (item == null)
			throw new NullPointerException();

		if (arr.length == N)
			resize(2 * arr.length);

		arr[N++] = item;
	}
	// remove and return a random item
	public Item dequeue() {

		if (isEmpty())
			throw new NoSuchElementException();
		int random = (int)(StdRandom.uniform() * N);

		Item t = arr[random];
		arr[random] = arr[N - 1];
		arr[N - 1] = null;
		N--;
		if (N > 0 && arr.length > 4 * N)
			resize(arr.length / 2);

		return t;
	}
	// return a random item (but do not remove it)
	public Item sample() {

		if (isEmpty())
			throw new NoSuchElementException();

		int random = (int)(StdRandom.uniform() * N);
		return arr[random];
	}
	// return an independent iterator over items in random order

	private class RandomIterator implements Iterator<Item> {
		private int idx;

		public RandomIterator() {
			idx = N - 1;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public boolean hasNext() {
			return idx >= 0;
		}

		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();

			int random = (int)(StdRandom.uniform() * idx);
			Item ret = arr[random];

			arr[random] = arr[idx];
			arr[idx] = ret;
			idx--;
			return ret;
		}
	}

	public Iterator<Item> iterator() {
		return new RandomIterator();
	}

	// unit testing (optional)
	public static void main(String[] args) {
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		rq.enqueue("1");
		rq.enqueue("2");
		rq.enqueue("3");
		rq.enqueue("4");
		rq.enqueue("5");

		for (String a : rq)
			System.out.println(a);

		System.out.println();

		System.out.println("deq" + rq.dequeue());
		System.out.println("deq" + rq.dequeue());
		System.out.println("deq" + rq.dequeue());
		System.out.println("deq" + rq.dequeue());
		System.out.println("deq" + rq.dequeue());


		for (String a : rq)
			System.out.println(a);

	}
}

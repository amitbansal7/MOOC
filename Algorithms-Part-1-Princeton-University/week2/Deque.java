import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int n;


    private class Node {
        private final Item data;
        private Node prev;
        private Node next;

        public Node(Item x) {
            data = x;
            prev = null;
            next = null;
        }
    }

    public Deque() {
        first = null;
        last = null;
        n = 0;
    }
    public boolean isEmpty() {
        return n == 0;
    }
    public int size() {
        return n;
    }
    public void addFirst(Item item) {
        if (item == null)
            throw new java.lang.IllegalArgumentException();

        n++;
        if (first == null) {
            first = new Node(item);
            last = first;
        } else {
            Node newnode = new Node(item);
            newnode.next = first;
            first.prev = newnode;
            first = newnode;
        }
    }
    public void addLast(Item item) {
        if (item == null)
            throw new java.lang.IllegalArgumentException();

        n++;
        Node newnode = new Node(item);
        if (first == null) {
            first = last = newnode;
        } else {
            last.next = newnode;
            newnode.prev = last;
            last = newnode;
        }
    }
    public Item removeFirst() {
        if (n == 0)
            throw new NoSuchElementException();
        n--;

        Node temp = first;
        first = first.next;
        if (isEmpty())
            last = null;
        else
            first.prev = null;
        return temp.data;
    }
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();

        n--;
        Node temp = last;
        last = last.prev;

        if (isEmpty()) {
            first = null;
        } else
            last.next = null;

        return temp.data;
    }

    private class DequeIterator implements Iterator<Item> {

        private Node crawl = first;

        public boolean hasNext() {
            return crawl != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (crawl == null)
                throw new NoSuchElementException();

            Item ret = crawl.data;
            crawl = crawl.next;
            return ret;
        }
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    // public static void main(String[] args) {

    // n
}

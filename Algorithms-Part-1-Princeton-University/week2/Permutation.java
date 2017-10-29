import edu.princeton.cs.algs4.StdIn;
public class Permutation {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> que = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            que.enqueue(item);
        }

        for (String item : que) {
            if (k == 0)
                break;

            System.out.println(item);
            k--;
        }
    }
}

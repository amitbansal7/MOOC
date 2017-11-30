import java.util.Iterator;
import java.util.TreeSet;
import java.util.Queue;
import java.util.LinkedList;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;



public class PointSET {

	private TreeSet<Point2D> TSet;

	public	PointSET()	{
		TSet = new TreeSet<Point2D>();
	}
	public	boolean isEmpty()	{
		return TSet.isEmpty();
	}
	public	int size() {
		return TSet.size();
	}
	public	void insert(Point2D p) {
		if (p == null)
			throw new java.lang.IllegalArgumentException();

		if (!contains(p))
			TSet.add(p);
	}
	public	boolean contains(Point2D p) {
		if (p == null)
			throw new java.lang.IllegalArgumentException();

		return TSet.contains(p);

	}
	public	void draw() {
		for (Point2D P : TSet)
			P.draw();
	}
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null)
			throw new java.lang.IllegalArgumentException();

		Queue<Point2D> Q = new LinkedList<Point2D>();

		for (Point2D P : TSet)
			if (rect.contains(P))
				Q.add(P);

		return Q;

	}
	public	Point2D nearest(Point2D p) {
		if (p == null)
			throw new java.lang.IllegalArgumentException();

		if (isEmpty())
			return null;

		double MinDis = Double.MAX_VALUE;
		Point2D res = null;
		for (Point2D P : TSet) {
			double dis = P.distanceSquaredTo(p);

			if (dis < MinDis) {
				res = P;
				MinDis = dis;
			}
		}
		return res;
	}

	public static void main(String[] args) {
	}
}

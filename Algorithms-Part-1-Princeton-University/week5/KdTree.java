import java.util.Iterator;
import java.util.TreeSet;
import java.util.Queue;
import java.util.LinkedList;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;


public class KdTree {

    private int size;
    private Node root;

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D pt) {
            p = pt;
            rect = new RectHV(0.0, 0.0, 1.0, 1.0);
            lb = null;
            rt = null;
        }
    }

    public KdTree() {                             // construct an empty set of points
        size = 0;
        root = null;
    }

    public boolean isEmpty() {                    // is the set empty
        return size == 0;
    }

    public int size() {                       // number of points in the set
        return size;
    }

    public void insert(Point2D p) {            // add the point to the set (if it is not already in the set)
        if (p == null)
            throw new java.lang.IllegalArgumentException();

        size++;
        Node newNode = new Node(p);

        int lvl = 0;

        if (root == null) {
            root = newNode;
            return;
        }

        Node t = root;

        while (t != null) {
            if (t.p.equals(p)) {
                size--;
                return;
            }

            if (lvl % 2 == 0) {
                if (p.x() < t.p.x()) {
                    if (t.lb == null) {
                        t.lb = newNode;
                        newNode.rect = new RectHV(t.rect.xmin(), t.rect.ymin(), t.p.x(), t.rect.ymax());
                        return;
                    } else {
                        t = t.lb;
                    }
                } else {
                    if (t.rt == null) {
                        t.rt = newNode;
                        newNode.rect = new RectHV(t.p.x(), t.rect.ymin(), t.rect.xmax(), t.rect.ymax());
                        return;
                    } else {
                        t = t.rt;
                    }
                }
            } else {
                if (p.y() < t.p.y()) {
                    if (t.lb == null) {
                        t.lb = newNode;
                        newNode.rect = new RectHV(t.rect.xmin(), t.rect.ymin(), t.rect.xmax(), t.p.y());
                        return;
                    } else {
                        t = t.lb;
                    }
                } else {
                    if (t.rt == null) {
                        t.rt = newNode;
                        newNode.rect = new RectHV(t.rect.xmin(), t.p.y(), t.rect.xmax(), t.rect.ymax());
                        return;
                    } else {
                        t = t.rt;
                    }

                }
            }

            lvl++;
        }
    }

    private boolean containHelper(Node root, Point2D p, int h) {

        if (root == null)
            return false;

        if (root.p.equals(p))
            return true;

        if (h % 2 == 0) {
            if (root.p.x() < p.x())
                return containHelper(root.rt, p, h + 1);
            else
                return containHelper(root.lb, p, h + 1);
        } else {
            if (root.p.y() < p.y())
                return containHelper(root.rt, p, h + 1);
            else
                return containHelper(root.lb, p, h + 1);
        }

    }

    public boolean contains(Point2D p) {          // does the set contain point p?
        if (p == null)
            throw new java.lang.IllegalArgumentException();

        Node t = root;
        return containHelper(t, p, 0);
    }

    public void draw() {                       // draw all points to standard draw
        root.rect.draw();
        drawhelper(root, 0);
    }

    private void drawhelper(Node root, int level) {
        if (root == null)
            return;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        root.p.draw();

        if (level % 2 == 0) {
            StdDraw.setPenColor(StdDraw.RED);

            double x = root.p.x();
            double ymin = root.rect.ymin();
            double ymax = root.rect.ymax();

            Point2D p1 = new Point2D(x, ymin);
            Point2D p2 = new Point2D(x, ymax);

            p1.drawTo(p2);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);

            double y = root.p.y();
            double xmin = root.rect.ymin();
            double xmax = root.rect.ymax();

            Point2D p1 = new Point2D(xmin, y);
            Point2D p2 = new Point2D(xmax, y);

            p1.drawTo(p2);
        }
        drawhelper(root.lb, level + 1);
        drawhelper(root.rt, level + 1);
    }

    public Iterable<Point2D> range(RectHV rect) {           // all points that are inside the rectangle
        if (root == null)
            throw new java.lang.IllegalArgumentException();

        Queue<Point2D> Q = new LinkedList<Point2D>();

        rangehelper(root, Q, rect);
        return Q;
    }

    private void rangehelper(Node root, Queue<Point2D> q, RectHV rect) {
        if (root == null)
            return;

        if (!root.rect.intersects(rect))
            return;

        if (rect.contains(root.p))
            q.add(root.p);

        rangehelper(root.lb, q, rect);
        rangehelper(root.rt, q, rect);
    }

    public Point2D nearest(Point2D p) {           // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null)
            throw new java.lang.IllegalArgumentException();

        if (isEmpty())
            return null;

        Point2D res = null;
        double mindis = Double.MAX_VALUE;
        Queue<Node> Q = new LinkedList<Node>();

        Q.add(root);

        while (!Q.isEmpty()) {
            Node t = Q.remove();
            double dis = p.distanceSquaredTo(t.p);

            if (dis < mindis) {
                mindis = dis;
                res = t.p;
            }

            if (t.lb != null && t.lb.rect.distanceSquaredTo(p) < mindis)
                Q.add(t.lb);

            if (t.rt != null && t.rt.rect.distanceSquaredTo(p) < mindis)
                Q.add(t.rt);
        }
        return res;
    }
}

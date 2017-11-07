import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class BruteCollinearPoints {

    private LineSegment[] ans;
    private int n = 0;

    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new java.lang.IllegalArgumentException();

        for (Point p : points)
            if (p == null)
                throw new java.lang.IllegalArgumentException();

        for (int i = 0; i < points.length-1; i++)
            for (int j = i+1; j < points.length; j++)
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException();

        int len = points.length;
        ans = new LineSegment[len];
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                for (int k = j + 1; k < len; k++) {
                    for (int m = k + 1; m < len; m++) {
                        Point[] ps = new Point[4];
                        ps[0] = points[i];
                        ps[1] = points[j];
                        ps[2] = points[k];
                        ps[3] = points[m];

                        if (ps[0].slopeTo(ps[1]) == ps[0].slopeTo(ps[3]) && ps[0].slopeTo(ps[2]) == ps[0].slopeTo(ps[3])) {
                            Arrays.sort(ps);
                            ans[n++] =  new LineSegment(ps[0], ps[3]);
                        }
                    }
                }
            }
        }

    }
    public  int numberOfSegments() {
        return n;
    }
    public LineSegment[] segments() {
        LineSegment[] seg = new LineSegment[n];

        for (int i = 0; i < n; i++)
            seg[i] = ans[i];

        return seg;
    }
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

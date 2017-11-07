import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private ArrayList<LineSegment> ans;

    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new java.lang.IllegalArgumentException();

        for (Point p : points)
            if (p == null)
                throw new java.lang.IllegalArgumentException();


        for (int i = 0; i < points.length-1; i++)
            for (int j = i+1; j < points.length; j++)
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException();


        ans = new ArrayList<>();
        ArrayList<Point> oneline = new ArrayList<>();
        Point[] copy = Arrays.copyOf(points, points.length);
        Arrays.sort(copy);

        for (Point p : points) {

            Arrays.sort(copy, p.slopeOrder());

            for (int j = 1; j < points.length; j++) {
                if (p.slopeTo(copy[j]) == p.slopeTo(copy[j - 1])) {
                    oneline.add(copy[j]);
                } else {
                    addSeg(oneline);
                    oneline.clear();
                    oneline.add(p);
                    oneline.add(copy[j]);
                }
            }
        }
    }

    private void addSeg(ArrayList<Point> arr) {
        if (arr.size() < 4)
            return;

        Point[] points = arr.toArray(new Point[arr.size()]);
        Arrays.sort(points);

        LineSegment newline = new LineSegment(points[0], points[points.length - 1]);

        for (LineSegment line : ans) {
            if (line.toString().equals(newline.toString()))
                return;
        }
        ans.add(newline);
    }

    public  int numberOfSegments() {
        return ans.size();
    }
    public LineSegment[] segments() {
        return ans.toArray(new LineSegment[ans.size()]);
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


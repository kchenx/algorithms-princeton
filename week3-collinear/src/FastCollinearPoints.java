/******************************************************************************
 *  Compilation:  javac FastCollinearPoints.java
 *  Execution:    java FastCollinearPoints
 *  Dependencies: Point.java LineSegment.java
 *  
 *  A sorting algorithm to detect 4-tuples of collinear points.
 *  
 *  This program takes as a command-line argument a .txt file to test the
 *  program. The file consists of an integer `n`, followed by `n` points
 *  represented by space-separated integers `x y`, each between 0 and 32,767.
 *
 ******************************************************************************/

import java.util.ArrayList;
import java.util.Arrays;

// only needed for the test client
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {

    private LineSegment[] segments;

    /**
     * Finds all line segments containing 4 points.
     * 
     * @param points list of points
     */
    public FastCollinearPoints(Point[] points) {
        // ensure argument not null
        if (points == null) {
            throw new IllegalArgumentException("Null argument");
        }

        // ensure no null elements
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("Array contains null element");
            }
        }

        Point[] p = Arrays.copyOf(points, points.length);

        // ensure no duplicates
        for (int i = 0; i < p.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Array contains repeated points");
            }
        }

        // for storing all the line segments with at least 4 points
        ArrayList<LineSegment> segmentList = new ArrayList<LineSegment>();

        // find lines with at least 4 points by iterating over all points
        for (Point point : p) {
            // sort by slope made with `point`
            Arrays.sort(p, point.slopeOrder());
            int count = 0;
            for (int i = 0; i < p.length; i++) {
                if (i < p.length - 1 && p[i] == p[i + 1]) {
                    count++;
                } else if (count >= 3) {
                    Arrays.sort(p, i - count, i);
                    LineSegment segment = new LineSegment(p[i - count], p[i - 1]);
                    segmentList.add(segment);
                    count = 0;
                } else {
                    count = 0;
                }
            }
        }

        segments = new LineSegment[segmentList.size()];
        segments = segmentList.toArray(segments);
    }

    /**
     * Calculates number of line segments containing 4 points.
     * 
     * @return number of line segments
     */
    public int numberOfSegments() {
        return segments.length;
    }

    /**
     * Returns list of line segments containing 4 points.
     * 
     * @return list of line segments
     */
    public LineSegment[] segments() {
        return segments;
    }

    /**
     * Test client
     * @param args txt file providing list of points
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            StdOut.println("Usage: BruteCollinearPoints filename.txt");
            return;
        }
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

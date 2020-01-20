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

    private final LineSegment[] segments;

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

        Point[] sortedPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(sortedPoints);

        // ensure no duplicates
        for (int i = 0; i < sortedPoints.length - 1; i++) {
            if (sortedPoints[i].compareTo(sortedPoints[i + 1]) == 0) {
                throw new IllegalArgumentException("Array contains repeated points");
            }
        }

        // for storing all the line segments with at least 4 points
        ArrayList<LineSegment> segmentList = new ArrayList<LineSegment>();

        // find lines with at least 4 points by iterating over all points
        for (Point point : sortedPoints) {
            // sort by natural order and then slope made with `point`
            Point[] p = Arrays.copyOf(sortedPoints, sortedPoints.length);
            Arrays.sort(p, point.slopeOrder());

            int lo = 1; // the point itself will be in the first sorted position
            int hi = 2;
            while (hi < p.length) {
                if (point.slopeTo(p[lo]) == point.slopeTo(p[hi])) {
                    hi++;
                } else if (hi - lo >= 3 && point.compareTo(p[lo]) < 0) {
                    segmentList.add(new LineSegment(point, p[hi - 1]));
                    lo = hi;
                } else {
                    lo = hi;
                }
            }
            if (hi - lo >= 3 && point.compareTo(p[lo]) < 0) {
                segmentList.add(new LineSegment(point, p[hi - 1]));
            }
        }

        // convert to array
        segments = segmentList.toArray(new LineSegment[segmentList.size()]);
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
        return Arrays.copyOf(segments, segments.length);
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

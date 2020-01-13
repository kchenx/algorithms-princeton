/******************************************************************************
 *  Compilation:  javac BruteCollinearPoints.java
 *  Execution:    java BruteCollinearPoints
 *  Dependencies: Point.java LineSegment.java
 *  
 *  A brute force algorithm to detect 4-tuples of collinear points
 *
 ******************************************************************************/

public class BruteCollinearPoints {

    private LineSegment[] results;

    /**
     * Finds all line segments containing 4 points.
     * 
     * @param points list of points
     */
    public BruteCollinearPoints(Point[] points) {
        results = new LineSegment[1];
    }

    /**
     * Calculates number of line segments containing 4 points.
     * 
     * @return number of line segments
     */
    public int numberOfSegments() {
        return results.length;
    }

    /**
     * Returns list of line segments containing 4 points.
     * 
     * @return list of line segments
     */
    public LineSegment[] segments() {
        return results;
    }
}

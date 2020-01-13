/******************************************************************************
 *  Compilation:  javac FastCollinearPoints.java
 *  Execution:    java FastCollinearPoints
 *  Dependencies: Point.java LineSegment.java
 *  
 *  A sorting algorithm to detect 4-tuples of collinear points
 *
 ******************************************************************************/

public class FastCollinearPoints {

    private LineSegment[] results;

    /**
     * Finds all line segments containing 4 points.
     * 
     * @param points list of points
     */
    public FastCollinearPoints(Point[] points) {
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

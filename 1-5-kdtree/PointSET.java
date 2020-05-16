/******************************************************************************
 *  Compilation:  javac PointSET.java
 *  Execution:
 *  Dependencies: Point2D.java RectHV.java Queue.java
 *
 *  Represents a set of points in the unit square
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

import java.util.TreeSet;

public class PointSET {
    // set of points
    private final TreeSet<Point2D> set;

    /**
     * Constructs an empty set of points
     */
    public PointSET() {
        set = new TreeSet<>();
    }

    /**
     * Is the set empty?
     *
     * @return true iff the set is empty
     */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /**
     * Returns number of points in the set
     *
     * @return number of points in the set
     */
    public int size() {
        return set.size();
    }

    /**
     * Adds the point `p` to the set (if it is not already in the set)
     *
     * @param p point to be added
     */
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("insert called with null argument");
        }
        set.add(p);
    }

    /**
     * Does the set contain point `p`?
     *
     * @param p point
     * @return true iff the set contains `p`
     */
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("contains called with null argument");
        }
        return set.contains(p);
    }

    /**
     * Draws all points to standard draw
     */
    public void draw() {
        for (Point2D p : set) {
            p.draw();
        }
    }

    /**
     * Returns all points that are inside the rectangle (or on the boundary)
     *
     * @param rect rectangle
     * @return all points that are inside the rectangle (or on the boundary)
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("range called with null argument");
        }
        Queue<Point2D> points = new Queue<>();
        for (Point2D p : set) {
            if (rect.contains(p)) {
                points.enqueue(p);
            }
        }
        return points;
    }

    /**
     * Returns a nearest neighbor in the set to point p; null if the set is empty
     *
     * @param p point
     * @return a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("nearest called with null argument");
        }
        Point2D result = null;
        double sqdist = Double.POSITIVE_INFINITY;
        for (Point2D pt : set) {
            double tempsqdist = p.distanceSquaredTo(pt);
            if (tempsqdist < sqdist) {
                result = pt;
                sqdist = tempsqdist;
            }
        }
        return result;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
    }

}

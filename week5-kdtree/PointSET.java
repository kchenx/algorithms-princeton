/******************************************************************************
 *  Compilation:  javac PointSET.java
 *  Execution:
 *  Dependencies: Point2D.java RectHV.java
 *
 *  Represents a set of points in the unit square
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {
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
        set.add(p);
    }

    /**
     * Does the set contain point `p`?
     *
     * @param p point
     * @return true iff the set contains `p`
     */
    public boolean contains(Point2D p) {
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
        ArrayList<Point2D> points = new ArrayList<>();
        for (Point2D p : set) {
            if (rect.contains((p))) {
                points.add(p);
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
        Point2D result = null;
        double sqdist = Double.POSITIVE_INFINITY;
        for (Point2D pt : set) {
            if (p.distanceSquaredTo(pt) < sqdist) {
                result = pt;
                sqdist = p.distanceSquaredTo(pt);
            }
        }
        return result;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
    }

}

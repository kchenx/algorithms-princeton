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

public class KdTree {

    // orientation of node
    private static final boolean XCOOR = true;
    private static final boolean YCOOR = false;

    private Node root;
    private int size;

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        private Node(Point2D p) {
            this.p = p;
            lb = null;
            rt = null;
        }
    }

    /**
     * Constructs an empty set of points
     */
    public KdTree() {
        root = null;
        size = 0;
    }

    /**
     * Is the set empty?
     *
     * @return true iff the set is empty
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Returns number of points in the set
     *
     * @return number of points in the set
     */
    public int size() {
        return size;
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
        Node n = new Node(p);
        root = insert(n, XCOOR);
        size++;
    }

    private Node insert(Node n, boolean orientation) {
        if (orientation == XCOOR) {

        }
        else { // if orientation == YCOOR

        }
    }

    /**
     * Does the set contain point `p`?
     *
     * @param p point
     * @return true iff the set contains `p`
     */
    public boolean contains(Point2D p) {
    }

    /**
     * Draws all points to standard draw
     */
    public void draw() {
    }

    /**
     * Returns all points that are inside the rectangle (or on the boundary)
     *
     * @param rect rectangle
     * @return all points that are inside the rectangle (or on the boundary)
     */
    public Iterable<Point2D> range(RectHV rect) {
    }

    /**
     * Returns a nearest neighbor in the set to point p; null if the set is empty
     *
     * @param p point
     * @return a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
    }


}

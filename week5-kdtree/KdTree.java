/******************************************************************************
 *  Compilation:  javac PointSET.java
 *  Execution:
 *  Dependencies: Point2D.java RectHV.java
 *
 *  Represents a set of points in the unit square
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    // parameters
    private static final double XMIN = 0;
    private static final double YMIN = 0;
    private static final double XMAX = 1;
    private static final double YMAX = 1;

    // tree info
    private Node root;
    private int size;

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the rectangle
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        private Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
            this.lb = null;
            this.rt = null;
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
        root = insert(root, p, XMIN, YMIN, XMAX, YMAX, 0);
    }

    private Node insert(Node n, Point2D p, double xmin, double ymin, double xmax,
                        double ymax, int depth) {
        if (n == null) {
            size++;
            return new Node(p, new RectHV(xmin, ymin, xmax, ymax));
        }
        int cmp = comparePoints(p, n.p, depth);
        if (cmp < 0 && isVertical(depth)) {
            n.lb = insert(n.lb, p, xmin, ymin, n.p.x(), ymax, depth + 1);
        }
        else if (cmp < 0) {
            n.lb = insert(n.lb, p, xmin, ymin, xmax, n.p.y(), depth + 1);
        }
        else if (cmp > 0 && isVertical(depth)) {
            n.rt = insert(n.rt, p, n.p.x(), ymin, xmax, ymax, depth + 1);
        }
        else {
            n.rt = insert(n.rt, p, xmin, n.p.y(), xmax, ymax, depth + 1);
        }
        return n;
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
        return contains(root, p, 0);
    }

    private boolean contains(Node n, Point2D p, int depth) {
        if (n == null) {
            return false;
        }
        int cmp = comparePoints(p, n.p, depth);
        if (cmp < 0) {
            return contains(n.lb, p, depth + 1);
        }
        else if (cmp > 0) {
            return contains(n.rt, p, depth + 1);
        }
        else {
            assert p.equals(n.p);
            return true;
        }
    }

    /**
     * Draws all points to standard draw
     */
    public void draw() {
        draw(root, 0);
    }

    private void draw(Node n, int depth) {
        if (n == null) {
            return;
        }

        StdDraw.setPenRadius(0.003);
        if (isVertical(depth)) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());
        }

        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        n.p.draw();

        draw(n.lb, depth + 1);
        draw(n.rt, depth + 1);
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
        return root.p;
    }

    /**
     * Compare two points by coordinate `partition`
     *
     * @param a     point a
     * @param b     point b
     * @param depth depth of node in tree
     * @return comparison integer
     */
    private int comparePoints(Point2D a, Point2D b, int depth) {
        if (isVertical(depth)) {
            if (a.x() < b.x()) {
                return -1;
            }
            else if (a.x() > b.x()) {
                return 1;
            }
        }
        return a.compareTo(b);
    }

    /**
     * Returns true iff depth represents a vertical division
     *
     * @param depth depth into 2D tree
     * @return true iff depth represents a vertical division
     */
    private boolean isVertical(int depth) {
        return depth % 2 == 0;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
    }


}

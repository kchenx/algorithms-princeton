/******************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:    java Percolation
 *  Dependencies: WeightedQuickUnionUF.java
 *
 *  This program implements a class which models the percolation problem:
 *
 *    - A percolation system is an `n`-by-`n` grid of sites
 *    - Each site is either "open" or "blocked"
 *    - A "full" site is an open site that can be connected to an open site
 *      in the top row via a chain of neighboring (left, right, up, down)
 *      open sites
 *    - A system "percolates" iff there is a full site in the bottom row.
 *      In other words, a system percolates iff we fill all open sites connected
 *      to the top row and that process fills some open site on the bottom row.
 *
 *  The program does this with Union-Find data structures, modeling each site
 *  as an element. When a site is opened, then it is union-ed with its neighboring
 *  sites, and it is additionally union-ed to the top virtual node if it is in
 *  the first row, and the bottom virtual node if it is in the bottom row.
 *
 *  Thus, the system percolates iff the top and bottom virtual nodes are connected.
 *  Note that we keep a separate union-find structure to track full-ness because
 *  once the system percolates, there may be "backwash": if the bottom virtual node
 *  is connected to open sites that do not reach the top, since they are part of the
 *  same set as the bottom virtual node, which percolates, it will seem like they
 *  are full (have a path to the top row) when they actually do not. In our other
 *  union-find structure to track fullness, we do not union with the bottom
 *  virtual node to solve the problem.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // union-find structure to see if system percolates
    private final WeightedQuickUnionUF uf;

    // union-find structure to see if node full
    private final WeightedQuickUnionUF ufNoBackwash;

    // virtual nodes
    private final int topVirtualNode;
    private final int bottomVirtualNode;

    // grid to check if site is open: a site is open iff true
    private final boolean[][] m;

    // number of open sites
    private int nopen = 0;

    // dimension of n-by-n grid
    private final int n;

    /**
     * Creates <tt>n</tt>-by-<tt>n</tt> percolation system, with all sites
     * initially blocked.
     *
     * @param n dimension of n-by-n grid
     */
    public Percolation(final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }

        ufNoBackwash = new WeightedQuickUnionUF(n * n + 2);
        uf = new WeightedQuickUnionUF(n * n + 2);

        m = new boolean[n + 1][n + 1];

        this.n = n;
        topVirtualNode = n * n;
        bottomVirtualNode = n * n + 1;
    }

    /**
     * Opens the site with indices (<tt>row</tt>, <tt>col</tt>)
     * if it is not already open.
     *
     * @param row row index
     * @param col col index
     */
    public void open(final int row, final int col) {
        if (isOpen(row, col)) {
            return;
        }

        m[row][col] = true;
        nopen++;

        // connect to adjacent nodes if they exist
        if (row > 1 && isOpen(row - 1, col)) {
            unionToBoth(xyTo1D(row - 1, col), xyTo1D(row, col));
        }
        if (col > 1 && isOpen(row, col - 1)) {
            unionToBoth(xyTo1D(row, col - 1), xyTo1D(row, col));
        }
        if (row < n && isOpen(row + 1, col)) {
            unionToBoth(xyTo1D(row + 1, col), xyTo1D(row, col));
        }
        if (col < n && isOpen(row, col + 1)) {
            unionToBoth(xyTo1D(row, col + 1), xyTo1D(row, col));
        }

        // connect to virtual nodes if appropriate
        if (row == 1) {
            unionToBoth(xyTo1D(row, col), topVirtualNode);
        }
        if (row == n) {
            uf.union(xyTo1D(row, col), bottomVirtualNode);
        }
    }

    /**
     * Checks if the site with indices (<tt>row</tt>, <tt>col</tt>) is open.
     *
     * @param row row index
     * @param col col index
     * @return true iff the site (<tt>row</tt>, <tt>col</tt>) is open
     */
    public boolean isOpen(final int row, final int col) {
        if (!inRange(row, col)) {
            throw new IllegalArgumentException("Out of bounds");
        }

        return m[row][col];
    }

    /**
     * Checks if the site (<tt>row</tt>, <tt>col</tt>) is full.
     * A "full" site is an open site that can be connected to an open site
     * in the top row via a chain of neighboring (left, right, up, down)
     * open sites.
     *
     * @param row row index
     * @param col col index
     * @return true iff the site (<tt>row</tt>, <tt>col</tt>) is full
     */
    public boolean isFull(final int row, final int col) {
        if (!inRange(row, col)) {
            throw new IllegalArgumentException("Out of bounds");
        }

        return (isOpen(row, col)) && (ufNoBackwash.find(xyTo1D(row, col))
                == ufNoBackwash.find(topVirtualNode));
    }

    /**
     * Calculates the number of open sites.
     *
     * @return number of open sites
     */
    public int numberOfOpenSites() {
        return nopen;
    }

    /**
     * Calculates if the system percolates.
     * A system "percolates" iff there is a full site in the bottom row.
     *
     * @return true iff the system percolates
     */
    public boolean percolates() {
        if (n == 1) {
            return isOpen(1, 1);
        }
        return uf.find(topVirtualNode) == uf.find(bottomVirtualNode);
    }

    /**
     * Converts (<tt>row</tt>, <tt>col</tt>) indices (which are 1-based)
     * to a single 0-based index for Union-Find structure.
     *
     * @param row row index
     * @param col col index
     * @return index for Union-Find
     */
    private int xyTo1D(final int row, final int col) {
        return (row - 1) * n + (col - 1);
    }

    /**
     * Calculates if indices are "valid": within the range of the percolation
     * system.
     *
     * @param row row index
     * @param col col index
     * @return true iff indices are valid
     */
    private boolean inRange(final int row, final int col) {
        return row > 0 && col > 0 && row <= n && col <= n;
    }

    /**
     * Merges the set containing the element <tt>p</tt> with the set
     * containing the element <tt>q</tt> in both the <tt>uf</tt> and
     * <tt>ufNoBackwash</tt> union-find structures.
     *
     * @param p one element
     * @param q the other element
     */
    private void unionToBoth(int p, int q) {
        uf.union(p, q);
        ufNoBackwash.union(p, q);
    }

    // test client (see PercolationStats.java)
    public static void main(final String[] args) {
    }
}

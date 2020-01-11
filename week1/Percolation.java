/******************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:    java Percolation
 *
 *  This program implements a class which models the percolation problem:
 *
 *    - A percolation system is an n-by-n grid of sites
 *    - Each site is either "open" or "blocked"
 *    - A "full" site is an open site that can be connected to an open site 
 *      in the top row via a chain of neighboring (left, right, up, down) 
 *      open sites
 *    - A system "percolates" iff there is a full site in the bottom row.
 *      In other words, a system percolates iff we fill all open sites connected 
 *      to the top row and that process fills some open site on the bottom row. 
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final boolean[][] m;
    private final int n;
    private final WeightedQuickUnionUF uf;
    private int nopen = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }

        m = new boolean[n + 1][n + 1];
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                m[i][j] = false;
            }
        }

        this.n = n;

        uf = new WeightedQuickUnionUF(n * n + 2);

        // connect top row to source
        for (int i = 0; i < n; i++) {
            uf.union(i, n * n);
        }

        // connect bottom row to sink
        for (int i = n * (n - 1); i < n * n; i++) {
            uf.union(i, n * n + 1);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(final int row, final int col) {
        if (isOpen(row, col)) {
            return;
        }

        m[row][col] = true;
        nopen++;

        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(xyTo1D(row - 1, col), xyTo1D(row, col));
        }
        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(xyTo1D(row, col - 1), xyTo1D(row, col));
        }
        if (row < n && isOpen(row + 1, col)) {
            uf.union(xyTo1D(row + 1, col), xyTo1D(row, col));
        }
        if (col < n && isOpen(row, col + 1)) {
            uf.union(xyTo1D(row, col + 1), xyTo1D(row, col));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(final int row, final int col) {
        if (!validateIndices(row, col)) {
            throw new IllegalArgumentException("Out of bounds");
        }

        return m[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(final int row, final int col) {
        if (!validateIndices(row, col)) {
            throw new IllegalArgumentException("Out of bounds");
        }

        return (isOpen(row, col)) && (uf.find(xyTo1D(row, col)) == uf.find(n * n));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return nopen;
    }

    // does the system percolate?
    public boolean percolates() {
        if (n == 1) {
            return isOpen(1, 1);
        }
        return uf.find(n * n) == uf.find(n * n + 1);
    }

    // converts (row, col) to index for Union-Find
    private int xyTo1D(final int row, final int col) {
        return (row - 1) * n + (col - 1);
    }

    // returns true iff indices are valid
    private boolean validateIndices(final int row, final int col) {
        return row > 0 && col > 0 && row <= n && col <= n;
    }

    // test client (optional)
    public static void main(final String[] args) {
        return;
    }
}

/******************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:    java Percolation
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
    private WeightedQuickUnionUF uf;
    
    // union-find structure to see if node full
    private WeightedQuickUnionUF ufNoBackwash;
    
    // virtual node to be attached to top sites
    private final int topVirtualNode;
    
    // virtual node to be attached to bottom sites
    private final int bottomVirtualNode;
    
    // grid to check if site is open: a site is open iff true
    private boolean[][] m;
    
    // number of sites open
    private int nopen = 0;
    
    // size of grid: percolation system is `n`-by-`n`
    private final int n;
    

    // creates n-by-n grid, with all sites initially blocked
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

    // opens the site (row, col) if it is not open already
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

    // is the site (row, col) open?
    public boolean isOpen(final int row, final int col) {
        if (!inRange(row, col)) {
            throw new IllegalArgumentException("Out of bounds");
        }

        return m[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(final int row, final int col) {
        if (!inRange(row, col)) {
            throw new IllegalArgumentException("Out of bounds");
        }

        return (isOpen(row, col)) && (ufNoBackwash.find(xyTo1D(row, col)) == ufNoBackwash.find(topVirtualNode));
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
        return uf.find(topVirtualNode) == uf.find(bottomVirtualNode);
    }

    // converts (row, col) to index for Union-Find
    private int xyTo1D(final int row, final int col) {
        return (row - 1) * n + (col - 1);
    }

    // returns true iff indices are valid
    private boolean inRange(final int row, final int col) {
        return row > 0 && col > 0 && row <= n && col <= n;
    }
    
    // unions to both `uf` and `ufNoBackwash` structures
    private void unionToBoth(int p, int q) {
        uf.union(p, q);
        ufNoBackwash.union(p, q);
    }

    // test client (optional)
    public static void main(final String[] args) {
        return;
    }
}

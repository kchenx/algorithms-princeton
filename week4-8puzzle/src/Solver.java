/******************************************************************************
 *  Compilation:  javac-algs4 Solver.java
 *  Execution:    java-algs4 Solver filename1.txt filename2.txt ...
 *  Dependencies: Board.java MinPQ.java
 *
 *  This program implements the A* search algorithm to solve n-by-n slider 
 *  puzzles.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {

    /**
     * Finds a solution to the initial board (using the A* algorithm)
     * @param initial board to be solved
     */
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("initial board null");
        }
        return;
    }

    /**
     * Calculates if the initial board solvable
     * @return true iff the initial board is solvable
     */
    public boolean isSolvable() {
        return false;
    }

    /**
     * Calculates min number of moves to solve initial board
     * @return min number of moves to solve initial board
     */
    public int moves() {
        return 0;
    }

    /**
     * Calculates sequence of boards in a shortest solution
     * @return
     */
    public Iterable<Board> solution() {
        Queue<Board> q = new Queue<Board>();
        return q;
    }

    /**
     * Test client
     * @param args file containing initial board
     */
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}

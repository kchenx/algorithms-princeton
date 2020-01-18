/******************************************************************************
 *  Compilation:  javac-algs4 Board.java
 *  Execution:    java-algs4 Board filename1.txt filename2.txt ...
 *  Dependencies: Board.java Solver.java
 *
 *  This program creates an initial board from each filename specified
 *  on the command line and finds the minimum number of moves to
 *  reach the goal state.
 *
 *  % java-algs4 PuzzleChecker puzzle*.txt
 *  puzzle00.txt: 0
 *  puzzle01.txt: 1
 *  puzzle02.txt: 2
 *  puzzle03.txt: 3
 *  puzzle04.txt: 4
 *  puzzle05.txt: 5
 *  puzzle06.txt: 6
 *  ...
 *  puzzle3x3-impossible: -1
 *  ...
 *  puzzle42.txt: 42
 *  puzzle43.txt: 43
 *  puzzle44.txt: 44
 *  puzzle45.txt: 45
 *
 ******************************************************************************/


public class Board {
    public class Board {

        // create a board from an n-by-n array of tiles,
        // where tiles[row][col] = tile at (row, col)
        public Board(int[][] tiles);

        // string representation of this board
        public String toString();

        // board dimension n
        public int dimension();

        // number of tiles out of place
        public int hamming();

        // sum of Manhattan distances between tiles and goal
        public int manhattan();

        // is this board the goal board?
        public boolean isGoal();

        // does this board equal y?
        public boolean equals(Object y);

        // all neighboring boards
        public Iterable<Board> neighbors();

        // a board that is obtained by exchanging any pair of tiles
        public Board twin();

        // unit testing (not graded)
        public static void main(String[] args);

    }
}

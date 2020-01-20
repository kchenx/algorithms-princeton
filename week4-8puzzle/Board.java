/******************************************************************************
 *  Compilation:  javac-algs4 Board.java
 *  Execution:    java-algs4 Board
 *  Dependencies: Stack.java
 *
 *  This program models an n-by-n board with sliding tiles for n-by-n slider 
 *  puzzles.
 *
 ******************************************************************************/

import java.lang.Math;
import java.util.Arrays;
import edu.princeton.cs.algs4.Queue;

public class Board {

    private int[][] board;
    int n;

    /**
     * Creates a board from an n-by-n array of tiles,
     * where tiles[row][col] = tile at (row, col)
     */
    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException("Board null");
        }

        n = tiles.length;
        board = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = tiles[i][j];
            }
        }
    }

    /** 
     * Returns string representation of this board
     * @return 
     */
    @Override
    public String toString() {
        String repr = Integer.toString(n) + "\n";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                repr += " " + Integer.toString(board[i][j]);
            }
            repr += "\n";
        }
        return repr;
    }

    /**
     * Returns board dimension `n`
     * @return board dimension `n`
     */
    public int dimension() {
        return board.length;
    }

    /**
     * Calculates number of tiles out of place
     * @return number of tiles out of place
     */
    public int hamming() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != i * n + j + 1 && board[i][j] != 0) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Calculates sum of Manhattan distances between tiles and goal
     * @return sum of Manhattan distances
     */
    public int manhattan() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) {
                    continue;
                }
                // convert to zero-indexing, find goal position
                int val = board[i][j] - 1;
                int goalRow = val / n;
                int goalCol = val % n;
                int distance = Math.abs(goalRow - i) + Math.abs(goalCol - j);
                count += distance;
            }
        }
        return count;
    }

    /**
     * Calculates if this board is the goal board
     * @return true iff the board is the goal board
     */
    public boolean isGoal() {
        return hamming() == 0;
    }

    /**
     * Calculates if this board is equal to `y`
     * @return true iff the board is equal to `y`
     */
    @Override
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;

        return Arrays.deepEquals(this.board, that.board);
    }

    /**
     * Returns all neighboring boards
     * @return all neighboring boards
     */
    public Iterable<Board> neighbors() {
        Queue<Board> q = new Queue<Board>();

        // coordinates of blank square
        int x = 0;
        int y = 0;

        int[][] neighbor = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 0) {
                    x = i;
                    y = j;
                }
                neighbor[i][j] = board[i][j];
            }
        }

        // add neighbors if they exist
        if (x > 1) {
            swap(neighbor, x, y, x - 1, y);
            Board neighborBoard = new Board(neighbor);
            q.enqueue(neighborBoard);
            swap(neighbor, x, y, x, y - 1);
        }
        if (y > 1) {
            swap(neighbor, x, y, x, y - 1);
            Board neighborBoard = new Board(neighbor);
            q.enqueue(neighborBoard);
            swap(neighbor, x, y, x, y - 1);
        }
        if (x < n - 1) {
            swap(neighbor, x, y, x + 1, y);
            Board neighborBoard = new Board(neighbor);
            q.enqueue(neighborBoard);
            swap(neighbor, x, y, x + 1, y);
        }
        if (y < n - 1) {
            swap(neighbor, x, y, x, y + 1);
            Board neighborBoard = new Board(neighbor);
            q.enqueue(neighborBoard);
            swap(neighbor, x, y, x, y + 1);
        }

        return q;
    }

    /**
     * Returns a board that is obtained by exchanging any pair of tiles
     * @return board obtained by exchanging any pair of tiles
     */
    public Board twin() {
        int[][] twin = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                twin[i][j] = board[i][j];
            }
        }

        // swap a pair of tiles (checking that blank square is not swapped)
        boolean firstTileBlank = (twin[0][0] == 0);
        boolean secondTileBlank = (twin[0][1] == 0);
        // the "third tile" is twin[1][0]

        if (firstTileBlank) {
            // swap second and third
            swap(twin, 0, 1, 1, 0);
        } else if (secondTileBlank) {
            // swap first and third
            swap(twin, 0, 0, 1, 0);
        } else {
            // swap first and second
            swap(twin, 0, 0, 0, 1);
        }

        Board twinBoard = new Board(twin);
        return twinBoard;
    }

    /**
     * Swaps integers at position (x1, y1) and (x2, y2) in the matrix `matrix`, 
     * where every parameter is zero-indexed
     * 
     * @param matrix matrix where integers will be swapped
     * @param x1 x-coordinate of first block
     * @param y1 y-coordinate of first block
     * @param x2 x-coordinate of second block
     * @param y2 y-coordinate of second block
     */
    private static void swap(int[][] matrix, int x1, int y1, int x2, int y2) {
        if (matrix == null) {
            throw new IllegalArgumentException("Matrix null");
        }
        if (x1 < 0 || x1 >= matrix.length || x2 < 0 || x2 >= matrix.length ||
                y1 < 0 || y1 >= matrix[0].length || y1 < 0 || y2 >= matrix[0].length) {
            throw new IllegalArgumentException("Indices out of bound");
        }
        int temp = matrix[x1][y1];
        matrix[x1][y1] = matrix[x2][y2];
        matrix[x2][y2] = temp;
    }

    /**
     * Unit testing
     * @param args
     */
    public static void main(String[] args) {
        int n = 3;
        int[][] tiles = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board board = new Board(tiles);

        int[][] tiles2 = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles2[i][j] = i * n + j;
            }
        }
        Board board2 = new Board(tiles2);

        System.out.println(board);
        System.out.println(board.dimension());
        System.out.println(board.hamming());
        System.out.println(board.manhattan());
        System.out.println(board.isGoal());
        System.out.println(board.equals(board2));
        System.out.println(board.neighbors());
        System.out.println(board.twin());
    }

}

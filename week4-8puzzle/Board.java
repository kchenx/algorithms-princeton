/******************************************************************************
 *  Compilation:  javac-algs4 Board.java
 *  Execution:    java-algs4 Board
 *  Dependencies: Stack.java
 *
 *  This program models an n-by-n board with sliding tiles for n-by-n slider
 *  puzzles.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;

public class Board {

    final private char[][] tiles;
    final private int n;

    /**
     * Creates a board from an n-by-n array of tiles,
     * where tiles[row][col] = tile at (row, col)
     */
    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException("Board null");
        }

        n = tiles.length;
        this.tiles = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = (char) tiles[i][j];
            }
        }
    }

    /**
     * Returns string representation of this board
     *
     * @return string representation of this board
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", (int) tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    /**
     * Returns board dimension `n`
     *
     * @return board dimension `n`
     */
    public int dimension() {
        return n;
    }

    /**
     * Calculates number of tiles out of place, not counting blank tile
     *
     * @return number of tiles out of place
     */
    public int hamming() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != i * n + j + 1 && tiles[i][j] != 0) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Calculates sum of Manhattan distances between tiles and goal
     *
     * @return sum of Manhattan distances
     */
    public int manhattan() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    continue;
                }
                // convert to zero-indexing, find goal position
                int val = tiles[i][j] - 1;
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
     *
     * @return true iff the board is the goal board
     */
    public boolean isGoal() {
        return hamming() == 0;
    }

    /**
     * Calculates if this board is equal to `y`
     *
     * @return true iff the board is equal to `y`
     */
    @Override
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;

        return Arrays.deepEquals(this.tiles, that.tiles);
    }

    /**
     * Returns all neighboring boards
     *
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
                if (tiles[i][j] == 0) {
                    x = i;
                    y = j;
                }
                neighbor[i][j] = tiles[i][j];
            }
        }

        // add neighbors if they exist
        if (x > 0) {
            swap(neighbor, x, y, x - 1, y);
            Board neighborBoard = new Board(neighbor);
            q.enqueue(neighborBoard);
            swap(neighbor, x, y, x - 1, y);
        }
        if (y > 0) {
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
     *
     * @return board obtained by exchanging any pair of tiles
     */
    public Board twin() {
        int[][] twin = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                twin[i][j] = tiles[i][j];
            }
        }

        // swap a pair of tiles (checking that blank square is not swapped)
        boolean firstTileBlank = (twin[0][0] == 0);
        boolean secondTileBlank = (twin[0][1] == 0);
        // the "third tile" is twin[1][0]

        if (firstTileBlank) {
            // swap second and third
            swap(twin, 0, 1, 1, 0);
        }
        else if (secondTileBlank) {
            // swap first and third
            swap(twin, 0, 0, 1, 0);
        }
        else {
            // swap first and second
            swap(twin, 0, 0, 0, 1);
        }

        return new Board(twin);
    }

    /**
     * Swaps integers at position (x1, y1) and (x2, y2) in the matrix `matrix`,
     * where every parameter is zero-indexed
     *
     * @param matrix matrix where integers will be swapped
     * @param x1     x-coordinate of first block
     * @param y1     y-coordinate of first block
     * @param x2     x-coordinate of second block
     * @param y2     y-coordinate of second block
     */
    private static void swap(int[][] matrix, int x1, int y1, int x2, int y2) {
        if (matrix == null) {
            throw new IllegalArgumentException("Matrix null");
        }
        if (x1 < 0 || x1 >= matrix.length || x2 < 0 || x2 >= matrix.length ||
                y1 < 0 || y1 >= matrix[0].length || y2 < 0 || y2 >= matrix[0].length) {
            throw new IllegalArgumentException("Indices out of bound");
        }
        int temp = matrix[x1][y1];
        matrix[x1][y1] = matrix[x2][y2];
        matrix[x2][y2] = temp;
    }

    /**
     * Unit testing
     */
    public static void main(String[] args) {
        int n = 3;
        int[][] tiles1 = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
        Board board1 = new Board(tiles1);

        int[][] tiles2 = { { 0, 1, 3 }, { 4, 2, 5 }, { 7, 8, 6 } };
        Board board2 = new Board(tiles2);

        System.out.println(board1);
        System.out.println(board1.dimension() == n);
        System.out.println(board1.hamming() == 5);
        System.out.println(board1.manhattan() == 10);
        System.out.println(!board1.isGoal());
        System.out.println(!board1.equals(board2));
        System.out.println(board1.neighbors());
        System.out.println(board1.twin());

        System.out.println(board2);
        System.out.println(board2.dimension() == n);
        System.out.println(board2.hamming() == 4);
        System.out.println(board2.manhattan() == 4);
        System.out.println(!board2.isGoal());
        System.out.println(board2.neighbors());
        System.out.println(board2.twin());
    }

}

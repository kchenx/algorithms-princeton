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
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private boolean solvable;
    private int nmoves;
    private Stack<Board> sequence;

    private class Node implements Comparable<Node> {
        Board board;
        Node prev;
        int moves;
        int priority;

        private Node(Board board, Node prev, int moves) {
            this.board = board;
            this.prev = prev;
            this.moves = moves;
            this.priority = moves + board.manhattan();
        }

        @Override
        public int compareTo(Node that) {
            return Integer.compare(this.priority, that.priority);
        }
    }

    /**
     * Finds a solution to the initial board (using the A* algorithm)
     *
     * @param initial board to be solved
     */
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("initial board null");
        }

        sequence = new Stack<Board>();
        Queue<Node> removed = new Queue<Node>();    // ensure removed nodes not garbage collected
        MinPQ<Node> pq = new MinPQ<>();
        MinPQ<Node> twinpq = new MinPQ<>();
        pq.insert(new Node(initial, null, 0));
        twinpq.insert(new Node(initial.twin(), null, 0));

        while (true) {
            Node node = pq.delMin();
            if (node.board.isGoal()) {
                solvable = true;
                nmoves = node.moves;
                while (node != null) {
                    sequence.push(node.board);
                    node = node.prev;
                }
                return;
            }
            Iterable<Board> neighbors = node.board.neighbors();
            for (Board b : neighbors) {
                if (node.prev == null || !b.equals(node.prev.board)) {
                    Node n = new Node(b, node, node.moves + 1);
                    pq.insert(n);
                }
            }
            nmoves++;
            removed.enqueue(node);

            Node twinnode = twinpq.delMin();
            if (twinnode.board.isGoal()) {
                solvable = false;
                nmoves = -1;
                return;
            }
            Iterable<Board> twinneighbors = twinnode.board.neighbors();
            for (Board b : twinneighbors) {
                if (twinnode.prev == null || !b.equals(twinnode.prev.board)) {
                    Node n = new Node(b, twinnode, twinnode.moves + 1);
                    twinpq.insert(n);
                }
            }
        }
    }

    /**
     * Calculates if the initial board solvable
     *
     * @return true iff the initial board is solvable
     */
    public boolean isSolvable() {
        return solvable;
    }

    /**
     * Calculates min number of moves to solve initial board
     *
     * @return min number of moves to solve initial board
     */
    public int moves() {
        return nmoves;
    }

    /**
     * Calculates sequence of boards in a shortest solution
     *
     * @return sequence of boards in shortest solution
     */
    public Iterable<Board> solution() {
        return sequence;
    }

    /**
     * Test client
     *
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
        }
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }

}

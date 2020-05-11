/******************************************************************************
 *  Compilation:  javac Permutation.java
 *  Execution:    java Permutation k
 *  Dependencies: RandomizedQueue.java StdIn.java StdOut.java
 *
 *  This program takes an integer `k` as an argument.
 *
 *  Then, it reads a sequence of strings and prints exactly `k` of them,
 *  uniformly at random.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        // ensure proper usage
        if (args.length != 1) {
            System.out.println("Usage: Permutation k");
            return;
        }

        // add strings from standard input to randomized queue
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            q.enqueue(s);
        }

        // print `k` integers chosen uniformly at random
        int k = Integer.parseInt(args[0]);
        assert k <= q.size();
        for (int i = 0; i < k; i++) {
            StdOut.println(q.dequeue());
        }
    }
}

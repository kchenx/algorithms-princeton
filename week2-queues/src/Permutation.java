/******************************************************************************
 *  Compilation:  javac Permutation.java
 *  Execution:    java Permutation k
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
        if (args.length != 1) {
            System.out.println("Usage: Permutation k");
            return;
        }

        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> q = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            q.enqueue(s);
        }

        assert k <= q.size();

        for (int i = 0; i < k; i++) {
            StdOut.println(q.dequeue());
        }
    }
}

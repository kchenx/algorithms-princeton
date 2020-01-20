/******************************************************************************
 *  Compilation:  javac RandomizedQueue.java
 *  Execution:    java RandomizedQueue
 *  Dependencies: StdRandom.java
 *
 *  This program implements a randomized queue class as an self-expanding array 
 *  and runs unit tests in `main`.
 *
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {


    private Item[] rq;        // randomized queue
    private int nitems = 0;  // number of items in queue

    public RandomizedQueue() {
        rq = (Item[]) new Object[1];
    }

    /**
     * Calculates if the randomized queue is empty.
     * 
     * @return true iff the randomized queue is empty.
     */
    public boolean isEmpty() {
        return nitems == 0;
    }

    /**
     * Calculates the number of items in the randomized queue
     * 
     * @return number of items on the randomized queue
     */
    public int size() {
        return nitems;
    }

    /**
     * Adds an item to the randomized queue
     * 
     * @param  item                     item to be added
     * @throws IllegalArgumentException if the item is null
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (nitems == rq.length) {
            resize(2 * rq.length);
        }

        rq[nitems++] = item;
    }

    /**
     * Remove and return an item in the queue selected uniformly at random
     * 
     * @return                        item in the queue selected uniformly at random
     * @throws NoSuchElementException if the queue is empty
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        if (nitems <= rq.length / 4) {
            resize(rq.length / 2);
        }

        int i = StdRandom.uniform(nitems);
        Item temp = rq[i];
        rq[i] = rq[--nitems];
        rq[nitems] = null;
        return temp;
    }

    /**
     * Returns an item in the queue selected uniformly at random without removing it.
     * 
     * @return                        item in the queue selected uniformly at random
     * @throws NoSuchElementException if the queue is empty
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int i = StdRandom.uniform(nitems);
        return rq[i];
    }

    /**
     * Returns an independent iterator over the items in the queue in random order
     */
    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private final int[] order;
        private int current = 0;

        public RandomizedQueueIterator() {
            order = StdRandom.permutation(nitems);
        }

        @Override
        public boolean hasNext() {
            return current < order.length;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return rq[order[current++]];
        }
    }

    /**
     * Resizes the randomized queue to the size <tt>capacity</tt>
     * 
     * @param capacity the new size of the queue
     */
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < nitems; i++) {
            copy[i] = rq[i];
        }
        rq = copy;
    }

    private static void printTest(int n, boolean result) {
        String s = result ? "pass" : "FAILED";
        StdOut.printf("test%03d: %s\n", n, s);
    }

    // unit testing (required)
    public static void main(String[] args) {

        // test `isEmpty`
        StdOut.printf("\n----- TESTING ISEMPTY -----\n");
        int n = 1;

        RandomizedQueue<Integer> q1 = new RandomizedQueue<Integer>();

        printTest(n++, q1.isEmpty() == true);

        boolean thrown = false;
        try {
            q1.dequeue();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        printTest(n++, q1.isEmpty() && thrown);

        q1.enqueue(1);
        printTest(n++, !q1.isEmpty());

        q1.enqueue(2);
        printTest(n++, !q1.isEmpty());

        q1.dequeue();
        printTest(n++, !q1.isEmpty());

        q1.dequeue();
        printTest(n++, q1.isEmpty());


        // test `size`
        StdOut.printf("\n----- TESTING SIZE -----\n");
        n = 1;

        RandomizedQueue<Integer> q2 = new RandomizedQueue<Integer>();
        printTest(n++, q2.size() == 0);

        thrown = false;
        try {
            q2.dequeue();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        printTest(n++, q2.size() == 0 && thrown);

        q2.enqueue(1);
        printTest(n++, q2.size() == 1);

        q2.enqueue(2);
        printTest(n++, q2.size() == 2);

        q2.dequeue();
        printTest(n++, q2.size() == 1);

        q2.dequeue();
        printTest(n++, q2.size() == 0);


        // test `enqueue`
        StdOut.printf("\n----- TESTING ENQUEUE -----\n");

        RandomizedQueue<Integer> q3 = new RandomizedQueue<Integer>();

        int nelts = 10;
        for (int i = 0; i < nelts; i++) {
            q3.enqueue(i);
        }

        for (int j : q3) {
            StdOut.print(j + " ");
        }
        StdOut.println();


        // test `dequeue`
        StdOut.printf("\n----- TESTING DEQUEUE -----\n");
        n = 1;

        for (int i = 0; i < nelts; i++) {
            StdOut.print(q3.dequeue() + " ");
        }
        StdOut.println();

        thrown = false;
        try {
            q3.dequeue();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        printTest(n++, thrown);


        // test `sample`
        StdOut.printf("\n----- TESTING SAMPLE -----\n");
        n = 1;

        RandomizedQueue<Integer> q5 = new RandomizedQueue<Integer>();

        thrown = false;
        try {
            q5.sample();
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        printTest(n++, thrown);

        for (int i = 0; i < nelts; i++) {
            q5.enqueue(i);
        }
        for (int i = 0; i < 20; i++) {
            StdOut.print(q5.sample() + " ");
        }
        StdOut.println();


        // test independence of iterators
        StdOut.printf("\n----- TESTING ITERATOR -----\n");
        n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++) {
            queue.enqueue(i);
        }
        for (int a : queue) {
            for (int b : queue) {
                StdOut.print(a + "-" + b + " ");
            }
            StdOut.println();
        }
    }

}
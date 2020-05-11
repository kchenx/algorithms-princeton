/******************************************************************************
 *  Compilation:  javac Deque.java
 *  Execution:    java Deque
 *  Dependencies: none
 *
 *  This program implements a deque class as a doubly-linked list and runs
 *  unit tests in `main`.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    // first and last items in deque
    private Node first;
    private Node last;

    private int nitems = 0;   // number of items in deque

    private class Node {
        Item item;
        Node prev;
        Node next;

        private Node(Item item) {
            this.item = item;
        }
    }

    /**
     * Calculates if the deque is empty.
     *
     * @return true iff the deque is empty.
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Calculates the number of items in the deque.
     *
     * @return number of items in the deque
     */
    public int size() {
        return nitems;
    }

    /**
     * Adds the item to the front of the deque.
     *
     * @param item item to be added to the front
     * @throws IllegalArgumentException if the item is null
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node n = new Node(item);

        if (isEmpty()) {
            first = n;
            last = n;
        }
        else {
            first.prev = n;
            n.next = first;
            first = n;
        }
        nitems++;
    }

    /**
     * Adds the item to the back of the deque.
     *
     * @param item item to be added to the back
     * @throws IllegalArgumentException if the item is null
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node n = new Node(item);

        if (isEmpty()) {
            first = n;
        }
        else {
            last.next = n;
            n.prev = last;
        }
        last = n;
        nitems++;
    }

    /**
     * Removes and returns the item at the front of the deque.
     *
     * @return item at the front
     * @throws NoSuchElementException if deque is empty
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node n = first;
        if (first.next == null) {
            first = null;
            last = null;
        }
        else {
            first = first.next;
            first.prev = null;
        }
        nitems--;
        return n.item;
    }

    /**
     * Removes and returns the item at the back of the deque.
     *
     * @return item at the back
     * @throws NoSuchElementException if deque is empty
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node n = last;
        if (last.prev == null) {
            first = null;
            last = null;
        }
        else {
            last = last.prev;
            last.next = null;
        }
        nitems--;
        return n.item;
    }

    /**
     * Returns an iterator over the elements in the deque from front to back.
     */
    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
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

            Item item = current.item;
            current = current.next;
            return item;
        }
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

        Deque<Integer> q1 = new Deque<>();

        printTest(n++, q1.isEmpty());

        boolean thrown = false;
        try {
            q1.removeFirst();
        }
        catch (NoSuchElementException e) {
            thrown = true;
        }
        printTest(n++, q1.isEmpty() && thrown);

        thrown = false;
        try {
            q1.removeLast();
        }
        catch (NoSuchElementException e) {
            thrown = true;
        }
        printTest(n++, q1.isEmpty() && thrown);

        q1.addFirst(1);
        printTest(n++, !q1.isEmpty());

        q1.addLast(2);
        printTest(n++, !q1.isEmpty());

        q1.removeLast();
        printTest(n++, !q1.isEmpty());

        q1.removeFirst();
        printTest(n, q1.isEmpty());


        // test `size`
        StdOut.printf("\n----- TESTING SIZE -----\n");
        n = 1;

        Deque<Integer> q2 = new Deque<>();
        printTest(n++, q2.size() == 0);

        thrown = false;
        try {
            q2.removeLast();
        }
        catch (NoSuchElementException e) {
            thrown = true;
        }
        printTest(n++, q2.size() == 0 && thrown);

        thrown = false;
        try {
            q2.removeLast();
        }
        catch (NoSuchElementException e) {
            thrown = true;
        }
        printTest(n++, q2.size() == 0 && thrown);

        q2.addFirst(1);
        printTest(n++, q2.size() == 1);

        q2.addLast(2);
        printTest(n++, q2.size() == 2);

        q2.removeLast();
        printTest(n++, q2.size() == 1);

        q2.removeFirst();
        printTest(n, q2.size() == 0);


        // test `addFirst`
        StdOut.printf("\n----- TESTING ADDFIRST -----\n");
        n = 1;

        Deque<Integer> q3 = new Deque<>();

        int nelts = 10;
        int[] backwards = new int[nelts];
        for (int i = 0; i < nelts; i++) {
            q3.addFirst(i);
            backwards[i] = nelts - i - 1;
        }

        int k = 0;
        for (int j : q3) {
            printTest(n++, j == backwards[k++]);
        }


        // test `addLast`
        StdOut.printf("\n----- TESTING ADDFIRST -----\n");
        n = 1;

        Deque<Integer> q4 = new Deque<>();

        int[] forwards = new int[nelts];
        for (int i = 0; i < nelts; i++) {
            q4.addLast(i);
            forwards[i] = i;
        }

        k = 0;
        for (int j : q4) {
            printTest(n++, j == forwards[k++]);
        }


        // test `removeFirst`
        StdOut.printf("\n----- TESTING REMOVEFIRST -----\n");
        n = 1;

        for (int i = 0; i < nelts; i++) {
            printTest(n++, q3.removeFirst() == backwards[i]);
        }

        thrown = false;
        try {
            q3.removeFirst();
        }
        catch (NoSuchElementException e) {
            thrown = true;
        }
        printTest(n, thrown);


        // test `removeLast`
        StdOut.printf("\n----- TESTING REMOVELAST -----\n");
        n = 1;

        for (int i = 0; i < nelts; i++) {
            printTest(n++, q4.removeLast() == backwards[i]);
        }

        thrown = false;
        try {
            q4.removeLast();
        }
        catch (NoSuchElementException e) {
            thrown = true;
        }
        printTest(n, thrown);

    }

}

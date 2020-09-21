/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] array;
    private int size;
    private int capacity;
    private int originalSize = 8;
    private double ratio = 0.25;

    // construct an empty randomized queue
    public RandomizedQueue() {
        array = (Item[]) new Object[originalSize];
        size = 0;
        capacity = originalSize;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    private void resize(int newSize) {
        Item[] newArray = (Item[]) new Object[newSize];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
        capacity = newSize;
    }
    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("cannot add null into a randomizedQueue");
        }
        if (size == capacity) {
            resize(capacity * 2);
        }
        array[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("the queue is empty");
        }
        int idx = StdRandom.uniform(size);
        Item retValue = array[idx];
        array[idx] = array[size - 1];
        array[size - 1] = null;
        size--;
        if (size > 0 && (double)size / capacity <= ratio) {
            resize(capacity / 2);
        }
        return retValue;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("the queue is empty");
        }
        int idx = StdRandom.uniform(size);
        return array[idx];
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] copyArray;
        private int remain;
        public RandomizedQueueIterator() {
            copyArray = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                copyArray[i] = array[i];
            }
            StdRandom.shuffle(copyArray);
            remain = size;
        }

        @Override
        public Item next() {
            if (remain == 0) {
                throw new NoSuchElementException();
            }
            return copyArray[--remain];
        }

        @Override
        public boolean hasNext() {
            return remain != 0;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        for (int i = 0; i < 18; i++) {
            rq.enqueue("A" + i);
        }
        System.out.println("first iterator");
        for (String s : rq) {
            System.out.print(s + " ");
        }
        System.out.println();
        System.out.println("second iterator ");
        for (String s : rq) {
            System.out.print(s + " ");
        }
        System.out.println();
        for (int i = 0; i < 18; i++) {
            System.out.print("deque ");
            System.out.print(rq.dequeue());
            System.out.println(". remain " + rq.size() + " elements. now capacity ");
        }

    }

}


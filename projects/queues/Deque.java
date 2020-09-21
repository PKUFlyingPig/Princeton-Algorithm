/* *****************************************************************************
 *  Name: FlyingPig
 *  Date: 2020.9.21
 *  Description: deque implemented based on linked-list
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Item value;
        Node next;
        Node pre;
        public Node() {
            value = null;
            next = null;
            pre = null;
        }
        public Node(Item value, Node pre, Node next) {
            this.value = value;
            this.pre = pre;
            this.next = next;
        }
    }

    private Node guard;
    private int size;
    // construct an empty deque
    public Deque() {
        guard = new Node();
        guard.pre = guard;
        guard.next = guard;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("can not add null into the deque");
        }
        Node first = new Node(item, guard, guard.next);
        guard.next.pre = first;
        guard.next = first;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("can not add null into the deque");
        }
        Node last = new Node(item, guard.pre, guard);
        guard.pre.next = last;
        guard.pre = last;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("can not remove value from an empty deque");
        }
        Item retValue = guard.next.value;
        guard.next.next.pre = guard;
        guard.next = guard.next.next;
        size--;
        return retValue;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("can not remove value from an empty deque");
        }
        Item retValue = guard.pre.value;
        guard.pre.pre.next = guard;
        guard.pre = guard.pre.pre;
        size--;
        return retValue;
    }

    private class dequeIterator implements Iterator<Item> {
        private Node ptr;
        private int remains;
        public dequeIterator() {
            ptr = guard.next;
            remains = size;
        }

        @Override
        public Item next() {
            if (remains == 0) {
                throw new NoSuchElementException();
            }
            Item retValue = ptr.value;
            ptr = ptr.next;
            remains--;
            return retValue;
        }

        @Override
        public boolean hasNext() {
            return remains > 0;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("this deque implementation doesn't support remove in iterator");
        }
    }
    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new dequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> dq = new Deque<>();
        for (int i = 0; i < 5; i++) {
            dq.addFirst("A" + i);
        }
        for (int i = 0; i < 5; i++) {
            dq.addLast("B" + i);
        }
        for (String s : dq) {
            System.out.println(s);
        }
        System.out.println("dq has " + dq.size() + " elements in total");
        for (int i = 0; i < 10; i++) {
            System.out.println(dq.removeFirst());
            System.out.println(dq.removeLast());
            System.out.println(dq.size());
        }
    }
}

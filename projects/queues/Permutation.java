/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Permutation number");
        }
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }
        int k = Integer.parseInt(args[0]);
        for (int i = 0; i < k; i++) {
            System.out.println(rq.dequeue());
        }
    }
}

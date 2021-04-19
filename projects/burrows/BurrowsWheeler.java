/* *****************************************************************************
 *  Name: FlyingPig
 *  Date: 2021.4.19
 *  Description: BurrowsWheeler transform
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BurrowsWheeler {

    private static int mod(int x, int a) {
        return (x + a) % a;
    }

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        String s = BinaryStdIn.readString();
        ArrayList<Character> t = new ArrayList<>();
        CircularSuffixArray CSA = new CircularSuffixArray(s);
        for (int i = 0; i < CSA.length(); i++) {
            int index = CSA.index(i);
            t.add(s.charAt(mod(index - 1, CSA.length())));
            if (index == 0) BinaryStdOut.write(i);
        }
        for (char x : t) {
            BinaryStdOut.write(x);
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        ArrayList<Character> t = new ArrayList<>();
        while (!BinaryStdIn.isEmpty()) {
            t.add(BinaryStdIn.readChar());
        }
        char[] firstColumn = new char[t.size()];
        int[] next = new int[t.size()];
        for (int i = 0; i < t.size(); i++) {
            firstColumn[i] = t.get(i);
        }
        Arrays.sort(firstColumn);
        for (int i = 0; i < t.size(); ) {
            int j = i + 1;
            while (j < t.size() && firstColumn[j] == firstColumn[j - 1]) j++;
            for (int k = 0; k < t.size(); k++) {
                if (t.get(k) == firstColumn[i]) {
                    next[i++] = k;
                    if (i == j) break;
                }
            }
        }
        int pointer = first;
        for (int i = 0; i < next.length; i++) {
            BinaryStdOut.write(firstColumn[pointer]);
            pointer = next[pointer];
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if      (args[0].equals("-")) transform();
        else if (args[0].equals("+")) inverseTransform();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}

/* *****************************************************************************
 *  Name: FlyingPig
 *  Date: 2021.4.19
 *  Description: CircularSuffixArray data structure for Burrows-Wheeler compression algorithm
 **************************************************************************** */

import java.util.Arrays;

public class CircularSuffixArray {

    private final int[] indexes;
    private final int N;
    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException("null string");
        }
        N = s.length();
        CircularSuffix[] suffixes = new CircularSuffix[N];
        String text = s + s;
        for (int i = 0; i < N; i++) {
            suffixes[i] = new CircularSuffix(text, i);
        }
        Arrays.sort(suffixes);
        indexes = new int[N];
        for (int i = 0; i < N; i++) {
            indexes[i] = suffixes[i].index;
        }
    }

    private static class CircularSuffix implements Comparable<CircularSuffix> {
        private final String text;
        private final int index;

        private CircularSuffix(String s, int idx) {
            text = s;
            index = idx;
        }

        private int length() {
            return text.length() / 2;
        }

        private char charAt(int i) {
            return text.charAt(index + i);
        }

        public int compareTo(CircularSuffix that) {
            if (this == that) return 0;  // optimization
            int n = Math.min(this.length(), that.length());
            for (int i = 0; i < n; i++) {
                if (this.charAt(i) < that.charAt(i)) return -1;
                if (this.charAt(i) > that.charAt(i)) return +1;
            }
            return this.length() - that.length();
        }
    }

    // length of s
    public int length() {
        return N;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= N) {
            throw new IllegalArgumentException("index out of range");
        }
        return indexes[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray CSA = new CircularSuffixArray("ABRACADABRA!");
        System.out.println("length of s : " + CSA.length());
        for (int i = 0; i < CSA.length(); i++) {
            System.out.println(CSA.index(i));
        }
    }

}

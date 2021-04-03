/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;
import java.util.Set;

public class BoggleSolver {
    private Node root;
    private Set<String> validWords;
    private boolean[][] marked;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        for (String s : dictionary) {
            put(s);
        }
    }

    private static class Node {
        private boolean exist = false;
        private Node[] next = new Node[26];
    }

    private void put(String s) {
        root = put(root, s, 0);
    }

    private Node put(Node node, String s, int d) {
        if (node == null) {
            node = new Node();
        }
        if (d == s.length()) {
            node.exist = true;
            return node;
        }
        if (d > s.length()) return node;
        char c = s.charAt(d);
        node.next[c - 'A'] = put(node.next[c - 'A'], s, d+1);
        return node;
    }

    private boolean get(String s) {
        return get(root, s, 0);
    }

    private boolean get(Node node, String s, int d) {
        if (node == null) return false;
        if (d == s.length()) {
            return node.exist;
        }
        char c = s.charAt(d);
        return get(node.next[c - 'A'], s, d+1);
    }


    private boolean inBoard(BoggleBoard board, int row, int col) {
        return row >= 0 && row < board.rows() && col >= 0 && col < board.cols();
    }

    private void dfs(BoggleBoard board, Node node, int row, int col, String word) {
        marked[row][col] = true;
        char c = board.getLetter(row, col);
        String curWord = word + c;
        if (c == 'Q') {
            curWord += 'U';
            node = node.next['U' - 'A'];
        }
        if (node == null) {
            marked[row][col] = false;
            return;
        }
        if (curWord.length() >= 3 && node.exist) validWords.add(curWord);
        for (int drow = -1; drow <= 1; drow++)
            for (int dcol = -1; dcol <= 1; dcol++)
            {
                if (drow == 0 && dcol == 0) continue;
                int newRow = row + drow;
                int newCol = col + dcol;
                if (!inBoard(board, newRow, newCol))continue;
                char nextChar = board.getLetter(newRow, newCol);
                if (node.next[nextChar - 'A'] != null && !marked[newRow][newCol]) {
                    dfs(board, node.next[nextChar - 'A'], newRow, newCol, curWord);
                }
            }
        marked[row][col] = false;
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        validWords = new HashSet<>();
        int m = board.rows();
        int n = board.cols();
        if (root == null) return validWords;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                if (root.next[board.getLetter(i, j) - 'A'] != null) {
                    marked = new boolean[m][n];
                    dfs(board, root.next[board.getLetter(i, j) - 'A'], i, j, "");
                }
            }
        return validWords;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (!get(word)) return 0;
        int len = word.length();
        if (len < 3) return 0;
        if (len <= 4) return 1;
        else if (len == 5) return 2;
        else if (len == 6) return 3;
        else if (len == 7) return 5;
        else return 11;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}

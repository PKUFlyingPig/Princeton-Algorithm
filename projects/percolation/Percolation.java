/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufForFull;
    private boolean[][] ifOpen;
    private int gridwidth;
    private int opensites;
    private int[] dx = {-1, 0, 0, 1};
    private int[] dy = {0, -1, 1, 0};
    private boolean isPercolated;
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n-by-n grid's n must be positive");
        }
        uf = new WeightedQuickUnionUF(n * n + 2);
        ufForFull = new WeightedQuickUnionUF(n * n + 1);
        gridwidth = n;
        opensites = 0;
        isPercolated = false;
        ifOpen = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ifOpen[i][j] = false;
            }
        }
    }

    /** decide if given point is in the grid. */
    private boolean ifInGrid(int row, int col) {
        if (row <= 0 || row > gridwidth || col <= 0 || col > gridwidth) {
            return false;
        }
        return true;
    }

    /** connect the given site with its neighbours if the neighbour has been open. */
    private void connectNeighbours(int row, int col) {
        int newRow, newCol;
        int currIndex = calcIndex(row, col);
        for (int i = 0; i < 4; i++) {
            newRow = row + dx[i];
            newCol = col + dy[i];
            if (ifInGrid(newRow, newCol) && isOpen(newRow, newCol)) {
                uf.union(calcIndex(newRow, newCol), currIndex);
                ufForFull.union(calcIndex(newRow, newCol), currIndex);
            }
        }
    }

    /** opens the site (row, col) if it is not open already. */
    public void open(int row, int col) {
        if (!ifInGrid(row, col)) {
            throw new IllegalArgumentException("the given site (row, col) must in the n-by-n grid");
        }
        int currIndex = calcIndex(row, col);
        if (!ifOpen[row - 1][col - 1]) {
            ifOpen[row - 1][col - 1] = true;
            if (row == 1) {
                uf.union(0, currIndex);
                ufForFull.union(0, currIndex);
            }
            if (row == gridwidth) {
                uf.union(gridwidth * gridwidth + 1, currIndex);
            }
            connectNeighbours(row, col);
            opensites++;
        }
    }

    /** is the site (row, col) open? */
    public boolean isOpen(int row, int col) {
        if (!ifInGrid(row, col)) {
            throw new IllegalArgumentException("the given site (row, col) must in the n-by-n grid");
        }
        return ifOpen[row - 1][col - 1];
    }

    /** calculate the index for the given site (row, col), where the index starts at 1. */
    private int calcIndex(int row, int col) {
        return (row - 1) * gridwidth + col;
    }

    /** is the site (row, col) full? */
    public boolean isFull(int row, int col) {
        if (!ifInGrid(row, col)) {
            throw new IllegalArgumentException("the given site (row, col) must in the n-by-n grid");
        }
        return ufForFull.find(calcIndex(row, col)) == ufForFull.find(0);
    }

    /** returns the number of open sites. */
    public int numberOfOpenSites() {
        return opensites;
    }

    /** does the system percolate? */
    public boolean percolates() {
        return uf.find(0) == uf.find(gridwidth * gridwidth + 1);
    }

    public static void main(String[] args) {
        Percolation perc = new Percolation(3);
        perc.open(1, 3);
        perc.open(2, 3);
        perc.open(3, 3);
        perc.open(3, 1);
    }
}

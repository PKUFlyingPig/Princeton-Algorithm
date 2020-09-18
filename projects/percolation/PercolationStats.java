/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] percoThreshold;
    private double area;
    private int gridWidth;
    private int T;

    /** perform independent trials on an n-by-n grid. */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0){
            throw new IllegalArgumentException();
        }
        gridWidth = n;
        area = n * n;
        T = trials;
        percoThreshold = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                perc.open(row, col);
            }
            percoThreshold[i] = perc.numberOfOpenSites() / area;
        }
    }

    /** sample mean of percolation threshold. */
    public double mean() {
        return StdStats.mean(percoThreshold);
    }

    /** sample standard deviation of percolation threshold. */
    public double stddev() {
        return StdStats.stddev(percoThreshold);
    }

    /** low endpoint of 95% confidence interval. */
    public double confidenceLo() {
        double pBar = mean();
        double s = stddev();
        return pBar - 1.96 * s / Math.sqrt(T);
    }

    /** high endpoint of 95% confidence interval. */
    public double confidenceHi() {
        double pBar = mean();
        double s = stddev();
        return pBar + 1.96 * s / Math.sqrt(T);
    }

    /** test client (see below). */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java-algs4 PercolationStats n T");
            return;
        }
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats percStats = new PercolationStats(n, T);
        System.out.println("mean                    = " + percStats.mean());
        System.out.println("stddev                  = " + percStats.stddev());
        System.out.println("95% confidence interval = [" + percStats.confidenceLo()
                                   + ", " + percStats.confidenceHi() + "]");
    }
}

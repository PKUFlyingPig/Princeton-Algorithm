/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private List<LineSegment> colliLineSegments= new ArrayList<>();
    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        // corner case checking
        if (points == null) {
            throw new IllegalArgumentException("argument to constructor is null");
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("one point is null");
            }
        }
        int len = points.length;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("repeated point");
                }
            }
        }
        if (len < 4) {
            return;
        }

        points = Arrays.copyOf(points, len);
        Arrays.sort(points);

        Point[] tmp = Arrays.copyOf(points, len);
        for (Point p : points) {
            Arrays.sort(tmp, p.slopeOrder());
            // for (Point x : tmp) {
            //     System.out.print(x + " ");
            // }
            // System.out.println();
            for (int i = 1; i < len;) {
                int j = i + 1;
                while (j < len && p.slopeTo(tmp[i]) == p.slopeTo(tmp[j])) {
                    j++;
                }
                if (j - i >= 3 && tmp[0].compareTo(min(tmp, i, j - 1)) < 0) {
                    colliLineSegments.add(new LineSegment(tmp[0], max(tmp, i, j - 1)));
                }
                if (j == len) {
                    break;
                }
                i = j;
            }
        }
    }

    private Point min(Point[] a, int lo, int hi) {
        if (lo > hi || a == null) {
            throw new IllegalArgumentException();
        }
        Point ret = a[lo];
        for (int i = lo + 1; i <= hi; i++) {
            if (ret.compareTo(a[i]) > 0) {
                ret = a[i];
            }
        }
        return ret;
    }

    private Point max(Point[] a, int lo, int hi) {
        if (lo > hi || a == null) {
            throw new IllegalArgumentException();
        }
        Point ret = a[lo];
        for (int i = lo + 1; i <= hi; i++) {
            if (ret.compareTo(a[i]) < 0) {
                ret = a[i];
            }
        }
        return ret;
    }

    // the number of line segments
    public int numberOfSegments() {
        return colliLineSegments.size();
    }
    // the line segments
    public LineSegment[] segments() {
        LineSegment[] ret = new LineSegment[colliLineSegments.size()];
        int i = 0;
        for (LineSegment x : colliLineSegments) {
            ret[i++] = x;
        }
        return ret;
    }
    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

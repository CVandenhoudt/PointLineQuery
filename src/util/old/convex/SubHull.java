/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.old.convex;

import java.util.Arrays;
import util.geometry.Point;
import util.old.geometry.PointSet;

/**
 *
 * @author vandenboer
 */
public class SubHull {

    private PointSet<ConvexPoint> points;
    
    public SubHull() {
        this.points = new PointSet();
    }
    
    private SubHull(Point[] points) {
        for (Point p : points) {
            this.addPoint(p);
        }
    }
    
    public final void addPoint(Point p) {
        this.points.add(new ConvexPoint(p));
    }

    public PointSet<ConvexPoint> getPoints() {
        return points;
    }
    
    /**
     * https://en.wikibooks.org/wiki/Algorithm_Implementation/Geometry/Convex_hull/Monotone_chain#Java
     */
    public static SubHull calculateUpperHull(PointSet<Point> points) {
        int n = points.getPoints().size();
        int k = 0;
        Point[] P = points.getPoints().toArray(new Point[points.getPoints().size()]);
	Point[] H = new Point[2 * n];
        
        for (int i = n - 2, t = k + 1; i >= 0; i--) {
            try {
                while (k >= t && cross(H[k - 2], H[k - 1], P[i]) <= 0)
                    k--;
            } catch (ArrayIndexOutOfBoundsException aioob) {}
            H[k++] = P[i];
        }
        
        if (k > 1) {
            H = Arrays.copyOfRange(H, 0, k - 1); // remove non-hull vertices after k; remove k - 1 which is a duplicate
        }
        
        return new SubHull(H);
    }
    
    public static SubHull calculateLowerHull(PointSet points) {
        int n = points.getPoints().size();
        int k = 0;
        Point[] P = (Point[])(points.getPoints().toArray());
	Point[] H = new Point[2 * n];
        
        for (int i = 0; i < n; ++i) {
            try {
                while (k >= 2 && cross(H[k - 2], H[k - 1], P[i]) <= 0)
                    k--;
            } catch (ArrayIndexOutOfBoundsException aioob) {}
            H[k++] = P[i];
        }
        
        if (k > 1) {
            H = Arrays.copyOfRange(H, 0, k - 1); // remove non-hull vertices after k; remove k - 1 which is a duplicate
        }
        
        return new SubHull(H);
    }
    
    private static double cross(Point O, Point A, Point B) {
        return (A.x - O.x) * (B.y - O.y) - (A.y - O.y) * (B.x - O.x);
    }
    
    public static SubHull linkSubHulls(SubHull h1, SubHull h2) throws ConvexHullSizeException {
        if (h2.getPoints().getPoints().size() < 2) {
            throw new ConvexHullSizeException("Linking convex hulls with 1 or 0 points not possible");
        }
        h1.points.getPoints().first().addLink(h2.points.getPoints().first());
        h1.points.getPoints().first().addLink(((ConvexPoint[])(h2.points.getPoints().toArray()))[2]);
        
        return null;
        
    }
    
    
    
}

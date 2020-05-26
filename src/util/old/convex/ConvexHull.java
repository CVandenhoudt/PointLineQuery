/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.old.convex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import util.geometry.Line;
import util.geometry.Point;
import util.old.geometry.PointSet;

/**
 *
 * @author vandenboer
 */
public class ConvexHull {
    
    private Point xMin;
    private Point xMax;
    private Point yMin;
    private Point yMax;
    private SubHull lower;
    private SubHull upper;
    private Point[] convex;

    public ConvexHull(PointSet<Point> points) {
        this.xMin = points.getPoints().first();
        this.xMax = points.getPoints().last();
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE; 
        points.getPoints().forEach((p) -> {
            if (p.y < min) {
                this.yMin = p;
            } else if (p.y > max) {
                this.yMax = p;
            }
        });
        
        calculateHull(points);
    }
    
    private void calculateHull(PointSet<Point> points) {
        if (points.getPoints().size() < 3) {
            return;
        }
        
        this.convex = getConvexPoints(points);
        
        this.upper = new SubHull();
        
        int i = 0;
        while (!this.convex[i].equals(this.xMax)) {
            this.upper.addPoint(this.convex[i]);
            i++;
        }
        this.upper.addPoint(this.convex[i]);
        
        this.lower = new SubHull();
        
        for (; i < this.convex.length; i++) {
            this.lower.addPoint(this.convex[i]);
        }
        
        this.lower.addPoint(xMin);
    }

    public Point getxMin() {
        return xMin;
    }

    public Point getxMax() {
        return xMax;
    }

    public Point getyMin() {
        return yMin;
    }

    public Point getyMax() {
        return yMax;
    }

    public SubHull getLower() {
        return lower;
    }

    public SubHull getUpper() {
        return upper;
    }
    
    public Line[] getLines() {
        Line[] lines = new Line[this.convex.length];
        
        for (int i = 0; i < lines.length - 1; i++) {
            lines[i] = new Line(this.convex[i], this.convex[i + 1]);
        }
        
        lines[lines.length - 1] = new Line(this.convex[this.convex.length - 1], this.xMin);
        
        return lines;
    }
    
    public static ConvexHull[] convexLayers(PointSet<Point> points) {
        List<ConvexHull> convexLayers = new ArrayList<>();
        ConvexHull hull;
        
        while (points.getPoints().size() > 2) {
            System.out.println("Creating layer " + convexLayers.size());
            hull = new ConvexHull(points);
            convexLayers.add(hull);
            points = removePointOfConvex(points, hull);
        }
        
        return convexLayers.toArray(new ConvexHull[convexLayers.size()]);
    }
    
    private static double cross(Point O, Point A, Point B) {
        return (A.x - O.x) * (double) (B.y - O.y) - (A.y - O.y) * (double) (B.x - O.x);
    }
    
    private static Point[] getConvexPoints(PointSet<Point> points) {
        Point[] P = points.getPoints().toArray(new Point[points.getPoints().size()]);
        if (P.length > 1) {
            int n = P.length, k = 0;
            Point[] H = new Point[2 * n];

            // Build lower hull
            for (int i = 0; i < n; ++i) {
                while (k >= 2 && cross(H[k - 2], H[k - 1], P[i]) <= 0) {
                    k--;
                }
                H[k++] = P[i];
            }

            // Build upper hull
            for (int i = n - 2, t = k + 1; i >= 0; i--) {
                while (k >= t && cross(H[k - 2], H[k - 1], P[i]) <= 0) {
                    k--;
                }
                H[k++] = P[i];
            }
            if (k > 1) {
                H = Arrays.copyOfRange(H, 0, k - 1); // remove non-hull vertices after k; remove k - 1 which is a duplicate
            }
            return H;
        } else if (P.length <= 1) {
            return P;
        } else {
            return null;
        }
    }
    
    public static PointSet<Point> removePointOfConvex(PointSet<Point> points, ConvexHull convex) {
        for (Point point : convex.convex) {
            points.remove(point);
        }
        return points;
    }
    
}

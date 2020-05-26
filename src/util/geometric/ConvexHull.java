package util.geometric;

/* 
 * Convex hull algorithm - Library (Java)
 * 
 * Copyright (c) 2017 Project Nayuki
 * https://www.nayuki.io/page/convex-hull-algorithm
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program (see COPYING.txt and COPYING.LESSER.txt).
 * If not, see <http://www.gnu.org/licenses/>.
 */
//import java.util.Objects;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import util.geometry.Line;
import util.geometry.Point;

public final class ConvexHull {

    // Returns a new list of points representing the convex hull of
    // the given set of points. The convex hull excludes collinear points.
    // This algorithm runs in O(n log n) time.
    public static List<Point> makeHull(List<Point> points) {
        List<Point> newPoints = new ArrayList<>(points);
        Collections.sort(newPoints);
        return makeHullPresorted(newPoints);
    }

    // Returns the convex hull, assuming that each points[i] <= points[i + 1]. Runs in O(n) time.
    public static List<Point> makeHullPresorted(List<Point> points) {
        if (points.size() <= 1) {
            return new ArrayList<>(points);
        }

        // Andrew's monotone chain algorithm. Positive y coordinates correspond to "up"
        // as per the mathematical convention, instead of "down" as per the computer
        // graphics convention. This doesn't affect the correctness of the result.
        List<Point> upperHull = new ArrayList<>();
        for (Point p : points) {
            while (upperHull.size() >= 2) {
                Point q = upperHull.get(upperHull.size() - 1);
                Point r = upperHull.get(upperHull.size() - 2);
                if ((q.x - r.x) * (p.y - r.y) >= (q.y - r.y) * (p.x - r.x)) {
                    upperHull.remove(upperHull.size() - 1);
                } else {
                    break;
                }
            }
            upperHull.add(p);
        }
        upperHull.remove(upperHull.size() - 1);

        List<Point> lowerHull = new ArrayList<>();
        for (int i = points.size() - 1; i >= 0; i--) {
            Point p = points.get(i);
            while (lowerHull.size() >= 2) {
                Point q = lowerHull.get(lowerHull.size() - 1);
                Point r = lowerHull.get(lowerHull.size() - 2);
                if ((q.x - r.x) * (p.y - r.y) >= (q.y - r.y) * (p.x - r.x)) {
                    lowerHull.remove(lowerHull.size() - 1);
                } else {
                    break;
                }
            }
            lowerHull.add(p);
        }
        lowerHull.remove(lowerHull.size() - 1);

        if (!(upperHull.size() == 1 && upperHull.equals(lowerHull))) {
            upperHull.addAll(lowerHull);
        }
        return upperHull;
    }

    public static List<List<Point>> generateConvexLayers(List<Point> points) {
        List<List<Point>> convexLayers = new ArrayList<>();
        
        while (points.size() > 0) {
            List<Point> convexHull = makeHull(points);
            points.removeAll(convexHull);
            convexLayers.add(convexHull);
        }
        
        return convexLayers;
    }
    
    public static List<List<ConvexPoint>> genrateLinksFromConvexLayers(List<List<Point>> convexLayers) {
        List<List<ConvexPoint>> result = new ArrayList<>();
        List<ConvexPoint> links = new ArrayList<>();
        
        for (int i = 0; i < convexLayers.size(); i++) {
            if (i == 0) {
                for (Point p : convexLayers.get(i)) {
                    links.add(new ConvexPoint(p));
                }
                for (int j = 1; j < links.size(); j++) {
                    links.get(j).setRightNeighbour(links.get((j + 1) % links.size()));
                }
                links.get(0).setLeftNeighbour(links.get(links.size() - 1));
            } else {
                if (convexLayers.get(i).size() > 1) {
                    links = generateLinksFrom2Layers(links, convexLayers.get(i));
                } else {
                    links = new ArrayList<>();
                    links.add(new ConvexPoint(convexLayers.get(i).get(0)));
                    for (ConvexPoint p : result.get(i - 1)) {
                        links.get(0).addLink(p);
                    }
                }
            }
            result.add(links);
        }
        
        return result;
    }
    
    //Both layers from lower to upper hull
    public static List<ConvexPoint> generateLinksFrom2Layers(List<ConvexPoint> outerHull, List<Point> selectedHull) {
        List<ConvexPoint> result = new ArrayList<>();
        ConvexPoint bp;
        Point max = getMax(selectedHull);
        
        int i = 0;
        int c = 0;
        while (!selectedHull.get(i).equals(max)) {
            bp = new ConvexPoint(selectedHull.get(i));
            try {
                while (bp.x > outerHull.get(c).x) {
                    bp.addLink(outerHull.get(c));
                    c++;
                }
                bp.addLink(outerHull.get(c));
            } catch (IndexOutOfBoundsException iobe) {
            }
            if (bp.getLinks().size() == 1) {
                try {
                    bp.addLink(outerHull.get(c - 1));
                } catch (IndexOutOfBoundsException iobe) {
                    bp.addLink(outerHull.get(c + 1));
                }
            }
            i++;
            result.add(bp);
        }
        
        while (i < selectedHull.size()) {
            bp = new ConvexPoint(selectedHull.get(i));
            try {
                while (bp.x < outerHull.get(c).x) {
                    bp.addLink(outerHull.get(c));
                    c++;
                }
                bp.addLink(outerHull.get(c));
            } catch (IndexOutOfBoundsException iobe) {
            }
            if (bp.getLinks().size() == 1) {
                try {
                    bp.addLink(outerHull.get(c + 1));
                } catch (IndexOutOfBoundsException iobe) {
                    bp.addLink(outerHull.get(c - 1));
                }
            }
            i++;
            result.add(bp);
        }
        
        while (c < outerHull.size()) {
            result.get(0).addLink(outerHull.get(c));
            c++;
        }
        
        if (result.get(0).getLinks().size() <= 1) {
            result.get(0).addLink(outerHull.get(outerHull.size() - 1));
        }
        
        if (result.get(result.size() - 1).getLinks().size() <= 1) {
            outerHull.stream().filter((p) -> (result.get(result.size() - 1).y < p.y)).forEachOrdered((p) -> {
                result.get(result.size() - 1).addLink(p);
            });
        }
        
        result.get(0).setLeftNeighbour(result.get(result.size() - 1));
        
        for (int j = 1; j < result.size(); j++) {
            result.get(j).setRightNeighbour(result.get((j + 1) % result.size()));
        }
        
        return result;
    }
    
    private static Point getMax(List<Point> points) {
        Point max = points.get(0);
        
        for (Point point : points) {
            if (point.x > max.x) {
                max = point;
            } else if (point.x == max.x && point.y > max.y) {
                max = point;
            }
        }
        
        return max;
    }
    
    public static Line[] makeLinesFromConvex(List<Point> convex) {
        Line[] lines = new Line[convex.size()];
        for (int i = 0; i < convex.size(); i++) {
            lines[i] = new Line(convex.get(i), convex.get((i + 1) % convex.size()));
        }
        return lines;
    }
    
    public static Line[] makeLinesFromLinks(List<List<ConvexPoint>> linkedConvexLayers) {
        List<Line> lines = new ArrayList<>();
        
        linkedConvexLayers.forEach((linkedConvexLayer) -> {
            linkedConvexLayer.forEach((convexPoint) -> {
                convexPoint.getLinks().forEach((linkedPoint) -> {
                    lines.add(new Line(convexPoint, linkedPoint));
                });
            });
        });
        
        return lines.toArray(new Line[lines.size()]);
    }
   
}

//final class Point implements Comparable<Point> {
//	
//	public final double x;
//	public final double y;
//	
//	
//	public Point(double x, double y) {
//		this.x = x;
//		this.y = y;
//	}
//	
//	
//	public String toString() {
//		return String.format("Point(%g, %g)", x, y);
//	}
//	
//	
//	public boolean equals(Object obj) {
//		if (!(obj instanceof Point))
//			return false;
//		else {
//			Point other = (Point)obj;
//			return x == other.x && y == other.y;
//		}
//	}
//	
//	
//	public int hashCode() {
//		return Objects.hash(x, y);
//	}
//	
//	
//	public int compareTo(Point other) {
//		if (x != other.x)
//			return Double.compare(x, other.x);
//		else
//			return Double.compare(y, other.y);
//	}
//	
//}

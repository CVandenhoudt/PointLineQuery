/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.geometric;

import java.util.ArrayList;
import java.util.List;
import util.geometry.Function;
import util.geometry.Point;

/**
 *
 * @author vandenboer
 */
public final class GeometricSolution {
    
    private int size;
    private List<List<ConvexPoint>> linkedLayers;
    private List<List<Point>> convexLayers;

    public GeometricSolution(List<Point> points) {
        this.size = points.size();
        this.convexLayers = ConvexHull.generateConvexLayers(points);
        this.linkedLayers = ConvexHull.genrateLinksFromConvexLayers(this.convexLayers);
    }
    
    public List<Point> getPointsAbove(Function f) {
        List<Point> result = new ArrayList<>();
        int intersectingLayer = this.getLastIntersectingLayer(f);
        
        if (intersectingLayer == -1) {
            this.convexLayers.forEach((convexLayer) -> {
                convexLayer.forEach((point) -> {
                    result.add(point);
                });
            });
            return result;
        }
        
        if (intersectingLayer < (this.convexLayers.size() - 1) && f.pointIsAbove(this.convexLayers.get(intersectingLayer + 1).get(0).x, this.convexLayers.get(intersectingLayer + 1).get(0).y)) {
            for (int i = intersectingLayer; i < this.convexLayers.size(); i++) {
                result.addAll(this.convexLayers.get(i));
            }
        }
        
        List<ConvexPoint> convexHullPoints = getPointPerConvexHull(intersectingLayer, f, true);
        
        if (convexHullPoints == null) {
            return result;
        }
        
        convexHullPoints.forEach((convexHullPoint) -> {
            result.addAll(getPointsPerConvexHullPoint(convexHullPoint, f, true));
        });
        
        return result;
    }
    
    public List<Point> getPointsBelow(Function f) {
        List<Point> result = new ArrayList<>();
        int intersectingLayer = this.getLastIntersectingLayer(f);
        
        if (intersectingLayer < (this.convexLayers.size() - 1) && f.pointIsBelow(this.convexLayers.get(intersectingLayer + 1).get(0).x, this.convexLayers.get(intersectingLayer + 1).get(0).y)) {
            for (int i = intersectingLayer; i < this.convexLayers.size(); i++) {
                result.addAll(this.convexLayers.get(i));
            }
        }
        
        List<ConvexPoint> convexHullPoints = getPointPerConvexHull(intersectingLayer, f, false);
        
        if (convexHullPoints == null) {
            return result;
        }
        
        convexHullPoints.forEach((convexHullPoint) -> {
            result.addAll(getPointsPerConvexHullPoint(convexHullPoint, f, false));
        });
        
        return result;
    }
    
    private int getLastIntersectingLayer(Function f) {
        int i = 0;
        
        for (; i < convexLayers.size(); i++) {
            List<Point> get = convexLayers.get(i);
            if (!instersectsLayer(get, f)) {
                return --i;
            }
        }
        
        return --i;
    }
    
    private boolean instersectsLayer(List<Point> layer, Function f) {
        if (layer.size() < 2) {
            return false;
        }
//        Line l;
//        for (int i = 0; i < layer.size(); i++) {
//            l = new Line(layer.get(i), layer.get((i+1) % (layer.size())));
//            if (l.intersects(f)) {
//                return true;
//            }
//        }
        boolean isAbove = f.pointIsAbove(layer.get(0).x, layer.get(0).y);
        return layer.stream().anyMatch((p) -> (f.pointIsAbove(p.x, p.y) != isAbove));
    }

    private List<ConvexPoint> getPointPerConvexHull(int startLayer, Function f, boolean isAbove) {
        List<ConvexPoint> points = new ArrayList<>();
        if (startLayer == this.linkedLayers.size()) {
            startLayer--;
        }
        ConvexPoint selectedPoint = this.linkedLayers.get(startLayer).get(0);
        if (isAbove) {
            //Locate starting point
            for (int i = this.linkedLayers.get(startLayer).size() - 1; i >= 0; i--) {
                selectedPoint = this.linkedLayers.get(startLayer).get(i);
                if (f.pointIsAbove(selectedPoint.x , selectedPoint.y)) {
                    points.add(selectedPoint);
                    break;
                }
            }
            
            if (points.isEmpty() && startLayer != 0) {
                if ((points = getPointPerConvexHull(startLayer - 1, f, isAbove)) != null) {
                    return points;
                }
                throw new AssertionError("Convex hull given with no intersection to function");
            } else if (startLayer == 0) {
                return null;
            }
            
            boolean isChanged;
            while (!selectedPoint.getLinks().isEmpty()) {
                isChanged = false;
                for (ConvexPoint p : selectedPoint.getLinks()) {
                    if (f.pointIsAbove(p.x, p.y)) {
                        points.add(p);
                        selectedPoint = p;
                        isChanged = true;
                        break;
                    }
                }
                if (!isChanged) {
                    if (selectedPoint.getLeftNeighbour() == null) {
                        return points;
                    }
                    selectedPoint = selectedPoint.getLeftNeighbour();
                }
            }
        } else {
            //Locate starting point
            for (int i = 0; i < this.linkedLayers.get(startLayer - 1).size(); i++) {
                selectedPoint = this.linkedLayers.get(startLayer - 1).get(i);
                if (f.pointIsBelow(selectedPoint.x , selectedPoint.y)) {
                    points.add(selectedPoint);
                    break;
                }
            }
            
            if (points.isEmpty() && startLayer != 0) {
                if ((points = getPointPerConvexHull(startLayer - 1, f, isAbove)) != null) {
                    return points;
                }
                throw new AssertionError("Convex hull given with no intersection to function");
            } else if (startLayer == 0) {
                return null;
            }
            
            boolean isChanged;
            while (!selectedPoint.getLinks().isEmpty()) {
                isChanged = false;
                for (ConvexPoint p : selectedPoint.getLinks()) {
                    if (f.pointIsBelow(p.x, p.y)) {
                        points.add(p);
                        selectedPoint = p;
                        isChanged = true;
                        break;
                    }
                }
                if (!isChanged) {
                    throw new AssertionError("Error cannot find appropiate link");
                }
            }
        }
        
        return points;
    }
    
    private List<Point> getPointsPerConvexHullPoint(ConvexPoint point, Function f, boolean isAbove) {
        List<Point> result = new ArrayList<>();
        ConvexPoint selected = point;
        if (isAbove) {
            while (selected != null && f.pointIsAbove(selected.x, selected.y)) {
                result.add(new Point(selected.x, selected.y));
                selected = selected.getLeftNeighbour();
            }
            selected = point.getRightNeighbour();
            while (selected != null && f.pointIsAbove(selected.x, selected.y)) {
                result.add(new Point(selected.x, selected.y));
                selected = selected.getRightNeighbour();
            }
        } else {
            while (selected != null && f.pointIsBelow(selected.x, selected.y)) {
                result.add(new Point(selected.x, selected.y));
                selected = selected.getLeftNeighbour();
            }
            selected = point.getRightNeighbour();
            while (selected != null && f.pointIsBelow(selected.x, selected.y)) {
                result.add(new Point(selected.x, selected.y));
                selected = selected.getRightNeighbour();
            }
        }
        return result;
    }

    public List<List<Point>> getConvexLayers() {
        return convexLayers;
    }

    public List<List<ConvexPoint>> getLinkedLayers() {
        return linkedLayers;
    }
    
    public int getSize() {
        return this.size;
    }
    
}

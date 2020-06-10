/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.avl.update;

import java.util.List;
import util.geometry.Point;

/**
 *
 * @author vandenboer
 */
public class AVLNode implements Comparable<AVLNode> {
    
    private double fillIn;
    private List<Point> points;

    public AVLNode(double fillIn, List<Point> points) {
        this.fillIn = fillIn;
        this.points = points;
    }

    @Override
    public int compareTo(AVLNode t) {
        return Double.compare(fillIn, t.fillIn);
    }
    
    public void addPoint(Point p) {
        this.points.add(p);
    }

    public List<Point> getPoints() {
        return points;
    }

    public double getFillIn() {
        return fillIn;
    }
    
}

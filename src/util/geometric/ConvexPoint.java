/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.geometric;

import java.util.ArrayList;
import java.util.List;
import util.geometry.Point;

/**
 *
 * @author vandenboer
 */
public class ConvexPoint extends Point {

    /**
     * when empty, it is the final layers
     */
    private List<ConvexPoint> links;
    private ConvexPoint leftNeighbour;
    private ConvexPoint rightNeighbour;
    
    public ConvexPoint(double x, double y) {
        super(x, y);
        this.links = new ArrayList<>();
    }

    public ConvexPoint(Point p) {
        this(p.x, p.y);
    }

    public List<ConvexPoint> getLinks() {
        return links;
    }
    
    public void addLink(ConvexPoint point) {
        this.links.add(point);
    }

    public ConvexPoint getLeftNeighbour() {
        return leftNeighbour;
    }

    public void setLeftNeighbour(ConvexPoint leftNeighbour) {
        this.leftNeighbour = leftNeighbour;
        this.leftNeighbour.rightNeighbour = this;
    }

    public ConvexPoint getRightNeighbour() {
        return rightNeighbour;
    }

    public void setRightNeighbour(ConvexPoint rightNeighbour) {
        this.rightNeighbour = rightNeighbour;
        this.rightNeighbour.leftNeighbour = this;
    }
    
}

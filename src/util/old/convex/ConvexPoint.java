/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.old.convex;

import java.util.ArrayList;
import java.util.List;
import util.geometry.Point;

/**
 *
 * @author vandenboer
 */
public class ConvexPoint extends Point {
    
    private List<ConvexPoint> links; 
    
    public ConvexPoint(Point p) {
        super(p.x, p.y);
        this.links = new ArrayList<>();
    }
    
    public void addLink(ConvexPoint p) {
        this.links.add(p);
    }

    public List<ConvexPoint> getLinks() {
        return links;
    }
    
}

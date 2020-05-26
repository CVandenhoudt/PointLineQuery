/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.geometry;

import java.awt.geom.Line2D;

/**
 *
 * @author vandenboer
 */
public class Line {
    
    private Point start;
    private Point end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }
    
    public boolean intersects(Function f) {
        Point p1 = new Point(this.start.x, f.getYFromX(this.start.x));
        Point p2 = new Point(this.end.x, f.getYFromX(this.end.x));
        
        Line2D l = new Line2D.Double(p1.x, p1.y, p2.x, p2.y);
        
        return l.intersectsLine(this.start.x, this.start.y, this.end.x, this.end.y);
    }
    
}

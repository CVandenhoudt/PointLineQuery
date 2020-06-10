/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.avl.update;

import util.geometry.Function;
import util.geometry.Point;

@Deprecated
/**
 *
 * @author vandenboer
 */
public class AVLPoint extends Point {
    
    private Function f;
    
    public AVLPoint(double x, double y) {
        super(x, y);
    }
    
    public AVLPoint(Point p) {
        super(p.x, p.y);
    }

    public void setFunction(Function f) {
        this.f = f;
    }

    public Function getFunction() {
        return f;
    }

    @Override
    public int compareTo(Point point) {
        if (point instanceof AVLPoint) {
            AVLPoint avlp = (AVLPoint)point;
            if (!avlp.f.equals(this.f)) {
                throw new AssertionError("Comparing avl point with different functions");
            }
            return Double.compare(f.a * point.y + f.b * point.x, f.a * this.y + f.b * this.x);
        } else {
            throw new AssertionError("Comparing point to avl point");
        }
    }
    
}

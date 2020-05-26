/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.brute;

import java.util.ArrayList;
import java.util.List;
import util.geometry.Function;
import util.geometry.Point;

/**
 *
 * @author vandenboer
 */
public class BruteForceSolution {
    
    private List<Point> points;

    public BruteForceSolution(List<Point> points) {
        this.points = points;
    }
    
    public List<Point> getPointsAbove(Function f) {
        List<Point> result = new ArrayList<>();
        
        this.points.forEach((point) -> {
            if (f.pointIsAbove(point.x, point.y)) {
                result.add(point);
            }
        });
        
        return result;
    }
    
    public List<Point> getPointsBelow(Function f) {
        List<Point> result = new ArrayList<>();
        
        this.points.forEach((point) -> {
            if (f.pointIsBelow(point.x, point.y)) {
                result.add(point);
            }
        });
        
        return result;
    }
    
}

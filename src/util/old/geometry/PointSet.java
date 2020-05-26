/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.old.geometry;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;
import util.geometry.Point;

/**
 *
 * @author vandenboer
 */
public class PointSet<T extends Point> {
    
    private SortedSet<T> points;

    public PointSet(T... points) {
        this.points = new TreeSet<>(Arrays.asList(points));
    }

    public PointSet() {
        this.points = new TreeSet<>();
    }

    public SortedSet<T> getPoints() {
        return points;
    }
    
    public void add(T p) {
        if (this.points.contains(p)) {
            throw new AssertionError("Point already in collection");
        }
        this.points.add(p);
    }
    
    public void remove(T p) {
        this.points.remove(p);
    }
    
}

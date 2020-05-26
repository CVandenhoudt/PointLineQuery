/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.avl;

import java.util.List;
import util.geometry.Point;

/**
 *
 * @author vandenboer
 */
public class NodeData implements Comparable<Object> {

    private double key;
    private List<Point> permutation;

    public NodeData(double key, List<Point> permutation) {
        this.key = key;
        this.permutation = permutation;
    }
    
    @Override
    public int compareTo(Object t) {
        if (t instanceof Double) {
            return compare((Double)t);
        } else if (t instanceof NodeData) {
            return compare(((NodeData)t).key);
        }
        throw new AssertionError("Not comparable");
    }
    
    private int compare(double d) {
        if (d > this.key) {
            return -1;
        } else if (d == this.key) {
            return 0;
        }
        return 1;
    }

    public List<Point> getPermutation() {
        return permutation;
    }

    public double getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "Node Data: " + this.key; 
    }
    
}

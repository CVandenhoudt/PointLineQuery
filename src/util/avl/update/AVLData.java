/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.avl.update;

import java.util.List;
import util.avl.NodeData;
import util.geometry.Point;
import util.tree.AVLTree;

/**
 *
 * @author vandenboer
 */
public class AVLData implements Comparable<Object>{
    
    private double key;
    private AVLTree permutation;

    public AVLData(double key, AVLTree permutation) {
        this.key = key;
        this.permutation = permutation;
    }
    
    @Override
    public int compareTo(Object t) {
        if (t instanceof Double) {
            return compare((Double)t);
        } else if (t instanceof NodeData) {
            return compare(((AVLData)t).key);
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

    public AVLTree getPermutation() {
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

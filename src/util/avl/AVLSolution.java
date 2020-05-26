/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.avl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import util.geometry.Function;
import util.geometry.Point;
import util.tree.AVLTree;

/**
 *
 * @author vandenboer
 */
public class AVLSolution {
    
    private int size;
    private AVLTree tree;

    public AVLSolution(List<Point> points) {
        this.size = points.size();
        createTree(points);
    }
    
    private void createTree(List<Point> points) {
        this.tree = new AVLTree();
        Map permutations = Permutations.generatePermutations(points);
        
        NodeData data;
        Iterator it = permutations.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            data = new NodeData((Double)pair.getKey(), (List<Point>)pair.getValue());
            tree.insert(data);
        }
    }
    
    public List<Point> getPointsAbove(Function f) {
        List<Point> result = new ArrayList<>();
        List<Point> permutation = ((NodeData)(tree.query( - (f.b / f.a)).getValue())).getPermutation();
        Collections.reverse(permutation);
    
        Iterator it = permutation.iterator();
        Point p;
        while (it.hasNext()) {
            p = (Point)it.next();
            if (f.pointIsAbove(p.x, p.y)) {
                result.add(p);
            } else {
                return result;
            }
        }
        
        return result;
    }
    
    public List<Point> getPointsBelow(Function f) {
        List<Point> result = new ArrayList<>();
        List<Point> permutation = ((NodeData)(tree.query( - (f.b / f.a)).getValue())).getPermutation();
        
        Iterator it = permutation.iterator();
        Point p;
        while (it.hasNext()) {
            p = (Point)it.next();
            if (f.pointIsBelow(p.x, p.y)) {
                result.add(p);
            } else {
                return result;
            }
        }
        
        return result;
    }
    
    public int getSize() {
        return this.size;
    }
    
}

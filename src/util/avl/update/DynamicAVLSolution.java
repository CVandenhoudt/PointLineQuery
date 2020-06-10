/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.avl.update;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import util.geometry.Function;
import util.geometry.Point;
import util.tree.AVLTree;
import util.tree.Node;

/**
 *
 * @author vandenboer
 */
public class DynamicAVLSolution {
    
    private int size;
    private AVLTreeList tree;
    private List<Point> points;

    public DynamicAVLSolution(List<Point> points) {
        this.size = points.size();
        this.points = points;
        createTree(points);
    }
    
    private void createTree(List<Point> points) {
        this.tree = new AVLTreeList();
        Map permutations = util.avl.Permutations.generatePermutations(points);
        
        AVLData data;
        Iterator it = permutations.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            data = new AVLData((Double)pair.getKey(), (AVLTree)pair.getValue());
            tree.insert(data);
        }
    }
    
    public List<Point> getPointsAbove(Function f) {
        List<Point> result = new ArrayList<>();
        AVLTree permutation = ((AVLData)(tree.query( - (f.b / f.a)).getValue())).getPermutation();
    
        //TODO: Get all elements right from the 0 elements
        List<Node> nodes = permutation.getAllElementsRightFrom(0);
        
        nodes.stream().map((n) -> (AVLNode)(n.getValue())).forEachOrdered((node) -> {
            result.addAll(node.getPoints());
        });
        
        return result;
    }
    
    public List<Point> getPointsBelow(Function f) {
        List<Point> result = new ArrayList<>();
        AVLTree permutation = ((AVLData)(tree.query( - (f.b / f.a)).getValue())).getPermutation();
        
        //TODO: Get all elements left from the 0 elements
        List<Node> nodes = permutation.getAllElementsLeftFrom(0);
        
        nodes.stream().map((n) -> (AVLNode)(n.getValue())).forEachOrdered((node) -> {
            result.addAll(node.getPoints());
        });
        
        return result;
    }
    
    public int getSize() {
        return this.size;
    }
    
}

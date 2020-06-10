/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.avl.update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import util.geometry.Function;
import util.geometry.Point;
import util.tree.AVLTree;

/**
 *
 * @author vandenboer
 */
public class Permutations {
    
    /**
     * 
     * @param points to generate the permutations of.
     * @return map with the Double as key value for witch function it counts coupled with the list
     */
    public static Map<Double, AVLTree> generatePermutations(List<Point> points) {
        Map<Double, AVLTree> permutations = new HashMap<>();
        double key;
        
        for (Point point : points) {
            if (point.x == 0) {
                key = Double.NaN;
            } else {
                key = point.y / point.x;
            }
            if (!permutations.containsKey(key)) {
                permutations.put(key, sortPointsByFunction(points, new Function(point.y / point.x)));
            }
        }
        
        return permutations;
    }
    
    private static AVLTree sortPointsByFunction(List<Point> points, Function f) {
        Map<Double, List<Point>> sorted = new TreeMap<>();
        AVLTree result = new AVLTree();
        
        points.forEach((point) -> {
            double d = f.a * point.y + f.b * point.x;
            if (!sorted.containsKey(d)) {
                sorted.put(d, new ArrayList<>());
            }
            sorted.get(d).add(point);
        });
        
        Iterator it = sorted.entrySet().iterator();
        List<Point> list;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            list = (List<Point>) pair.getValue();
            double fillIn = (double)pair.getKey();
            result.insert(new AVLNode(fillIn, points));
        }
        
        return result;
    }
    
}

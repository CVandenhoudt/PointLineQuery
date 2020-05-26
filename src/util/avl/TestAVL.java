/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.avl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import main.PointsWindow;
import util.geometry.Function;
import util.geometry.Line;
import util.geometry.Point;

/**
 *
 * @author vandenboer
 */
public class TestAVL {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        List<Point> points = new ArrayList<>();
        PointsWindow window = new PointsWindow();
        
        double a = 1;
        double b = -1;
        double c = 4;
        
        Function f = new Function(a, c);
        
        //Tree
        //
        //      0.5
        //      / \
        //   0.25  \______
        //   /  \         \
        // 0.2  0.5        2
        //                / \
        //               1   4
        //                  / \
        //                 3   5
//        points.add(new Point(10, 10));
//        points.add(new Point(10, 20));
//        points.add(new Point(10, 30));
//        points.add(new Point(10, 40));
//        points.add(new Point(10, 50));
//        points.add(new Point(20, 10));
//        points.add(new Point(30, 10));
//        points.add(new Point(40, 10));
//        points.add(new Point(50, 10));
        
        Random r = new Random();
        
        for (int i = 0; i < 500; i++) {

            int x = Math.abs(r.nextInt(400));
            int y = Math.abs(r.nextInt(200));
            try {
                points.add(new Point(x, y));
            } catch (AssertionError ae) {
                i--;
            }
        }
        
        AVLSolution avl = new AVLSolution(points);
        List<Point> result = avl.getPointsAbove(f);
//        BruteForceSolution solution = new BruteForceSolution(points);
//        List<Point> result = solution.getPointsAbove(f);
        
//        Line[] lines = new Line[points.size() - 1];
//        
//        for (int i = 0; i < points.size() - 1; i++) {
//            lines[i] = new Line(points.get(i), points.get(i + 1));
//        }
//
//        window.addLines2(lines);
        
        System.out.println(points);
        System.out.println(f);
        
        points.forEach((point) -> {
            window.addPoints(point);
        });
        
        result.forEach((point) -> {
            window.addPoints2(point);
        });
        
        window.addLines(new Line(new Point(0, f.getYFromX(c)), new Point(1000, f.getYFromX(1000))));
        
        window.setVisible(true);
    }
    
}

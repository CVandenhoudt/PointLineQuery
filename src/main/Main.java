/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import util.geometric.GeometricSolution;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import util.avl.*;
import util.geometry.*;

/**
 *
 * @author vandenboer
 */
public class Main {
    
    private static GeometricSolution geometric;
    private static AVLSolution avl;
    
    public static void main(String[] args) { 
        
        int n = 5;
        final int fStart = 5;
        int f;
        int c = 4;
        
        List<Point> points;
        List<Function> functions;
        
//        solveAVL(points, functions);
//        solveGeometric(points, functions);
//        
//        Scanner scan = new Scanner(System.in);
//        while (f != 0) {
//            System.out.println("print amount of functions to query");
//            try {
//                f = Integer.parseInt(scan.nextLine());
//                functions = generateFunctions(f);
//                solveAVL(points, functions);
//                solveGeometric(points, functions);
//            } catch (NumberFormatException nfe) {
//                System.out.println("Not a number");
//                f = 1;
//            }
//        }

        for (int i = 0; i <= c; i++) {
            n = n * (int)Math.pow(10, i);
            f = fStart;
            points = generatePoints(n);
            solveAVL(points, new ArrayList<>());
            solveGeometric(points, new ArrayList<>());
            for (int ii = 0; ii <= c; ii++) {
                f = f * (int)Math.pow(10, i);
                functions = generateFunctions(f);
                solveAVL(points, new ArrayList<>());
                solveGeometric(points, new ArrayList<>());
            }
        }
        
    }
    
    public static void solveGeometric(List<Point> points, List<Function> functions) {
        long time = System.currentTimeMillis();
        final List<Point> result = new ArrayList<>();
        
        if (geometric == null || geometric.getSize() != points.size()) {
            System.out.println("Started Geometric solution data structure");
            geometric = new GeometricSolution(points);
            System.out.println("Datastructure Geometric took: " + (System.currentTimeMillis() - time) + "ms");
        }
        
        time = System.currentTimeMillis();
        System.out.println("Started Geometric query");
        functions.forEach((f) -> {
            result.clear();
            result.addAll(geometric.getPointsAbove(f));
        });
        System.out.println("Query Geometric took: " + (System.currentTimeMillis() - time) + "ms");
        
//        PointsWindow window = new PointsWindow();
//                
//        solution.getConvexLayers().forEach((convexLayer) -> {
//            window.addLines(ConvexHull.makeLinesFromConvex(convexLayer));
//            for (Point point : convexLayer) {
//                window.addPoints(point);
//            }
//        });
//        
//        window.addLines2(new Line(new Point(0, functions.get(0).getYFromX(0)), new Point(1000, functions.get(0).getYFromX(1000))));
//        
//        window.addPoints2(result.toArray(new Point[result.size()]));
//        
//        window.setVisible(true);
    }
    
    public static void solveAVL(List<Point> points, List<Function> functions) {
        long time = System.currentTimeMillis();
        
        if (avl == null || avl.getSize() != points.size()) {
            System.out.println("Started AVL solution data structure");
            avl = new AVLSolution(points);
            System.out.println("Datastructure AVL took: " + (System.currentTimeMillis() - time) + "ms");
        }
        
        time = System.currentTimeMillis();
        System.out.println("Started AVL query");
        functions.forEach((f) -> {
            List<Point> result = avl.getPointsAbove(f);
        });
        System.out.println("Query AVL took: " + (System.currentTimeMillis() - time) + "ms");
        
//        PointsWindow window = new PointsWindow();
//        points.forEach((point) -> {
//            window.addPoints(point);
//        });
//        
//        result.forEach((point) -> {
//            window.addPoints2(point);
//        });
//        
//        window.addLines(new Line(new Point(0, f.getYFromX(f.c)), new Point(1000, f.getYFromX(1000))));
//        
//        window.setVisible(true);
    }
    
    public static List<Point> generatePoints(int n) {
        List<Point> points = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < n; i++) {

            int x = Math.abs(r.nextInt(350));
            int y = Math.abs(r.nextInt(180));
            try {
                points.add(new Point(x + 10, y + 10));
            } catch (AssertionError ae) {
                i--;
            }
        }
        return points;
    }
    
    public static List<Function> generateFunctions(int n) {
        List<Function> functions = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            double a = Math.abs(r.nextDouble() % 100);
//            double b = Math.abs((r.nextDouble() % 99) + 1);
//            double c = Math.abs(r.nextDouble() % 100);
            functions.add(new Function(a/*, c*/));
        }
        return functions;
    }
    
}

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
//import java.util.Scanner;
import util.avl.*;
import util.geometry.*;
import util.other.Table;

/**
 *
 * @author vandenboer
 */
public class Main {
    
    private static GeometricSolution geometric;
    private static AVLSolution avl;
    private static boolean verbose = false;
    
    public static void main(String[] args) { 
        
        final int fStart = 1;
        final int nStart = 1;
        
        int n = nStart;
        int f = fStart;
        int c1 = 4;
        int c2 = 5;
        
        List<Point> points = new ArrayList();
        List<Function> functions = new ArrayList();
        
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

        int testSizeFunction = 1000;
        int testSizePoints = 20;
        double medA = 0;
        double medAt;
        double medG = 0;
        double medGt;
        
        double medApt;
        double medAp = 0;
        double medGpt;
        double medGp = 0;
        
        for (int i = 0; i <= c1; i++) {
            n = nStart * (int)Math.pow(10, i);
            for (int jj = 0; jj < testSizePoints; jj++) {
                points.addAll(generatePoints(n));
                medApt = System.currentTimeMillis();
                avl = new AVLSolution(points);
                medAp += System.currentTimeMillis() - medApt;
                medGpt = System.currentTimeMillis();
                geometric = new GeometricSolution(points);
                medGp += System.currentTimeMillis() - medGpt;
                points.clear();
                System.gc();
            }
            System.out.println(String.format("Medium timing for %d points for Geometric solution: %.2fms", n, medGp / testSizePoints));
            System.out.println(String.format("Medium timing for %d points for AVL solution: %.2fms", n, medAp / testSizePoints));
        }
        
        for (int i = 0; i <= c1; i++) {
            n = nStart * (int)Math.pow(10, i);
            points.clear();
            System.gc();
            points.addAll(generatePoints(n));
            avl = new AVLSolution(points);
            geometric = new GeometricSolution(points);
            for (int ii = 0; ii <= c2; ii++) {
                f = fStart * (int)Math.pow(10, ii);
                for (int jj = 0; jj < testSizeFunction; jj++) {
                    functions.clear();
                    System.gc(); 
                    functions.addAll(generateFunctions(f));
                    medAt = System.currentTimeMillis();
                    functions.forEach((fc) -> {
                        List<Point> result = avl.getPointsAbove(fc);
                    });
                    medA += System.currentTimeMillis() - medAt;
                    medGt = System.currentTimeMillis();
                    functions.forEach((fc) -> {
                        List<Point> result = geometric.getPointsAbove(fc);
                    });
                    medG += System.currentTimeMillis() - medGt;
                }
                System.out.println(String.format("Medium timing for %d functions on %d points for Geometric solution: %.2fms", f, n, medG / testSizeFunction));
                System.out.println(String.format("Medium timing for %d functions on %d points for AVL solution: %.2fms", f, n, medA / testSizeFunction));
                medG = 0;
                medA = 0;
            }
        }
        System.out.println("End");
    }
    
    public static void solveGeometric(List<Point> points, List<Function> functions) {
        long time = System.currentTimeMillis();
        final List<Point> result = new ArrayList<>();
        
        if (geometric == null || geometric.getSize() != points.size()) {
            if (verbose) {System.out.println("Started Geometric solution data structure");}
            geometric = new GeometricSolution(points);
            if (verbose) {System.out.println("Datastructure Geometric took: " + (System.currentTimeMillis() - time) + "ms");}
        }
        
        time = System.currentTimeMillis();
        if (verbose) {System.out.println("Started Geometric query");}
        functions.forEach((f) -> {
            result.clear();
            result.addAll(geometric.getPointsAbove(f));
        });
        if (verbose) {System.out.println("Query Geometric took: " + (System.currentTimeMillis() - time) + "ms");}
        
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
            if (verbose) {System.out.println("Started AVL solution data structure");}
            avl = new AVLSolution(points);
            if (verbose) {System.out.println("Datastructure AVL took: " + (System.currentTimeMillis() - time) + "ms");}
        }
        
        time = System.currentTimeMillis();
        if (verbose) {System.out.println("Started AVL query");}
        functions.forEach((f) -> {
            List<Point> result = avl.getPointsAbove(f);
        });
        if (verbose) {System.out.println("Query AVL took: " + (System.currentTimeMillis() - time) + "ms");}
        
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

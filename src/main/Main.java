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
import util.geometric.ConvexHull;
import util.geometry.*;

/**
 *
 * @author vandenboer
 */
public class Main {
    
    private static GeometricSolution geometric;
    private static AVLSolution avl;
    private static boolean verbose = false;
    
    public static void main(String[] args) { 
        PointsWindow window = new PointsWindow();
        List<Point> points = generatePoints(50);
        List<Point> pointsBuffer = new ArrayList<>();
        List<Point> result;
        List<Function> functions = generateFunctions(10);
        String text;
        
        try (Scanner input = new Scanner(System.in)) {
            pointsBuffer.addAll(points);
            
            window.clear();
            window.setVisible(true);
            window.addPoints(pointsBuffer.toArray(new Point[pointsBuffer.size()]));
            System.out.println("Press enter to start");
            text = input.nextLine();
            sleep(10000);

            solveGeometric(points, new ArrayList<>());
            geometric.getConvexLayers().forEach((convex) -> {
                window.addLines(ConvexHull.makeLinesFromConvex(convex));
            });
            sleep(15000);

            window.addLines2(ConvexHull.makeLinesFromLinks(geometric.getLinkedLayers()));
            sleep(5000);
            window.clear();
            window.addPoints(pointsBuffer.toArray(new Point[pointsBuffer.size()]));
            sleep(5000);
                
            for (Function f : functions) {
                points.clear();
                points.addAll(pointsBuffer);
                solveGeometric(points, new ArrayList<>());
                window.addPoints(pointsBuffer.toArray(new Point[pointsBuffer.size()]));
                geometric.getConvexLayers().forEach((convex) -> {
                    window.addLines(ConvexHull.makeLinesFromConvex(convex));
                });
//                sleep(5000);
                
                window.addLines2(new Line(new Point(0, f.getYFromX(0)), new Point(1000, f.getYFromX(1000))));
                sleep(5000);
                
                result = geometric.getPointsAbove(f);
                window.addPoints2(result.toArray(new Point[result.size()]));
                sleep(5000);
                window.clear();
                
                points.clear();
                points.addAll(pointsBuffer);
                solveAVL(points, new ArrayList<>());
                window.addPoints(pointsBuffer.toArray(new Point[pointsBuffer.size()]));
                window.addLines2(new Line(new Point(0, f.getYFromX(0)), new Point(1000, f.getYFromX(1000))));
                sleep(5000);
                
                result = avl.getPointsAbove(f);
                window.addPoints2(result.toArray(new Point[result.size()]));
                sleep(5000);
                
                window.clear();
            }
            input.close();
            System.exit(0);
        }
    }
    
    public static void sleep(long ms) {
        try {Thread.sleep(ms);} catch (InterruptedException ex) {}
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
    }
    
    public static List<Point> generatePoints(int n) {
        List<Point> points = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < n; i++) {

            int x = Math.abs(r.nextInt(200));
            int y = Math.abs(r.nextInt(120));
            try {
                points.add(new Point(x + 1, y + 1));
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

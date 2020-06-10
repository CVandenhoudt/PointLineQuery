/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import util.geometry.Line;
import util.geometry.Point;

/**
 * source http://zetcode.com/gfx/java2d/basicdrawing/
 * @author vandenboer
 */
class Surface extends JPanel implements ActionListener {

    public final double zoom = 5;
    private final int DELAY = 150;
    private Timer timer;
    
    private List<Point> points;
    private List<Point> points2;
    private List<Line> lines;
    private List<Line> lines2;

    public Surface() {
        this.points = new ArrayList<>();
        this.points2 = new ArrayList<>();
        this.lines = new ArrayList<>();
        this.lines2 = new ArrayList<>();
        initTimer();
    }

    private void initTimer() {
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    public Timer getTimer() {
        return timer;
    }

    public void addPoints(Point... p) {
        this.points.addAll(Arrays.asList(p));
    }

    public void addPoints2(Point... p) {
        this.points2.addAll(Arrays.asList(p));
    }  
    
    public void addLines(Line... l) {
        this.lines.addAll(Arrays.asList(l));
    }
    
    public void addLines2(Line... l) {
        this.lines2.addAll(Arrays.asList(l));
    }
    
    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        AffineTransform at = new AffineTransform();
        at.scale(zoom, zoom);
        
        g2d.setTransform(at);
        
        g2d.setPaint(Color.blue);

        lines.forEach((line) -> {
            g2d.drawLine((int)line.getStart().x, (int)line.getStart().y, (int)line.getEnd().x, (int)line.getEnd().y);
        });
        
        g2d.setPaint(Color.green);
        lines2.forEach((line) -> {
            g2d.drawLine((int)line.getStart().x, (int)line.getStart().y, (int)line.getEnd().x, (int)line.getEnd().y);
        });
        
        g2d.setPaint(Color.black);
        
        points.forEach((point) -> {
            g2d.drawLine((int)point.x, (int)point.y, (int)point.x, (int)point.y);
        });
        
        g2d.setPaint(Color.red);
        
        points2.forEach((point) -> {
            g2d.drawLine((int)point.x, (int)point.y, (int)point.x, (int)point.y);
        });
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void clear() {
        this.lines = new ArrayList<>();
        this.lines2 = new ArrayList<>();
        this.points = new ArrayList<>();
        this.points2 = new ArrayList<>();
    }
}

public class PointsWindow extends JFrame {

    private final Surface surface;
    public final double zoom;
    
    public PointsWindow() {
        this.surface = new Surface();
        this.zoom = this.surface.zoom;
        initUI();
    }

    public void addPoints(Point... p) {
        this.surface.addPoints(p);
    }
    
    public void addPoints2(Point... p) {
        this.surface.addPoints2(p);
    }
    
    public void addLines(Line... l) {
        this.surface.addLines(l);
    }
    
    public void addLines2(Line... l) {
        this.surface.addLines2(l);
    }
    
    private void initUI() {
        add(surface);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Timer timer = surface.getTimer();
                timer.stop();
            }
        });
        
        setTitle("Points");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void clear() {
        this.surface.clear();
    }
}
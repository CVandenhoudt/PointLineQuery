/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.geometry;

/**
 *
 * @author vandenboer
 */
public class Function {
    
    public double a;
    public double b;
    public double c;

    /**
     * A function defines as ax + by + c
     * @param a * x
     * @param b * y
     * @param c added
     */
    public Function(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    /**
     * Function defines as y = ax + c
     * @param a * x
     * @param b added constant
     */
    public Function(double a, double b) {
        this.b = -1;
        this.a = a;
        this.c = b;
    }
    
    /**
     * Function defines as y = ax
     * @param a * x
     */
    public Function(double a) {
        this.b = -1;
        this.a = a;
        this.c = 0;
    }
    
    public double fillIn(double x, double y) {
        return a * x + b * y + c;
    }
    
    public double fillInIgnoreC(double x, double y) {
        return a * x + b * y;
    }
    
    public double getDistanceFromPointToLine(double x, double y) {
        double sum = Math.abs(a * x + b * y + c);
        double sqrt = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
        return sum / sqrt;
    }
    
    public boolean pointIsBelow(double x, double y) {
        return y > (-(a * x) / b) - (c / b);
//        return a * x + b * y < -c;
//        return y < getYFromX(x);
    }
    
    public boolean pointIsAbove(double x, double y) {
        return y < (-(a * x) / b) - (c / b);
//        return a * x + b * y > -c;
//        return y > getYFromX(x);
    }
    
    public double getYFromX(double x) {
        double result = - (a / b) * x - (c / b);
        return result;
    }

    @Override
    public String toString() {
        return String.format("y = %.2fx + %.2f", - (a / b), - (c / b));
    }
    
}

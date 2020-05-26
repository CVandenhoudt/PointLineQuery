/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.old.convex;

/**
 *
 * @author vandenboer
 */
public class ConvexHullSizeException extends Exception {

    /**
     * Creates a new instance of <code>ConvexHullSizeException</code> without
     * detail message.
     */
    public ConvexHullSizeException() {
    }

    /**
     * Constructs an instance of <code>ConvexHullSizeException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ConvexHullSizeException(String msg) {
        super(msg);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.tree;

/**
 *
 * @author vandenboer
 */
public abstract class Tree {
    
    protected Node root; 

    public Tree(Comparable value) {
        if (value == null) {
            this.root = null;
            return;
        }
        this.root = new Node(value);
    }
    
    /** A utility function to get the height of the tree
     * @param n node to get height from
     * @return the height of the given node
     */ 
    protected int height(Node n) { 
        if (n == null) 
            return 0; 
  
        return n.getHeight(); 
    } 
    
    /** A utility function to get maximum of two integers 
     * @param a number 1
     * @param b number 2
     * @return the highest number between a and b
     */
    protected int max(int a, int b) { 
        return (a > b) ? a : b; 
    } 
    
    /** A utility function to print preorder traversal of the tree. 
    * The function also prints height of every node 
    * @param node node to start printing the preorder with
    */
    public void preOrder(Node node) { 
        if (node != null) { 
            System.out.print(node.getValue() + " "); 
            preOrder(node.getLeft()); 
            preOrder(node.getRight()); 
        } 
    }

    public Node getRoot() {
        return root;
    }
    
    public abstract void insert(Comparable key);
    
    public abstract void remove(Comparable key);
    
    public abstract Node query(Comparable key);
}

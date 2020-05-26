/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.tree;

import util.avl.NodeData;

/**
 *
 * @author vandenboer
 */
public class AVLTree extends Tree {

    public AVLTree() {
        super(null);
    }
  
    // A utility function to right rotate subtree rooted with y 
    // See the diagram given above. 
    public Node rightRotate(Node y) { 
        Node x = y.getLeft(); 
        Node T2 = x.getRight(); 
  
        // Perform rotation 
        x.setRight(y); 
        y.setLeft(T2);
        
        // Update heights 
        y.setHeight(max(height(y.getLeft()), height(y.getRight())) + 1);
        x.setHeight(max(height(x.getLeft()), height(x.getRight())) + 1);
  
        // Return new root 
        return x; 
    } 
  
    // A utility function to left rotate subtree rooted with x 
    // See the diagram given above. 
    public Node leftRotate(Node x) { 
        Node y = x.getRight(); 
        Node T2 = y.getLeft(); 
  
        // Perform rotation 
        y.setLeft(x); 
        x.setRight(T2); 
  
        //  Update heights 
        x.setHeight(max(height(x.getLeft()), height(x.getRight())) + 1);
        y.setHeight(max(height(y.getLeft()), height(y.getRight())) + 1);
  
        // Return new root 
        return y; 
    } 
  
    // Get Balance factor of node N 
    public int getBalance(Node N) { 
        if (N == null) 
            return 0; 
  
        return height(N.getLeft()) - height(N.getRight()); 
    } 
  
    
    @Override
    public void insert(Comparable key) { 
        this.root = this.insert(this.root, key);
    }
    
    private Node insert(Node node, Comparable key) { 
  
        /* 1.  Perform the normal BST insertion */
        if (node == null) 
            return (new Node(key)); 
  
        if (key.compareTo(node.getValue()) < 0) 
            node.setLeft(insert(node.getLeft(), key)); 
        else if (key.compareTo(node.getValue()) > 0) 
            node.setRight(insert(node.getRight(), key)); 
        else // Duplicate keys not allowed 
            return node; 
  
        /* 2. Update height of this ancestor node */
        node.setHeight(1 + max(height(node.getLeft()), height(node.getRight())));
  
        /* 3. Get the balance factor of this ancestor 
              node to check whether this node became 
              unbalanced */
        int balance = getBalance(node); 
  
        // If this node becomes unbalanced, then there 
        // are 4 cases Left Left Case 
        if (balance > 1 && key.compareTo(node.getLeft().getValue()) < 0) 
            return rightRotate(node); 
  
        // Right Right Case 
        if (balance < -1 && key.compareTo(node.getRight().getValue()) > 0) 
            return leftRotate(node); 
  
        // Left Right Case 
        if (balance > 1 && key.compareTo(node.getLeft().getValue()) > 0) { 
            node.setLeft(leftRotate(node.getLeft())); 
            return rightRotate(node); 
        } 
  
        // Right Left Case 
        if (balance < -1 && key.compareTo(node.getRight().getValue()) < 0) { 
            node.setRight(rightRotate(node.getRight())); 
            return leftRotate(node); 
        } 
  
        /* return the (unchanged) node pointer */
        return node; 
    }

    @Override
    public void remove(Comparable key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Node query(Comparable key) {
        return this.query(key, root);
    }
    
    private Node query(Comparable key, Node node) {
        if (node == null) {
            return null;
        }
        
        Node result;
        
        if (node.getValue().compareTo(key) == 0) {
//            System.out.println(String.format("Element found because: %.2f = %.2f", ((NodeData)(node.getValue())).getKey(), key));
            result = node;
        } else if (node.getValue().compareTo(key) > 0) {
//            System.out.println(String.format("Going left because: %.2f > %.2f", ((NodeData)(node.getValue())).getKey(), key));
            result = query(key, node.getLeft());
        } else {
//            System.out.println(String.format("Going left because: %.2f < %.2f", ((NodeData)(node.getValue())).getKey(), key));
            result = query(key, node.getRight());
        }
        
        if (result == null) {
            return node;
        }
        
        return result;
    }
    
} 
// This code has been contributed by Mayank Jaiswal 


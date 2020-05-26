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
public class BaseTree extends Tree {

    public BaseTree() {
        super(null);
    }
    
    @Override
    public void insert(Comparable key) {
        this.root = this.insert(this.root, key);
    }
    
    private Node insert(Node node, Comparable key) {
        if (node == null) {
            return new Node(key);
        } else if (key.compareTo(node.getValue()) < 0) {
            node.setLeft(insert(node.getLeft(), key)); 
        } else {
            node.setRight(insert(node.getRight(), key)); 
        }
        
        node.setHeight(1 + max(height(node.getLeft()), height(node.getRight())));
        
        return node;
    }

    @Override
    public void remove(Comparable key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Node query(Comparable key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

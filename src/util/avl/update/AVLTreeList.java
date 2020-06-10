/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.avl.update;

import java.util.ArrayList;
import java.util.List;
import util.tree.AVLTree;

/**
 *
 * @author vandenboer
 */
public class AVLTreeList extends AVLTree {

    private List<Comparable> data;
    
    public AVLTreeList() {
        data = new ArrayList<>();
    }

    @Override
    public void insert(Comparable key) {
        this.data.add(key);
        super.insert(new NodeData(this.data.size() - 1));
    }
    
    private class NodeData implements Comparable<NodeData>{
        
        private int index;

        public NodeData(int index) {
            this.index = index;
        }

        @Override
        public int compareTo(NodeData d) {
            Comparable c = data.get(this.index);
            return c.compareTo(data.get(d.index));
        }
    }
    
}

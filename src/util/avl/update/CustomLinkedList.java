/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.avl.update;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

@Deprecated
/**
 *
 * @author vandenboer
 */
public class CustomLinkedList<T> implements List {
    
    private DataEntry<T> firstEntry;
    private int size;
    
    @Override
    public int size() {
        return this.size;
    }
    
    @Override
    public boolean add(Object e) {
        DataEntry added = new DataEntry();
        added.data = e;
        if (this.firstEntry == null) {
            this.firstEntry = added;
            this.firstEntry.nextEntry = this.firstEntry;
            this.firstEntry.prevEntry = this.firstEntry;
        } else {
            this.firstEntry.prevEntry.nextEntry = added;
            this.firstEntry.prevEntry = added;
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        DataEntry selected = this.firstEntry;
        do {
            if (selected.data == o) {
                if (selected.equals(this.firstEntry)) {
                    this.firstEntry = this.firstEntry.nextEntry;
                }
                selected.prevEntry.nextEntry = selected.nextEntry;
                selected.nextEntry.prevEntry = selected.prevEntry;
                selected.data = null;
                return true;
            } else {
                selected = selected.nextEntry;
            }
        } while (!selected.equals(this.firstEntry));
        return false;
    }
    
    @Override
    public void clear() {
        this.firstEntry = null;
    }
    
    @Override
    public int indexOf(Object o) {
        DataEntry selected = this.firstEntry;
        int i = 0;
        do {
            if (selected.data == o) {
                return i;
            } else {
                selected = selected.nextEntry;
                i++;
            }
        } while (!selected.equals(this.firstEntry));
        return -1;
    }

    public boolean insertAfter(Object object, Object target) {
        DataEntry selected = this.firstEntry;
        DataEntry insert = new DataEntry();
        insert.data = object;
        do {
            if (selected.data == target) {
                selected.nextEntry.prevEntry = insert;
                selected.nextEntry = insert;
                return true;
            } else {
                selected = selected.nextEntry;
            }
        } while (!selected.equals(this.firstEntry));
        return false;
    }
    
    public boolean insertBefore(Object object, Object target) {
        DataEntry selected = this.firstEntry;
        DataEntry insert = new DataEntry();
        insert.data = object;
        do {
            if (selected.data == target) {
                selected.prevEntry.nextEntry = insert;
                selected.prevEntry = insert;
                return true;
            } else {
                selected = selected.nextEntry;
            }
        } while (!selected.equals(this.firstEntry));
        return false;
    }
    
    @Override
    public boolean isEmpty() {
        return firstEntry == null;
    }

    @Override
    public boolean contains(Object o) {
        return this.indexOf(o) != -1;
    }

    @Override
    public boolean addAll(Collection clctn) {
        clctn.forEach((t) -> {
            this.add(t);
        });
        return true;
    }
    
    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object[] toArray(Object[] ts) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void add(int i, Object e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Object remove(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean containsAll(Collection clctn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean addAll(int i, Collection clctn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeAll(Collection clctn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean retainAll(Collection clctn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object get(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object set(int i, Object e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ListIterator listIterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ListIterator listIterator(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List subList(int i, int i1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private class DataEntry<T> {
        public T data;
        public DataEntry nextEntry;
        public DataEntry prevEntry;

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 23 * hash + Objects.hashCode(this.data);
            hash = 23 * hash + Objects.hashCode(this.nextEntry);
            hash = 23 * hash + Objects.hashCode(this.prevEntry);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final DataEntry<?> other = (DataEntry<?>) obj;
            return Objects.equals(this.data, other.data);
        }

    }
    
}

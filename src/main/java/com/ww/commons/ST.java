package com.ww.commons;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class ST<Key extends Comparable<Key>, Value> implements Iterable<Key> {

    private TreeMap<Key, Value> st;

    public ST() {
        st = new TreeMap<>();
    }

    public Value get(Key key) {
        if (null == key) {
            throw new IllegalArgumentException("calls get() with null key");
        }
        return st.get(key);
    }

    public void put(Key key, Value value) {
        if (null == key) {
            throw new IllegalArgumentException("calls put() with null key");
        }
        if (null == value) {
            st.remove(key);
        } else {
            st.put(key, value);
        }
    }

    public void delete(Key key) {
        if (null == key) {
            throw new IllegalArgumentException("calls delete() with null key");
        }
        st.remove(key);
    }

    public boolean contains(Key key) {
        if (null == key) {
            throw new IllegalArgumentException("calls contains() with null key");
        }
        return st.containsKey(key);
    }

    public int size() {
        return st.size();
    }

    public boolean isEmpty() {
        return 0 == size();
    }

    public Iterable<Key> keys() {
        return st.keySet();
    }

    public Iterator<Key> iterator() {
        return st.keySet().iterator();
    }

    public Key min() {
        if (isEmpty()) {
            throw new NoSuchElementException("calls min() with empty symbol table");
        }
        return st.firstKey();
    }

    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException("calls max() with empty symbol table");
        }
        return st.lastKey();
    }

    public Key ceiling(Key key) {
        if (null == key) {
            throw new IllegalArgumentException("argument to ceiling() is null");
        }
        Key k = st.ceilingKey(key);
        if (null == key) {
            throw new NoSuchElementException("all keys are less than " + key);
        }
        return k;
    }

    public Key floor(Key key) {
        if (null == key) {
            throw new IllegalArgumentException("argument to floor() is null");
        }
        Key k = st.floorKey(key);
        if (null == k) {
            throw new NoSuchElementException("all keys are greater than " + key);
        }
        return k;
    }
}

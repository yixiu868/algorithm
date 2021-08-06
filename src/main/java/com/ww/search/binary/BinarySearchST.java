package com.ww.search.binary;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Queue;

import com.ww.commons.StdIn;
import com.ww.commons.StdOut;

/**
 * @Description: 二分查找
 * @author xiaohua
 * @date 2021年8月6日 上午12:43:29
 */
public class BinarySearchST<Key extends Comparable<Key>, Value> {
    
    private static final int INIT_CAPACITY = 2;

    private Key[] keys;
    
    private Value[] vals;
    
    private int n = 0;
    
    public BinarySearchST() {
        this(INIT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public BinarySearchST(int capacity) {
        keys = (Key[])new Comparable[capacity];
        vals = (Value[])new Object[capacity];
    }
    
    /**
     * 数组扩容
     * @param capacity
     */
    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        assert capacity >= n;
        Key[] tempk = (Key[]) new Comparable[capacity];
        Value[] tempv = (Value[]) new Object[capacity];
        
        for (int i = 0; i < n; i++) {
            tempk[i] = keys[i];
            tempv[i] = vals[i];
        }
        vals = tempv;
        keys = tempk;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return 0 == size();
    }
    
    /**
     * 判断是否包含key
     * @param key
     * @return
     */
    public boolean contains(Key key) {
        if (null == key) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        
        return null != get(key);
    }

    /**
     * 查找key的值
     * @param key
     * @return
     */
    public Value get(Key key) {
        if (null == key) {
            throw new IllegalArgumentException("argument to get() is null");
        }
        
        if (isEmpty()) {
            return null;
        }
        
        int i = rank(key);
        if (i < n && 0 == keys[i].compareTo(key)) {
            return vals[i];
        } 
        
        return null;
    }

    /**
     * 返回表中小于给定键的数量（二分查找）
     * @param key
     * @return
     */
    public int rank(Key key) {
        if (null == key) {
            throw new IllegalArgumentException("argument to rank() is null");
        }
        
        int lo = 0, hi = n - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(keys[mid]);
            if (cmp < 0) {
                hi = mid - 1;
            } else if (cmp > 0) {
                lo = mid + 1;
            } else {
                return mid;
            }
        }

        return lo;
    }

    /**
     * 插入键值
     * @param key
     * @param val
     */
    public void put(Key key, Value val) {
        if (null == key) {
            throw new IllegalArgumentException("first argument to put() is null");
        }
        
        if (null == val) {
            delete(key);
            return;
        }
        
        // 查找键，找到则更新值，否则创建新的元素
        int i = rank(key);
        if (i < n && 0 == keys[i].compareTo(key)) {
            vals[i] = val;
            return;
        }
        
        // 扩容
        if (n == keys.length) {
            resize(2 * keys.length);
        }

        for (int j = n; j > i; j--) {
            keys[j] = keys[j-1];
            vals[j] = vals[j-1];
        }
        keys[i] = key;
        vals[i] = val;
        n++;
        
        assert check();
    }

    public void delete(Key key) {
        if (null == key) {
            throw new IllegalArgumentException("argument to delete() is null");
        }
        
        if (isEmpty()) {
            return;
        }
        
        // 查找键，找到删除，找不到不做处理
        int i = rank(key);
        
        if (i == n || 0 != keys[i].compareTo(key)) {
            return;
        }
        
        for (int j = i; j < n - 1; j++) {
            keys[j] = keys[j+1];
            vals[j] = vals[j+1];
        }
        
        n--;
        
        keys[n] = null;
        vals[n] = null;
        
        // 缩容
        if (n > 0 && n == keys.length / 4) {
            resize(keys.length / 2);
        }
        
        assert check();
    }
    
    /**
     * 删除最小键值对
     */
    public void deleteMin() {
        if (isEmpty()) {
            new NoSuchElementException("Symbol table underflow error");
        }
        delete(min());
    }
    
    /**
     * 删除最大键值对
     */
    public void deleteMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Symbol table underflow error");
        }
        delete(max());
    }

    public Key min() {
        if (isEmpty()) {
            throw new NoSuchElementException("called min() with empty symbol table");
        }
        
        return keys[0];
    }

    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException("called max() with empty symbol table");
        }
        
        return keys[n-1];
    }

    public Key select(int k) {
        if (k < 0 || k >= size()) {
            throw new IllegalArgumentException("called select() with invalid argument: " + k);
        }
        
        return keys[k];
    }

    /**
     * 向下取整
     * @param key
     * @return
     */
    public Key floor(Key key) {
        if (null == key) {
            throw new IllegalArgumentException("argument to floor() is null");
        }
        
        int i = rank(key);
        if (i < n && 0 == key.compareTo(keys[i])) {
            return keys[i];
        }
        
        if (0 == i) {
            return null;
        } else {
            return keys[i-1];
        }
    }

    /**
     * 向上取整
     * @param key
     * @return
     */
    public Key ceiling(Key key) {
        if (null == key) {
            throw new IllegalArgumentException("argument to ceiling() is null");
        }
        
        int i = rank(key);
        if (i == n) {
            return null;
        } else {
            return keys[i];
        }
    }
    
    public int size(Key lo, Key hi) {
        if (null == lo) {
            throw new IllegalArgumentException("first argument to size() is null");
        }
        
        if (null == hi) {
            throw new IllegalArgumentException("second argument to size() is null");
        }
        
        if (lo.compareTo(hi) > 0) {
            return 0;
        }
        
        if (contains(hi)) {
            return rank(hi) - rank(lo) + 1;
        } else {
            return rank(hi) - rank(lo);
        }
    }
    
    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        if (null == lo) {
            throw new IllegalArgumentException("first argument to keys() is null");
        }
        
        if (null == hi) {
            throw new IllegalArgumentException("second argument to keys() is null");
        }
        
        Queue<Key> queue = new ArrayDeque<>();
        if (lo.compareTo(hi) > 0) {
            return queue;
        }
        
        for (int i = rank(lo); i < rank(hi); i++) {
            queue.add(keys[i]);
        }
        
        if (contains(hi)) {
            queue.add(keys[rank(hi)]);
        }
        
        return queue;
    }
    
    private boolean check() {
        return isSorted() && rankCheck();
    }
    
    private boolean isSorted() {
        for (int i = 0; i < size(); i++) {
            if (keys[i].compareTo(keys[i-1]) < 0) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean rankCheck() {
        for (int i = 0; i < size(); i++) {
            if (i != rank(select(i))) {
                return false;
            }
        }
        
        for (int i = 0; i < size(); i++) {
            if (keys[i].compareTo(select(rank(keys[i]))) != 0) {
                return false;
            }
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        BinarySearchST<String, Integer> st = new BinarySearchST<>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        
        for (String key : st.keys) {
            StdOut.println(key + " " + st.get(key));
        }
    }
}

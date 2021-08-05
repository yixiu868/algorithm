package com.ww.search.binary;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 二分查找
 * @param <Key>
 * @param <Value>
 */
public class BinarySearchST<Key extends Comparable<Key>, Value> {

    private Key[] keys;
    private Value[] vals;
    private int N;

    @SuppressWarnings("unchecked")
    public BinarySearchST(int capacity) {
        keys = (Key[])new Comparable[capacity];
        vals = (Value[])new Object[capacity];
    }

    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return 0 == N;
    }

    public Value get(Key key) {
        if (isEmpty()) {
            return null;
        }
        int i = rank(key);
        if (i < N && 0 == keys[i].compareTo(key)) {
            return vals[i];
        } else {
            return null;
        }
    }

    public int rank(Key key) {
        int lo = 0, hi = N - 1;
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

    public void put(Key key, Value val) {
        // 查找键，找到则更新值，否则创建新的元素
        int i = rank(key);
        if (i < N && 0 == keys[i].compareTo(key)) {
            vals[i] = val;
            return;
        }

        for (int j = N; j > i; j--) {
            keys[j] = keys[j-1];
            vals[j] = vals[j-1];
        }
        keys[i] = key;
        vals[i] = val;
        N++;
    }

    public void delete(Key key) {
        // 查找键，找到删除，找不到不做处理
        int i = rank(key);
        if (i < N && 0 == keys[i].compareTo(key)) {
            for (int j = N; j > i; j--) {
                keys[j-1] = keys[j];
                vals[j-1] = vals[j];
            }
            N--;
        }
    }

    public Key min() {
        return keys[0];
    }

    public Key max() {
        return keys[N-1];
    }

    public Key select(int k) {
        return keys[k];
    }

    public Key ceiling(Key key) {
        int i = rank(key);
        return keys[i];
    }

    /*public Key floor(Key key) {

    }*/

    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new ArrayDeque<>();
        for (int i = rank(lo); i <= rank(hi); i++) {
            queue.add(keys[i]);
        }
        return queue;
    }
}

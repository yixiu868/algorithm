package com.ww.search.hash;

import com.ww.search.SequentialSearchST;

/**
 * 基于拉链法的散列表
 */
public class SeparateChainingHashST<Key, Value> {

    private int N; // 键值对总数
    private int M; // 散列表的大小
    private SequentialSearchST<Key, Value>[] st; // 存放链表对象的数组

    public SeparateChainingHashST() {
        this(997);
    }

    public SeparateChainingHashST(int M) {
        this.M = M; // 创建M条链表
        st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[M];
        for (int i = 0; i < M; i++) {
            st[i] = new SequentialSearchST();
        }
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public Value get(Key key) {
        return (Value)(st[hash(key)].get(key));
    }

    /**
     * put操作，若key存在，则覆盖
     * @param key
     * @param val
     */
    public void put(Key key, Value val) {
        if (null == get(key)) {
            N++;
        }
        st[hash(key)].put(key, val);
    }

    /**
     * 删除操作
     * @param key
     */
    public void delete(Key key) {
        if (null != get(key)) {
            N--;
        }
        st[hash(key)].delete(key);
    }

    public int size() {
        return N;
    }

    public static void main(String[] args) {
        SeparateChainingHashST<String, String> map = new SeparateChainingHashST<>();
        map.put("1", "战士");
        map.put("2", "将军");

        System.out.println(map.get("1"));
        System.out.println(map.size());

        map.put("1", "炮架");
        System.out.println(map.get("1"));

        map.delete("1");
        System.out.println(map.size());
    }
}

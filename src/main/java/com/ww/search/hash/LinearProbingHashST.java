package com.ww.search.hash;

/**
 * 基于线性探测的散列表
 * @param <Key>
 * @param <Value>
 */
public class LinearProbingHashST<Key, Value> {

    private int N; // 键值对的总数
    private int M = 16; // 线性探测表的大小
    private Key[] keys; // 键
    private Value[] vals; // 值

    public LinearProbingHashST() {
        keys = (Key[]) new Object[M];
        vals = (Value[]) new Object[M];
    }

    public LinearProbingHashST(int cap) {
        keys = (Key[]) new Object[cap];
        vals = (Value[]) new Object[cap];
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    /**
     * 调整数组大小
     */
    private void resize(int cap) {
        LinearProbingHashST<Key, Value> t = new LinearProbingHashST<>(cap);
        for (int i = 0; i < M; i++) {
            if (null != keys[i]) {
                t.put(keys[i], vals[i]);
            }
        }
        keys = t.keys;
        vals = t.vals;
        M = t.M;
    }

    /**
     * put操作
     * @param key
     * @param val
     */
    public void put(Key key, Value val) {
        if (N >= M/2) {
            resize(2 * M); // 将M加倍
        }
        int i;
        for (i = hash(key); keys[i] != null; i = (i+1) % M) {
            if (keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
        }
        keys[i] = key;
        vals[i] = val;
        N++;
    }

    /**
     * 获取元素
     * @param key
     * @return
     */
    public Value get(Key key) {
        for (int i = hash(key); keys[i] != null; i = (i+1) % M) {
            if (keys[i].equals(key)) {
                return vals[i];
            }
        }

        return null;
    }

    /**
     * 删除操作
     * @param key
     */
    public void delete(Key key) {
        if (!contains(key)) {
            return;
        }

        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + 1) % M;
        }
        keys[i] = null; // 删除指定位置
        vals[i] = null;
        i = (i + 1) % M;

        /** 该位置后所有元素重新put操作 */
        while (null != keys[i]) {
            Key keyToRedo = keys[i];
            Value valToRedo = vals[i];
            keys[i] = null;
            vals[i] = null;
            N--;
            put(keyToRedo, valToRedo);
            i = (i + 1) % M;
        }
        N--;
        if (N > 0 && N == M/8) {
            resize(M/2);
        }
    }

    public boolean contains(Key key) {
        if (null == key) {
            return false;
        }

        int i = hash(key);
        while (!key.equals(keys[i]) && i != M) {
            i = (i + 1) % M;
        }
        if (M != i) {
            return true;
        }

        return false;
    }

    public int size() {
        return N;
    }

    public static void main(String[] args) {
        LinearProbingHashST<String, String> map = new LinearProbingHashST<>();
        map.put("1", "催眠曲");
        map.put("2", "交响乐");
        System.out.println("元素数:" + map.size());
        System.out.println("key=1," + map.get("2"));
    }
}

package com.ww.commons;

import java.util.Random;

public final class StdRandom {

    private static Random random;

    private static long seed;

    static {
        seed = System.currentTimeMillis();
        random = new Random(seed);
    }

    private StdRandom() {}

    /**
     * 获取此类实例的伪随机种子生成器
     * @param s
     */
    public static void setSeed(long s) {
        seed = s;
        random = new Random(seed);
    }

    public static long getSeed() {
        return seed;
    }

    /**
     * 返回一个随机的范围在[0,1)之间的double类型的数
     * @return
     */
    public static double uniform() {
        return random.nextDouble();
    }

    /**
     * 返回一个随机的范围[0,N)之间的int类型的数
     * @param N
     * @return
     */
    public static int uniform(int N) {
        return random.nextInt(N);
    }

    public static double random() {
        return uniform();
    }

    /**
     * 返回一个范围在[a,b)的int类型
     * @param a
     * @param b
     * @return
     */
    public static int uniform(int a, int b) {
        return a + uniform(b - a);
    }

    /**
     * 返回一个范围[a,b)的实数
     * @param a
     * @param b
     * @return
     */
    public static double uniform(double a, double b) {
        return a + uniform() * (b - a);
    }

    /**
     * 返回一个随机boolean值，该p表示此布尔值为真的概率
     * @param p
     * @return
     */
    public static boolean bernoulli(double p) {
        return uniform() < p;
    }

    /** 数组操作 */
    /**
     * 随机打乱指定的Object型数组
     * @param a
     */
    public static void shuffle(Object[] a) {
        int N = a.length;

        for (int i = 0; i < N; i++) {
            int r = i + uniform(N-i);
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * 随机打乱指定的double型数组
     * @param a
     */
    public static void shuffle(double[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + uniform(N-i);
            double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * 随机打乱指定Object类型数组中指定范围的数据
     * @param a
     * @param lo
     * @param hi
     */
    public static void shuffle(Object[] a, int lo, int hi) {
        if (lo < 0 || lo > hi || hi >= a.length) {
            throw new IndexOutOfBoundsException("不合法的边界");
        }

        for (int i = lo; i <= hi; i++) {
            int r = i + uniform(hi-i+1);
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * 随机打乱指定double类型数组中指定范围的数据
     * @param a
     * @param lo
     * @param hi
     */
    public static void shuffle(double[] a, int lo, int hi) {
        if (lo < 0 || lo > hi || hi >= a.length) {
            throw new IndexOutOfBoundsException("不合法的边界");
        }

        for (int i = lo; i <= hi; i++) {
            int r = i + uniform(hi-i+1);
            double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }
}

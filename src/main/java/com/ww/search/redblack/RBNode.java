package com.ww.search.redblack;

public class RBNode<T extends Comparable<T>> {

    private static final boolean RED = true;

    boolean color; // 颜色
    T key; // 键值
    RBNode<T> left; // 左子节点
    RBNode<T> right; // 右子节点
    RBNode<T> parent; // 父节点

    public RBNode(boolean color, T key, RBNode<T> left, RBNode<T> right, RBNode<T> parent) {
        this.color = color;
        this.key = key;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    // 获得节点的键值
    public T getKey() {
        return key;
    }

    @Override
    public String toString() {
        return ""+key+(this.color == RED ? "R":"B");
    }
}

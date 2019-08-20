package com.ww.search.redblack;

public class RBTree<T extends Comparable<T>> {

    private static final boolean RED = true;

    RBNode<T> root;

    /*************对红黑树节点x进行左旋操作 ******************/
    /*
     * 左旋示意图：对节点x进行左旋
     *     p                       p
     *    /                       /
     *   x                       y
     *  / \                     / \
     * lx  y      ----->       x  ry
     *    / \                 / \
     *   ly ry               lx ly
     * 左旋做了三件事：
     * 1. 将y的左子节点赋给x的右子节点,并将x赋给y左子节点的父节点(y左子节点非空时)
     * 2. 将x的父节点p(非空时)赋给y的父节点，同时更新p的子节点为y(左或右)
     * 3. 将y的左子节点设为x，将x的父节点设为y
     */
    private void leftRotate(RBNode<T> x) {
        // 1、将y的左子节点赋给x的右子节点，并将x赋给y左子节点的父节点(y左子节点非空时)
        RBNode<T> y = x.right;
        x.right = y.left;
        if (null != y.left) {
            y.left.parent = x;
        }

        // 2、将x的父节点p(非空时)赋给y的父节点，同时更新p的子节点y(左或右)
        y.parent = x.parent;
        if (null == x.parent) {
            this.root = y; // 如果x的父节点为空(x为根节点)，则将y设为根节点
        } else {
            if (x == x.parent.left) { // 如果x是左子节点
                x.parent.left = y; // 则也将y设为左子节点
            } else {
                x.parent.right = y; // 设置为右子节点
            }
        }

        // 3、将y的左子节点设为x，将x的父节点设为y
        y.left = x;
        x.parent = y;
    }

    /*************对红黑树节点y进行右旋操作 ******************/
    /*
     * 左旋示意图：对节点y进行右旋
     *        p                   p
     *       /                   /
     *      y                   x
     *     / \                 / \
     *    x  ry   ----->      lx  y
     *   / \                     / \
     * lx  rx                   rx ry
     * 右旋做了三件事：
     * 1. 将x的右子节点赋给y的左子节点,并将y赋给x右子节点的父节点(x右子节点非空时)
     * 2. 将y的父节点p(非空时)赋给x的父节点，同时更新p的子节点为x(左或右)
     * 3. 将x的右子节点设为y，将y的父节点设为x
     */
    private void rightRotate(RBNode<T> y) {
       // 1、将x的右子节点赋给y的左子节点，并将y赋给x右子节点的父节点
       RBNode<T> x = y.left;
       y.left = x.right;
       if (null != x.right) {
           x.right.parent = y;
       }

       // 2、将y的父节点p(非空时)赋给x的父节点，同时更新p的子节点为x(左或右)
        x.parent = y.parent;
       if (null == y.parent) {
           this.root = x; // 如果y的父节点为空(y为根节点)，则旋转后将x设为根节点
       } else {
           if (y == y.parent.left) {
               y.parent.left = x; // 将x设置为左子节点
           } else {
               y.parent.right = x; // 将x设置为右子节点
           }
       }

       // 3、将x的右子节点设置为y，y的父节点设为x
        x.right = y;
       y.parent = x;
    }

    /**
     * 向红黑树种插入节点
     * @param key
     */
    public void insert(T key) {
        RBNode<T> node = new RBNode<T>(RED, key, null, null, null);
        if (null != node) {
            insert(node);
        }
    }

    public void insert(RBNode<T> node) {
        RBNode<T> current = null; // 表示最后node的父节点
        RBNode<T> x = this.root; // 用来向下搜索

        // 1、找到插入位置
        while (null != x) {
            current = x;
            int cmp = node.key.compareTo(x.key);
            if (cmp < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        node.parent = current; // 找到插入的位置，将当前current作为node的父节点

        // 2、判断node是左子节点还是右子节点
        if (null != current) {
            int cmp = node.key.compareTo(current.key);
            if (cmp < 0) {
                current.left = node;
            } else {
                current.right = node;
            }
        } else {
            this.root = node;
        }

        // 3、利用旋转操作将其修正
        insertFixUp(node);
    }

    /**
     ①、插入节点的父节点和其叔叔节点（祖父节点的另一个子节点）均为红色。
 　　②、插入节点的父节点是红色的，叔叔节点是黑色的，且插入节点是其父节点的右子节点。
 　　③、插入节点的父节点是红色的，叔叔节点是黑色的，且插入节点是其父节点的左子节点。
     * @param node
     */
    private void insertFixUp(RBNode<T> node) {
        RBNode<T> parent, gparent; // 定义父节点和祖父节点

        // 需要修正条件：父节点存在，且父节点的颜色是红色
        while ((null != (parent = parentOf(node))) && isRed(parent)) {
            gparent = parentOf(parent); // 获得其祖父节点

            // 若父节点是祖父节点的左子节点
            if (parent == gparent.left) {
                RBNode<T> uncle = gparent.right; // 获得叔叔节点

                // case1：叔叔节点也是红色
                if (null != uncle && isRed(uncle)) {
                    setBlack(parent); // 把父节点和叔叔节点涂黑
                    setBlack(uncle);
                    setRed(gparent); // 把祖父节点涂红
                    node = gparent; // 把位置放在祖父节点处
                    continue; // 循环，重新判断
                }

                // case2：叔叔节点是黑色，且当前节点是右子节点
                if (node == parent.right) {
                    leftRotate(parent); // 从父节点左旋
                    RBNode<T> tmp = parent; // 然后将父节点和自己调换一下，为下面右旋做准备
                    parent = node;
                    node = tmp;
                }

                // case3：叔叔节点是黑色，且当前节点是左子节点
                setBlack(parent);
                setRed(gparent);
                rightRotate(gparent);
            } else { // 如果父节点是祖父节点的右子节点，与上面情况完全相反
                RBNode<T> uncle = gparent.left;

                // case1：叔叔节点也是红色的
                if (null != uncle && isRed(uncle)) {
                    setBlack(parent);
                    setBlack(uncle);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }

                // case2：叔叔节点是黑色，且当前节点是左子节点
                if (node == parent.left) {
                    rightRotate(parent);
                    RBNode<T> tmp = parent;
                    parent = node;
                    node = tmp;
                }

                // case3：叔叔节点是黑色的，且当前节点是右子节点
                setRed(parent);
                setRed(gparent);
                leftRotate(gparent);
            }
        }

        setBlack(root); // 设置根节点为黑色
    }

    private RBNode<T> parentOf(RBNode<T> node) {
        return node.parent;
    }

    private boolean isRed(RBNode<T> node) {
        return node.color;
    }

    private void setBlack(RBNode<T> node) {
        node.color = false;
    }

    private void setRed(RBNode<T> node) {
        node.color = true;
    }
}

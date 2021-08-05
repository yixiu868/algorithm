package com.ww.search.bstree;

public class BSTree<T extends Comparable<T>> {

    BSTNode<T> mRoot; // 根结点

    @SuppressWarnings("hiding")
    class BSTNode<T extends Comparable<T>> {
        T key;
        BSTNode<T> left; // 左孩子
        BSTNode<T> right; // 右孩子
        BSTNode<T> parent; // 父结点

        public BSTNode(T key, BSTNode<T> parent, BSTNode<T> left, BSTNode<T> right) {
            this.key = key;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        public T getKey() {
            return key;
        }
    }

    public BSTree() {
        mRoot = null;
    }

    /**
     * 前序遍历
     * 访问根结点
     * 先序遍历左子树
     * 先序遍历右子树
     */
    public void preOrder() {
        preOrder(mRoot);
    }

    private void preOrder(BSTNode<T> tree) {
        if (null != tree) {
            System.out.print(tree.key + " ");
            preOrder(tree.left);
            preOrder(tree.right);
        }
    }

    /**
     * 中序遍历
     * 中序遍历左子树
     * 访问根结点
     * 中序遍历右子树
     */
    public void inOrder() {
        inOrder(mRoot);
    }

    private void inOrder(BSTNode<T> tree) {
        if (null != tree) {
            inOrder(tree.left);
            System.out.print(tree.key + " ");
            inOrder(tree.right);
        }
    }

    /**
     * 后序遍历
     * 后序遍历左子树
     * 后序遍历右子树
     * 访问根结点
     */
    public void postOrder() {
        postOrder(mRoot);
    }

    private void postOrder(BSTNode<T> tree) {
        if (null != tree) {
            postOrder(tree.left);
            postOrder(tree.right);
            System.out.print(tree.key + " ");
        }
    }

    /**
     * 查找
     * @param key
     * @return
     */
    public BSTNode<T> search(T key) {
        return search(mRoot, key);
    }

    private BSTNode<T> search(BSTNode<T> x, T key) {
        if (null == x) {
            return x;
        }

        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return search(x.left, key);
        } else if (cmp > 0) {
            return search(x.right, key);
        } else {
            return x;
        }
    }

    /**
     * 非递归查找
     * @param key
     * @return
     */
    public BSTNode<T> iterativeSearch(T key) {
        return iterativeSearch(mRoot, key);
    }

    private BSTNode<T> iterativeSearch(BSTNode<T> x, T key) {
        while (null != x) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                x = x.left;
            } else if (cmp > 0) {
                x = x.right;
            } else {
                return x;
            }
        }

        return x;
    }

    /**
     * 查找最大结点
     * @return
     */
    public T maximum() {
        BSTNode<T> p = maximum(mRoot);
        if (null != p) {
            return p.key;
        }

        return null;
    }

    private BSTNode<T> maximum(BSTNode<T> tree) {
        if (null == tree) {
            return null;
        }

        while (null != tree.right) {
            tree = tree.right;
        }

        return tree;
    }

    /**
     * 查找最小结点
     * @return
     */
    public T minimum() {
        BSTNode<T> p = minimum(mRoot);
        if (null != p) {
            return p.key;
        }

        return null;
    }

    private BSTNode<T> minimum(BSTNode<T> tree) {
        if (null == tree) {
            return null;
        }

        while (null != tree.left) {
            tree = tree.left;
        }

        return tree;
    }

    /**
     * 查找结点(x)的前驱结点，查找"二叉树中数据值小于该结点"的"最大结点"
     * @param x
     * @return
     */
    public BSTNode<T> predecessor(BSTNode<T> x) {
        // 如果x存在左孩子，则x的前驱结点为以其左孩子为根的子树的最大结点
        if (null != x.left) {
            return maximum(x.left);
        }

        // 如果x没有左孩子，则x则有以下两种可能：
        // 1、x是一个右孩子，则x的前驱结点为它的父结点
        // 2、x是一个左孩子，则查找x的最低的父结点，并且该父结点要具有右孩子，找到的这个最低的父结点就是x的前驱结点
        BSTNode<T> y = x.parent;
        while ((null != y) && (x == y.left)) {
            x = y;
            y = y.parent;
        }

        return y;
    }

    /**
     * 查找二叉树中数据值大于该结点的最小结点
     * @param x
     * @return
     */
    public BSTNode<T> successor(BSTNode<T> x) {
        // 如果x存在右孩子，则x的后继结点为以右孩子为根的子树的最小结点
        if (null != x.right) {
            return minimum(x.right);
        }

        // 如果x没有右孩子，由以下两种可能：
        // 1、x是一个左孩子，则x的后继结点为它的父结点
        // 2、x是一个右孩子，则查找x的最低父结点，并且该父结点具有左孩子
        BSTNode<T> y = x.parent;
        while ((null != y) && (x == y.right)) {
            x = y;
            y = y.parent;
        }

        return y;
    }

    /**
     * 新建结点key，并将其插入到二叉树
     * @param key
     */
    public void insert(T key) {
        BSTNode<T> z = new BSTNode<T>(key, null, null, null);
        if (null != z) {
            insert(this, z);
        }
    }

    private void insert(BSTree<T> bst, BSTNode<T> z) {
        int cmp;
        BSTNode<T> y = null;
        BSTNode<T> x = bst.mRoot;

        // 查找z插入位置
        while (null != x) {
            y = x;
            cmp = z.key.compareTo(x.key);
            if (cmp < 0) {
                x = x.left;
            } else if (cmp > 0) {
                x = x.right;
            } else {
                x.key = z.key;
            }
        }

        z.parent = y;
        if (null == y) {
            bst.mRoot = z;
        } else {
            cmp = z.key.compareTo(y.key);
            if (cmp < 0) {
                y.left = z;
            } else if (cmp > 0) {
                y.right = z;
            } else {
                y.key = z.key;
            }
        }
    }

    /**
     * 删除结点
     * @param key
     */
    @SuppressWarnings("unused")
    public void remove(T key) {
        BSTNode<T> z, node;

        if (null != (z = search(mRoot, key))) {
            if (null != (node = remove(this, z))) {
                node = null;
            }
        }
    }

    private BSTNode<T> remove(BSTree<T> bst, BSTNode<T> z) {
        BSTNode<T> x = null;
        BSTNode<T> y = null;

        if ((null == z.left) || (null == z.right)) {
            y = z;
        } else {
            y = successor(z);
        }

        if (null != y.left) {
            x = y.left;
        } else {
            x = y.right;
        }

        if (null != x) {
            x.parent = y.parent;
        }

        if (null == y.parent) {
            bst.mRoot = x;
        } else if (y == y.parent.left) {
            y.parent.left = x;
        } else {
            y.parent.right = x;
        }

        if (y != z) {
            z.key = y.key;
        }

        return y;
    }

    /**
     * 打印二叉树
     */
    public void print() {
        if (null != mRoot) {
            print(mRoot, mRoot.key, 0);
        }
    }

    private void print(BSTNode<T> tree, T key, int direction) {
        if (null != tree) {
            if (0 == direction) {
                System.out.printf("%2d is root\n", tree.key);
            } else {
                System.out.printf("%2d is %2d's %6s child\n", tree.key, key, direction==1?"right" : "left");
            }

            print(tree.left, tree.key, -1);
            print(tree.right,tree.key,  1);
        }
    }

    /**
     * 销毁二叉树
     */
    public void clear() {
        destroy(mRoot);
        mRoot = null;
    }

    private void destroy(BSTNode<T> tree) {
        if (null == tree) {
            return;
        }

        if (null != tree.left) {
            destroy(tree.left);
        }
        if (null != tree.right) {
            destroy(tree.right);
        }

        tree = null;
    }
}

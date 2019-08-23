package com.ww.search.rbtree;

import javafx.scene.control.TabPane;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;

public class RBTree<T extends Comparable<T>> {

    private final RBTreeNode<T> root;

    // node number
    private AtomicLong size = new AtomicLong(0);

    private volatile boolean overrideMode = true;

    public RBTree() {
        this.root = new RBTreeNode<T>();
    }

    public RBTree(boolean overrideMode) {
        this();
        this.overrideMode = overrideMode;
    }

    public boolean isOverrideMode() {
        return overrideMode;
    }

    public void setOverrideMode(boolean overrideMode) {
        this.overrideMode = overrideMode;
    }

    public long getSize() {
        return size.get();
    }

    private RBTreeNode<T> getRoot() {
        return root.getLeft();
    }

    public T addNode(T value) {
        RBTreeNode<T> t = new RBTreeNode<T>(value);
        return addNode(t);
    }

    public T find(T value) {
        RBTreeNode<T> dataRoot = getRoot();
        while (null != dataRoot) {
            int cmp = dataRoot.getValue().compareTo(value);
            if (cmp < 0) {
                dataRoot = dataRoot.getRight();
            } else if (cmp > 0) {
                dataRoot = dataRoot.getLeft();
            } else {
                return dataRoot.getValue();
            }
        }

        return null;
    }

    /**
     * 获取node兄弟节点
     * @param node
     * @param parent
     * @return
     */
    private RBTreeNode<T> getSibling(RBTreeNode<T> node, RBTreeNode<T> parent) {
        parent = null == node ? parent : node.getParent();
        if (null == node) {
            return parent.getLeft() == null ? parent.getRight() : parent.getLeft();
        }
        if (node == parent.getLeft()) {
            return parent.getRight();
        } else {
            return parent.getLeft();
        }
    }

    /**
     * 节点黑色判断
     * @param node
     * @return
     */
    private boolean isBlack(RBTreeNode<T> node) {
        return null == node || node.isBlack();
    }

    /**
     * 判断根节点
     * @param node
     * @return
     */
    private boolean isRoot(RBTreeNode<T> node) {
        return root.getLeft() == node && node.getParent() == null;
    }

    /**
     * 找到node节点后继节点
     * @param node
     * @return
     */
    private RBTreeNode<T> removeMin(RBTreeNode<T> node) {
        RBTreeNode<T> parent = node;
        // 找到最小节点
        while (null != node && null != node.getLeft()) {
            parent = node;
            node = node.getLeft();
        }

        // 移除最小节点
        if (parent == node) {
            return node;
        }

        parent.setLeft(node.getRight());
        setParent(node.getRight(), parent);

        return node;
    }

    /**
     * 查询节点x父节点
     * @param x
     * @return
     */
    private RBTreeNode<T> findParentNode(RBTreeNode<T> x) {
        RBTreeNode<T> dataRoot = getRoot();
        RBTreeNode<T> child = dataRoot;

        while (null != child) {
            int cmp = child.getValue().compareTo(x.getValue());
            if (0 == cmp) {
                return child;
            }
            if (cmp > 0) {
                dataRoot = child;
                child = child.getLeft();
            } else if (cmp < 0) {
                dataRoot = child;
                child = child.getRight();
            }
        }

        return dataRoot;
    }

    /**
     * 设置node节点父节点
     * @param node
     * @param parent
     */
    private void setParent(RBTreeNode<T> node, RBTreeNode<T> parent) {
        if (null != node) {
            node.setParent(parent);
            if (root == parent) {
                node.setParent(null);
            }
        }
    }

    /**
     * 获取叔叔结点
     * @param node
     * @return
     */
    private RBTreeNode<T> getUncle(RBTreeNode<T> node) {
        RBTreeNode<T> parent = node.getParent();
        RBTreeNode<T> ancestor = parent.getParent();

        if (null == ancestor) {
            return null;
        }

        if (parent == ancestor.getLeft()) {
            return ancestor.getRight();
        } else {
            return ancestor.getLeft();
        }
    }

    /**
     * 左旋转
     * @param node
     */
    private void rotateLeft(RBTreeNode<T> node) {
        RBTreeNode<T> right = node.getRight();
        if (null == right) {
            throw new IllegalStateException("右子节点为空");
        }
        RBTreeNode<T> parent = node.getParent();
        node.setRight(right.getLeft());
        setParent(right.getLeft(), node);

        right.setLeft(node);
        setParent(node, right);

        if (null == parent) {
            root.setLeft(right);
            setParent(right, null);
        } else {
            if (node == parent.getLeft()) {
                parent.setLeft(right);
            } else {
                parent.setRight(right);
            }
            setParent(right, parent);
        }
    }

    /**
     * 右旋转
     * @param node
     */
    private void rotateRight(RBTreeNode<T> node) {
        RBTreeNode<T> left = node.getLeft();
        if (null == left) {
            throw new IllegalStateException("左子节点为空");
        }
        RBTreeNode<T> parent = node.getParent();
        node.setLeft(left.getRight());
        setParent(left.getRight(), node);

        left.setRight(node);
        setParent(node, left);

        if (null == parent) {
            root.setLeft(left);
            setParent(left, null);
        } else {
            if (node == parent.getLeft()) {
                parent.setLeft(left);
            } else {
                parent.setRight(left);
            }
            setParent(left, parent);
        }
    }


    private T addNode(RBTreeNode<T> node) {
        node.setLeft(null);
        node.setRight(null);
        node.setRed(true);
        setParent(node, null);

        // 根节点
        if (null == root.getLeft()) {
            root.setLeft(node);
            node.setRed(false);
            size.incrementAndGet();
        } else {
            RBTreeNode<T> x = findParentNode(node);
            int cmp = x.getValue().compareTo(node.getValue());
            if (this.overrideMode && 0 == cmp) {
                T v = x.getValue();
                x.setValue(node.getValue());
                return v;
            } else if (0 == cmp) {
                // 非可覆盖模式，节点已存在，不做处理
                return x.getValue();
            }

            setParent(node, x);

            if (cmp > 0) {
                x.setLeft(node);
            } else {
                x.setRight(node);
            }

            fixInsert(node);
            size.incrementAndGet();
        }

        return null;
    }

    /**
     * 插入节点，红黑树自平衡
     * @param x
     */
    private void fixInsert(RBTreeNode<T> x) {
        RBTreeNode<T> parent = x.getParent();

        while (null != parent && parent.isRed()) {
            RBTreeNode<T> uncle = getUncle(x);
            if (null == uncle) { // 需要旋转
                RBTreeNode<T> ancestor = parent.getParent();
                // 父节点为左节点
                if (parent == ancestor.getLeft()) {
                    boolean isRight = x == parent.getRight();
                    if (isRight) {
                        rotateLeft(parent);
                    }
                    rotateRight(ancestor);

                    if (isRight) {
                        x.setRed(false);
                        parent = null;
                    } else {
                        parent.setRed(false);
                    }
                    ancestor.setRed(true);
                } else {
                    boolean isLeft = x == parent.getLeft();
                    if (isLeft) {
                        rotateRight(parent);
                    }
                    rotateLeft(ancestor);

                    if (isLeft) {
                        x.setRed(false);
                        parent = null; // 停止循环
                    } else {
                        parent.setRed(false);
                    }
                    ancestor.setRed(true);
                }
            } else { // 如果uncle是红色节点
                parent.setRed(false);
                uncle.setRed(false);
                parent.getParent().setRed(true);
                x = parent.getParent();
                parent = x.getParent();
            }
        }
        getRoot().makeBlack();
        getRoot().setParent(null);
    }

    /**
     * remove the node by give value,if this value not exists in tree return null
     * @param value include search key
     * @return the value contain in the removed node
     */
    public T remove(T value){
        RBTreeNode<T> dataRoot = getRoot();
        RBTreeNode<T> parent = root;

        while(dataRoot!=null){
            int cmp = dataRoot.getValue().compareTo(value);
            if(cmp<0){
                parent = dataRoot;
                dataRoot = dataRoot.getRight();
            }else if(cmp>0){
                parent = dataRoot;
                dataRoot = dataRoot.getLeft();
            }else{
                if(dataRoot.getRight()!=null){
                    RBTreeNode<T> min = removeMin(dataRoot.getRight());
                    //x used for fix color balance
                    RBTreeNode<T> x = min.getRight()==null ? min.getParent() : min.getRight();
                    boolean isParent = min.getRight()==null;

                    min.setLeft(dataRoot.getLeft());
                    setParent(dataRoot.getLeft(),min);
                    if(parent.getLeft()==dataRoot){
                        parent.setLeft(min);
                    }else{
                        parent.setRight(min);
                    }
                    setParent(min,parent);

                    boolean curMinIsBlack = min.isBlack();
                    //inherit dataRoot's color
                    min.setRed(dataRoot.isRed());

                    if(min!=dataRoot.getRight()){
                        min.setRight(dataRoot.getRight());
                        setParent(dataRoot.getRight(),min);
                    }
                    //remove a black node,need fix color
                    if(curMinIsBlack){
                        if(min!=dataRoot.getRight()){
                            fixRemove(x,isParent);
                        }else if(min.getRight()!=null){
                            fixRemove(min.getRight(),false);
                        }else{
                            fixRemove(min,true);
                        }
                    }
                }else{
                    setParent(dataRoot.getLeft(),parent);
                    if(parent.getLeft()==dataRoot){
                        parent.setLeft(dataRoot.getLeft());
                    }else{
                        parent.setRight(dataRoot.getLeft());
                    }
                    //current node is black and tree is not empty
                    if(dataRoot.isBlack() && !(root.getLeft()==null)){
                        RBTreeNode<T> x = dataRoot.getLeft()==null
                                ? parent :dataRoot.getLeft();
                        boolean isParent = dataRoot.getLeft()==null;
                        fixRemove(x,isParent);
                    }
                }
                setParent(dataRoot,null);
                dataRoot.setLeft(null);
                dataRoot.setRight(null);
                if(getRoot()!=null){
                    getRoot().setRed(false);
                    getRoot().setParent(null);
                }
                size.decrementAndGet();
                return dataRoot.getValue();
            }
        }
        return null;
    }
    /**
     * fix remove action
     * @param node
     * @param isParent
     */
    private void fixRemove(RBTreeNode<T> node,boolean isParent){
        RBTreeNode<T> cur = isParent ? null : node;
        boolean isRed = isParent ? false : node.isRed();
        RBTreeNode<T> parent = isParent ? node : node.getParent();

        while(!isRed && !isRoot(cur)){
            RBTreeNode<T> sibling = getSibling(cur,parent);
            //sibling is not null,due to before remove tree color is balance

            //if cur is a left node
            boolean isLeft = parent.getRight()==sibling;
            if(sibling.isRed() && !isLeft){//case 1
                //cur in right
                parent.makeRed();
                sibling.makeBlack();
                rotateRight(parent);
            }else if(sibling.isRed() && isLeft){
                //cur in left
                parent.makeRed();
                sibling.makeBlack();
                rotateLeft(parent);
            }else if(isBlack(sibling.getLeft()) && isBlack(sibling.getRight())){//case 2
                sibling.makeRed();
                cur = parent;
                isRed = cur.isRed();
                parent=parent.getParent();
            }else if(isLeft && !isBlack(sibling.getLeft())
                    && isBlack(sibling.getRight())){//case 3
                sibling.makeRed();
                sibling.getLeft().makeBlack();
                rotateRight(sibling);
            }else if(!isLeft && !isBlack(sibling.getRight())
                    && isBlack(sibling.getLeft()) ){
                sibling.makeRed();
                sibling.getRight().makeBlack();
                rotateLeft(sibling);
            }else if(isLeft && !isBlack(sibling.getRight())){//case 4
                sibling.setRed(parent.isRed());
                parent.makeBlack();
                sibling.getRight().makeBlack();
                rotateLeft(parent);
                cur=getRoot();
            }else if(!isLeft && !isBlack(sibling.getLeft())){
                sibling.setRed(parent.isRed());
                parent.makeBlack();
                sibling.getLeft().makeBlack();
                rotateRight(parent);
                cur=getRoot();
            }
        }
        if(isRed){
            cur.makeBlack();
        }
        if(getRoot()!=null){
            getRoot().setRed(false);
            getRoot().setParent(null);
        }

    }

    /**
     * 打印红黑树
     * @param root
     */
    public void printTree(RBTreeNode<T> root) {
        LinkedList<RBTreeNode<T>> queue = new LinkedList<RBTreeNode<T>>();
        LinkedList<RBTreeNode<T>> queue2 = new LinkedList<RBTreeNode<T>>();

        if (null == root) {
            return;
        }
        queue.add(root);
        boolean firstQueue = true;

        while (!queue.isEmpty() || !queue2.isEmpty()) {
            LinkedList<RBTreeNode<T>> tempQueue = firstQueue ? queue : queue2;
            RBTreeNode<T> node = tempQueue.poll();
            if (null != node) {
                String pos = null == node.getParent() ? "" : (node == node.getParent().getLeft() ? "LE" : "R");
                String pstr = null == node.getParent() ? "" : node.getParent().toString();
                String cstr = node.isRed() ? "R" : "B";
                cstr = null == node.getParent() ? cstr : cstr + " ";
                System.out.print(node + "(" + cstr + pstr + pos + ")\t");

                if (null != node.getLeft()) {
                    (firstQueue ? queue2 : queue).add(node.getLeft());
                }

                if (null != node.getRight()) {
                    (firstQueue ? queue2 : queue).add(node.getRight());
                }
            } else {
                System.out.println();
                firstQueue = !firstQueue;
            }
        }
    }

    public static void main(String[] args) {
        RBTree<String> bst = new RBTree<String>();
        bst.addNode("d");
        bst.addNode("d");
        bst.addNode("c");
        bst.addNode("c");
        bst.addNode("b");
        bst.addNode("f");

        bst.addNode("a");
        bst.addNode("e");

        bst.addNode("g");
        bst.addNode("h");


        bst.remove("c");

        bst.printTree(bst.getRoot());
    }
}

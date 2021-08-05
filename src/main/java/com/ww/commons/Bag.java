package com.ww.commons;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Bag<Item> implements Iterable<Item> {

    private Node<Item> first;

    private int n;

    public Bag() {
        this.first = null;
        this.n = 0;
    }

    public boolean isEmpty() {
        return null == this.first;
    }

    public int size() {
        return this.n;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void add(Item item) {
        Node<Item> oldfirst = this.first;
        this.first = new Node();
        this.first.item = item;
        this.first.next = oldfirst;
        this.n += 1;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator(first);
    }

    private class ListIterator implements Iterator<Item> {

        private Bag.Node<Item> current;

        public ListIterator(Node<Item> first) {
            this.current = first;
        }

        public boolean hasNext() {
            return null != this.current;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    private static class Node<Item> {

        private Item item;

        private Node<Item> next;
    }

    public static void main(String[] args) {
        Bag<String> bag = new Bag<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            bag.add(item);
        }

        System.out.println("size of bag = " + bag.size());
        for (String s : bag) {
            System.out.println(s);
        }
    }
}

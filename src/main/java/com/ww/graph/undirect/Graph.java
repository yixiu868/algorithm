package com.ww.graph.undirect;

import com.ww.commons.Bag;
import com.ww.commons.In;
import com.ww.commons.Stack;

import java.util.NoSuchElementException;

public class Graph {

    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V; // 顶点数目

    private int E; // 边的数目

    private Bag<Integer>[] adj; // 邻接表

    @SuppressWarnings("unchecked")
    public Graph(int V) {
        if (V < 0) {
            throw new IllegalArgumentException("Number of vertices must be nonnegative");
        }
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[])new Bag[V]; // 创建邻接表
        for (int v = 0; v < V; v++) { // 将所有链表初始化为空
            adj[v] = new Bag<>();
        }
    }

    @SuppressWarnings("unchecked")
    public Graph(In in) {
        try {
            this.V = in.readInt();
            if (V < 0) {
                throw new IllegalArgumentException("number of vertices in a Graph must be nonnegative");
            }
            adj = (Bag<Integer>[]) new Bag[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new Bag<>();
            }
            int E = in.readInt();
            if (E < 0) {
                throw new IllegalArgumentException("number of vertices in a Graph must be nonnegative");
            }
            for (int i = 0; i < E; i++) {
                int v = in.readInt();
                int w = in.readInt();
                validateVertex(v);
                validateVertex(w);
                addEdge(v, w);
            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in Graph constructor", e);
        }
    }

    public Graph(Graph G) {
        this(G.V());
        this.E = G.E();
        for (int v = 0; v < G.V(); v++) {
            Stack<Integer> reverse = new Stack<>();
            for (int w : G.adj[v]) {
                reverse.push(w);
            }
            for (int w : reverse) {
                adj[v].add(w);
            }
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
        }
    }

    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            sb.append(v + ": ");
            for (int w : adj[v]) {
                sb.append(w + " ");
            }
            sb.append(NEWLINE);
        }
        return sb.toString();
    }
}

package com.ww.graph.undirect;

import com.ww.commons.In;
import com.ww.commons.Queue;
import com.ww.commons.Stack;
import com.ww.commons.StdOut;

/**
 * 广度优先搜索查找图中路径
 */
public class BreadthFirstPaths {

    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked; // 到达该顶点的最短路径已知吗
    private int[] edgeTo; // 到达该顶点的已知路径上的最后一个顶点
    private int[] distTo;

    public BreadthFirstPaths(Graph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        distTo = new int[G.V()];
        bfs(G, s);
    }

    public BreadthFirstPaths(Graph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        edgeTo = new int[G.V()];

        for (int v = 0; v < G.V(); v++) {
            distTo[v] = INFINITY;
        }
        bfs(G, sources);
    }

    private void bfs(Graph G, int s) {
        Queue<Integer> queue = new Queue<>();
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = INFINITY;
        }
        distTo[s] = 0;
        marked[s] = true; // 标记起点
        queue.enqueue(s); // 起点加入队列
        while (!queue.isEmpty()) {
            int v = queue.dequeue(); // 从队列中删去下一顶点
            for (int w : G.adj(v)) {
                if (!marked[w]) { // 对于每个为被标记的相邻顶点
                    edgeTo[w] = v; // 保存最短路路径的最后一条边
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true; // 标记它，因为最短路已加
                    queue.enqueue(w); // 并将它加入队列中
                }
            }
        }
    }

    private void bfs(Graph G, Iterable<Integer> sources) {
        Queue<Integer> q = new Queue<>();
        for (int s : sources) {
            marked[s] = true;
            distTo[s] = 0;
            q.enqueue(s);
        }

        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public int distTo(int v) {
        return distTo[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<Integer> path = new Stack<>();
        int x;
        for (x = v; 0 != distTo[x]; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(x);
        return path;
    }

    private boolean check(Graph G, int s) {
        if (0 != distTo[s]) {
            StdOut.println("distance of source " + s + " to itself = " + distTo[s]);
            return false;
        }

        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                if (hasPathTo(v) != hasPathTo(w)) {
                    StdOut.println("edge " + v + "-" + w);
                    StdOut.println("hasPathTo(" + v + ") = " + hasPathTo(v));
                    StdOut.println("hasPathTo(" + w + ") = " + hasPathTo(w));
                    return false;
                }

                if (hasPathTo(v) && (distTo[w] > distTo[v] + 1)) {
                    StdOut.println("edge " + v + "-" + w);
                    StdOut.println("distTo[" + v + "] = " + distTo[v]);
                    StdOut.println("distTo[" + w + "] = " + distTo[w]);
                    return false;
                }
            }
        }

        for (int w = 0; w < G.V(); w++) {
            if (!hasPathTo(w) || w == s) {
                continue;
            }
            int v = edgeTo[w];
            if (distTo[w] != distTo[v] + 1) {
                StdOut.println("shortest path edge " + v + "-" + w);
                StdOut.println("distTo[" + v + "] = " + distTo[v]);
                StdOut.println("distTo[" + w + "] = " + distTo[w]);
                return false;
            }
        }

        return true;
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
        }
    }

    private void validateVertices(Iterable<Integer> vertices) {
        if (null == vertices) {
            throw new IllegalArgumentException("argument is null");
        }
        int V = marked.length;
        for (int v : vertices) {
            if (v < 0 || v >= V) {
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
            }
        }
    }

    public static void main(String[] args) {
        In in = new In("D:/test/mediumG.txt");
        Graph G = new Graph(in);
        int s = Integer.parseInt("0");
        BreadthFirstPaths bfs = new BreadthFirstPaths(G, s);
        for (int v = 0; v < G.V(); v++) {
            if (bfs.hasPathTo(v)) {
                StdOut.printf("%d to %d (%d): ", s, v, bfs.distTo(v));
                for (int x : bfs.pathTo(v)) {
                    if (x == s) {
                        StdOut.print(x);
                    } else {
                        StdOut.print("-" + x);
                    }
                }
                StdOut.println();
            } else {
                StdOut.printf("%d to %d (-): not connected\n", s, v);
            }
        }
    }
}

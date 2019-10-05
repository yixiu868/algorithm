package com.ww.graph.undirect;

import com.ww.commons.In;
import com.ww.commons.StdOut;

public class TestSearch {

    public static void main(String[] args) {
        Graph G = new Graph(new In("D:/test/tinyG1.txt"));
        int s = Integer.parseInt("0");
        DepthFirstSearch search = new DepthFirstSearch(G, s);

        for (int v = 0; v < G.V(); v++) {
            if (search.marked(v)) {
                StdOut.print(v + " ");
            }
        }
        StdOut.println();

        if (search.count() != G.V()) {
            StdOut.print("NOT ");
        }
        StdOut.println("connected");
    }
}

package com.ww.graph.undirect;

import com.ww.commons.In;
import com.ww.commons.StdOut;


public class DepthFirstPathsTest {

    public static void main(String[] args) {
        Graph G = new Graph(new In("D:/test/tinyCG.txt"));
        int s = Integer.parseInt("0");
        BreadthFirstPaths search = new BreadthFirstPaths(G, s);

        for (int v = 0; v < G.V(); v++) {
            StdOut.print(s + " to " + v + ": ");
            if (search.hasPathTo(v)) {
                for (int x : search.pathTo(v)) {
                    if (x == s) {
                        StdOut.print(x);
                    } else {
                        StdOut.print("-" + x);
                    }
                    StdOut.println();
                }
            }
        }
    }
}

package mst;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class TestAcyclicMST {

    @Test
    void testAcyclicMST() {
        Graph g = new Graph(4);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 2);
        g.addEdge(2, 3, 3);
        g.addEdge(0, 3, 4);

        KruskalMST kruskal = new KruskalMST();
        kruskal.findMST(g);

        assertTrue(isAcyclic(kruskal.getMstEdges(), g.getVertices()),
                "MST must be acyclic");
    }

    private boolean isAcyclic(List<Edge> edges, int vertices) {
        int[] parent = new int[vertices];
        for (int i = 0; i < vertices; i++) parent[i] = i;

        for (Edge e : edges) {
            int x = find(parent, e.getSrc());
            int y = find(parent, e.getDest());
            if (x == y) return false;
            parent[x] = y;
        }
        return true;
    }

    private int find(int[] parent, int i) {
        if (parent[i] == i) return i;
        return find(parent, parent[i]);
    }
}

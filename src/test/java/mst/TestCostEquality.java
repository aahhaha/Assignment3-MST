package mst;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestCostEquality {

    @Test
    void testTotalCostIsSame() {
        Graph g = new Graph(5);
        g.addEdge(0, 1, 1);
        g.addEdge(0, 2, 3);
        g.addEdge(1, 2, 2);
        g.addEdge(1, 3, 4);
        g.addEdge(2, 4, 5);
        g.addEdge(3, 4, 6);

        PrimMST prim = new PrimMST();
        prim.findMST(g);
        KruskalMST kruskal = new KruskalMST();
        kruskal.findMST(g);

        assertEquals(prim.getTotalCost(), kruskal.getTotalCost(), 1e-9,
                "Prim and Kruskal must produce same MST cost");
    }
}

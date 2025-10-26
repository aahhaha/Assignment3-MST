package mst;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestDisconnectedGraph {

    @Test
    void testDisconnectedGraphHandled() {
        Graph g = new Graph(4);
        g.addEdge(0, 1, 1);

        PrimMST prim = new PrimMST();
        prim.findMST(g);

        assertTrue(prim.getMstEdges().size() < g.getVertices() - 1,
                "Disconnected graph should produce partial MST or message");
    }
}

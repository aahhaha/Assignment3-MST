package mst;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestPerformanceMetrics {

    @Test
    void testPerformanceMetricsNonNegative() {
        Graph g = new Graph(5);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 2);
        g.addEdge(2, 3, 3);
        g.addEdge(3, 4, 4);

        PrimMST prim = new PrimMST();
        prim.findMST(g);

        assertTrue(prim.getOperationCount() >= 0, "Operation count must be non-negative");
        assertTrue(prim.getExecutionTimeMs() >= 0, "Execution time must be non-negative");
    }
}

package mst;

import java.util.*;

public class KruskalMST {

    private List<Edge> mstEdges = new ArrayList<>();
    private double totalCost = 0.0;
    private long executionTimeMs = 0;
    private int operationCount = 0;

    public void findMST(Graph graph) {
        long start = System.nanoTime();

        List<Edge> edges = new ArrayList<>(graph.getAllEdges());
        edges.sort(Comparator.comparingDouble(Edge::getWeight));

        UnionFind uf = new UnionFind(graph.getVertices());

        for (Edge e : edges) {
            int v = e.getSrc();
            int w = e.getDest();

            if (!uf.connected(v, w)) {
                uf.union(v, w);
                mstEdges.add(e);
                totalCost += e.getWeight();
                operationCount++;
            }

            if (mstEdges.size() == graph.getVertices() - 1)
                break;
        }

        long end = System.nanoTime();
        executionTimeMs = (end - start) / 1_000_000;
    }

    public List<Edge> getMstEdges() {
        return mstEdges;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public long getExecutionTimeMs() {
        return executionTimeMs;
    }

    public int getOperationCount() {
        return operationCount;
    }
}

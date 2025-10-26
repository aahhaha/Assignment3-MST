package mst;

import java.util.*;

public class PrimMST {

    private List<Edge> mstEdges = new ArrayList<>();
    private double totalCost = 0.0;
    private long executionTimeMs = 0;
    private int operationCount = 0;

    public void findMST(Graph graph) {
        long start = System.nanoTime();

        int V = graph.getVertices();
        boolean[] visited = new boolean[V];
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingDouble(Edge::getWeight));

        // начинаем с вершины 0
        visited[0] = true;
        pq.addAll(graph.getAdj(0));
        operationCount++;

        while (!pq.isEmpty() && mstEdges.size() < V - 1) {
            Edge edge = pq.poll();
            operationCount++;

            int v = edge.getSrc();
            int w = edge.getDest();

            if (visited[v] && visited[w]) continue;

            mstEdges.add(edge);
            totalCost += edge.getWeight();

            int newVertex = visited[v] ? w : v;
            visited[newVertex] = true;

            for (Edge e : graph.getAdj(newVertex)) {
                if (!visited[e.getDest()]) {
                    pq.offer(e);
                    operationCount++;
                }
            }
        }

        long end = System.nanoTime();
        executionTimeMs = (end - start) / 1_000_000;
    }

    // === Методы для тестов ===
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

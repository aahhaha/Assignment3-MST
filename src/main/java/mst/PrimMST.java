package mst;

import java.util.*;

public class PrimMST {
    private final List<Edge> mstEdges = new ArrayList<>();
    private double totalCost = 0;
    private int operationCount = 0;

    public void findMST(Graph graph) {
        int V = graph.getVertices();
        boolean[] visited = new boolean[V];
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingDouble(Edge::getWeight));

        visited[0] = true;
        for (Edge e : graph.getEdges()) {
            if (e.getSrc() == 0 || e.getDest() == 0) pq.add(e);
        }

        while (!pq.isEmpty() && mstEdges.size() < V - 1) {
            Edge edge = pq.poll();
            operationCount++;
            int next = -1;

            if (visited[edge.getSrc()] && !visited[edge.getDest()]) next = edge.getDest();
            else if (visited[edge.getDest()] && !visited[edge.getSrc()]) next = edge.getSrc();
            else continue;

            mstEdges.add(edge);
            totalCost += edge.getWeight();
            visited[next] = true;

            for (Edge e : graph.getEdges()) {
                if ((e.getSrc() == next && !visited[e.getDest()]) ||
                        (e.getDest() == next && !visited[e.getSrc()])) {
                    pq.add(e);
                }
            }
        }
    }

    public List<Edge> getMstEdges() {
        return mstEdges;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public int getOperationCount() {
        return operationCount;
    }
}

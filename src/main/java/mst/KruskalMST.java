package mst;

import java.util.*;

public class KruskalMST {
    private final List<Edge> mstEdges = new ArrayList<>();
    private double totalCost = 0;
    private int operationCount = 0;

    private static class UnionFind {
        private final int[] parent;
        private final int[] rank;

        public UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) parent[i] = i;
        }

        public int find(int v) {
            if (v != parent[v]) parent[v] = find(parent[v]);
            return parent[v];
        }

        public void union(int a, int b) {
            a = find(a);
            b = find(b);
            if (a != b) {
                if (rank[a] < rank[b]) parent[a] = b;
                else if (rank[a] > rank[b]) parent[b] = a;
                else {
                    parent[b] = a;
                    rank[a]++;
                }
            }
        }
    }

    public void findMST(Graph graph) {
        List<Edge> edges = new ArrayList<>(graph.getEdges());
        Collections.sort(edges);

        UnionFind uf = new UnionFind(graph.getVertices());
        for (Edge edge : edges) {
            operationCount++;
            int src = edge.getSrc();
            int dest = edge.getDest();

            if (uf.find(src) != uf.find(dest)) {
                uf.union(src, dest);
                mstEdges.add(edge);
                totalCost += edge.getWeight();
            }
            if (mstEdges.size() == graph.getVertices() - 1) break;
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

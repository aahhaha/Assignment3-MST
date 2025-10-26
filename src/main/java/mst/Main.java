package mst;

import com.google.gson.*;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String inputFile = "src/main/resources/input.json";
        String outputFile = "src/main/resources/output.json";

        try {
            Gson gson = new Gson();
            JsonObject jsonData = JsonParser.parseReader(new FileReader(inputFile)).getAsJsonObject();
            JsonArray graphsArray = jsonData.getAsJsonArray("graphs");

            JsonArray resultsArray = new JsonArray();

            for (JsonElement graphElem : graphsArray) {
                JsonObject graphObj = graphElem.getAsJsonObject();
                int id = graphObj.get("id").getAsInt();
                JsonArray nodes = graphObj.getAsJsonArray("nodes");
                JsonArray edges = graphObj.getAsJsonArray("edges");

                Graph graph = new Graph(nodes.size());
                for (JsonElement e : edges) {
                    JsonObject edgeObj = e.getAsJsonObject();
                    String fromNode = edgeObj.get("from").getAsString();
                    String toNode = edgeObj.get("to").getAsString();
                    int src = findIndex(nodes, fromNode);
                    int dest = findIndex(nodes, toNode);
                    double weight = edgeObj.get("weight").getAsDouble();
                    graph.addEdge(src, dest, weight);
                }

                PrimMST prim = new PrimMST();
                long startPrim = System.nanoTime();
                prim.findMST(graph);
                long endPrim = System.nanoTime();

                KruskalMST kruskal = new KruskalMST();
                long startKruskal = System.nanoTime();
                kruskal.findMST(graph);
                long endKruskal = System.nanoTime();

                JsonObject resultObj = new JsonObject();
                resultObj.addProperty("graph_id", id);

                JsonObject inputStats = new JsonObject();
                inputStats.addProperty("vertices", graph.getVertices());
                inputStats.addProperty("edges", graph.getEdges().size());
                resultObj.add("input_stats", inputStats);

                JsonObject primObj = new JsonObject();
                primObj.add("mst_edges", edgesToJsonArray(prim.getMstEdges(), nodes));
                primObj.addProperty("total_cost", prim.getTotalCost());
                primObj.addProperty("operations_count", prim.getOperationCount());
                primObj.addProperty("execution_time_ms", (endPrim - startPrim) / 1_000_000.0);
                resultObj.add("prim", primObj);

                JsonObject kruskalObj = new JsonObject();
                kruskalObj.add("mst_edges", edgesToJsonArray(kruskal.getMstEdges(), nodes));
                kruskalObj.addProperty("total_cost", kruskal.getTotalCost());
                kruskalObj.addProperty("operations_count", kruskal.getOperationCount());
                kruskalObj.addProperty("execution_time_ms", (endKruskal - startKruskal) / 1_000_000.0);
                resultObj.add("kruskal", kruskalObj);

                resultsArray.add(resultObj);
            }

            JsonObject finalOutput = new JsonObject();
            finalOutput.add("results", resultsArray);

            try (FileWriter writer = new FileWriter(outputFile)) {
                gson.toJson(finalOutput, writer);
            }

            System.out.println("Results written to " + outputFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int findIndex(JsonArray nodes, String nodeName) {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getAsString().equals(nodeName)) {
                return i;
            }
        }
        return -1;
    }

    private static JsonArray edgesToJsonArray(List<Edge> edges, JsonArray nodes) {
        JsonArray array = new JsonArray();
        for (Edge e : edges) {
            JsonObject obj = new JsonObject();
            obj.addProperty("from", nodes.get(e.getSrc()).getAsString());
            obj.addProperty("to", nodes.get(e.getDest()).getAsString());
            obj.addProperty("weight", e.getWeight());
            array.add(obj);
        }
        return array;
    }
}

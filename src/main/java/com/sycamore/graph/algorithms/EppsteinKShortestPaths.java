package com.sycamore.graph.algorithms;

import com.sycamore.graph.structure.Edge;

import java.util.*;
import java.util.stream.IntStream;

record Path(List<String> nodes, double cost) implements Comparable<Path> {
    @Override
    public int compareTo(Path other) {
        return Double.compare(this.cost, other.cost);
    }
}

public class EppsteinKShortestPaths {
    private final Map<String, List<Edge>> graph;

    public EppsteinKShortestPaths(Map<String, List<Edge>> graph) {
        this.graph = graph;
    }

    // Step 1: Compute shortest path tree (SPT) using Dijkstra from target node
    private Map<String, Double> computeShortestPathTree(String target) {
        Map<String, Double> dist = new HashMap<>();
        PriorityQueue<Path> pq = new PriorityQueue<>();
        pq.add(new Path(List.of(target), 0.0));

        while (!pq.isEmpty()) {
            Path path = pq.poll();
            String node = path.nodes().getFirst();
            if (dist.containsKey(node)) continue; // Skip if already visited
            dist.put(node, path.cost());

            for (var entry : graph.entrySet()) {
                for (Edge edge : entry.getValue()) {
                    if (edge.target().equals(node)) {
                        pq.add(new Path(List.of(entry.getKey()), path.cost() + edge.weight()));
                    }
                }
            }
        }
        return dist;
    }

    // Step 2: Compute k-shortest paths using Eppstein's method
    public List<Path> findKShortestPaths(String source, String target, int k) {
        Map<String, Double> shortestDistances = computeShortestPathTree(target);
        PriorityQueue<Path> pq = new PriorityQueue<>();
        List<Path> result = new ArrayList<>();

        pq.add(new Path(List.of(source), 0.0));

        while (!pq.isEmpty() && result.size() < k) {
            Path currentPath = pq.poll();
            String lastNode = currentPath.nodes().getLast();

            if (lastNode.equals(target)) {
                result.add(currentPath);
                continue;
            }

            for (Edge edge : graph.getOrDefault(lastNode, Collections.emptyList())) {
                double reducedCost = edge.weight() + shortestDistances.getOrDefault(edge.target(), Double.MAX_VALUE) - shortestDistances.getOrDefault(lastNode, 0.0);
                List<String> newNodes = new ArrayList<>(currentPath.nodes());
                newNodes.add(edge.target());
                pq.add(new Path(newNodes, currentPath.cost() + reducedCost));
            }
        }
        return result.stream().map(this::reconstruct).toList();
    }

    private Path reconstruct(Path path) {
        var nodes = path.nodes();
        var cost = IntStream.range(0, nodes.size() - 1)
                .mapToDouble(idx -> graph.get(nodes.get(idx)).stream()
                        .filter(it -> it.target().equals(nodes.get(idx + 1)))
                        .mapToDouble(it -> it.weight())
                        .findFirst().orElseThrow())
                .reduce(0.0, Double::sum);
        return new Path(nodes, cost);
    }

    public static void main(String[] args) {
        Map<String, List<Edge>> graph = new HashMap<>();
        graph.put("Warsaw", List.of(new Edge("Berlin", 573.0), new Edge("Prague", 680.0)));
        graph.put("Berlin", List.of(new Edge("Paris", 1050.0), new Edge("Amsterdam", 650.0)));
        graph.put("Prague", List.of(new Edge("Budapest", 530.0)));
        graph.put("Budapest", List.of(new Edge("Brno", 325.0)));
        graph.put("Amsterdam", List.of(new Edge("Paris", 500.0)));

        EppsteinKShortestPaths solver = new EppsteinKShortestPaths(graph);
        List<Path> paths = solver.findKShortestPaths("Warsaw", "Paris", 3);

        for (int i = 0; i < paths.size(); i++) {
            System.out.println("Path " + (i + 1) + ": " + paths.get(i).nodes() + ", Cost: " + paths.get(i).cost());
        }
    }
}

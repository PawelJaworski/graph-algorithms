package com.sycamore.graph.traversal;

import com.sycamore.graph.structure.Edge;
import com.sycamore.graph.structure.Graph;

import java.util.*;
import java.util.stream.IntStream;

public class EppsteinKShortestPaths {



    // Step 2: Compute k-shortest paths using Eppstein's method
    public List<Path> findKShortestPaths(String source, String target, Graph graph, int k) {
        Map<String, Double> shortestDistances = computeShortestPathTree(target, graph);
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

            for (Edge edge : graph.neighboursOf(lastNode)) {
                if (currentPath.nodes().contains(edge.target())) {
                    continue;
                }
                double reducedCost = edge.weight() + shortestDistances.getOrDefault(edge.target(), Double.MAX_VALUE) - shortestDistances.getOrDefault(lastNode, 0.0);
                List<String> newNodes = new ArrayList<>(currentPath.nodes());
                newNodes.add(edge.target());
                pq.add(new Path(newNodes, currentPath.cost() + reducedCost));
            }
        }
        return result.stream()
                .map(it -> reconstruct(it, graph)).toList();
    }

    // Step 1: Compute shortest path tree (SPT) using Dijkstra from target node
    private Map<String, Double> computeShortestPathTree(String target, Graph graph) {
        Map<String, Double> dist = new HashMap<>();
        PriorityQueue<Path> pq = new PriorityQueue<>();
        pq.add(new Path(List.of(target), 0.0));

        while (!pq.isEmpty()) {
            Path path = pq.poll();
            String node = path.nodes().getFirst();
            if (dist.containsKey(node)) {
                continue; // Skip if already visited
            }
            dist.put(node, path.cost());

            for (var entry : graph.edges().entrySet()) {
                for (Edge edge : entry.getValue()) {
                    if (edge.target().equals(node)) {
                        pq.add(new Path(List.of(entry.getKey()), path.cost() + edge.weight()));
                    }
                }
            }
        }
        return dist;
    }

    private Path reconstruct(Path path, Graph graph) {
        var nodes = path.nodes();
        var cost = IntStream.range(0, nodes.size() - 1)
                .mapToDouble(idx -> graph.neighboursOf(nodes.get(idx)).stream()
                        .filter(it -> it.target().equals(nodes.get(idx + 1)))
                        .mapToDouble(Edge::weight)
                        .findFirst().orElseThrow())
                .reduce(0.0, Double::sum);
        return new Path(nodes, cost);
    }
}

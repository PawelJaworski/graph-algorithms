package com.sycamore.graph.algorithms;

import com.sycamore.graph.structure.Edge;

import java.util.*;

public class AStarTraversal {
    public TraversalResult traverse(Graph graph, String start, String end, int n) {
        PriorityQueue<Path> queue = new PriorityQueue<>(Comparator.comparingDouble(Path::cost));
        queue.add(new Path(List.of(start), 0d));

        List<Path> paths = new ArrayList<>();
        while (!queue.isEmpty() && paths.size() < n) {
            Path currentPath = queue.poll();
            String currentNode = currentPath.lastNode();

            if (currentNode.equals(end)) {
                paths.add(currentPath);
            }

            graph.neighboursOf(currentNode)
                    .stream()
                    .filter(it -> !currentPath.nodes().contains(it.getEnd()))
                    .forEach(neighbor -> {
                var nextNode = neighbor.getEnd();
                double newCost = currentPath.cost() + neighbor.getCost();
                Path newPath = currentPath.add(nextNode, newCost + heuristic(nextNode, end));
                queue.add(newPath);
            });
        }

        return TraversalResult.builder()
                .paths(paths.stream()
                        .map(Path::nodes)
                        .toList())
                .build();
    }

    private static double heuristic(String a, String b) {
        return 0;
    }
}

class Graph {
    private final Map<String, Set<Edge>> connections = new HashMap<>();

    void addEdge(String from, String to, double weight) {
        if (!connections.containsKey(from)) {
            connections.put(from, new HashSet<>());
        }
        if (!connections.containsKey(to)) {
            connections.put(to, new HashSet<>());
        }
        connections.get(from).add(new Edge(from, to, weight));
        connections.get(to).add(new Edge(to, from, weight));
    }

    Set<Edge> neighboursOf(String node) {
        return connections.getOrDefault(node, Set.of());
    }
}

record Path(List<String> nodes, double cost) {
    Path add(String node, double newCost) {
        var newNodes = new ArrayList<>(nodes);
        newNodes.add(node);
        return new Path(newNodes, newCost);
    }
    String lastNode() {
        return nodes.getLast();
    }
}
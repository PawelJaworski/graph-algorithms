package com.sycamore.graphalgorithms;

import lombok.Builder;

import java.util.*;

public class AStarTraversal {
    public TraversalResult traverse(Graph graph, Node start, Node end, int n) {
        PriorityQueue<Path> queue = new PriorityQueue<>(Comparator.comparingDouble(Path::cost));
        queue.add(new Path(List.of(start), 0d));

        List<Path> paths = new ArrayList<>();
        while (!queue.isEmpty() && paths.size() < n) {
            Path currentPath = queue.poll();
            Node currentNode = currentPath.lastNode();

            if (currentNode.equals(end)) {
                paths.add(currentPath);
            }

            graph.neighboursOf(currentNode)
                    .stream()
                    .filter(it -> !currentPath.nodes().contains(new Node(it.end())))
                    .forEach(neighbor -> {
                var nextNode = neighbor.end();
                double newCost = currentPath.cost() + neighbor.cost();
                Path newPath = currentPath.add(new Node(nextNode), newCost + heuristic(nextNode, end.id()));
                queue.add(newPath);
            });
        }

        return TraversalResult.builder()
                .paths(paths.stream()
                        .map(it -> it.nodes().stream()
                                .map(Node::id)
                                .toList())
                        .toList())
                .build();
    }

    private static double heuristic(String a, String b) {
        return 0;
    }
}

record Node(String id) {

}

record Edge(String start, String end, double cost) {
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

    Set<Edge> neighboursOf(Node node) {
        return connections.getOrDefault(node.id(), Set.of());
    }
}

record Path(List<Node> nodes, double cost) {
    Path add(Node node, double newCost) {
        var newNodes = new ArrayList<>(nodes);
        newNodes.add(node);
        return new Path(newNodes, newCost);
    }
    Node lastNode() {
        return nodes.getLast();
    }
}
package com.sycamore.graph.traversal;

import com.sycamore.graph.structure.Graph;

import java.util.*;

public class AStarTraversal {
    public Optional<Path> traverse(Graph graph, String start, String end) {
        PriorityQueue<Path> queue = new PriorityQueue<>(Comparator.comparingDouble(Path::cost));
        queue.add(new Path(List.of(start), 0d));

        while (!queue.isEmpty()) {
            Path currentPath = queue.poll();
            String currentNode = currentPath.nodes()
                    .getLast();

            if (currentNode.equals(end)) {
                return Optional.of(currentPath);
            }

            graph.neighboursOf(currentNode)
                    .stream()
                    .filter(it -> !currentPath.nodes().contains(it.target()))
                    .forEach(neighbor -> {
                var nextNode = neighbor.target();
                double newCost = currentPath.cost() + neighbor.weight();
                Path newPath = currentPath.add(nextNode, newCost + heuristic(nextNode, end));
                queue.add(newPath);
            });
        }

        return Optional.empty();
    }

    private static double heuristic(String a, String b) {
        return 0;
    }
}

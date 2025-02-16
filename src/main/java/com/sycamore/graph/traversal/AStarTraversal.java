package com.sycamore.graph.traversal;

import com.sycamore.graph.structure.Graph;

import java.util.*;

public class AStarTraversal {
    public Optional<Path> traverse(String start, String end, Graph graph, Heuristic heuristic) {
        PriorityQueue<Path> queue = new PriorityQueue<>(Comparator.comparingDouble(it ->
                it.cost() + heuristic.calculate(it.nodes().getLast(), end)));
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
                Path newPath = currentPath.add(nextNode, newCost);
                queue.add(newPath);
            });
        }

        return Optional.empty();
    }

    public interface Heuristic {
        double calculate(String node, String target);
    }

    public static class Dijkstra implements Heuristic {

        @Override
        public double calculate(String node, String target) {
            return 0;
        }
    }
}

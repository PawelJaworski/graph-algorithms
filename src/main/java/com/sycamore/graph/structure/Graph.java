package com.sycamore.graph.structure;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph {
    private final Map<String, Set<Edge>> connections = new HashMap<>();

    public void addEdge(String from, String to, double weight) {
        if (!connections.containsKey(from)) {
            connections.put(from, new HashSet<>());
        }
        if (!connections.containsKey(to)) {
            connections.put(to, new HashSet<>());
        }
        connections.get(from).add(new Edge(to, weight));
        connections.get(to).add(new Edge(from, weight));
    }

    public Set<Edge> neighboursOf(String node) {
        return connections.getOrDefault(node, Set.of());
    }
}

package com.sycamore.graph.structure;

import java.util.Map;
import java.util.Set;

public record Graph(Map<String, Set<Edge>> edges) {
    public Set<Edge> neighboursOf(String node) {
        return edges.getOrDefault(node, Set.of());
    }
}

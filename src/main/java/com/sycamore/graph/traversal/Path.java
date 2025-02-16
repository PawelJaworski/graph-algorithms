package com.sycamore.graph.traversal;

import java.util.ArrayList;
import java.util.List;

public record Path(List<String> nodes, double cost) implements Comparable<Path> {
    Path add(String node, double newCost) {
        var newNodes = new ArrayList<>(nodes);
        newNodes.add(node);
        return new Path(newNodes, newCost);
    }

    @Override
    public int compareTo(Path other) {
        return Double.compare(this.cost, other.cost);
    }
}

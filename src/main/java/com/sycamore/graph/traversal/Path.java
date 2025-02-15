package com.sycamore.graph.traversal;

import java.util.List;

public record Path(List<String> nodes, double cost) implements Comparable<Path> {
    @Override
    public int compareTo(Path other) {
        return Double.compare(this.cost, other.cost);
    }
}

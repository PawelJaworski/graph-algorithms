package com.sycamore.graph.algorithms;

import lombok.Builder;

import java.util.List;

@Builder
public record TraversalResult(List<List<String>> paths) {
}

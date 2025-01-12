package com.sycamore.graphalgorithms;

import lombok.Builder;

import java.util.List;

@Builder
public record TraversalResult(List<List<String>> paths) {
}

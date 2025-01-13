package com.sycamore.graph.structure;

import lombok.Value;

@Value
public class Edge {
    String start;
    String end;
    double cost;
}

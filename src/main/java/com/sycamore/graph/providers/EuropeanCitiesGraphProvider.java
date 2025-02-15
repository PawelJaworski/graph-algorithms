package com.sycamore.graph.providers;

import com.sycamore.graph.structure.Edge;

import java.util.*;

public class EuropeanCitiesGraphProvider {
    public Map<String, Set<Edge>> getGraph() {
        Map<String, Set<Edge>> graph = new HashMap<>();

        graph.put("Warsaw", Set.of(
                new Edge("Krakow", 294.3),
                new Edge("Wroclaw", 354.8)
        ));
        graph.put("Wroclaw", Set.of(
                new Edge("Prague", 324.8),
                new Edge("Munich", 718.4),
                new Edge("Berlin", 344.0)
        ));
        graph.put("Krakow", Set.of(
                new Edge("Bratislava", 454.0)
        ));
        graph.put("Bratislava", Set.of(
                new Edge("Prague", 332.8)
        ));
        graph.put("Prague", Set.of(
                new Edge("Wien", 331.4)
        ));
        graph.put("Wien", Set.of(
                new Edge("Munich", 402.9),
                new Edge("Bern", 750.0),
                new Edge("Venice", 605.0)
        ));
        graph.put("Munich", Set.of(
                new Edge("Bern", 436.0)
        ));
        graph.put("Berlin", Set.of(
                new Edge("Dortmund", 493.6)
        ));
        graph.put("Dortmund", Set.of(
                new Edge("Amsterdam", 220.0),
                new Edge("Paris", 525.0)
        ));
        graph.put("Amsterdam", Set.of(
                new Edge("Brussels", 210.0)
        ));
        graph.put("Brussels", Set.of(
                new Edge("Paris", 320.0)
        ));
        graph.put("Paris", Set.of(
                new Edge("Toulouse", 680.0),
                new Edge("Marseille", 775.0)
        ));
        graph.put("Toulouse", Set.of(
                new Edge("Barcelona", 395.0)
        ));
        graph.put("Barcelona", Set.of(
                new Edge("Madrid", 620.0)
        ));
        graph.put("Madrid", Set.of(
                new Edge("Lisboa", 625.0)
        ));
        graph.put("Bern", Set.of(
                new Edge("Marseille", 500.0),
                new Edge("Genoa", 400.0),
                new Edge("Milan", 350.0)
        ));
        graph.put("Marseille", Set.of(
                new Edge("Toulouse", 395.0)
        ));
        graph.put("Genoa", Set.of(
                new Edge("Milan", 145.0)
        ));
        graph.put("Venice", Set.of(
                new Edge("Milan", 270.0)
        ));

        var transposed = transposeGraph(graph);

        return mergeMaps(graph, transposed);
    }

    public Map<String, Set<Edge>> transposeGraph(Map<String, Set<Edge>> graph) {
        Map<String, Set<Edge>> transposedGraph = new HashMap<>();

        for (Map.Entry<String, Set<Edge>> entry : graph.entrySet()) {
            String source = entry.getKey();
            for (Edge edge : entry.getValue()) {
                transposedGraph.putIfAbsent(edge.target(), new HashSet<>());
                transposedGraph.get(edge.target()).add(new Edge(source, edge.weight()));
            }
        }

        return transposedGraph;
    }

    private Map<String, Set<Edge>> mergeMaps(Map<String, Set<Edge>> map1, Map<String, Set<Edge>> map2) {
        var merged = new HashMap<>(map1);

        // Merging map2 into map1
        map2.forEach((key, value) ->
                merged.merge(key, value, (set1, set2) -> {
                    Set<Edge> mergedSet = new HashSet<>(set1);
                    mergedSet.addAll(set2);
                    return mergedSet;
                })
        );

        return merged;
    }
}

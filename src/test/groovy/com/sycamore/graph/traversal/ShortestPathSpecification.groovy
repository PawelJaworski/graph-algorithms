package com.sycamore.graph.traversal

import com.sycamore.graph.providers.EuropeanCitiesGraphProvider
import com.sycamore.graph.structure.Graph
import spock.lang.Specification

class ShortestPathSpecification extends Specification implements TestDataAbility {
    Graph graph = new EuropeanCitiesGraphProvider().graph

    def "find shortest path using A-Star algorithm"() {
        given:
        def aStarTraversal = new AStarTraversal()

        when:
        def fastestPath = aStarTraversal.traverse(graph, "Warsaw", "Barcelona")
                .orElseThrow()

        then:
        fastestPath.nodes() == ["Warsaw", "Wroclaw", "Prague", "Munich", "Bern", "Marseille", "Barcelona"]
        fastestPath.cost() == 2495.6
    }

    def "find k-shortest paths using Eppstein's algorithm"() {
        given:
        def eppsteinTraversal = new EppsteinKShortestPaths()

        when:
        def fastestPaths = eppsteinTraversal.findKShortestPaths("Warsaw", "Barcelona", graph, 3)

        then:
        with(fastestPaths[0]) {
            nodes() == ["Warsaw", "Wroclaw", "Prague", "Munich", "Bern", "Marseille", "Barcelona"]
            cost() == 2495.6
        }
        with(fastestPaths[1]) {
            nodes() == ["Warsaw", "Wroclaw", "Munich", "Bern", "Marseille", "Barcelona"]
            cost() == 2509.2
        }
        with(fastestPaths[2]) {
            nodes() == ["Warsaw", "Wroclaw", "Prague", "Bern", "Marseille", "Barcelona"]
            cost() == 2539.6
        }
    }
}

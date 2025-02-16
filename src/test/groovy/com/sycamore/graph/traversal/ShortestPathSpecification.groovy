package com.sycamore.graph.traversal

import com.sycamore.graph.providers.EuropeanCitiesGraphProvider
import com.sycamore.graph.structure.Graph
import spock.lang.Specification

class ShortestPathSpecification extends Specification implements TestDataAbility {
    Graph graph = new EuropeanCitiesGraphProvider().graph

    def "expect true"() {
        given:
        def aStarTraversal = new AStarTraversal()

        when:
        def result = aStarTraversal.traverse(graph, WARSAW, PARIS)

        println("$result")
        then:
        true
    }

    def "find k-shortest paths with Eppstein's algorithm"() {
        given:
        def eppstein = new EppsteinKShortestPaths()

        when:
        def warsawToBarcelona = eppstein.findKShortestPaths("Warsaw", "Barcelona", graph, 3)
        def barcelonaToWarsaw = eppstein.findKShortestPaths("Barcelona","Warsaw", graph, 3)

        warsawToBarcelona.forEach { println(it) }
        println()
        barcelonaToWarsaw.forEach { println(it) }
        then:
        true
    }
}

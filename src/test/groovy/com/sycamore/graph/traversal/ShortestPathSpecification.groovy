package com.sycamore.graph.traversal

import com.sycamore.graph.providers.EuropeanCitiesGraphProvider
import com.sycamore.graph.structure.Graph
import spock.lang.Specification

class ShortestPathSpecification extends Specification implements TestDataAbility {
    def graph = new Graph()
    def graphProvider = new EuropeanCitiesGraphProvider()

    def setup() {
        graph.addEdge(BERLIN, PARIS, 987)
        graph.addEdge(BERLIN, PRAGUE, 318)
        graph.addEdge(BERLIN, WARSAW, 552)
        graph.addEdge(WARSAW, PRAGUE, 579)
    }

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
        def eppstein = new EppsteinKShortestPaths(graphProvider.graph)

        when:
        def shortestPaths = eppstein.findKShortestPaths("Warsaw", "Barcelona", 3)

        shortestPaths.forEach { println(it) }
        then:
        true
    }
}

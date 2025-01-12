package com.sycamore.graphalgorithms

import spock.lang.Specification

class ShortestPathSpecification extends Specification implements TestDataAbility {
    def graph = new Graph()

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
        def result = aStarTraversal.traverse(graph, new Node(WARSAW), new Node(PARIS), 100)

        println("$result")
        then:
        true
    }
}

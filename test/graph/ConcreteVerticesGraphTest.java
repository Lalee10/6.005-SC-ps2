/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {

	/*
	 * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
	 */
	@Override
	public Graph<String> emptyInstance() {
		return new ConcreteVerticesGraph();
	}

	/*
	 * Testing ConcreteVerticesGraph.toString()...
	 * 
	 * Partitions: Empty graph. 1 vertex, 2 vertices and 1 edge
	 */
	@Test
	public void testGraphToStringEmpty() {
		Graph<String> g = emptyInstance();
		assertEquals("Expected empty graph to print empty {}", "graph.ConcreteVerticesGraph@{}", g.toString());
	}

	@Test
	public void testGraphToStringOneVertex() {
		Graph<String> g = emptyInstance();
		g.add("A");
		assertEquals("Expected 1 vertex in string", "graph.ConcreteVerticesGraph@{graph.Vertex@{A}, }", g.toString());
	}

	@Test
	public void testGraphToStringTwoVerticesOneEdge() {
		Graph<String> g = emptyInstance();
		g.add("A");
		g.add("B");
		g.set("A", "B", 1);
		String fullStr = g.toString();
		assertTrue("Expected correct name", fullStr.contains("graph.ConcreteVerticesGraph@{"));
		assertEquals("Expected final }", '}', fullStr.charAt(fullStr.length() - 1));
		assertTrue("Expected vertex A with edge", fullStr.contains("graph.Vertex@{A, 1->B}"));
		assertTrue("Expected vertex B with no edge", fullStr.contains("graph.Vertex@{B}"));
	}

	/*
	 * Testing Vertex...
	 */

	/*
	 * setEdgeTo: - set a new edge with a value - set a nonexistent edge to zero -
	 * set an existent edge to zero - set an existent edge to a new value
	 */
	@Test
	public void testSetEdgeToNewEdge() {
		Vertex a = new Vertex("A");
		Vertex b = new Vertex("B");

		assertEquals("expected 0 return for new edge", 0, a.setEdgeTo(b, 1));
		Map<Vertex, Integer> targets = a.getOutwardEdges();

		assertTrue("expected A to have B as a target", targets.containsKey(b));
		assertTrue("expected A to have an edge to B of weight 1", targets.containsValue(1));
		assertEquals("expected only one entry in a.targets", 1, targets.size());
	}

	@Test
	public void testSetExistingEdgeToZero() {
		Vertex a = new Vertex("A");
		Vertex b = new Vertex("B");

		assertEquals("expected 0 return for new edge", 0, a.setEdgeTo(b, 1));
		assertEquals("expected return of previous edge weight", 1, a.setEdgeTo(b, 0));

		assertTrue("expected no targets at A", a.getOutwardEdges().isEmpty());
	}

	@Test
	public void testSetNonexistentEdgeToZero() {
		Vertex a = new Vertex("A");
		Vertex b = new Vertex("B");

		assertEquals("expected 0 return for new edge", 0, a.setEdgeTo(b, 0));

		assertTrue("expected no targets at A", a.getOutwardEdges().isEmpty());
	}

	@Test
	public void testSetExistingEdgeToNewValue() {
		Vertex a = new Vertex("A");
		Vertex b = new Vertex("B");

		assertEquals("expected 0 return for new edge", 0, a.setEdgeTo(b, 1));
		assertEquals("expected return of previous edge", 1, a.setEdgeTo(b, 2));

		Map<Vertex, Integer> outwardEdges = a.getOutwardEdges();

		assertTrue("expected A to have B as a target", outwardEdges.containsKey(b));
		assertTrue("expected A to have an edge to B of weight 1", outwardEdges.containsValue(2));
		assertEquals("expected only one entry in a.targets", 1, outwardEdges.size());
	}

	/*
	 * Tests for Vertex.toString() partitions: name, 0 edges
	 * "graph.Vertex@{'$name'}" name, 1 edge
	 * "graph.Vertex@{'$name', $w->'$v2.name'}" name, 2+ edges
	 * "graph.Vertex@{'<name>', e->'<name2>', e->'<name3>'}"
	 */
	@Test
	public void testVertextoStringNoEdges() {
		Vertex v = new Vertex("A");
		assertEquals("Expected vertex with 0 edges to print name only", "graph.Vertex@{A}", v.toString());
	}

	@Test
	public void testVertextoStringOneEdge() {
		Vertex v = new Vertex("A");
		Vertex v2 = new Vertex("B");
		v.setEdgeTo(v2, 1);
		assertEquals("Expected vertex with 1 edge to print weight & target name", "graph.Vertex@{A, 1->B}",
				v.toString());
	}

	@Test
	public void testVertextoStringTwoEdges() {
		Vertex v = new Vertex("A");
		Vertex v2 = new Vertex("B");
		Vertex v3 = new Vertex("C");

		v.setEdgeTo(v2, 2);
		v.setEdgeTo(v3, 2);

		// May print in random order
		assertTrue("Expected vertex with 2 edges to print both of them",
				v.toString().equals("graph.Vertex@{A, 2->B, 2->C}")
						|| v.toString().equals("graph.Vertex@{A, 2->C, 2->B}"));
	}

}
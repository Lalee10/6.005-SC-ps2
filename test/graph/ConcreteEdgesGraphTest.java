/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {

	/*
	 * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
	 */
	@Override
	public Graph<String> emptyInstance() {
		return new ConcreteEdgesGraph();
	}

	/*
	 * Testing ConcreteEdgesGraph...
	 */

	// Testing strategy for ConcreteEdgesGraph.toString()
	// Partitions for toString():
	// 1) Empty Graph
	// 2) Non Empty Graph
	//

	// TODO tests for ConcreteEdgesGraph.toString()

	@Test
	public void testEmptyGraphToString() {
		ConcreteEdgesGraph ceg = new ConcreteEdgesGraph();
		assertTrue(ceg.toString().equals("Empty Graph"));
	}

	@Test
	public void testNonEmptyGraphToString() {
		ConcreteEdgesGraph ceg = new ConcreteEdgesGraph();
		ceg.add("A");
		ceg.add("B");
		ceg.add("C");
		ceg.set("A", "B", 10);
		ceg.set("B", "C", 5);
		ceg.set("C", "B", 15);

		String graphStr = "From: A  To: B  Weight: 10" + " --- " + "From: B  To: C  Weight: 5" + " --- "
				+ "From: C  To: B  Weight: 15" + " --- ";

		assertTrue(ceg.toString().equals(graphStr));
	}

	/*
	 * Testing Edge...
	 */

	// Testing strategy for Edge
	/*
	 * Testing partitions Edge.equals: 1) Equal Edges 2) Different weights 3)
	 * Different targets 4) Different sources
	 * 
	 * Testing partitions Edge.toString: There is only one partition
	 * 
	 */

	// TODO tests for operations of Edge
	Edge e1 = new Edge("A", "B", 10);
	Edge e2 = new Edge("A", "B", 10);
	Edge e3 = new Edge("A", "B", 20);
	Edge e4 = new Edge("A", "C", 10);
	Edge e5 = new Edge("C", "B", 10);

	@Test
	public void testEdgeEquals() {
		assertTrue(e1.equals(e2));
	}

	@Test
	public void testEdgeEqualsDiffWeight() {
		assertFalse(e1.equals(e3));
	}

	@Test
	public void testEdgeEqualsDiffTarget() {
		assertFalse(e1.equals(e4));
	}

	@Test
	public void testEdgeEqualsDiffSource() {
		assertFalse(e1.equals(e5));
	}

	@Test
	public void testEdgeToString() {
		String edgeStr = "From: " + "A" + "  To: " + "B" + "  Weight: " + "10";
		assertTrue(e1.toString().equals(edgeStr));
	}

	@Test
	public void testEdgeGetters() {
		assertEquals(e1.getFrom(), "A");
		assertEquals(e1.getTo(), "B");
		assertEquals(e1.getWeight(), 10);
	}

}

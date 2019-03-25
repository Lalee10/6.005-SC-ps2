/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph implements Graph<String> {

	private final Set<String> vertices = new HashSet<>();
	private final List<Edge> edges = new ArrayList<>();

	// Abstraction function:
	// Represents a graph with a set of vertices connected to each other
	// by weighted edges.
	// Representation invariant:
	//
	// Safety from rep exposure:
	// all members are private and final
	// vertices and edges are mutable (sets and edges) but the values contained
	// within are of immutable type (Edge and String)

	// TODO checkRep

	@Override
	public boolean add(String vertex) {
		return vertices.add(vertex);
	}

	@Override
	public int set(String source, String target, int weight) {
		if (weight < 0) {
			return 0;
		}

		int edgeIndex = -1;
		int p_weight = 0;

		for (int i = 0; i < edges.size(); i++) {
			Edge e = edges.get(i);
			if (e.getFrom().equals(source) && e.getTo().equals(target)) {
				edgeIndex = i;
			}
		}

		if (weight > 0) {
			Edge newEdge = new Edge(source, target, weight);
			if (edgeIndex < 0) {
				add(source);
				add(target);
				edges.add(newEdge);
			} else {
				Edge previousEdge = edges.set(edgeIndex, newEdge);
				p_weight = previousEdge.getWeight();
			}
		} else if (weight == 0 && edgeIndex >= 0) {
			Edge previousEdge = edges.remove(edgeIndex);
			p_weight = previousEdge.getWeight();
		}

		return p_weight;

	}

	@Override
	public boolean remove(String vertex) {
		boolean vertexExists = vertices.contains(vertex);

		if (vertexExists) {
			vertices.remove(vertex);
			for (int i = 0; i < edges.size(); i++) {
				if (edges.get(i).getFrom().equals(vertex) || edges.get(i).getTo().equals(vertex)) {
					edges.remove(i);
				}
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Set<String> vertices() {
		return Collections.unmodifiableSet(vertices);
	}

	@Override
	public Map<String, Integer> sources(String target) {
		Map<String, Integer> sourceMap = new HashMap<String, Integer>();
		for (Edge e : edges) {
			if (e.getTo().equals(target)) {
				sourceMap.put(e.getFrom(), e.getWeight());
			}
		}
		return sourceMap;
	}

	@Override
	public Map<String, Integer> targets(String source) {
		Map<String, Integer> targetMap = new HashMap<String, Integer>();
		for (Edge e : edges) {
			if (e.getFrom().equals(source)) {
				targetMap.put(e.getTo(), e.getWeight());
			}
		}
		return targetMap;
	}

	// toString()
	@Override
	public String toString() {
		if (edges.isEmpty()) {
			return "Empty Graph";
		}
		String str = "";
		for (Edge e : edges) {
			str += e.toString() + " --- ";
		}
		return str;
	}

}

/**
 * TODO specification Immutable. This class is internal to the rep of
 * ConcreteEdgesGraph.
 * 
 * <p>
 * PS2 instructions: the specification and implementation of this class is up to
 * you.
 */
class Edge {

	// TODO fields
	private final String from;
	private final String to;
	private final int weight;

	// Abstraction function:
	// TODO
	// Representation invariant:
	// TODO
	// Safety from rep exposure:
	// TODO

	// constructor

	public Edge(final String from, final String to, final int weight) {

		this.from = from;
		this.to = to;
		this.weight = weight;
		checkRep();
	}
	// checkRep

	private void checkRep() {
		assert from != null;
		assert to != null;
		assert weight > 0;
	}

	// methods

	public String getFrom() {
		return from;
	}

	/** Returns this Edge's target */
	public String getTo() {
		return to;
	}

	/** Returns this Edge's weight */
	public int getWeight() {
		return weight;
	}

	// toString()

	@Override
	public String toString() {
		return "From: " + getFrom().toString() + "  To: " + getTo().toString() + "  Weight: " + getWeight();
	}

	public boolean equals(Edge e) {
		if (this.getFrom().equals(e.getFrom()) && this.getTo().equals(e.getTo()) && this.getWeight() == e.getWeight()) {
			return true;
		}
		return false;

	}

}

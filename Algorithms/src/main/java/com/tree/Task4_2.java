package com.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Given a directed graph, design an algorithm to find out whether there is a route
 * between two nodes.
 */
public class Task4_2 {

	static class V {
		final String id;
		boolean visited;
		
		public V(String id) {
			this.id = id;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj instanceof V) {
				return this.id.equals(((V)obj).id);
			}
			return false;
		}
		
		@Override
		public int hashCode() {		
			return this.id.hashCode();
		}
		
		@Override
		public String toString() {		
			return this.id;
		}
	}
	
	V[] vertices;
	List<List<Integer>> edges;
	
	public boolean hasPath(int srcInd, int dstInd) {		
		V src = vertices[srcInd];
		V dst = vertices[dstInd];
		if (src.equals(dst)) {
			 return true;
		}
		
		Queue<Integer> queue = new LinkedList<>();
		src.visited = true;
		queue.add(srcInd);
		
		while (!queue.isEmpty()) {
			int ind = queue.remove();
			for (Integer child : edges.get(ind)) {
				if (!vertices[child].visited) {
					vertices[child].visited = true;	
					queue.add(child);
					if (vertices[child].equals(dst)) {
						return true;//found
					}
				}
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		Task4_2 graph = new Task4_2();
		
		graph.vertices = new V[8];		
		graph.edges = new ArrayList<>(20);		
		
		graph.vertices[0] = new V("r0");
		graph.vertices[1] = new V("s1");
		graph.vertices[2] = new V("t2");
		graph.vertices[3] = new V("u3");
		graph.vertices[4] = new V("v4");
		graph.vertices[5] = new V("w5");
		graph.vertices[6] = new V("x6");
		graph.vertices[7] = new V("y7");

		graph.edges.add(0, Arrays.asList(1, 4));
		graph.edges.add(1, Arrays.asList(0, 5));
		graph.edges.add(2, Arrays.asList(3, 5, 6));		
		graph.edges.add(3, Arrays.asList(2, 6, 7));
		graph.edges.add(4, Arrays.asList(0));		
		graph.edges.add(5, Arrays.asList(1, 2, 6));
		graph.edges.add(6, Arrays.asList(2, 3, 5, 7));
		graph.edges.add(7, Arrays.asList(3, 6));
		
		//r->x
		System.out.println(graph.hasPath(0, 6));
	}
}

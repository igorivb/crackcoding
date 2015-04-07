package com.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.IntWrapper;

public class StronglyConnectedComponents {

	enum Color { WHITE, GREY, BLACK }
	
	static class Vertex {
		final int id;
		final String str;
		int discoveryTime;
		int finishTime;
		Color color = Color.WHITE;
		Vertex parent;
		
		public Vertex(int id, String str) {
			this.id = id;
			this.str = str;
		}
		
		public Vertex(int id) {
			this(id, String.valueOf(id));
		}
		
		@Override
		public String toString() {		
			return str;
		}
	}		
	
	public boolean isScc(List<Vertex> vertices, List<List<Integer>> edges) {
		//TODO: implement
		return false;
	}
	
	
	public List<List<Vertex>> scc(List<Vertex> vertices, List<List<Integer>> edges) {
		dfsFirst(vertices, edges);
		
		List<List<Integer>> transposedEdges = this.transpose(edges);		
		
		return dfsSecond(vertices, transposedEdges);
	}		

	private List<List<Integer>> transpose(List<List<Integer>> edges) {
		List<List<Integer>> result = new ArrayList<>(edges.size());
		for (int i = 0; i < edges.size(); i ++) {
			result.add(new ArrayList<>());
		}
		
		for (int i = 0; i < edges.size(); i ++) {
			for (int edge : edges.get(i)) {
				result.get(edge).add(i);				
			}
		}
						
//		for (int i = 0; i < result.size(); i ++) {
//			for (int edge : result.get(i)) {
//				System.out.printf("(%d, %d)  ", i, edge);
//			}
//			System.out.println();
//		}
		
		return result;
	}

	private List<List<Vertex>> dfsSecond(List<Vertex> vertices, List<List<Integer>> edges) {				
		for (Vertex v : vertices) { //clear colors
			v.color = Color.WHITE; 
		}
		
		List<Vertex> sorted = new ArrayList<>(vertices);		
		Collections.sort(sorted, new Comparator<Vertex>() { //sort by finish time in desc
			@Override
			public int compare(Vertex o1, Vertex o2) {
				return o1.finishTime < o2.finishTime ? 1 : -1;
			}
		});
		
		List<List<Vertex>> components = new ArrayList<>();
		
		for (Vertex v : sorted) { //iterate starting by max finish time
			if (v.color == Color.WHITE) {
				List<Vertex> component = new ArrayList<>();
				components.add(component);
				//System.out.println("try: " + v);
				dfsSecondVisit(v, vertices, edges, component);
			}
		}
		
		return components;
	}

	private void dfsSecondVisit(Vertex v, List<Vertex> vertices, List<List<Integer>> edges, List<Vertex> result) {
		v.color = Color.GREY;
		result.add(v);
		
		for (Integer edge : edges.get(v.id)) {
			Vertex child = vertices.get(edge);
			if (child.color == Color.WHITE) { 
				
				//System.out.printf("(%s, %s)%n", v.id, child.id);
				
				dfsSecondVisit(child, vertices, edges, result);
			}			
		}
		
		v.color = Color.BLACK;
	}

	private void dfsFirst(List<Vertex> vertices, List<List<Integer>> edges) {
		IntWrapper time = new IntWrapper();
		for (Vertex v : vertices) {
			if (v.color == Color.WHITE) {
				dfsFirstVisit(v, vertices, edges, time);
			}
		}
	}

	void dfsFirstVisit(Vertex v, List<Vertex> vertices, List<List<Integer>> edges, IntWrapper time) {
		v.discoveryTime = time.val ++;
		v.color = Color.GREY;
		
		for (Integer edge : edges.get(v.id)) {
			Vertex child = vertices.get(edge);
			if (child.color == Color.WHITE) {				
				child.parent = v;
				
				//System.out.printf("(%s, %s)%n", v.id, child.id);
				
				dfsFirstVisit(child, vertices, edges, time); //recurse
			}
		}
		
		v.color = Color.BLACK;
		v.finishTime = time.val ++;		
	}
	
	public static void main(String[] args) {
		List<Vertex> vertices = new ArrayList<>();
		List<List<Integer>> edges = new ArrayList<>();
		
		//graph -1
//		vertices.add(new Vertex(0));
//		vertices.add(new Vertex(1));
//		vertices.add(new Vertex(2));
//		
//		edges.add(Arrays.asList(1, 2));
//		edges.add(Arrays.asList(0, 2));
//		edges.add(new ArrayList<>());
		
		//graph -2
		vertices.addAll(Arrays.asList(
			new Vertex(0, "a"), new Vertex(1, "b"), new Vertex(2, "c"), 
			new Vertex(3, "d"), new Vertex(4, "e"), new Vertex(5, "f"), 
			new Vertex(6, "g"), new Vertex(7, "h")));
		
		edges.add(0, Arrays.asList(1));
		edges.add(1, Arrays.asList(2, 4, 5));
		edges.add(2, Arrays.asList(3, 6));
		edges.add(3, Arrays.asList(2, 7));
		edges.add(4, Arrays.asList(0, 5));
		edges.add(5, Arrays.asList(6));
		edges.add(6, Arrays.asList(5, 7));
		edges.add(7, Arrays.asList(7));
		
		
		StronglyConnectedComponents scc = new StronglyConnectedComponents();
		List<List<Vertex>> components = scc.scc(vertices, edges);
		for (int i = 0; i < components.size(); i ++) {
			System.out.printf("Component %d: ", i);
			for (int j = 0, n = components.get(i).size(); j < n; j ++) {
				Vertex v = components.get(i).get(j);
				System.out.print(v + (j == n - 1 ? "\n" : ", "));
			}
		}
	}
}

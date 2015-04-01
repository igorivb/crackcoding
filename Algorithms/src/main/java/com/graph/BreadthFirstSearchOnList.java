package com.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BreadthFirstSearchOnList {

	enum Color { WHITE, GREY, BLACK }
	
	public static class Vertex {
		final String id;
		int depth;
		Vertex parent;
		Color color = Color.WHITE;
		List<Vertex> edges = new ArrayList<>();
		
		public Vertex(String id) {
			this.id = id;
		}
	} 
	
	Vertex[] vertices;	
	
	public void traverse(Vertex v) {
		v.color = Color.GREY;
		
		Queue<Vertex> queue = new LinkedList<>();				
		queue.add(v);
		
		while (!queue.isEmpty()) {
			v = queue.remove();
			System.out.print(v.id + " ");
			for (Vertex edge : v.edges) {
				if (edge.color == Color.WHITE)  {
					queue.add(edge);					
					edge.color = Color.GREY;
					edge.parent = v;
					edge.depth = v.depth + 1;
				}
			}
			v.color = Color.BLACK;
		}
	} 
	
	public static void main(String[] args) {
		BreadthFirstSearchOnList graph = new BreadthFirstSearchOnList();
		graph.vertices = new Vertex[8];					
		
		graph.vertices[0] = new Vertex("r0");
		graph.vertices[1] = new Vertex("s1");
		graph.vertices[2] = new Vertex("t2");
		graph.vertices[3] = new Vertex("u3");
		graph.vertices[4] = new Vertex("v4");
		graph.vertices[5] = new Vertex("w5");
		graph.vertices[6] = new Vertex("x6");
		graph.vertices[7] = new Vertex("y7");

		graph.vertices[0].edges.addAll(Arrays.asList(graph.vertices[1], graph.vertices[4]));				
		graph.vertices[1].edges.addAll(Arrays.asList(graph.vertices[0], graph.vertices[5]));		
		graph.vertices[2].edges.addAll(Arrays.asList(graph.vertices[3], graph.vertices[5], graph.vertices[6]));		
		graph.vertices[3].edges.addAll(Arrays.asList(graph.vertices[2], graph.vertices[6], graph.vertices[7]));		
		graph.vertices[4].edges.addAll(Arrays.asList(graph.vertices[0]));		
		graph.vertices[5].edges.addAll(Arrays.asList(graph.vertices[1], graph.vertices[2], graph.vertices[6]));		
		graph.vertices[6].edges.addAll(Arrays.asList(graph.vertices[2], graph.vertices[3], graph.vertices[5], graph.vertices[7]));		
		graph.vertices[7].edges.addAll(Arrays.asList(graph.vertices[3], graph.vertices[6]));
		
		graph.traverse(graph.vertices[1]);
		
	}
}

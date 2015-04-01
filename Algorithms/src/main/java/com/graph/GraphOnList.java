package com.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GraphOnList {

	
	enum Color { WHITE, GREY, BLACK }
	
	enum EdgeType { TREE, BACK, FORWARD, CROSS }
	
	
	public static class Vertex {
		final String id;
		
		int depth;
		
		int start;
		int end;
		
		Vertex parent;
		Color color = Color.WHITE;
		List<Edge> edges = new ArrayList<>();
		
		public Vertex(String id) {
			this.id = id;
		}
		
		public void addEdge(Vertex dst) {
			edges.add(new Edge(this, dst));
		}
		
		public void addAll(Vertex... dst) {
			for (Vertex d : dst) {
				this.addEdge(d);
			}
		}
	} 
	
	
	public static class Edge {		
		final Vertex src;
		final Vertex dst;
		EdgeType type;		
		public Edge(Vertex src, Vertex dst) {
			this.src = src;
			this.dst = dst;
		}
	}
	
	
	Vertex[] vertices;	
	int time = 0;
	
	public void bfs(Vertex v) {
		v.color = Color.GREY;
		
		Queue<Vertex> queue = new LinkedList<>();				
		queue.add(v);
		
		while (!queue.isEmpty()) {
			v = queue.remove();
			System.out.print(v.id + " ");
			for (Edge edge : v.edges) {
				Vertex dst = edge.dst;
				if (dst.color == Color.WHITE)  {
					queue.add(dst);					
					dst.color = Color.GREY;
					dst.parent = v;
					dst.depth = v.depth + 1;
				}
			}
			v.color = Color.BLACK;
		}
	} 
	
	
	public void dfs() {
		for (Vertex v : this.vertices) {
			if (v.color == Color.WHITE) {
				dfsTraverse(v);
			}
		}
	}
	
	public void dfsTraverse(Vertex v) {
		v.color = Color.GREY;
		v.start = (++ time);		
		System.out.print("(" + v.id);
		
		for (Edge edge : v.edges) {
			if (edge.dst.color == Color.WHITE) {
				edge.type = EdgeType.TREE;
				edge.dst.parent = v;
				dfsTraverse(edge.dst);
			} else if (edge.dst.color == Color.GREY) {
				edge.type = EdgeType.BACK;
			} else if (edge.src.start < edge.dst.start) {
				edge.type = EdgeType.FORWARD;
			} else {
				edge.type = EdgeType.CROSS;
			}
		}
		
		v.color = Color.BLACK;
		v.end = (++ time);				
		System.out.print(v.id + ")");
	}
	
	public void printEdges() {
		for (Vertex v : this.vertices) {
			System.out.printf("%s: (%2d, %2d)%n", v.id, v.start, v.end);
			for (Edge e : v.edges) {
				System.out.printf("\t(%s, %s) - %s%n", e.src.id, e.dst.id, e.type);
			}
		}
	}
	
	public void printParents(Vertex v) {		
		do {
			System.out.print(v.id + " -> ");
		} while ((v = v.parent) != null);
	}
	
	public static void main(String[] args) {
		GraphOnList graph = new GraphOnList();
		graph.vertices = new Vertex[8];					
		
		graph.vertices[0] = new Vertex("r0");
		graph.vertices[1] = new Vertex("s1");
		graph.vertices[2] = new Vertex("t2");
		graph.vertices[3] = new Vertex("u3");
		graph.vertices[4] = new Vertex("v4");
		graph.vertices[5] = new Vertex("w5");
		graph.vertices[6] = new Vertex("x6");
		graph.vertices[7] = new Vertex("y7");

		graph.vertices[0].addAll(graph.vertices[1], graph.vertices[4]);				
		graph.vertices[1].addAll(graph.vertices[0], graph.vertices[5]);		
		graph.vertices[2].addAll(graph.vertices[3], graph.vertices[5], graph.vertices[6]);		
		graph.vertices[3].addAll(graph.vertices[2], graph.vertices[6], graph.vertices[7]);		
		graph.vertices[4].addAll(graph.vertices[0]/*, graph.vertices[7]*/);		
		graph.vertices[5].addAll(graph.vertices[1], graph.vertices[2], graph.vertices[6]);		
		graph.vertices[6].addAll(graph.vertices[2], graph.vertices[3], graph.vertices[5], graph.vertices[7]);		
		graph.vertices[7].addAll(graph.vertices[3], graph.vertices[6]);
		
		//graph.bfs(graph.vertices[1]);
		
		graph.dfs();
		
		System.out.println();
		
		graph.printEdges();		
		graph.printParents(graph.vertices[7]);		
	}
}

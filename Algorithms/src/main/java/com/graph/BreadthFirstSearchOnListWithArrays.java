package com.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * With 2 arrays: for vertices and edges			
 */
public class BreadthFirstSearchOnListWithArrays {

	Vertice[] vertices;
	List<List<Integer>> edges;
	
	
	enum Color {WHITE, GREY, BLACK}
	
	
	static class Vertice {
		final String id;
		
		int parent = -1; // -1 means no parent
		int depth = 0;
		Color color = Color.WHITE;
		
		public Vertice(String id) {
			this.id = id;
		}
		
		@Override
		public String toString() {		
			return this.id;
		}
	} 		
	
	
	public void traverse(int vInd) {						
		LinkedList<Integer> queue = new LinkedList<>();
		vertices[vInd].color = Color.GREY;
		queue.add(vInd);
		
		while (!queue.isEmpty()) {						
			int ind = queue.removeFirst();	
			
			System.out.print(vertices[ind] + " ");						
			
			List<Integer> links = edges.get(ind);
			for (Integer childInd : links) {
				if (vertices[childInd].color == Color.WHITE) {
					visit(childInd, ind);						
					queue.add(childInd);	
				}				
			}			
			vertices[ind].color = Color.BLACK;				
		}
	}
		
	void visit(int childInd, int parentInd) {
		vertices[childInd].parent = parentInd;
		vertices[childInd].depth = vertices[parentInd].depth + 1;
		vertices[childInd].color = Color.GREY;
	}
	
	public static void main(String[] args) {
		BreadthFirstSearchOnListWithArrays graph = new BreadthFirstSearchOnListWithArrays();
		graph.vertices = new Vertice[8];		
		graph.edges = new ArrayList<>(20);		
		
		graph.vertices[0] = new Vertice("r0");
		graph.vertices[1] = new Vertice("s1");
		graph.vertices[2] = new Vertice("t2");
		graph.vertices[3] = new Vertice("u3");
		graph.vertices[4] = new Vertice("v4");
		graph.vertices[5] = new Vertice("w5");
		graph.vertices[6] = new Vertice("x6");
		graph.vertices[7] = new Vertice("y7");

		graph.edges.add(0, Arrays.asList(1, 4));
		graph.edges.add(1, Arrays.asList(0, 5));
		graph.edges.add(2, Arrays.asList(3, 5, 6));		
		graph.edges.add(3, Arrays.asList(2, 6, 7));
		graph.edges.add(4, Arrays.asList(0));		
		graph.edges.add(5, Arrays.asList(1, 2, 6));
		graph.edges.add(6, Arrays.asList(2, 3, 5, 7));
		graph.edges.add(7, Arrays.asList(3, 6));
		
		graph.traverse(1);
		
	}

}

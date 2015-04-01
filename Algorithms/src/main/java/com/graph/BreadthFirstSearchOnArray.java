package com.graph;

import java.util.LinkedList;
import java.util.Queue;


public class BreadthFirstSearchOnArray {

	enum Color { WHITE, GREY, BLACK }
	
	public static class MyVertex {
		final String id;
		int depth;
		int parent = -1;
		Color color = Color.WHITE;
				
		public MyVertex(String id) {
			this.id = id;
		}
	} 
	
	MyVertex[] vertices;
	int[][] edges;
	
	public void traverse(int vInd) {
		Queue<Integer> queue = new LinkedList<>();
		vertices[vInd].color = Color.GREY;
		queue.add(vInd);
		
		while (!queue.isEmpty()) {
			vInd = queue.remove();
		
			System.out.print(vertices[vInd].id + " ");
			
			for (int i = 0; i < edges.length; i ++) {				
				if (edges[vInd][i] == 1 && vertices[i].color == Color.WHITE) {
					queue.add(i);	
					vertices[i].color = Color.GREY;
					vertices[i].depth = vertices[vInd].depth + 1;
					vertices[i].parent = vInd;
				}				
			}			
			
			vertices[vInd].color = Color.BLACK;
		}
	}
	
	public static void main(String[] args) {
		BreadthFirstSearchOnArray graph = new BreadthFirstSearchOnArray();
		graph.vertices = new MyVertex[8];		
		graph.edges = new int[8][8];	
		
		graph.vertices[0] = new MyVertex("r0");
		graph.vertices[1] = new MyVertex("s1");
		graph.vertices[2] = new MyVertex("t2");
		graph.vertices[3] = new MyVertex("u3");
		graph.vertices[4] = new MyVertex("v4");
		graph.vertices[5] = new MyVertex("w5");
		graph.vertices[6] = new MyVertex("x6");
		graph.vertices[7] = new MyVertex("y7");
		
		graph.edges[0][1] = 1; graph.edges[0][4] = 1;		
		graph.edges[1][0] = 1; graph.edges[1][5] = 1;		
		graph.edges[2][3] = 1; graph.edges[2][5] = 1; graph.edges[2][6] = 1;		
		graph.edges[3][2] = 1; graph.edges[3][6] = 1; graph.edges[3][7] = 1;		
		graph.edges[4][0] = 1;		
		graph.edges[5][1] = 1; graph.edges[5][2] = 1; graph.edges[5][6] = 1;		
		graph.edges[6][2] = 1; graph.edges[6][3] = 1; graph.edges[6][5] = 1; graph.edges[6][7] = 1;		
		graph.edges[7][3] = 1; graph.edges[7][6] = 1;						
		
		graph.traverse(1);
		
	}
}

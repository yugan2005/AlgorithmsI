package Week2B;

import java.util.Comparator;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Prob1Q2 {
	
	// this is the Dijkstra's algorithm 

	public static void main(String[] args) {
		String prob = "A->B    40    A->E    28    A->F    19    B->C    86    D->C    33    F->B    13    F->C   101    F->E     2    F->G    30    G->C    69    G->D    32    G->H     6    H->D    30";
		EdgeWeightedDigraph G = new EdgeWeightedDigraph(8);
		String[] temp = prob.trim().split("\\s+");
		for (int i = 0; i < temp.length; i += 2) {
			
			String[] vertexes = temp[i].split("->");
			int v = vertexes[0].charAt(0)-'A';
			int w = vertexes[1].charAt(0)-'A';
			int weight = Integer.parseInt(temp[i+1]);
			G.addEdge(new DirectedEdge(v, w, weight));
		}
		
		int sizeOfMST = 0;
		while (!minPQ.isEmpty() && sizeOfMST<=9){
			String minEdgeStr = minPQ.delMin();
			String[] vertexStr = minEdgeStr.split(",")[0].split("-");
			int v = vertexStr[0].charAt(0)-'A';
			int w = vertexStr[1].charAt(0)-'A';
			if (!uf.connected(v, w)){
				uf.union(v, w);
				System.out.println(minEdgeStr);
				sizeOfMST++;
			}
			
		}

	}

}

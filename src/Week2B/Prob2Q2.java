package Week2B;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.IndexMinPQ;

public class Prob2Q2 {

	// this is the Dijkstra's algorithm

	public static void main(String[] args) {
		String prob = "A->B    40    A->E    28    A->F    19    B->C    86    D->C    33    F->B    13    F->C   101    F->E     2    F->G    30    G->C    69    G->D    32    G->H     6    H->D    30";
		EdgeWeightedDigraph G = new EdgeWeightedDigraph(8);
		String[] temp = prob.trim().split("\\s+");
		for (int i = 0; i < temp.length; i += 2) {

			String[] vertexes = temp[i].split("->");
			int v = vertexes[0].charAt(0) - 'A';
			int w = vertexes[1].charAt(0) - 'A';
			int weight = Integer.parseInt(temp[i + 1]);
			G.addEdge(new DirectedEdge(v, w, weight));
		}

		// for (DirectedEdge e : G.edges()) {
		// System.out.println(e);
		// }

		int v = 0;
		int addedVertex = 1;

		IndexMinPQ<Double> pq = new IndexMinPQ<>(G.V());

		double[] distTo = new double[G.V()];
		for (int i = 0; i < G.V(); i++) {
			if (i == v)
				distTo[i] = 0;
			else
				distTo[i] = Double.POSITIVE_INFINITY;
		}

		int[] edgeTo = new int[G.V()];
		boolean[] visited = new boolean[G.V()];
		visited[v] = true;

		for (DirectedEdge e : G.adj(v)) {
			edgeTo[e.to()] = v;
			distTo[e.to()] = e.weight();
			pq.insert(e.to(), distTo[e.to()]);
		}

		while (!pq.isEmpty() && addedVertex < G.V()) {
			v = pq.delMin();
			visited[v] = true;
			addedVertex += 1;
			for (DirectedEdge e : G.adj(v)) {
				int w = e.to();
				if (!visited[w]) {
					if (distTo[w] > distTo[v] + e.weight()) {
						distTo[w] = distTo[v] + e.weight();
						edgeTo[w] = v;
						if (!pq.contains(w)) {
							pq.insert(w, distTo[w]);
						} else
							pq.decreaseKey(w, distTo[w]);
					}

				}
			}
			
			if (v == 6) {
				System.out.println("After G:");
				for (int i = 0; i < G.V(); i++) {
					System.out.println(distTo[i]);
				}
				System.out.println();
			}

		}

		for (int i = 0; i < G.V(); i++)
			System.out.println(distTo[i]);

	}

}

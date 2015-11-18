package Week2B;

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;

public class Prob3Quiz2 {

	// this is the Bellman-Ford algorithm

	public static void main(String[] args) {
		String prob = "B->A    38    C->D    16    C->B    61    C->G    21    E->A    39    F->B     5    F->C     1    F->E     7    F->A    51    G->F    33    H->G    37    H->C    18    H->D    40";
		EdgeWeightedDigraph G = new EdgeWeightedDigraph(8);
		List<DirectedEdge> edgeList = new ArrayList<>(13);
		String[] temp = prob.trim().split("\\s+");
		for (int i = 0; i < temp.length; i += 2) {

			String[] vertexes = temp[i].split("->");
			int v = vertexes[0].charAt(0) - 'A';
			int w = vertexes[1].charAt(0) - 'A';
			int weight = Integer.parseInt(temp[i + 1]);
			DirectedEdge e = new DirectedEdge(v, w, weight);
			G.addEdge(e);
			edgeList.add(e);
		}

		// for (DirectedEdge e : G.edges()) {
		// System.out.println(e);
		// }

		double[] distTo = new double[G.V()];

		for (int i = 0; i < G.V(); i++) {
			distTo[i] = Double.POSITIVE_INFINITY;
		}

		distTo['H' - 'A'] = 0;

		DirectedEdge[] edgeTo = new DirectedEdge[G.V()];

		for (int i = 0; i < edgeList.size(); i++) {
			for (DirectedEdge e : edgeList) {
				int v = e.from();
				int w = e.to();
				if (distTo[w] > distTo[v] + e.weight()) {
					distTo[w] = distTo[v] + e.weight();
					edgeTo[w] = e;
				}
			}
			if (i == 2) {
				System.out.println("After 3 passes");
				for (int k = 0; k < G.V(); k++) {
					System.out.println(distTo[k]);
				}

			}
		}

		System.out.println("After all passes");
		for (int k = 0; k < G.V(); k++) {
			System.out.println(distTo[k]);
		}

	}

}

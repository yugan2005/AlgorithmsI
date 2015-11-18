package Week2B;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Stack;

public class Prob2Quiz2 {

	// this is the Toplocial-sort algorithm

	public static void main(String[] args) {
		String prob = "B->A    15    B->C    12    C->D    35    C->G    14    D->G     4    D->H     0    E->A    62    E->B    46    E->F    14    F->B    29    F->C    48    F->G    59    G->H    14";
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

		Stack<Integer> reverseOrder = new Stack<>();

		boolean[] visited = new boolean[G.V()];
		for (int i = 0; i < G.V(); i++) {
			if (!visited[i]) {
				dfs(G, i, visited, reverseOrder);
			}
		}

		for (int i : reverseOrder) {
			System.out.println(Character.toString(((char) ('A' + i))));
		}

		double[] distTo = new double[G.V()];

		for (int i = 0; i < G.V(); i++) {
			distTo[i] = Double.POSITIVE_INFINITY;
		}

		distTo[reverseOrder.peek()] = 0;

		DirectedEdge[] edgeTo = new DirectedEdge[G.V()];

		for (int v : reverseOrder) {
			for (DirectedEdge e : G.adj(v)) {
				int w = e.to();
				if (distTo[w] > distTo[v] + e.weight()) {
					distTo[w] = distTo[v] + e.weight();
					edgeTo[w] = e;

				}
			}
			if (v == 'D' - 'A') {
				System.out.println("After relax vertex D");
				for (int i = 0; i < G.V(); i++) {
					System.out.println(distTo[i]);
				}
			}
		}

	}

	private static void dfs(EdgeWeightedDigraph g, int i, boolean[] visited, Stack<Integer> reverseOrder) {
		visited[i] = true;
		for (DirectedEdge e : g.adj(i)) {
			if (!visited[e.to()])
				dfs(g, e.to(), visited, reverseOrder);
		}
		reverseOrder.push(i);
	}

}

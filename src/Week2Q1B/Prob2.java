package Week2Q1B;

import java.util.Comparator;

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.MinPQ;

public class Prob2 {

	// this is the Prim's algorithm Lazy evaluation

	private class myComparator implements Comparator<Edge> {

		@Override
		public int compare(Edge edge0, Edge edge1) {
			Integer weight0 = (int) edge0.weight();
			Integer weight1 = (int) edge1.weight();

			return weight0.compareTo(weight1);
		}

	}

	public static void main(String[] args) {
		EdgeWeightedGraph G = new EdgeWeightedGraph(10);
		MinPQ<Edge> minPQ = new MinPQ<>(17, new Prob2().new myComparator());
		String prob = "B-A      12    F-A       1    B-G      15    C-B      13    B-F       4    H-B       2    D-C      14    C-H       5    H-D      16    E-D       7    D-I       3    E-I      17    E-J      10    G-F       8    G-H      11    H-I       6    I-J       9";
		String[] temp = prob.trim().split("\\s+");
		for (int i = 0; i < temp.length; i += 2) {
			int v = temp[i].split("-")[0].charAt(0) - 'A';
			int w = temp[i].split("-")[1].charAt(0) - 'A';
			Edge edge = new Edge(v, w, Integer.parseInt(temp[i + 1]));
			G.addEdge(edge);
		}

		int sizeOfMST = 0;
		boolean[] inMST = new boolean[G.V()];
		int v = 1;
		inMST[v] = true;
		while (sizeOfMST < 9) {
			for (Edge edge : G.adj(v)) {
				if (!inMST[edge.other(v)]) {
					minPQ.insert(edge);
				}
			}
			Edge addEdge = minPQ.delMin();
			while (inMST[addEdge.either()] && inMST[addEdge.other(addEdge.either())]) {
				addEdge = minPQ.delMin();
			}
			System.out.println(addEdge);
			sizeOfMST += 1;
			if (!inMST[addEdge.either()]) {
				inMST[addEdge.either()] = true;
				v = addEdge.either();
			} else {
				inMST[addEdge.other(addEdge.either())] = true;
				v = addEdge.other(addEdge.either());
			}
		}
	}

}

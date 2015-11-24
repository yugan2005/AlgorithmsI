package IIWeek3Quiz1;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

public class Prob2 {

	public static void main(String[] args) {
		String input = "A->B     10  /  10    A->F     13  /  13    A->G     10  /  33    G->B      0  /   5    B->C     10  /  10    I->C      0  /  11    H->C      4  /  12    G->C     16  /  32    C->D     30  /  33    I->D      0  /   6    D->J      7  /   7    D->E     23  /  23    E->J     23  /  25    F->G     13  /  14    G->H      7  /   7    H->I      3  /   3    I->J      3  /   7";
//		String input = "A->B     0  /  18    A->F     0 /  13    A->G      0  /  17    B->C     0 /  18    C->D     0  /  18    C->G      0  /  12    D->E      0 /   4    D->H      0  /  14    D->J     0 /  15    E->J      0  /   5    F->G     0 /  14    G->B      0  /   6    G->H     0 /  16    H->C      0  /  14    H->I     0 /  18    I->D      0  /  10    I->J     0  /  17";

		String[] tokens = input.trim().split("\\s+");

		int numVert = 10;

		FlowNetwork flowNet = new FlowNetwork(numVert);

		for (int i = 0; i < tokens.length; i += 4) {
			int v = tokens[i].charAt(0) - 'A';
			int w = tokens[i].charAt(3) - 'A';
			int flow = Integer.parseInt(tokens[i + 1]);
			int capacity = Integer.parseInt(tokens[i + 3]);
			flowNet.addEdge(new FlowEdge(v, w, capacity, flow));
		}

		while (true) {
			// using bread first search

			boolean[] visited = new boolean[flowNet.V()];
			FlowEdge[] pathTo = new FlowEdge[flowNet.V()];

			Queue<Integer> queue = new Queue<>();
			queue.enqueue(0);
			visited[0] = true;

			while (!queue.isEmpty()) {
				int v = queue.dequeue();
				for (FlowEdge e : flowNet.adj(v)) {
					int w = e.other(v);
					if (!visited[w]) {
						if (e.residualCapacityTo(w) > 0) {
							pathTo[w] = e;
							visited[w] = true;
							queue.enqueue(w);
						}
					}

					if (pathTo['J' - 'A'] != null)
						break;
				}
			}

			if (pathTo['J' - 'A'] != null) {
				Stack<FlowEdge> path = new Stack<>();
				int vertex = 'J' - 'A';
				while (pathTo[vertex] != null) {
					path.push(pathTo[vertex]);
					vertex = pathTo[vertex].other(vertex);
				}

				String output = "";
				FlowEdge start = path.peek();
				int oneEnd = start.from();
				output = output + String.format("%c ", 'A' + oneEnd);
				double bottleNeck = Double.POSITIVE_INFINITY;
				for (FlowEdge v : path) {
					oneEnd = v.other(oneEnd);
					if (v.residualCapacityTo(oneEnd) < bottleNeck) {
						bottleNeck = v.residualCapacityTo(oneEnd);
					}
					output = output + String.format("%c ", 'A' + oneEnd);
				}
				System.out.println(output);
				System.out.println(bottleNeck);

				oneEnd = start.from();
				for (FlowEdge v : path) {
					oneEnd = v.other(oneEnd);
					v.addResidualFlowTo(oneEnd, bottleNeck);
				}
			} else {
				System.out.println("MinCut is as follow");
				String result = "";
				for (int i=0; i<flowNet.V(); i++){
					if (visited[i]) {
						result = result + String.format("%c ", 'A' + i);
					}
				}
				System.out.println(result);
				break;
			}
				

		}

	}

}

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

public class SAP {
	private Digraph G;
	private int commonAncestor;

	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G) {
		if (G == null)
			throw new NullPointerException("Input digraph is Null");
		this.G = G;
	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {
		commonAncestor = -1;
		BreadthFirstDirectedPaths ancestorOfV = new BreadthFirstDirectedPaths(G, v);
		BreadthFirstDirectedPaths ancestorOfW = new BreadthFirstDirectedPaths(G, w);
		int shortestLen = G.V() + 1;
		for (int i = 0; i < G.V(); i++) {
			if (ancestorOfV.hasPathTo(i) && ancestorOfW.hasPathTo(i)) {
				int lenFromVToI = ancestorOfV.distTo(i);
				int lenFromWToI = ancestorOfV.distTo(i);
				if ((lenFromVToI + lenFromWToI) < shortestLen) {
					shortestLen = lenFromVToI + lenFromWToI;
					commonAncestor = i;
				}
			}
		}
		if (shortestLen == G.V() + 1)
			shortestLen = -1;
		return shortestLen;
	}

	// a common ancestor of v and w that participates in a shortest ancestral
	// path; -1 if no such path
	public int ancestor(int v, int w) {
		length(v, w);
		return commonAncestor;
	}

	// length of shortest ancestral path between any vertex in v and any vertex
	// in w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		int shortestLen = G.V() + 1;
		for (int i : v) {
			for (int j : w) {
				int currentLen = length(i, j);
				if (currentLen > -1 && currentLen < shortestLen) {
					shortestLen = currentLen;
					commonAncestor = 
				}
			}
		}

		if (shortestLen == G.V() + 1)
			shortestLen = -1;
		return shortestLen;
	}

	// a common ancestor that participates in shortest ancestral path; -1 if no
	// such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		int shortestLen = G.V() + 1;
		int commonAncestor = G.V() + 1;

		for (int i : v) {
			for (int j : w) {
				int currentLen = length(i, j);
				if (currentLen > -1 && currentLen < shortestLen) {
					shortestLen = currentLen;
				}
			}
		}

	}

	// do unit testing of this class
	public static void main(String[] args) {

	}
}

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
	private final Digraph G;
	private int cachedV, cachedW, cachedCommonAncestor, cachedShortestLength;
	private Iterable<Integer> cachedIterableV, cachedIterableW;
	private int cachedIterableShortestLength, cachedIterableCommonAncestor;

	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G) {
		if (G == null)
			throw new NullPointerException("Input digraph is Null");
		this.G = new Digraph(G);
	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {
		if ((v == cachedV && w == cachedW) || (v == cachedW && w == cachedV))
			return cachedShortestLength;

		if (v < 0 || w < 0 || v > G.V() - 1 || w > G.V() - 1)
			throw new IndexOutOfBoundsException();

		cachedV = v;
		cachedW = w;
		int commonAncestor = -1;
		int shortestLen = G.V() + 1;

		BreadthFirstDirectedPaths ancestorOfV = new BreadthFirstDirectedPaths(G, v);
		BreadthFirstDirectedPaths ancestorOfW = new BreadthFirstDirectedPaths(G, w);

		for (int i = 0; i < G.V(); i++) {
			if (ancestorOfV.hasPathTo(i) && ancestorOfW.hasPathTo(i)) {
				int lenFromVToI = ancestorOfV.distTo(i);
				int lenFromWToI = ancestorOfW.distTo(i);
				if ((lenFromVToI + lenFromWToI) < shortestLen) {
					shortestLen = lenFromVToI + lenFromWToI;
					commonAncestor = i;
				}
			}
		}

		if (shortestLen == G.V() + 1)
			shortestLen = -1;
		cachedShortestLength = shortestLen;
		cachedCommonAncestor = commonAncestor;
		return shortestLen;
	}

	// a common ancestor of v and w that participates in a shortest ancestral
	// path; -1 if no such path
	public int ancestor(int v, int w) {
		if ((v == cachedV && w == cachedW) || (v == cachedW && w == cachedV))
			return cachedCommonAncestor;
		length(v, w);
		return cachedCommonAncestor;
	}

	// length of shortest ancestral path between any vertex in v and any vertex
	// in w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		if (v == null || w == null)
			throw new NullPointerException();

		for (int intInIterable : v) {
			if (intInIterable < 0 || intInIterable > G.V() - 1)
				throw new IndexOutOfBoundsException();
		}

		for (int intInIterable : w) {
			if (intInIterable < 0 || intInIterable > G.V() - 1)
				throw new IndexOutOfBoundsException();
		}

		if ((iterableIntegerEqual(v, cachedIterableV) && iterableIntegerEqual(w, cachedIterableW))
				|| (iterableIntegerEqual(w, cachedIterableV) && iterableIntegerEqual(v, cachedIterableW)))
			return cachedIterableShortestLength;

		cachedIterableV = v;
		cachedIterableW = w;

		int cloestCommonAncestor = -1;
		int shortestLen = G.V() + 1;
		for (int i : v) {
			for (int j : w) {
				int currentLen = length(i, j);
				if (currentLen > -1 && currentLen < shortestLen) {
					shortestLen = currentLen;
					cloestCommonAncestor = cachedCommonAncestor;
				}
			}
		}

		if (shortestLen == G.V() + 1)
			shortestLen = -1;

		cachedIterableShortestLength = shortestLen;
		cachedIterableCommonAncestor = cloestCommonAncestor;
		return shortestLen;
	}

	// a common ancestor that participates in shortest ancestral path; -1 if no
	// such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		if ((iterableIntegerEqual(v, cachedIterableV) && iterableIntegerEqual(w, cachedIterableW))
				|| (iterableIntegerEqual(w, cachedIterableV) && iterableIntegerEqual(v, cachedIterableW)))
			return cachedIterableCommonAncestor;
		length(v, w);
		return cachedIterableCommonAncestor;
	}

	private boolean iterableIntegerEqual(Iterable<Integer> c1, Iterable<Integer> c2) {
		return iterableContainIterable(c1, c2) && iterableContainIterable(c2, c1);
	}

	private boolean iterableContainIterable(Iterable<Integer> c1, Iterable<Integer> c2) {
		if (c1 == null || c2 == null)
			return false;
		
		for (int i : c1) {
			boolean contain = false;
			for (int j : c2) {
				if (i == j) {
					contain = true;
					break;
				}
			}
			if (!contain)
				return false;
		}
		return true;
	}

	// do unit testing of this class
	public static void main(String[] args) {

		In in = new In(args[0]);
		Digraph G = new Digraph(in);
		SAP sap = new SAP(G);
		while (!StdIn.isEmpty()) {
			int v = StdIn.readInt();
			int w = StdIn.readInt();
			int length = sap.length(v, w);
			int ancestor = sap.ancestor(v, w);
			StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
		}

	}
}

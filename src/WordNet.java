import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

public class WordNet {
	private Digraph G;
	private SAP S;
	private Map<String, List<Integer>> dictionary;
	private Map<Integer, String> synsetDictionary;

	// constructor takes the name of the two input files
	public WordNet(String synsets, String hypernyms) {
		if (synsets == null || hypernyms == null)
			throw new NullPointerException();
		dictionary = new HashMap<>();
		synsetDictionary = new HashMap<>();
		try {
			In nounInput = new In(synsets);
			while (nounInput.hasNextLine()) {
				String line = nounInput.readLine();
				String[] fields = line.split(",");
				int synsetId = Integer.parseInt(fields[0]);
				synsetDictionary.put(synsetId, fields[1]);
				String[] nouns = fields[1].split(" ");
				for (String noun : nouns) {
					if (dictionary.get(noun)==null){
						List<Integer> wordIds = new ArrayList<>();
						wordIds.add(synsetId);
						dictionary.put(noun, wordIds);
					}
					dictionary.get(noun).add(synsetId);
				}
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(synsets + " file cannot read properly");
		}

		G = new Digraph(synsetDictionary.size());

		try {
			In graphIn = new In(hypernyms);
			while (graphIn.hasNextLine()) {
				String line = graphIn.readLine();
				String[] synsetIdsInStr = line.split(",");
				int v = Integer.parseInt(synsetIdsInStr[0]);
				for (int i = 1; i < synsetIdsInStr.length; i++) {
					G.addEdge(v, Integer.parseInt(synsetIdsInStr[i]));
				}
			}

		} catch (Exception e) {
			throw new IllegalArgumentException(hypernyms + " file cannot read properly");
		}

		DirectedCycle cycle = new DirectedCycle(G);
		if (cycle.hasCycle())
			throw new IllegalArgumentException("It has Directed Cycle");

		int rootCounter = 0;
		for (int i = 0; i < G.V(); i++) {
			if (G.outdegree(i) == 0) {
				rootCounter++;
				if (rootCounter > 1)
					throw new IllegalArgumentException("It has more than one root");
			}
		}
		S = new SAP(G);
	}

	// returns all WordNet nouns
	public Iterable<String> nouns() {
		return dictionary.keySet();
	}

	// is the word a WordNet noun?
	public boolean isNoun(String word) {
		if (word == null)
			throw new NullPointerException();

		return dictionary.containsKey(word);

	}

	// distance between nounA and nounB (defined below)
	public int distance(String nounA, String nounB) {
		if (nounA == null || nounB == null)
			throw new NullPointerException();
		if (dictionary.get(nounA) == null || dictionary.get(nounB) == null)
			throw new IllegalArgumentException();

		List<Integer> idA = dictionary.get(nounA);
		List<Integer> idB = dictionary.get(nounB);

		return S.length(idA, idB);
	}

	// a synset (second field of synsets.txt) that is the common ancestor of
	// nounA and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB) {
		if (nounA == null || nounB == null)
			throw new NullPointerException();
		if (dictionary.get(nounA) == null || dictionary.get(nounB) == null)
			throw new IllegalArgumentException();

		List<Integer> idA = dictionary.get(nounA);
		List<Integer> idB = dictionary.get(nounB);
		
		int commonAncestor = S.ancestor(idA, idB);
		return synsetDictionary.get(commonAncestor);
	}

	// do unit testing of this class
	public static void main(String[] args) {

	}

}

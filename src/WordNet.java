import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class WordNet {
	private Digraph wordNet;
	private Map<Integer, Set<String>> nouns;

	// constructor takes the name of the two input files
	public WordNet(String synsets, String hypernyms) {
		if (synsets == null || hypernyms == null)
			throw new NullPointerException();
		nouns = new HashMap<>();
		try {
			In nounInput = new In(synsets);
			while (nounInput.hasNextLine()) {
				String line = nounInput.readLine();
				String[] fields = line.split(",");
				int synsetId = Integer.parseInt(fields[0]);
				Set<String> synonyms = new HashSet<>(Arrays.asList(fields[1].split(" ")));
				nouns.put(synsetId, synonyms);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(synsets + " file cannot read properly");
		}

		wordNet = new Digraph(nouns.size());

		try {
			In graphIn = new In(hypernyms);
			while (graphIn.hasNextLine()) {
				String line = graphIn.readLine();
				String[] synsetIdInStr = line.split(",");
				int v = Integer.parseInt(synsetIdInStr[0]);
				for (int i = 1; i < synsetIdInStr.length; i++) {
					wordNet.addEdge(v, Integer.parseInt(synsetIdInStr[i]));
				}
			}

		} catch (Exception e) {
			throw new IllegalArgumentException(hypernyms + " file cannot read properly");
		}

	}

	// returns all WordNet nouns
	public Iterable<String> nouns() {
		Set<String> allNouns = new HashSet<>();
		for (int synsetId:nouns.keySet()){
			allNouns.addAll(nouns.get(synsetId));
		}
		return allNouns;
	}

	// is the word a WordNet noun?
	public boolean isNoun(String word) {
		return false;

	}

	// distance between nounA and nounB (defined below)
	public int distance(String nounA, String nounB) {
		return 0;

	}

	// a synset (second field of synsets.txt) that is the common ancestor of
	// nounA and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB) {
		return nounB;

	}

	// do unit testing of this class
	public static void main(String[] args) {

	}

}

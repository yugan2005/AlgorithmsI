import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
	private WordNet wordNet;

	public Outcast(WordNet wordnet) {
		// constructor takes a WordNet object
		this.wordNet = wordnet;
	}

	public String outcast(String[] nouns) {
		// given an array of WordNet nouns, return an outcast
		String outcastNoun = nouns[0];
		int distance = 0;
		for (String noun : nouns) {
			int currentDist = 0;
			for (int i = 0; i < nouns.length; i++) {
				if (!noun.equals(nouns[i])) {
					currentDist += wordNet.distance(noun, nouns[i]);
				}
			}
			if (currentDist > distance) {
				distance = currentDist;
				outcastNoun = noun;
			}
		}
		return outcastNoun;
	}

	public static void main(String[] args) {
		// see test client below
		WordNet wordnet = new WordNet(args[0], args[1]);
		Outcast outcast = new Outcast(wordnet);
		for (int t = 2; t < args.length; t++) {
			In in = new In(args[t]);
			String[] nouns = in.readAllStrings();
			StdOut.println(args[t] + ": " + outcast.outcast(nouns));
		}
	}
}

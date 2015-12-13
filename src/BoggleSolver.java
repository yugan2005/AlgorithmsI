import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {
	private static final int R = 26;
	private static final char startChar = 'A';
	private Tries myDict;
	private int row, col;

	private class Node {
		private boolean wordInDict;
		private Node[] next = new Node[R];

	}

	private class Tries {
		private Node root = new Node();

		private void put(String input) {
			root = put(root, input, 0);
		}

		private Node put(Node node, String input, int i) {

			int idx = input.charAt(i) - startChar;

			if (node.next[idx] == null) {
				node.next[idx] = new Node();
			}
			if (i == input.length() - 1) {
				node.next[idx].wordInDict = true;
			} else {
				node.next[idx] = put(node.next[idx], input, i + 1);
			}

			return node;
		}

		private boolean isInsideDict(String input) {
			return isInsideDict(root, input, 0);
		}

		private boolean isInsideDict(Node node, String input, int i) {

			int idx = input.charAt(i) - startChar;

			if (node.next[idx] == null)
				return false;
			if (i == input.length() - 1)
				return node.next[idx].wordInDict;

			return isInsideDict(node.next[idx], input, i + 1);
		}

	}

	// Initializes the data structure using the given array of strings as the
	// dictionary.
	// (You can assume each word in the dictionary contains only the uppercase
	// letters A through Z.)
	public BoggleSolver(String[] dictionary) {
		myDict = new Tries();
		for (String word : dictionary) {
			myDict.put(word);
		}
	}

	// Returns the set of all valid words in the given Boggle board, as an
	// Iterable.
	public Iterable<String> getAllValidWords(BoggleBoard board) {
		row = board.rows();
		col = board.cols();
		Set<String> result = new HashSet<>();
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				Integer[] cell = new Integer[] { i, j };
				boolean[][] visited = new boolean[row][col];
				dfs(cell, myDict.root, board, result, visited, "");
			}
		}
		return result;

	}

	private void dfs(Integer[] cell, Node node, BoggleBoard board, Set<String> result, boolean[][] visited,
			String runningStr) {
		if (visited[cell[0]][cell[1]])
			return;
		visited[cell[0]][cell[1]] = true;
		int runningStrLen = runningStr.length();
		char currentChar = board.getLetter(cell[0], cell[1]);
		String currentStr = "" + currentChar;
		int idx = currentChar - startChar;
		Node nextNode = node.next[idx];

		if (nextNode == null) {
			visited[cell[0]][cell[1]] = false;
			return;
		}

		if (currentChar == 'Q') {
			currentChar = 'U';
			runningStrLen++;
			currentStr += 'U';
			idx = currentChar - startChar;
			nextNode = nextNode.next[idx];
			
			if (nextNode == null) {
				visited[cell[0]][cell[1]] = false;
				return;
			}
		}

		if (nextNode.wordInDict) {

			if (runningStrLen >= 2)
				result.add(runningStr + currentStr);
		}

		for (Integer[] nextcell : adj(cell)) {
			dfs(nextcell, nextNode, board, result, visited, runningStr + currentStr);

		}
		visited[cell[0]][cell[1]] = false;

	}

	private Iterable<Integer[]> adj(Integer[] cell) {
		int i = cell[0];
		int j = cell[1];
		List<Integer[]> result = new ArrayList<>();
		if (i != 0) {
			if (j != 0) {
				result.add(new Integer[] { i - 1, j - 1 });
			}
			if (j != col - 1) {
				result.add(new Integer[] { i - 1, j + 1 });
			}
			result.add(new Integer[] { i - 1, j });
		}
		if (i != row - 1) {
			if (j != 0) {
				result.add(new Integer[] { i + 1, j - 1 });
			}
			if (j != col - 1) {
				result.add(new Integer[] { i + 1, j + 1 });
			}
			result.add(new Integer[] { i + 1, j });
		}

		if (j != 0) {
			result.add(new Integer[] { i, j - 1 });
		}

		if (j != col - 1) {
			result.add(new Integer[] { i, j + 1 });
		}
		return result;
	}

	// Returns the score of the given word if it is in the dictionary, zero
	// otherwise.
	// (You can assume the word contains only the uppercase letters A through
	// Z.)
	public int scoreOf(String word) {
		if (!myDict.isInsideDict(word))
			return 0;

		int l = word.length();

		if (l <= 2)
			return 0;
		if (l <= 4)
			return 1;
		if (l == 5)
			return 2;
		if (l == 6)
			return 3;
		if (l == 7)
			return 5;
		return 11;
	}

	public static void main(String[] args) {
		In in = new In(args[0]);
		String[] dictionary = in.readAllStrings();
		BoggleSolver solver = new BoggleSolver(dictionary);
//		for (int i = 0; i < 1000; i++) {
//			BoggleBoard board = new BoggleBoard(10, 10);
//			solver.getAllValidWords(board);
//			System.out.println("done: " + i);
//		}
		 BoggleBoard board = new BoggleBoard(args[1]);
		 int score = 0;
		 int counter = 0;
		 for (String word : solver.getAllValidWords(board)) {
		 StdOut.println(word);
		 score += solver.scoreOf(word);
		 counter ++;
		 }
		 StdOut.println("Score = " + score);
		 System.out.println(counter);
	}

}

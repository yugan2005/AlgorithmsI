package Week5BQuiz1;

import java.util.ArrayList;
import java.util.List;

public class Prob1 {
	private static final int R = 3;
	private static final char startChar = '1';

	private static class Node {
		private boolean mark;
		private Node[] next = new Node[R];
		
		public boolean isMarked() {
			return mark;
		}
		public void setMark(boolean mark) {
			this.mark = mark;
		}
	}

	private static class Trie {
		private int nodeCounter = 1;
		
		private Node root = new Node();

		public void put(String in) {
			root = put(in, 0, root);
		}

		private Node put(String in, int i, Node node) {
			int currentChar = in.charAt(i) - startChar;
			if (node.next[currentChar]==null) {
				node.next[currentChar] = new Node();
				nodeCounter++;
			}
			if (i != in.length() - 1) {
				node.next[currentChar] = put(in, i + 1, node.next[currentChar]);
			}
			else {
				node.next[currentChar].setMark(true);
			}
			return node;
		}

		public int getNodeCounter() {
			return nodeCounter;
		}
		
		public Iterable<String> getStrings(){
			List<String> result = new ArrayList<>();
			String currentString = "";
			getString(root, result, currentString);
			return result;
		}

		private void getString(Node node, List<String> result, String previousString) {
			for (int i=0; i<R; i++){
				if (node.next[i]!=null){
					String currentString = previousString+ ((char) (startChar+i));
					if (node.next[i].isMarked()){
						result.add(currentString);
					}
					getString(node.next[i], result, currentString);
				}
			}
			
		}

	}

	public static void main(String[] args) {
		
		String input = "22 1333 33 132 32 213 1122";
		String[] inputS = input.trim().split("\\s+");
		
		
		Trie myTrie = new Trie();
		
		for (int i=0; i<inputS.length; i++){
			myTrie.put(inputS[i]);
		}
		
		System.out.println(myTrie.getNodeCounter());
		
		Iterable<String> insideString = myTrie.getStrings();
		
		for (String s : insideString){
			System.out.println(s);
		}
		

	}

}

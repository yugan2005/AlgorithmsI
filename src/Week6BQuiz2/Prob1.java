package Week6BQuiz2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Prob1 {
	// This is the Huffman Trie
	Node root;
	private final int R=256;
	Map<Character, String> ST;
	
	
	private class Node implements Comparable<Node>{
		char c;
		int counter;
		boolean isLeaf = false;
		Node left, right;
		
		Node(char c, int freq){
			this.c=c;
			this.counter=freq;
		}
		
		@Override
		public int compareTo(Node that) {
			return this.counter-that.counter;
		}
	}
	
	private void readInput(String input){
		int[] freq = new int[R];
		for (int i=0; i<input.length(); i++){
			freq[input.charAt(i)]++;
		}
		ArrayList<Node> nodes = new ArrayList<>();
		for (int i=0; i<R; i++){
			if (freq[i]>0){
				Node node = new Node(((char) i), freq[i]);
				node.isLeaf=true;
				nodes.add(node);
			}
		}
		
		PriorityQueue<Node> nodePQ = new PriorityQueue<>(nodes.size());
		for (Node node:nodes){
			nodePQ.add(node);
		}
		
		while (nodePQ.size()>1){
			Node node1 = nodePQ.poll();
			Node node2 = nodePQ.poll();
			Node newNode = new Node(((char) 0), node1.counter+node2.counter);
			newNode.left=node1;
			newNode.right=node2;
			nodePQ.add(newNode);
		}
		
		root = nodePQ.poll();
		buildST();
	}
	
	private void buildST(){
		ST = new HashMap<>();
		String encoding= "";
		build(encoding, root, ST);
	}

	private void build(String encoding, Node node, Map<Character, String> ST) {
		if (node.isLeaf) {
			ST.put(node.c, encoding);
			return;
		}
		
		if (node.left!=null){
			build(encoding+"0", node.left, ST);
		}
		
		if (node.right!=null){
			build(encoding+"1", node.right, ST);
		}
	}
	
	private String compress(String input){
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<input.length(); i++){
			sb.append(ST.get(input.charAt(i)));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		String input = "ULDUDLUXUUTRRRRRDDDUXUURXARUDULUURDUUARXUAXLUD";
		Prob1 test = new Prob1();
		test.readInput(input);
		String encoded = test.compress(input);
		System.out.println(encoded);
		System.out.println(encoded.length());
		System.out.println("The ST is:");
		for (Map.Entry<Character, String> entry: test.ST.entrySet()){
			System.out.println(String.format("%s\t%s", entry.getKey(), entry.getValue()));
		}
		

	}

}

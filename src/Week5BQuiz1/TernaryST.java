package Week5BQuiz1;

import java.util.ArrayList;
import java.util.List;

public class TernaryST {
	private Node root;
	
	
	private static class Node{
		private boolean marked;
		private int depth;
		private char c;
		private Node left, right, next;
		
	}
	
	public void put(String s){
		root = put(s, root, 0, 0);
	}
	
	private Node put(String s, Node node, int i, int depth){
		if ( node!= null && node.c!=s.charAt(i)){
			if (node.c<s.charAt(i)) {
				node.right=put(s, node.right, i, depth+1);
			}
			else {
				node.left = put(s, node.left,i, depth+1);
			}
		}
		else {
			if (node == null){
				node = new Node();
				node.depth=depth;
				node.c = s.charAt(i);
			}
			
			if (i==s.length()-1){
				node.marked = true;
			}
			else {
				node.next = put(s,node.next, i+1, depth+1);
			}
		}
		
		return node;
	}
	
	public Iterable<String> getAllStrings(){
		List<String> result = new ArrayList<>();
		String currentString = "";
		getString(root, result, currentString);
		return result;
	}
	
	public int getDepth(String input){
		int result = getDepth(input, root, 0);
		return result;
	}
	
	

	private int getDepth(String input, Node node, int i) {
		if (node.c==input.charAt(i)){
			if (i<input.length()-1 && node.next!=null){
				return getDepth(input, node.next, i+1);
			}
			else if (i==input.length()-1){
				if (node.marked) return node.depth;
				else return -1;
			}
			else return -1;
		}
		else if (node.c<input.charAt(i)){
			if (node.right!=null) return getDepth(input, node.right, i);
			else return -1;
		}
		
		if (node.left!=null) return getDepth(input, node.left, i);
		return -1;
	}

	private void getString(Node node, List<String> result, String previousString) {
		String currentString = previousString+node.c;
		if (node.marked) {
			result.add(currentString);
		}
		if (node.left!=null){
			getString(node.left, result, previousString);
		}
		if (node.right!=null){
			getString(node.right, result, previousString);
		}
		if (node.next!=null){
			getString(node.next, result, currentString);
		}
	}

	public static void main(String[] args) {
		String probInput = "413 122 342 234 422 552 323";
		String[] input = probInput.trim().split("\\s+");
		TernaryST myTernaryST = new TernaryST();
		for (int i=0; i<input.length; i++){
			myTernaryST.put(input[i]);
		}
		
		Iterable<String> inside = myTernaryST.getAllStrings();
		for (String s:inside){
			System.out.println(s);
		}
		
		StringBuilder sb = new StringBuilder();
		
		for (int i=0; i<input.length; i++){
			sb.append(String.format(" %d", myTernaryST.getDepth(input[i])));
		} 
		
		System.out.println(sb.toString().trim());

	}

}

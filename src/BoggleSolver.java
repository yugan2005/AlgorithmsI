import java.util.ArrayList;
import java.util.List;

public class BoggleSolver {
	private static final int R=25;
	private static final char startChar = 'A';
	private Tries myDict;
	int row, col;
	
	
	private class Node{
		private boolean wordInDict;
		private Node[] next= new Node[R];
		
	}
	
	private class Tries{
		private Node root = new Node();
		
		private void put(String input){
			root = put(root, input, 0);
		}

		private Node put(Node node, String input, int i) {
			if (input.charAt(i)=='U') {
				if (i==input.length()-1) {
					node.wordInDict=true;
					return node;
				}
				else return put(node, input, i+1);
			}
			int idx = input.charAt(i) - startChar;
			if (input.charAt(i) > 'U') idx--;
			if (node.next[idx]==null){
				node.next[idx]= new Node();
			}
			if (i==input.length()-1){
				node.next[idx].wordInDict = true;
			}
			else {
				node.next[idx]=put(node.next[idx], input, i+1);
			}
			return node;
		}
		
		public boolean isInsideDict(String input){
			return isInsideDict(root, input, 0);
		}

		private boolean isInsideDict(Node node, String input, int i) {
			if (input.charAt(i)=='U') {
				if (i==input.length()-1) {
					return node.wordInDict=true;
				}
				else return isInsideDict(node, input, i+1);
			}			
			
			int idx = input.charAt(i) - startChar;
			if (input.charAt(i) > 'U') idx--;

			if (node.next[idx]==null) return false;
			if (i==input.length()-1) return node.next[idx].wordInDict;
			return isInsideDict(node.next[idx], input, i+1);
		}
//		
//		private Node getRoot(){
//			return root;
//		}
		
//		public boolean isInTries(Node node, char ch){
//			if (node.next[ch-startChar]==null) return false;
//			return true;
//		}
		
	}
	
	 // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary){
    	myDict = new Tries();
    	for (String word : dictionary){
    		myDict.put(word);
    	}
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board){
    	row = board.rows();
    	col = board.cols();
    	List<String> result = new ArrayList<>();
    	for (int i=0; i<row; i++){
    		for (int j=0; j<col; j++){
    			Integer[] cell = new Integer[] {i, j};
    			boolean[][] visited = new boolean[row][col];
    			dfs(cell, myDict.root, board, result, visited, "");
    		}
    	}
		return result;
    	
    }
    


	private void dfs(Integer[] cell, Node node, BoggleBoard board, List<String> result, boolean[][] visited, String runningStr) {
		if (visited[cell[0]][cell[1]]) return;
		visited[cell[0]][cell[1]] = true;
		char currentChar = board.getLetter(cell[0], cell[1]);
		Node nextNode =  node.next[currentChar-startChar];
		if (nextNode ==null) return;
		if (nextNode.wordInDict) {
			if (currentChar == 'Q') {
				result.add(runningStr+"QU");
			}
			else {
				result.add(runningStr+currentChar);
			}
		}
		for (Integer[] nextcell : adj(cell)){
			dfs(nextcell, nextNode, board, result, visited, runningStr+currentChar);
		}
		
	}

	private Iterable<Integer[]> adj(Integer[] cell){
		int i = cell[0];
		int j = cell[1];
    	List<Integer[]> result = new ArrayList<>();
    	if (i!=0){
    		if (j!=0){
    			result.add(new Integer[]{i-1, j-1});
    		}
    		if (j!=col-1){
    			result.add(new Integer[]{i-1, j+1});
    		}
    		result.add(new Integer[]{i-1, j});
    	}
    	if (i!=row-1){
    		if (j!=0){
    			result.add(new Integer[]{i+1, j-1});
    		}
    		if (j!=col-1){
    			result.add(new Integer[]{i+1, j+1});
    		}
    		result.add(new Integer[]{i+1, j});
    	}
    	
    	if (j!=0){
    		result.add(new Integer[]{i, j-1});
    	}
    	
    	if (j!=col-1){
    		result.add(new Integer[]{i, j+1});
    	}
    	return result;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word){
		if(!myDict.isInsideDict(word)) return 0;
		
		int l = word.length();
		
		if (l<=2) return 0;
		if (l<=4) return 1;
		if (l==5) return 2;
		if (l==6) return 3;
		if (l==7) return 5;
		return 11;
    }

}

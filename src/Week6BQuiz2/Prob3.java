package Week6BQuiz2;

public class Prob3 {
	// This is LZW compression part
	private final static int R = 128;
	
	private static class Node{
		int codePoint;
		Node[] next = new Node[R];
		
		Node(){
			
		}
		
		Node(int i){
			this.codePoint = i;
		}
	
	}
	
	private static Node root = new Node();
	

	public static void main(String[] args) {
		
		String input = "A C B B A A A A A B A B A B B";
		input = String.join("", input.trim().split("\\s+"));
		
		for (int i=0; i<R; i++){
			root.next[i]=new Node(i);
		}
		
		int codePointIdx = R+1;
		int idx = 0;
		
		StringBuilder sb = new StringBuilder();
		
		while (idx<input.length()) {
			Node currentNode = root;
			while (currentNode.next[input.charAt(idx)]!=null){
				currentNode = currentNode.next[input.charAt(idx)];
				idx++;
				if (idx==input.length()){
					break;
				}
			}
			String code = String.format("%X ", currentNode.codePoint);
			sb.append(code);
			if (idx!=input.length()){
				currentNode.next[input.charAt(idx)]=new Node(codePointIdx);
				codePointIdx++;
			}
		}
		sb.append(String.format("%X", R));
		
		System.out.println(sb.toString());
		

	}

}

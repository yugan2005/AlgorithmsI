package Week6BQuiz1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Stack;

public class Prob1 {
	private char[] re;
	private Digraph G;
	private int M;
	private Set<Integer> reachableState;
	
	private void buildNFA(String regex){
		String regexWithNoSpace = String.join("", regex.trim().split("\\s+"));
		M = regexWithNoSpace.length();
		re = regexWithNoSpace.toCharArray();
		G = new Digraph(M+1);
		
		Stack<Integer> ops = new Stack<>();
		for (int i=0; i<M; i++){
			int lp = -1;
			int or = -1;
			
			if (re[i]=='(' || re[i]=='|') {
				ops.push(i);
			}
			
			if (re[i]==')'){
				or = ops.pop();
				if (re[or]=='|') {
					lp = ops.pop();
					G.addEdge(lp, or+1);
					G.addEdge(or, i);
				} else {
					lp = or;
					
				}
			}
			
			if (i<M-1 && re[i+1]=='*') {
				G.addEdge(lp, i+1);
				G.addEdge(i+1, lp);
			}
			
			if (re[i]=='(' || re[i]=='*' || re[i]== ')')
				G.addEdge(i, i+1);
		}
		
		
	}
	
	private void matchMove(char c){
		
		Set<Integer> newReachableState = new HashSet<>();
		
		for (int i : reachableState){
			if (re[i]==c) {
				newReachableState.add(i+1);
				//TODO here
			}
		}
		
		
	}
	

}

package IIWeek3Quiz1;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.Queue;


public class Prob1 {
	
	public static void main(String[] args){
		String input = 
				"A->B     18  /  18    A->F     13  /  13    A->G      0  /  17    B->C     18  /  18    C->D     18  /  18    C->G      0  /  12    D->E      4  /   4    D->H      5  /  14    D->J     15  /  15    E->J      4  /   5    F->G     13  /  14    G->B      0  /   6    G->H     13  /  16    H->C      0  /  14    H->I     18  /  18    I->D      6  /  10    I->J     12  /  17";
		String[] tokens = input.trim().split("\\s+");
		
		int numVert = 10;
		
		FlowNetwork flowNet = new FlowNetwork(numVert);
		
		for (int i=0; i<tokens.length; i+=4){
			int v = tokens[i].charAt(0)-'A'; 
			int w = tokens[i].charAt(3)-'A';
			int flow = Integer.parseInt(tokens[i+1]);
			int capacity = Integer.parseInt(tokens[i+3]);
			flowNet.addEdge(new FlowEdge(v, w, capacity, flow));
		}
		
		//using bread first search
		
		Queue<Integer> 
		
		
	
	
	
	
	}

}

package Week2Q1B;

import java.util.Comparator;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Prob1 {
	
	// this is the  Kruskal's algorithm 

	private class myComparator implements Comparator<String> {

		@Override
		public int compare(String arg0, String arg1) {
			Integer weight0 = Integer.parseInt(arg0.trim().split(",")[1]);
			Integer weight1 = Integer.parseInt(arg1.trim().split(",")[1]);

			return weight0.compareTo(weight1);
		}

	}

	public static void main(String[] args) {
		WeightedQuickUnionUF uf = new WeightedQuickUnionUF(10);
		MinPQ<String> minPQ = new MinPQ<>(17, new Prob1().new myComparator());
		String prob = "F-A    16    G-A    13    B-A     3     C-B     6    H-B     5    G-B     2    C-D    12    C-I     9    C-H     1    D-I    14    J-D     8    D-E     7    J-E    17    F-G    15    G-H     4    I-H    11    I-J    10";
		String[] temp = prob.trim().split("\\s+");
		for (int i = 0; i < temp.length; i += 2) {
			String record = temp[i] + "," + temp[i + 1];
			minPQ.insert(record);
		}
		
		int sizeOfMST = 0;
		while (!minPQ.isEmpty() && sizeOfMST<=9){
			String minEdgeStr = minPQ.delMin();
			String[] vertexStr = minEdgeStr.split(",")[0].split("-");
			int v = vertexStr[0].charAt(0)-'A';
			int w = vertexStr[1].charAt(0)-'A';
			if (!uf.connected(v, w)){
				uf.union(v, w);
				System.out.println(minEdgeStr);
				sizeOfMST++;
			}
			
		}

	}

}

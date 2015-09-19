package Week1;
import edu.princeton.cs.algs4.*;

public class Quiz1 {
	

	public static void main(String[] args) {
		
		// Question1
		QuickFindUF myQF = new QuickFindUF(10);
		
		myQF.union(7,9);
		myQF.union(9, 6);
		myQF.union(0, 8);
		myQF.union(1, 5);
		myQF.union(7, 0);
		myQF.union(4, 5);
		
		String question1="";
		for (int i=0; i<10; i++){
			question1 += myQF.find(i)+" ";
		}
		System.out.println(question1);
		
		// Question2
		MyWeightedUnionFind myWQU = new MyWeightedUnionFind(10);
		
		myWQU.union(3, 6);
		myWQU.union(8, 4);
		myWQU.union(5, 2);
		myWQU.union(4, 0);
		myWQU.union(9, 1);
		myWQU.union(4, 2);
		myWQU.union(3, 7);
		myWQU.union(1, 7);
		myWQU.union(8, 7);
		
		String question2 = "";

		for (int i : myWQU.parent()) question2 += i+" ";
		System.out.println(question2);
		
		
		

	}

}

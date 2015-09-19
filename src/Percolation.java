import edu.princeton.cs.algs4.*;

public class Percolation {
	private WeightedQuickUnionUF grid;
	private int n;
	private boolean[] open;
	
	public Percolation(int N){
		if (N<=0) throw new IllegalArgumentException();
		this.n=N;
		open = new boolean[n*n+2];
		open[0]=true;
		open[N*N+1]=true;
		grid = new WeightedQuickUnionUF(N*N+2);
		for (int i=1; i<=N; i++){
			grid.union(0, i);
			grid.union(N*(N-1)+i, N*N+1);
		}
	}
	
	public void open(int i, int j){
		// open site (row i, column j) if it is not open already
		if (i<1 || i>n || j<1 || j>n) throw new IllegalArgumentException();
		if (isOpen(i,j)) return;
		open[i*j]=true;
		int[] neighbor = getNeighor(i, j);
		for (int k:neighbor) {
			if (open[k]) grid.connected(i*j, k);	
		}
	}
	
   private int[] getNeighor(int i, int j) {
	   int[] neighbor = new int[4];
	   int idx=0;
	   if (i!=1) {
		   neighbor[idx] = (i-1)*j;
		   idx++;
	   }
	   if (j!=1){
		   neighbor[idx] = (j-1)*i;
		   idx++;
	   }
	   if (i!=n){
		   neighbor[idx] = (i+1)*j;
		   idx++;
	   }
	   if (j!=n){
		   neighbor[idx] = (j+1)*i;
		   idx++;
	   }
	   
	   int[] result = new int[idx];
	   for (int k=0; k<idx; k++){
		   result[k]=neighbor[k];
	   }	   
	   return result;
	}

   public boolean isOpen(int i, int j) {
	   // is site (row i, column j) open?
		if (i<1 || i>n || j<1 || j>n) throw new IllegalArgumentException();
		return open[i*j];
   }
   
   
   public boolean isFull(int i, int j){
	   // is site (row i, column j) full?
	   if (i<1 || i>n || j<1 || j>n) throw new IllegalArgumentException();
	   return grid.connected(0, i*j);
   }
   
   public boolean percolates(){
	   return grid.connected(0, n*n+1);
   }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}

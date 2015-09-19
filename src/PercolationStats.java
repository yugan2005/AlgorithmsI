import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
	private int t, n;
	private double[] threshold;
	
	public PercolationStats(int N, int T){
		// perform T independent experiments on an N-by-N grid
		if (N<=0 || T<=0) throw new IllegalArgumentException();
		this.t=T;
		this.n=N;
		threshold = new double[t];
		int[] openSequence = new int[n*n];
		for (int i=0; i<n*n; i++){
			openSequence[i]=i+1;
		}
		for (int i=0; i<t; i++){
			Percolation test = new Percolation(n*n);
			StdRandom.shuffle(openSequence);
			for (int j=0; j<N*N; j++){
				int col = openSequence[j]%n;
				int row = (openSequence[j]-col)/n+1;
				test.open(row,col);
				if (test.percolates()){
					threshold[i]=j;
					break;
				}
			}			
		}
	}
	
	public double mean(){
		// sample mean of percolation threshold
		double sum = 0;
		for (int i=0; i<t; i++){
			sum += threshold[i];
		}
		return sum/t;		
	}
	
	public double stddev(){
		// sample standard deviation of percolation threshold
		double mean = mean();
		double var = 0;
		for (int i=0; i<t; i++){
			var += (threshold[i]-mean)*(threshold[i]-mean);			
		}
		return Math.sqrt(var/(t-1));
	}
	
	public double confidenceLo(){
		// low  endpoint of 95% confidence interval
		double mean = mean();
		double std = stddev();
		return (mean - 1.96*std/Math.sqrt(t));
	}

	public double confidenceHi(){
		// high endpoint of 95% confidence interval
		double mean = mean();
		double std = stddev();
		return (mean + 1.96*std/Math.sqrt(t));
	}

	public static void main(String[] args) {
		
		PercolationStats myTest = new PercolationStats(200, 20);
		
		System.out.println(myTest.mean());
		System.out.println(myTest.stddev());


	}

}

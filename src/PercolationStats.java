import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private int t, n;
	private double mean, stddev;

	public PercolationStats(int N, int T) {
		// perform T independent experiments on an N-by-N grid
		if (N <= 0 || T <= 0)
			throw new IllegalArgumentException();
		this.t = T;
		this.n = N;
		double[] threshold = new double[t];
		int[] openSequence = new int[n * n];
		for (int i = 0; i < n * n; i++) {
			openSequence[i] = i + 1;
		}
		for (int i = 0; i < t; i++) {
			Percolation test = new Percolation(n);
			StdRandom.shuffle(openSequence);
			for (int j = 0; j < n * n; j++) {
				int col = openSequence[j] % n + 1;
				int row = (openSequence[j] - 1) / n + 1;
				test.open(row, col);
				if (test.percolates()) {
					threshold[i] = ((double) j+1) / (n * n);
					break;
				}
			}
		}
		mean = StdStats.mean(threshold);
		stddev = StdStats.stddev(threshold);
	}

	public double mean() {
		// sample mean of percolation threshold
		return mean;
	}

	public double stddev() {
		// sample standard deviation of percolation threshold
		return stddev;
	}

	public double confidenceLo() {
		// low endpoint of 95% confidence interval
		return (mean - 1.96 * stddev / Math.sqrt(t));
	}

	public double confidenceHi() {
		// high endpoint of 95% confidence interval
		return (mean + 1.96 * stddev / Math.sqrt(t));
	}

	public static void main(String[] args) {
		
		int N = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);

		PercolationStats myTest = new PercolationStats(N, T);

		System.out.println("mean			 = "+ myTest.mean());
		System.out.println("stddev			 = "+ myTest.stddev());
		System.out.println("95/% confidence interval = "+ myTest.confidenceLo() + ", "+myTest.confidenceHi());
	}

}

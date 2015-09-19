import edu.princeton.cs.algs4.*;

public class Percolation {
	private WeightedQuickUnionUF percolationGrid;
	private WeightedQuickUnionUF fullGrid;
	private int n;
	private boolean[] open;

	public Percolation(int N) {
		if (N <= 0)
			throw new IllegalArgumentException();
		this.n = N;
		open = new boolean[n * n + 2];
		open[0] = true;
		open[n * n + 1] = true;
		percolationGrid = new WeightedQuickUnionUF(n * n + 2);
		fullGrid = new WeightedQuickUnionUF(n * n + 1);
	}

	public void open(int i, int j) {
		// open site (row i, column j) if it is not open already
		if (i < 1 || i > n || j < 1 || j > n)
			throw new IndexOutOfBoundsException();
		if (isOpen(i, j))
			return;
		open[(i - 1) * n + j] = true;
		int[] neighbor = getNeighor(i, j);
		for (int k : neighbor) {
			if (open[k]) {
				percolationGrid.union((i - 1) * n + j, k);
				if (k != n * n + 1)
					fullGrid.union((i - 1) * n + j, k);
			}
		}

	}

	private int[] getNeighor(int i, int j) {
		int[] neighbor = new int[4];
		int idx = 0;
		if (i != 1) {
			neighbor[idx] = (i - 2) * n + j;
			idx++;
		} else {
			neighbor[idx] = 0;
			idx++;
		}

		if (j != 1) {
			neighbor[idx] = (i - 1) * n + j - 1;
			idx++;
		}

		if (i != n) {
			neighbor[idx] = i * n + j;
			idx++;
		} else {
			neighbor[idx] = n * n + 1;
			idx++;
		}

		if (j != n) {
			neighbor[idx] = (i - 1) * n + j + 1;
			idx++;
		}

		int[] result = new int[idx];
		for (int k = 0; k < idx; k++) {
			result[k] = neighbor[k];
		}
		return result;
	}

	public boolean isOpen(int i, int j) {
		// is site (row i, column j) open?
		if (i < 1 || i > n || j < 1 || j > n)
			throw new IndexOutOfBoundsException();
		return open[(i - 1) * n + j];
	}

	public boolean isFull(int i, int j) {
		// is site (row i, column j) full?
		if (i < 1 || i > n || j < 1 || j > n)
			throw new IndexOutOfBoundsException();
		return (fullGrid.connected(0, (i - 1) * n + j));
	}

	public boolean percolates() {

		return percolationGrid.connected(0, n * n + 1);
	}

	public static void main(String[] args) {

		int n = 4;

		Percolation test = new Percolation(n);

		int[] openSequence = new int[n * n];

		for (int i = 0; i < n * n; i++) {
			openSequence[i] = i + 1;
		}

		StdRandom.shuffle(openSequence);

		for (int j = 0; j < n * n; j++) {
			int row = (openSequence[j] - 1) / n + 1;
			int col = openSequence[j] - (row - 1) * n;
			test.open(row, col);
			if (test.percolates()) {
				System.out.println(j);
				break;
			}
		}
	}

}

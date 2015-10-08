import edu.princeton.cs.algs4.ResizingArrayQueue;

public class Board {
	private final int[][] blocks; // The final modifier actually is not useful
									// as I throught
	private final int N;

	public Board(int[][] blocks) {
		// construct a board from an N-by-N array of blocks
		// (where blocks[i][j] = block in row i, column j)

		// YG note: The array of primitive type are NOT immutable, this bit me
		// again!
		// this.blocks = blocks;
		this.N = blocks.length;
		this.blocks = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				this.blocks[i][j] = blocks[i][j];
			}
		}
	}

	public int dimension() {
		// board dimension N
		return N;
	}

	public int hamming() {
		// number of blocks out of place
		int counter = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (blocks[i][j] != 0 && blocks[i][j] != (i * N + j + 1))
					counter++;
			}
		}
		return counter;
	}

	public int manhattan() {
		// sum of Manhattan distances between blocks and goal
		int counter = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (blocks[i][j] == 0)
					continue;
				int value = blocks[i][j];
				int goalI = (value - 1) / N;
				int goalJ = (value - 1) % N;
				counter += Math.abs(goalI - i) + Math.abs(goalJ - j);
			}
		}
		return counter;
	}

	public boolean isGoal() {
		// is this board the goal board?
		return hamming() == 0;
	}

	public Board twin() {
		// a board that is obtained by exchanging any pair of blocks
		int[][] blockTwin = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				blockTwin[i][j] = blocks[i][j];
			}
		}

		// find two non_zero block
		int[][] nonZeroIdx = new int[2][2];
		int counter = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (blocks[i][j] != 0) {
					nonZeroIdx[counter][0] = i;
					nonZeroIdx[counter][1] = j;
					counter++;
				}
				if (counter == 2) {
					int temp = blockTwin[nonZeroIdx[0][0]][nonZeroIdx[0][1]];
					blockTwin[nonZeroIdx[0][0]][nonZeroIdx[0][1]] = blockTwin[nonZeroIdx[1][0]][nonZeroIdx[1][1]];
					blockTwin[nonZeroIdx[1][0]][nonZeroIdx[1][1]] = temp;
					return new Board(blockTwin);
				}
			}
		}
		return null;
	}

	public boolean equals(Object y) {
		// does this board equal y?
		if (y==null) return false;
		if (this == y)
			return true;
		if (y.getClass() != this.getClass())
			return false;
		Board yBoard = (Board) y;
		if (N != yBoard.dimension())
			return false;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (this.blocks[i][j] != yBoard.blocks[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	public Iterable<Board> neighbors() {
		// all neighboring boards

		ResizingArrayQueue<Board> allNeighbors = new ResizingArrayQueue<>();

		int[] idxZero = new int[2];
		for (int idx = 0; idx < N * N; idx++) {
			int[] twoDIdx = convertIdx(idx);
			if (blocks[twoDIdx[0]][twoDIdx[1]] == 0) {
				idxZero = twoDIdx;
				break;
			}
		}
		int rowIdx = idxZero[0];
		int colIdx = idxZero[1];

		int[][] temp = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				temp[i][j] = blocks[i][j];
			}
		}

		if (rowIdx != 0) {
			swap(temp, rowIdx - 1, colIdx, rowIdx, colIdx);
			allNeighbors.enqueue(new Board(temp));
			swap(temp, rowIdx - 1, colIdx, rowIdx, colIdx);
		}

		if (rowIdx != N - 1) {
			swap(temp, rowIdx + 1, colIdx, rowIdx, colIdx);
			allNeighbors.enqueue(new Board(temp));
			swap(temp, rowIdx + 1, colIdx, rowIdx, colIdx);
		}

		if (colIdx != 0) {
			swap(temp, rowIdx, colIdx - 1, rowIdx, colIdx);
			allNeighbors.enqueue(new Board(temp));
			swap(temp, rowIdx, colIdx - 1, rowIdx, colIdx);
		}

		if (colIdx != N - 1) {
			swap(temp, rowIdx, colIdx + 1, rowIdx, colIdx);
			allNeighbors.enqueue(new Board(temp));
			swap(temp, rowIdx, colIdx + 1, rowIdx, colIdx);
		}

		return allNeighbors;
	}

	private void swap(int[][] a, int i1, int j1, int i2, int j2) {
		int temp = a[i1][j1];
		a[i1][j1] = a[i2][j2];
		a[i2][j2] = temp;
	}

	public String toString() {
		// string representation of this board (in the output format specified
		// below)
		
		StringBuilder boardStrBuilder = new StringBuilder();
		boardStrBuilder.append(String.valueOf(N));
		boardStrBuilder.append(System.lineSeparator());
		for (int i=0; i<N; i++){
			for (int j=0; j<N; j++){
				boardStrBuilder.append(" "+String.valueOf(blocks[i][j]));
			}
			boardStrBuilder.append(System.lineSeparator());
		}
		return boardStrBuilder.toString();
	}

	private int[] convertIdx(int oneDIdx) {
		int[] twoDIdx = new int[2];
		twoDIdx[0] = oneDIdx / N;
		twoDIdx[1] = oneDIdx % N;
		return twoDIdx;
	}

	public static void main(String[] args) {
		// unit tests (not graded)
	}

}

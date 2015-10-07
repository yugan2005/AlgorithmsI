
public class Board {
	private final int[][] blocks;
	private final int N;
	public Board(int[][] blocks) {
		// construct a board from an N-by-N array of blocks
		// (where blocks[i][j] = block in row i, column j)
		this.blocks=blocks;
		this.N = blocks.length;
	}

	public int dimension() {
		// board dimension N
		return N;
	}

	public int hamming() {
		// number of blocks out of place
		int counter = 0;
		for (int i=0; i<N; i++){
			for (int j=0; j<N; j++){
				if (blocks[i][j]!=0 && blocks[i][j]!=(i*N+j+1)) counter++;
			}
		}
		return counter;
	}

	public int manhattan() {
		// sum of Manhattan distances between blocks and goal
		int counter = 0;
		for (int i=0; i<N; i++){
			for (int j=0; j<N; j++){
				if (blocks[i][j]==0) continue;
				int value = blocks[i][j];
				int goalI = (value-1)/N;
				int goalJ = (value-1)%N;
				counter += Math.abs(goalI-i)+Math.abs(goalJ-j);
			}
		}
		return counter;
	}

	public boolean isGoal() {
		// is this board the goal board?
		return hamming()==0;
	}

	public Board twin() {
		// a board that is obtained by exchanging any pair of blocks
		return null;
	}

	public boolean equals(Object y) {
		// does this board equal y?
		if (this==y) return true;
		if (y.getClass()!=this.getClass()) return false;
		Board yBoard = (Board) y;
		if (this.dimension()!=yBoard.dimension()) return false;
		for (int i=0; i<this.dimension(); i++){
			for (int j=0; j<this.dimension(); j++){
				if (this.blocks[i][j]!=yBoard.blocks[i][j]){
					return false;
				}
			}
		}
		return true;
	}

	public Iterable<Board> neighbors() {
		// all neighboring boards
		return null;
	}

	public String toString() {
		// string representation of this board (in the output format specified
		// below)
		return null;
	}

	public static void main(String[] args) {
		// unit tests (not graded)
	}

}

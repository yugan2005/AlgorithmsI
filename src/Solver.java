// Reference of this A* algorithm: https://en.wikipedia.org/wiki/A*_search_algorithm
// This version try to exclude the already tested Board, but the improvement is not that great.
// The test for puzzle4x4-40.txt shows that this version took 170214, and the original version took 395893, so it is only 2.32 times improvement 
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	private SearchNode root, rootTwin;
	private MinPQ<SearchNode> pqRoot, pqTwin;
	private boolean solvable;
	private SearchNode solution;
	private SearchNode solutionTwin;
	private SET<String> testedBoardSet;
	private SET<String> testedBoardSetTwin;
	private int runSteps;

	public Solver(Board initial) {
		// find a solution to the initial board (using the A* algorithm)
		if (initial == null)
			throw new NullPointerException("Initial Board can not be null");
		Board initialTwin = initial.twin();
		root = new SearchNode(null, initial, 0);
		rootTwin = new SearchNode(null, initialTwin, 0);
		pqRoot = new MinPQ<SearchNode>();
		pqTwin = new MinPQ<SearchNode>();

		pqRoot.insert(root);
		pqTwin.insert(rootTwin);

		testedBoardSet = new SET<String>();
		testedBoardSetTwin = new SET<String>();

		runSteps = 0;

		while (!pqRoot.isEmpty() && !pqTwin.isEmpty()) {

			SearchNode currentNode = pqRoot.delMin();
			int currentMove = currentNode.moveMade;
			Board currentBoard = currentNode.board;

			if (currentBoard.isGoal()) {
				solvable = true;
				solution = currentNode;
				return;
			}

			SearchNode currentNodeTwin = pqTwin.delMin();
			int currentMoveTwin = currentNodeTwin.moveMade;
			Board currentBoardTwin = currentNodeTwin.board;

			if (currentBoardTwin.isGoal()) {
				solvable = false;
				solutionTwin = currentNodeTwin;
				return;
			}

			if (!testedBoardSet.contains(currentBoard.hashBoardCode())) {
				testedBoardSet.add(currentBoard.hashBoardCode());

				Iterable<Board> childBoards = currentBoard.neighbors();
				for (Board childBoard : childBoards) {
					if (currentNode.previousNode == null
							|| !childBoard
									.equals(currentNode.previousNode.board)) {
						SearchNode childNode = new SearchNode(currentNode,
								childBoard, currentMove + 1);
						pqRoot.insert(childNode);
					}
				}
			}

			if (!testedBoardSetTwin.contains(currentBoardTwin.hashBoardCode())) {
				testedBoardSetTwin.add(currentBoardTwin.hashBoardCode());

				Iterable<Board> childBoardsTwin = currentBoardTwin.neighbors();

				for (Board childBoardTwin : childBoardsTwin) {
					if (currentNodeTwin.previousNode == null
							|| !childBoardsTwin
									.equals(currentNodeTwin.previousNode.board)) {
						SearchNode childNodeTwin = new SearchNode(
								currentNodeTwin, childBoardTwin,
								currentMoveTwin + 1);
						pqTwin.insert(childNodeTwin);
					}
				}
			}

			runSteps++;
			System.out.println(currentMove);

		}

		solvable = false;
	}

	public boolean isSolvable() {
		// is the initial board solvable?
		return solvable;
	}

	public int moves() {
		// min number of moves to solve initial board; -1 if unsolvable
		if (solvable)
			return solution.moveMade;

		return -1;
	}

	public Iterable<Board> solution() {
		// sequence of boards in a shortest solution; null if unsolvable
		if (solvable) {
			Stack<Board> solutionStack = new Stack<>();
			SearchNode solutionNode = solution;
			while (solutionNode != null) {
				solutionStack.push(solutionNode.board);
				solutionNode = solutionNode.previousNode;
			}

			return solutionStack;
		}

		return null;
	}

	public Iterable<Board> solutionTwin() {
		// sequence of boards in a shortest solution; null if unsolvable
		if (!solvable) {
			Stack<Board> solutionStack = new Stack<>();
			SearchNode solutionNode = solutionTwin;
			while (solutionNode != null) {
				solutionStack.push(solutionNode.board);
				solutionNode = solutionNode.previousNode;
			}

			return solutionStack;
		}

		return null;
	}

	private class SearchNode implements Comparable<SearchNode> {
		SearchNode previousNode;
		Board board;
		int moveMade;
		int priorityNum;

		public SearchNode(SearchNode previousNode, Board board, int moveMade) {
			this.previousNode = previousNode;
			this.board = board;
			this.moveMade = moveMade;
			this.priorityNum = moveMade + board.manhattan();
		}

		@Override
		public int compareTo(SearchNode that) {
			return ((Integer) this.priorityNum).compareTo(that.priorityNum);
		}
	}

	public static void main(String[] args) {
		// solve a slider puzzle (given below)
		// create initial board from file
		In in = new In(args[0]);
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable()) {
			StdOut.println("No solution possible");
			System.out.println(solver.runSteps);
			StdOut.println("Twin Solution is:");
			for (Board board : solver.solutionTwin())
				StdOut.println(board);
		}

		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			System.out.println(solver.runSteps);
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}

}

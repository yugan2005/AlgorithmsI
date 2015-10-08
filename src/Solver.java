
import java.util.Comparator;

import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {
	GameTreeNode root, rootTwin;
	MinPQ<Board> pq, pqTwin;
	

	public Solver(Board initial) {
		// find a solution to the initial board (using the A* algorithm)
		Board initialTwin = initial.twin();
		root = new GameTreeNode(initial);
		rootTwin = new GameTreeNode(initialTwin);
		pq = new MinPQ<>(comparator)
		
		
	}

	public boolean isSolvable() {
		return false;
		// is the initial board solvable?
	}

	public int moves() {
		return 0;
		// min number of moves to solve initial board; -1 if unsolvable
	}

	public Iterable<Board> solution() {
		return null;
		// sequence of boards in a shortest solution; null if unsolvable
	}
	
	private class GameTreeNode{
		public Board parent;
		public Board current;
		public LinkedQueue<Board> children;
		public GameTreeNode(Board board){
			current = board;
		}
	}
	
	private class ManhattanPriority implements Comparator<Board>{

		@Override
		public int compare(Board o1, Board o2) {
			int priorityO1 = o1.manhattan()+
			return 0;
		}
		
	}

	public static void main(String[] args) {
		// solve a slider puzzle (given below)
	}
	
}

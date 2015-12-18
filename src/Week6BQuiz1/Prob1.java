package Week6BQuiz1;

import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Stack;

public class Prob1 {
	private char[] re;
	private Digraph G;
	private int M;
	private Set<Integer> reachableState;

	private void buildNFA(String regex) {
		String regexWithNoSpace = String.join("", regex.trim().split("\\s+"));
		M = regexWithNoSpace.length();
		re = regexWithNoSpace.toCharArray();
		G = new Digraph(M + 1);

		Stack<Integer> ops = new Stack<>();
		for (int i = 0; i < M; i++) {
			int lp = i;
			int or = -1;

			if (re[i] == '(' || re[i] == '|') {
				ops.push(i);
			}

			if (re[i] == ')') {
				or = ops.pop();
				if (re[or] == '|') {
					lp = ops.pop();
					G.addEdge(lp, or + 1);
					G.addEdge(or, i);
				} else {
					lp = or;

				}
			}

			if (i < M - 1 && re[i + 1] == '*') {
				G.addEdge(lp, i + 1);
				G.addEdge(i + 1, lp);
			}

			if (re[i] == '(' || re[i] == '*' || re[i] == ')')
				G.addEdge(i, i + 1);
		}

	}

	private void matchMove(char c) {

		Set<Integer> newReachableState = new HashSet<>();

		for (int i : reachableState) {
			if (i == M)
				continue;
			if (re[i] == c || re[i] == '.') {
				newReachableState.add(i + 1);
			}
		}

		reachableState = newReachableState;

		StringBuilder sb = new StringBuilder("After reading " + c + ": " + System.getProperty("line.separator"));
		sb.append("After Match transition" + System.getProperty("line.separator"));
		for (int i : reachableState) {
			sb.append(i + " ");
		}
		System.out.println(sb.toString());

	}

	private void epsilonMove() {
		Set<Integer> newReachableState = new HashSet<>();
		boolean[] reachable = new boolean[G.V()];

		for (int i : reachableState) {
			boolean[] visited = new boolean[G.V()];
			dfs(i, visited, reachable);
		}

		for (int i = 0; i <= M; i++) {
			if (reachable[i])
				newReachableState.add(i);
		}

		reachableState = newReachableState;

		StringBuilder sb = new StringBuilder("After epsilon transition" + System.getProperty("line.separator"));
		for (int i : reachableState) {
			sb.append(i + " ");
		}
		System.out.println(sb.toString());
	}

	private void dfs(int i, boolean[] visited, boolean[] reachable) {
		if (reachable[i] || visited[i])
			return;

		reachable[i] = true;
		visited[i] = true;

		for (int j : G.adj(i)) {
			dfs(j, visited, reachable);
		}
	}

	public static void main(String[] args) {
		String regInput = "( C ( ( A * | B ) C ) * ) ";
		String input = "C C B C C A ";
		input = String.join("", input.trim().split("\\s+"));

		Prob1 test = new Prob1();

		test.buildNFA(regInput);
		test.reachableState = new HashSet<>();
		test.reachableState.add(0);

		test.epsilonMove();

		for (int i = 0; i < input.length(); i++) {
			test.matchMove(input.charAt(i));
			test.epsilonMove();
		}

		for (int i : test.reachableState) {
			if (i == test.M) {
				System.out.println("matches!");
				break;
			}
		}

	}

}

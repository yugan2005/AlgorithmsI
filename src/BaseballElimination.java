import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {
	private FlowNetwork flowNet;
	private int sourceCapacity;
	private FordFulkerson fordFulkerson;

	private int numTeam, excluded;
	private Map<String, Integer> teamIndex;
	private Map<Integer, String> teamReverseIndex;
	private int[] w, l, r;
	private int[][] g;

	public BaseballElimination(String filename) {
		// create a baseball division from given filename in format specified
		// below
		In reader = new In(filename);
		numTeam = Integer.parseInt(reader.readLine().trim());
		teamIndex = new HashMap<>(numTeam);
		teamReverseIndex = new HashMap<>(numTeam);
		w = new int[numTeam];
		l = new int[numTeam];
		r = new int[numTeam];
		g = new int[numTeam][numTeam];

		for (int i = 0; i < numTeam; i++) {
			String[] teamInputs = reader.readLine().trim().split("\\s+");
			teamIndex.put(teamInputs[0], i);
			teamReverseIndex.put(i, teamInputs[0]);
			w[i] = Integer.parseInt(teamInputs[1]);
			l[i] = Integer.parseInt(teamInputs[2]);
			r[i] = Integer.parseInt(teamInputs[3]);
			for (int j = 0; j < numTeam; j++) {
				g[i][j] = Integer.parseInt(teamInputs[4 + j]);
			}
		}
		reader.close();

	}

	public int numberOfTeams() {
		// number of teams
		return numTeam;
	}

	public Iterable<String> teams() {
		// all teams
		return teamIndex.keySet();
	}

	public int wins(String team) {
		// number of wins for given team
		if (teamIndex.get(team) == null)
			throw new IllegalArgumentException("team not in the list");

		return w[teamIndex.get(team)];
	}

	public int losses(String team) {
		// number of losses for given team
		if (teamIndex.get(team) == null)
			throw new IllegalArgumentException("team not in the list");

		return l[teamIndex.get(team)];
	}

	public int remaining(String team) {
		// number of remaining games for given team
		if (teamIndex.get(team) == null)
			throw new IllegalArgumentException("team not in the list");

		return r[teamIndex.get(team)];
	}

	public int against(String team1, String team2) {
		// number of remaining games between team1 and team2
		if (teamIndex.get(team1) == null)
			throw new IllegalArgumentException("team not in the list");
		if (teamIndex.get(team2) == null)
			throw new IllegalArgumentException("team not in the list");

		int i1 = teamIndex.get(team1);
		int i2 = teamIndex.get(team2);
		return g[i1][i2];
	}

	public boolean isEliminated(String team) {
		// is given team eliminated?
		if (teamIndex.get(team) == null)
			throw new IllegalArgumentException("team not in the list");

		int excludedTeam = teamIndex.get(team);
		if (trivialElimination(excludedTeam) != null) {
			return true;
		}

		if (excludedTeam != excluded || excluded == 0) {
			excluded = excludedTeam;
			buildFlowNet(excluded);
			fordFulkerson = new FordFulkerson(flowNet, 0, flowNet.V() - 1);
		}

		return (fordFulkerson.value() < sourceCapacity);
	}

	private Iterable<String> trivialElimination(int team) {

		List<String> result = null;
		int maxWin = w[team] + r[team];

		for (int i = 0; i < numTeam; i++) {

			if (w[i] > maxWin) {
				result = new ArrayList<>(1);
				result.add(teamReverseIndex.get(i));
			}
		}
		return result;
	}

	private void buildFlowNet(int exclude) {
		flowNet = new FlowNetwork(numTeam * (numTeam - 1) + numTeam + 2);
		sourceCapacity = 0;
		// 0 is the dummy source
		// 1 ~ numTeam*(numTeam-1) are the game vertexes
		// numTeam*(numTeam-1)+1 ~ numTeam*(numTeam-1)+numTeam are the team
		// vertexes
		// numTeam*(numTeam-1)+numTeam+1 is the dummy sink

		for (int i = 0; i < numTeam; i++) {
			for (int j = i + 1; j < numTeam; j++) {
				if (i != exclude && j != exclude) {
					// total number of iteration should be
					// (numTeam-1)*(numTeam-2)/2
					int gameVertex = i * numTeam + j + 1;
					FlowEdge gameEdge = new FlowEdge(0, gameVertex, g[i][j]);
					flowNet.addEdge(gameEdge);
					sourceCapacity += g[i][j];

					int teamVertex = numTeam * (numTeam - 1) + 1 + i;
					FlowEdge teamEdge = new FlowEdge(gameVertex, teamVertex, g[i][j]);
					flowNet.addEdge(teamEdge);

					teamVertex = numTeam * (numTeam - 1) + 1 + j;
					teamEdge = new FlowEdge(gameVertex, teamVertex, g[i][j]);
					flowNet.addEdge(teamEdge);
				}
			}
		}

		for (int i = 0; i < numTeam; i++) {
			if (i != exclude) {
				int teamVertex = numTeam * (numTeam - 1) + 1 + i;
				int sinkCapacity = w[exclude] + r[exclude] - w[i];
				if (sinkCapacity > 0) {
					FlowEdge sinkEdge = new FlowEdge(teamVertex, numTeam * (numTeam - 1) + numTeam + 1, sinkCapacity);
					flowNet.addEdge(sinkEdge);
				}
			}
		}
	}

	public Iterable<String> certificateOfElimination(String team) {
		// subset R of teams that eliminates given team; null if not eliminated
		if (teamIndex.get(team) == null)
			throw new IllegalArgumentException("team not in the list");

		int excludedTeam = teamIndex.get(team);

		if (trivialElimination(excludedTeam) != null) {
			return trivialElimination(excludedTeam);
		}
		
		if (!isEliminated(team)) {
			return null;
		}
		
		Set<String> result = new HashSet<>();
		for (int i = 1; i <= numTeam * (numTeam - 1); i++) {
			if (fordFulkerson.inCut(i)) {
				int team1 = (i - 1) / numTeam;
				int team2 = (i - 1) % numTeam;
				result.add(teamReverseIndex.get(team1));
				result.add(teamReverseIndex.get(team2));
			}
		}
		return result;
	}

	public static void main(String[] args) {
		BaseballElimination division = new BaseballElimination(args[0]);
		for (String team : division.teams()) {
			if (division.isEliminated(team)) {
				StdOut.print(team + " is eliminated by the subset R = { ");
				for (String t : division.certificateOfElimination(team)) {
					StdOut.print(t + " ");
				}
				StdOut.println("}");
			} else {
				StdOut.println(team + " is not eliminated");
			}
		}
	}

}

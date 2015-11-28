import java.util.HashMap;
import java.util.Map;

import edu.princeton.cs.algs4.In;

public class BaseballElimination {
	int numTeam;
	Map<String, Integer> teamIndex;
	int[] w, l, r;
	int[][] g;

	public BaseballElimination(String filename){
		// create a baseball division from given filename in format specified
		// below
		In reader = new In(filename);
		numTeam = Integer.parseInt(reader.readLine().trim());
		teamIndex = new HashMap<>(numTeam);
		w = new int[numTeam];
		l = new int[numTeam];
		r = new int[numTeam];
		g = new int[numTeam][numTeam];

		for (int i = 0; i < numTeam; i++) {
			String[] teamInputs = reader.readLine().trim().split("\\s+");
			teamIndex.put(teamInputs[0], i);
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
		return w[teamIndex.get(team)];
	}

	public int losses(String team) {
		// number of losses for given team
		return l[teamIndex.get(team)];
	}

	public int remaining(String team) {
		// number of remaining games for given team
		return r[teamIndex.get(team)];
	}

	public int against(String team1, String team2) {
		// number of remaining games between team1 and team2
		int i1 = teamIndex.get(team1);
		int i2 = teamIndex.get(team2);
		return g[i1][i2];
	}

	public boolean isEliminated(String team) {
		// is given team eliminated?
		int teamX = teamIndex.get(team);
	}

	public Iterable<String> certificateOfElimination(String team) {
		// subset R of teams that eliminates given team; null if not eliminated
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

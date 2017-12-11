import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FlowEdge;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/*
	Correctness:  14/15 tests passed
	Memory:       4/4 tests passed
	Timing:       1/1 tests passed
*/

public class BaseballElimination {

	private final int numberOfTeams;
	private final int[][] g;
	private final int[] wins;
	private final int[] loss;
	private final int[] left;
	private final String[] teams;
	private int trivialteam;
	private boolean trivialelim = false;
	private FordFulkerson maxflow;

	public BaseballElimination(String filename) {
		In input = new In(filename);
		String line = input.readLine().trim();
		this.numberOfTeams = Integer.parseInt(line);

		g = new int[numberOfTeams][numberOfTeams];
		wins = new int[numberOfTeams];
		loss = new int[numberOfTeams];
		left = new int[numberOfTeams];
		teams = new String[numberOfTeams];

		for (int i = 0; i < numberOfTeams; i++) {
			line = input.readLine().trim();
			String[] lines = line.split("\\s+");

			teams[i] = lines[0];
			wins[i] = Integer.parseInt(lines[1]);
			loss[i] = Integer.parseInt(lines[2]);
			left[i] = Integer.parseInt(lines[3]);

			for (int j = 0; j < numberOfTeams; j++) {
				g[i][j] = Integer.parseInt(lines[4 + j]);
			}
		}
	}
	public int numberOfTeams() {
		return numberOfTeams;
	}
	public Iterable<String> teams() {
		return Arrays.asList(teams);
	}

	private int findTeamIndex(String team) {
		for (int i = 0; i < numberOfTeams; i++) {
			if (teams[i].equals(team)) {
				return i;
			}
		}
		return -1;
	}

	public int wins(String team) {
		int x = findTeamIndex(team);
		if (x == -1)
			throw new java.lang.IllegalArgumentException();
		return wins[x];
	}
	public int losses(String team) {
		int x = findTeamIndex(team);
		if (x == -1)
			throw new java.lang.IllegalArgumentException();
		return loss[x];
	}
	public int remaining(String team) {
		int x = findTeamIndex(team);
		if (x == -1)
			throw new java.lang.IllegalArgumentException();
		return left[x];
	}
	public int against(String team1, String team2) {
		int x = findTeamIndex(team1);
		int y = findTeamIndex(team2);

		if (x == -1 || y == -1)
			throw new java.lang.IllegalArgumentException();
		return g[x][y];
	}

	private int teamNumberToIndex(int i, int idx) {
		int col1 = (numberOfTeams-2)*(numberOfTeams-1)/2;

		if(i<idx)
			return i + col1 + 1;
		else
			return i + col1;
	}
	public boolean isEliminated(String team) {
		int id = findTeamIndex(team);
		if (id == -1)
			throw new java.lang.IllegalArgumentException();

		int col1 = ((numberOfTeams - 2) * (numberOfTeams - 1)) / 2;
		int size = 2 + col1 + numberOfTeams - 1;
		int src = 0;
		int sink = size - 1;
		FlowNetwork flow = new FlowNetwork(size);
		int total = 0;

		int k = 1;
		for (int i = 0; i < numberOfTeams; i++) {
			for (int j = i + 1; j < numberOfTeams; j++) {
				if (i != id && j != id) {
					int one = teamNumberToIndex(i, id);
					int two = teamNumberToIndex(j, id);
					total += g[i][j];
					flow.addEdge(new FlowEdge(0, k, g[i][j]));
					flow.addEdge(new FlowEdge(k, one, Double.POSITIVE_INFINITY));
					flow.addEdge(new FlowEdge(k, two, Double.POSITIVE_INFINITY));
					k++;
				}
			}
		}

		for (int i = 0; i < numberOfTeams; i++) {
			if (i != id) {
				int one = teamNumberToIndex(i, id);
				int cap = wins[id] + left[id] - wins[i];
				if(cap < 0){
					trivialteam = i;
					trivialelim = true;
					return true;
				}
				flow.addEdge(new FlowEdge(one, sink, cap));
			}
		}

		maxflow = new FordFulkerson(flow, src, sink);
		int mflow = (int)maxflow.value();
		return total != mflow;
	}
	public Iterable<String> certificateOfElimination(String team) {
		int id = findTeamIndex(team);
		if (id == -1)
			throw new java.lang.IllegalArgumentException();

		List<String> res = new ArrayList<>();
		int tm = findTeamIndex(team);
		if(isEliminated(team)){
			if(trivialelim == true){
				res.add(teams[trivialteam]);
				return res;
			}
			else
			{
				for(int i=0;i<numberOfTeams;i++){
					if(i != tm){
						if(maxflow.inCut(teamNumberToIndex(i, tm))){
							res.add(teams[i]);
						}
					}
				}
				return res;
			}
		}
		return null;
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
//Took some help from :https://github.com/gzc/MOOC-Course/blob/master/Princeton-Algorithm2/Algorithm2/BaseballElimination.java

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BaseballElimination {
    private final Map<String, Integer> name2id;
    private final Map<Integer, String> id2name;
    private final int[] win;
    private final int[] loss;
    private final int[] remain;
    private final int[][] game;
    private final int teamNum;
    private final boolean[] solved;
    private final boolean[] isOut;
    private final ArrayList<Set<String>> certificates;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);
        teamNum = in.readInt();
        win = new int[teamNum];
        loss = new int[teamNum];
        remain = new int[teamNum];
        game = new int[teamNum][teamNum];
        solved = new boolean[teamNum];
        isOut = new boolean[teamNum];
        certificates = new ArrayList<>();
        for (int i = 0; i < teamNum; i++) {
            certificates.add(null);
        }
        name2id = new HashMap<>();
        id2name = new HashMap<>();
        for (int i = 0; i < teamNum; i++) {
           String team = in.readString();
           name2id.put(team, i);
           id2name.put(i, team);
           win[i] = in.readInt();
           loss[i] = in.readInt();
           remain[i] = in.readInt();
            for (int j = 0; j < teamNum; j++) {
                game[i][j] = in.readInt();
            }
        }
    }

    // number of teams
    public int numberOfTeams() {
        return teamNum;
    }

    // all teams
    public Iterable<String> teams() {
        return name2id.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        if (!name2id.containsKey(team)) {
            throw new IllegalArgumentException("invalid team");
        }
        return win[name2id.get(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        if (!name2id.containsKey(team)) {
            throw new IllegalArgumentException("invalid team");
        }
        return loss[name2id.get(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        if (!name2id.containsKey(team)) {
            throw new IllegalArgumentException("invalid team");
        }
        return remain[name2id.get(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        if (!name2id.containsKey(team1) || !name2id.containsKey(team2)) {
            throw new IllegalArgumentException("invalid team");
        }
        return game[name2id.get(team1)][name2id.get(team2)];
    }

    // create the Flow Network for team whose id is id
    private FlowNetwork createNetwork(int id) {
        int s = 0;
        int t = teamNum * (teamNum - 1) / 2 + 2;
        int m = (teamNum - 1) * (teamNum - 2) / 2 + 1;
        FlowNetwork G = new FlowNetwork(t + 1);
        int temp = 0;
        for (int i = 0; i < teamNum; i++) {
            if (i == id) continue;
            for (int j = i + 1; j < teamNum; j++) {
                if (j == id) continue;
                temp++;
                G.addEdge(new FlowEdge(s, temp, game[i][j]));
                G.addEdge(new FlowEdge(temp, m + i, Double.POSITIVE_INFINITY));
                G.addEdge(new FlowEdge(temp, m + j, Double.POSITIVE_INFINITY));
            }
        }
        for (int i = 0; i < teamNum; i++) {
            if (i == id) continue;
            G.addEdge(new FlowEdge(m + i, t, win[id] + remain[id] - win[i]));
        }
        return G;
    }

    // solve the baseball elimination problem for given team
    private void solve(String team) {
        int id = name2id.get(team);
        if (solved[id]) return;
        solved[id] = true;
        // trivial elimination
        for (int i = 0; i < teamNum; i++) {
            if (win[id] + remain[id] < win[i]) {
                isOut[id] = true;
                certificates.set(id, new HashSet<>());
                certificates.get(id).add(id2name.get(i));
                return;
            }
        }

        // Nontrivial elimination
        int s = 0;
        int t = teamNum * (teamNum - 1) / 2 + 2;
        int m = (teamNum - 1) * (teamNum - 2) / 2 + 1;
        FlowNetwork G = createNetwork(id);
        FordFulkerson FF = new FordFulkerson(G, s, t);
        for (int i = 1; i < m; i++) {
            if (FF.inCut(i)) {
                isOut[id] = true;
                certificates.set(id, new HashSet<>());
                for (int j = 0; j < teamNum; j++) {
                    if (FF.inCut(j + m)) {
                        certificates.get(id).add(id2name.get(j));
                    }
                }
                return;
            }
        }
        isOut[id] = false;
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        if (!name2id.containsKey(team)) {
            throw new IllegalArgumentException("invalid team");
        }
        if (!solved[name2id.get(team)]) solve(team);
        return isOut[name2id.get(team)];
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        if (!name2id.containsKey(team)) {
            throw new IllegalArgumentException("invalid team");
        }
        if (!solved[name2id.get(team)]) solve(team);
        return certificates.get(name2id.get(team));
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
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}

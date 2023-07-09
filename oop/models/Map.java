package models;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Map {
    private int numNodes;
    private long[][] dist;
    private int[][] next;

    public Map(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String[] line = reader.readLine().split(" ");
        numNodes = Integer.parseInt(line[0]);
        int numEdges = Integer.parseInt(line[1]);
        dist = new long[numNodes][numNodes];
        next = new int[numNodes][numNodes];
        for (int i = 0; i < numNodes; i++) {
            Arrays.fill(dist[i], Long.MAX_VALUE);
            dist[i][i] = 0;
        }
        for (int i = 0; i < numEdges; i++) {
            line = reader.readLine().split(" ");
            int from = Integer.parseInt(line[0]) - 1;
            int to = Integer.parseInt(line[1]) - 1;
            long weight = Long.parseLong(line[2]);
            dist[from][to] = weight;
            next[from][to] = to;
        }
        reader.close();
    }

    public void floydWarshall() {
        for (int k = 0; k < numNodes; k++) {
            for (int i = 0; i < numNodes; i++) {
                for (int j = 0; j < numNodes; j++) {
                    if (dist[i][k] != Long.MAX_VALUE && dist[k][j] != Long.MAX_VALUE &&
                            dist[i][j] > dist[i][k] + dist[k][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }
    }

    public long getShortestDistance(int source, int destination) {
        return dist[source][destination];
    }

    public String getShortestPath(int source, int destination) {
        StringBuilder sb = new StringBuilder();
        sb.append(source + 1);
        int current = source;
        while (current != destination) {
            current = next[current][destination];
            sb.append(" -> ").append(current + 1);
        }
        return sb.toString();
    }
}
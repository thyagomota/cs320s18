/**
 * CSCI320 - Networking & Distributed Computing
 * Spring 2018
 * Defines a TSP input graph with trip costs.
 * @author Thyago Mota
 */

import java.io.*;
import java.util.*;

public class Graph {

    private short graph[][];
    private int   size;
    static final int INFINITY = Short.MAX_VALUE;

    Graph(String fileName) throws IOException {
        readData(fileName);
        normalize();
    }

    private void readData(String fileName) throws IOException {
        Scanner sc = new Scanner(new FileReader(fileName));
        this.size = sc.nextInt();
        this.graph = new short[this.size][this.size];
        for (int i = 0; i < this.size; i++)
            for (int j = 0; j < this.size; j++) {
                graph[i][j] = sc.nextShort();
                if (graph[i][j] == -1)
                    graph[i][j] = INFINITY;
            }
        sc.close();
    }

    private void normalize() {
        for (int i = 0; i < this.size; i++) {
            int min = graph[i][0];
            for (int j = 1; j < this.size; j++)
                if (graph[i][j] < min)
                    min = graph[i][j];
            for (int j = 0; j < this.size; j++)
                if (graph[i][j] != INFINITY)
                    graph[i][j] -= min;
        }
    }

    int tripCost(int trip[], int tripSize) {
        int cost = 0;
        if (tripSize <= 1)
            return INFINITY;
        for (int i = 0; i < tripSize - 1; i++) {
            if (cost + getCost(trip[i], trip[i + 1]) > INFINITY)
                return INFINITY;
            cost += getCost(trip[i],trip[i + 1]);
        }
        if (cost + getCost(trip[tripSize - 1], trip[0]) > INFINITY)
            return INFINITY;
        cost += getCost(trip[tripSize - 1], trip[0]);
        return cost;
    }

    void printTrip(int trip[], int tripSize, PrintStream out) throws IOException {
        String str = "";
        str += "[";
        for (int i = 0; i < tripSize; i++)
            str += trip[i] + ", ";
        if (str.length() > 2)
            str = str.substring(0, str.length() - 2);
        str += "] $" + tripCost(trip, tripSize);
        out.println(str);
    }

    public short[][] getGraph() {
        return graph;
    }

    public int getSize() {
        return size;
    }

    int getCost(int i, int j) {
        return graph[i][j];
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++)
                if (getCost(i, j) == INFINITY)
                    str += "Inf\t";
                else
                    str += getCost(i, j) + "\t";
            str += "\n";
        }
        return str;
    }
}

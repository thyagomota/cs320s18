import java.io.*;
import java.util.*;

class Tsp {

    private short graph[][];
    private int size;
    private int bestTrip[];
    private int bestTripCost;
    public static final String FILE_NAME = "data22.txt";
    public static short INFINITY = Short.MAX_VALUE;

    Tsp(String fileName) throws Exception {
        this.readData(fileName);
        this.normalize();
        this.bestTrip = new int[this.size];
        this.bestTripCost = Tsp.INFINITY;
    }

    void run() throws Exception {
        int start = 1;
        int trip[] = new int[this.size];
        trip[0] = start;
        int tripSize = 1;
        tripSearch(trip, tripSize);
    }

    private void readData(String fileName) throws IOException {
        Scanner sc = new Scanner(new FileReader(fileName));
        this.size = sc.nextInt();
        this.graph = new short[this.size][this.size];
        for (int i = 0; i < this.size; i++)
            for (int j = 0; j < this.size; j++) {
                graph[i][j] = sc.nextShort();
                if (graph[i][j] == -1)
                    graph[i][j] = Tsp.INFINITY;
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
                if (graph[i][j] != Tsp.INFINITY)
                    graph[i][j] -= min;
        }
    }

    void printTrip(int trip[], int tripSize, PrintStream out) throws IOException {
        String str = "";
        str += "[";
        for (int i = 0; i < tripSize; i++)
            str += trip[i] + ", ";
        if (str.length() > 2)
            str = str.substring(0, str.length() - 2);
        str += "] $" + this.tripCost(trip, tripSize);
        out.println(str);
    }

    void printData(PrintStream out) throws IOException {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++)
                if (this.graph[i][j] == Tsp.INFINITY)
                    out.print("Inf\t");
                else
                    out.print(this.graph[i][j] + "\t");
            out.println();
        }
    }

    int tripCost(int trip[], int tripSize) {
        int cost = 0;
        if (tripSize <= 1)
            return Tsp.INFINITY;
        for (int i = 0; i < tripSize - 1; i++) {
            if (cost + this.graph[trip[i]][trip[i + 1]] > Tsp.INFINITY)
                return Tsp.INFINITY;
            cost += this.graph[trip[i]][trip[i + 1]];
        }
        if (cost + this.graph[trip[tripSize - 1]][trip[0]] > Tsp.INFINITY)
            return Tsp.INFINITY;
        cost += this.graph[trip[tripSize - 1]][trip[0]];
        return cost;
    }

    void tripSearch(int trip[], int tripSize) throws IOException {
        int tripCost = this.tripCost(trip, tripSize);
        // we got a solution...
        if (tripSize == this.size) {
            // we want to keep it...
            if (tripCost < this.bestTripCost) {
                this.printTrip(trip, tripSize, System.out);
                System.arraycopy(trip, 0, bestTrip, 0, this.size);
                this.bestTripCost = tripCost;
            }

        }
        // it's a partial trip
        // the cost is worse than what I have so far -> return
        else if (tripCost > this.bestTripCost)
            return;
            // OK, we should do branching...
        else {
            for (int i = 0; i < this.size; i++) {
                // check whether this is a new city
                boolean found = false;
                for (int j = 0; j < tripSize; j++)
                    if (i == trip[j]) {
                        found = true;
                        break;
                    }
                if (!found) {
                    trip[tripSize] = i;
                    this.tripSearch(trip, tripSize + 1);
                }
            }
        }
    }

    int[] getBestTrip() {
        return this.bestTrip;
    }

    int getBestTripCost() {
        return this.bestTripCost;
    }

    int getSize() {
        return this.size;
    }

    public static void main(String[] args) {
        try {
            Tsp tsp = new Tsp(Tsp.FILE_NAME);
            tsp.printData(System.out);
            long startTime = System.nanoTime();
            tsp.run();
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            System.out.println("Best solution found in " + duration / 1000000000. + "s");
            tsp.printTrip(tsp.getBestTrip(), tsp.getSize(), System.out); // print the best solution
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
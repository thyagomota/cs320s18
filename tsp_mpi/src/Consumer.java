/**
 * CSCI320 - Networking & Distributed Computing
 * Spring 2018
 * Defines a consumer thread that requests tasks from master; communication with master is implemented using MPI; better solutions when found are immediately reported to the master.
 * @author Thyago Mota
 */

import mpi.*;
import java.io.*;

public class Consumer implements Runnable {

    private Comm  comm;
    private Graph graph;

    private int   bestTripFound[];
    private int   bestTripCost;

    Consumer(Graph graph, Comm comm) throws IOException {
        this.comm          = comm;
        this.graph         = graph;
        this.bestTripFound = new int[graph.getSize()];
        this.bestTripCost  = Graph.INFINITY;
    }

    // TODO: update bestTripCost only if it is better than current
    synchronized void setBestTripCost(int bestTripCost) {

    }

    // TODO: return bestTripCost
    synchronized int getBestTripCost() {
        return Graph.INFINITY;
    }

    public Graph getGraph() {
        return graph;
    }

    void tripSearch(int trip[], int tripSize) throws IOException {
        int tripCost = graph.tripCost(trip, tripSize);
        int size = graph.getSize();
        // a solution was found!
        if (tripSize == size) {
            // do we want to keep it?
            if (tripCost < getBestTripCost()) {
                // copy the trip to bestTripFound
                System.arraycopy(trip, 0, bestTripFound, 0, size);
                // update bestTripCost
                setBestTripCost(tripCost);
                // TODO: report to master that a best solution was found

            }
        }
        // it's a partial trip
        // check if the cost is worse than what we have so far
        else if (tripCost > this.bestTripCost)
            return;
        // OK, we should do branching...
        else {
            for (int i = 0; i < size; i++) {
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

    @Override
    public void run() {
        while (true) {
            int trip[] = new int[graph.getSize()];
            try {
                // TODO: send a task request to master using TAG_REQUEST_TASK

                // TODO: wait for the task to arrive

                // TODO: if city equals to INFINITY, break the loop because there are no more tasks to process

                // TODO: nope, start working on the task

            } catch (MPIException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

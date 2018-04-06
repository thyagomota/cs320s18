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

    // TODOd: update bestTripCost only if it is better than current
    synchronized void setBestTripCost(int bestTripCost) {
        if (bestTripCost < this.bestTripCost)
            this.bestTripCost = bestTripCost;
    }

    // TODOd: return bestTripCost
    synchronized int getBestTripCost() {
        return bestTripCost;
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
                // TODOd: report to master that a best solution was found
                try {
                    System.out.println("Slave #" + comm.getRank() + " is repoting a best trip to master");
                    comm.send(trip, tripSize, MPI.INT, 0, TspMpi.TAG_BEST_TRIP_REPORT);
                } catch (MPIException e) {
                    e.printStackTrace();
                }
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
                // TODOd: send a task request to master using TAG_REQUEST_TASK
                comm.send(trip, 1, MPI.INT, 0, TspMpi.TAG_REQUEST_TASK);

                // TODOd: wait for the task to arrive
                comm.recv(trip, trip.length, MPI.INT, 0, TspMpi.TAG_TASK);

                // TODOd: if city equals to INFINITY, break the loop because there are no more tasks to process
                if (trip[0] == Graph.INFINITY) {
                    System.out.println("Slave #" + comm.getRank() + " is done!");
                    break;
                }

                // TODOd: nope, start working on the task
                tripSearch(trip, 1);

            } catch (MPIException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * CSCI320 - Networking & Distributed Computing
 * Spring 2018
 * Implements a parallel solution for TSP based on branch & bound and using MPI for communications.
 * @author Thyago Mota
 */

import mpi.*;
import java.io.*;
import java.util.concurrent.*;

public class TspMpi {

    // MPI tags to classify different types of messages
    static final int TAG_REQUEST_TASK     = 0;
    static final int TAG_TASK             = 1;
    static final int TAG_BEST_TRIP_REPORT = 2;

    // TSP input graph file
    static final String FILE_NAME             = "../../../data22.txt";

    // used to define the queue capacity based on the number of processes
    static final double QUEUE_CAPACITY_FACTOR = 1.5;

    private Comm                   comm;
    private BlockingQueue<Integer> tasks;
    private Graph                  graph;
    private int                    bestTripFound[];
    private int                    bestTripCost;

    TspMpi(String fileName) throws MPIException, IOException {
        comm = MPI.COMM_WORLD;

        // queue settings
        int capacity = (int) Math.round(comm.getSize() * QUEUE_CAPACITY_FACTOR);
        tasks = new ArrayBlockingQueue<Integer>(capacity);

        // TSP-related
        this.graph = new Graph(fileName);
        this.bestTripFound = new int[graph.getSize()];
        this.bestTripCost = Graph.INFINITY;
    }

    void run() throws IOException, MPIException, InterruptedException {
        int np   = comm.getSize();
        int rank = comm.getRank();

        // master process code
        if (rank == 0) {

            // TODO: start the producer thread in the background


            // master main thread code
            int size = graph.getSize();
            int trip[] = new int[size];
            Status status;
            while (true) {
                // probe each slave for messages
                for (int i = 1; i < np; i++) {
                    // TODO: asynchronously probe for messages tagged with TAG_REQUEST_TASK

                    // TODO: if a message was indeed received, consume the message
                    // TODO: then, take a new task from the queue (if the queue is empty, create a task using INFINITY)
                    // TODO: then, send the task to the slave


                    // TODO: asynchronously probe for messages tagged with TAG_BEST_TRIP_REPORT

                    // TODO: if a message was indeed received, consume the message
                    // TODO: then, confirm if the message had a better cost
                    // TODO: if that is the case, save the reported trip as best trip
                    // TODO: and then, broadcast the new best trip to all processes

                }
            }
        } // end of rank == 0
        // slave processes code
        else {

            // TODO: start the consumer thread in the background


            // slave main thread code
            int size = graph.getSize();
            int trip[] = new int[size];
            // TODO: as long as the consumer thread is alive
            // TODO: listen to broadcast messages from master containing new global best trip
            // TODO: if a message arrives, update the best trip cost

            System.out.println("Slave #" + comm.getRank() + " is done!");
        }
    }

    public static void main(String[] args) throws MPIException, IOException, InterruptedException {
        MPI.Init(args);
        new TspMpi(FILE_NAME).run();
        MPI.Finalize();
    }
}

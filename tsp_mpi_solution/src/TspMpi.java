/**
 * CSCI320 - Networking & Distributed Computing
 * Spring 2018
 * Implements a parallel solution for TSP based on branch & bound and using MPI for communications.
 * @author Thyago Mota
 */

import mpi.*;
import java.io.*;
import java.util.Arrays;
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

            // TODOd: start the producer thread in the background
            Producer producer = new Producer(graph, tasks);
            Thread producerThread = new Thread(producer);
            producerThread.setName("producer");
            producerThread.start();

            // master main thread code
            int size = graph.getSize();
            int trip[] = new int[size];
            Status status;
            while (true) {
                // probe each slave for messages
                for (int i = 1; i < np; i++) {
                    // TODOd: asynchronously probe for messages tagged with TAG_REQUEST_TASK
                    status = comm.iProbe(i, TAG_REQUEST_TASK);

                    // TODOd: if a message was indeed received, consume the message
                    if (status != null) {
                        comm.recv(trip, 1, MPI.INT, i, TAG_REQUEST_TASK);

                        // TODOd: then, take a new task from the queue (if the queue is empty, create a task using INFINITY)
                        // TODOd: then, send the task to the slave

                        if (!tasks.isEmpty())
                            trip[0] = tasks.take();
                        else
                            trip[0] = Graph.INFINITY;
                        comm.send(trip, trip.length, MPI.INT, i, TAG_TASK);
                    }

                    // TODOd: asynchronously probe for messages tagged with TAG_BEST_TRIP_REPORT
                    status = comm.iProbe(i, TAG_BEST_TRIP_REPORT);

                    // TODOd: if a message was indeed received, consume the message
                    if (status != null) {
                        comm.recv(trip, trip.length, MPI.INT, i, TAG_BEST_TRIP_REPORT);
                        graph.printTrip(trip, trip.length, System.out);

                        // TODOd: then, confirm if the message had a better cost
                        int reportedTripCost = graph.tripCost(trip, trip.length);

                        // TODOd: if that is the case, save the reported trip as best trip
                        this.bestTripCost = bestTripCost;
                        System.arraycopy(trip, 0, bestTripFound, 0, trip.length);

                        // TODOd: and then, broadcast the new best trip to all processes
                        comm.bcast(trip, trip.length, MPI.INT, 0);
                    }
                }
            }
        } // end of rank == 0
        // slave processes code
        else {

            // TODOd: start the consumer thread in the background
            Consumer consumer = new Consumer(graph, comm);
            Thread consumerThread = new Thread(consumer);
            consumerThread.setName("consumer");
            consumerThread.start();

            // slave main thread code
            int size = graph.getSize();
            int trip[] = new int[size];
            // TODOd: as long as the consumer thread is alive
            while (consumerThread.isAlive()) {
                // TODO: listen to broadcast messages from master containing new global best trip
                comm.bcast(trip, trip.length, MPI.INT, 0);

                // TODO: if a message arrives, update the best trip cost
                int reportedTripCost = graph.tripCost(trip, trip.length);
                consumer.setBestTripCost(reportedTripCost);
            }

            System.out.println("Slave #" + comm.getRank() + " is done!");
        }
    }

    public static void main(String[] args) throws MPIException, IOException, InterruptedException {
        MPI.Init(args);
        new TspMpi(FILE_NAME).run();
        MPI.Finalize();
    }
}

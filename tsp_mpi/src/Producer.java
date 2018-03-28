/**
 * CSCI320 - Networking & Distributed Computing
 * Spring 2018
 * Defines a producer thread that adds TSP tasks into a queue; a task is defined by a starting city and represents a subset of the TSP search tree.
 * @author Thyago Mota
 */

import java.io.*;
import java.util.concurrent.*;

class Producer implements Runnable {

    private BlockingQueue<Integer> tasks;
    private Graph                  graph;

    Producer(Graph graph, BlockingQueue<Integer> tasks) throws IOException {
        this.graph = graph;
        this.tasks = tasks;
    }

    public Graph getGraph() {
        return graph;
    }

    @Override
    public void run() {
        // TODO: enqueue each city from the TSP graph as tasks


        System.out.println("Producer is done!");
    }
}

package edu.moravian.mathcs;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import java.io.IOException;

public class TspJms {

    static final String BROKER_URL      = "tcp://204.186.196.30:61616";
    static final String TASK_QUEUE      = "motat_tasks";
    static final String BEST_TRIP_TOPIC = "motat_best_trip";
    // TSP input graph file
    static final String FILE_NAME       = "data22.txt";
    static final int MASTER          = 0;

    private Connection conn;
    private Graph      graph;
    private int        bestTripFound[];
    private int        bestTripCost;
    private int        rank;

    TspJms(int rank, String fileName) throws JMSException, IOException {
        ConnectionFactory factory = new ActiveMQConnectionFactory(BROKER_URL);
        conn = factory.createConnection();
        conn.start();
        this.rank = rank;

        // TSP-related
        this.graph = new Graph(fileName);
        this.bestTripFound = new int[graph.getSize()];
        this.bestTripCost = Graph.INFINITY;
    }

    void run() throws IOException, JMSException {
        // master process code
        if (rank == MASTER) {

            // TODO: start the producer thread in the background
            Producer producer = new Producer(graph, conn);
            new Thread(producer).start();
        }
    }

    public static void main(String[] args) throws IOException, JMSException {
        // read command-line
        if (args.length < 1) {
            System.out.println("Use: " + TspJms.class.getName() + " rank");
            System.exit(1);
        }
        int rank = Integer.parseInt(args[0]);
        TspJms tspJms = new TspJms(rank, FILE_NAME);
        tspJms.run();
    }


}

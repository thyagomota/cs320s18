package edu.moravian.mathcs;

import javax.jms.*;
import javax.management.JMException;
import java.io.IOException;

class Producer implements Runnable {

    private Graph graph;
    private MessageProducer messageProducer;
    private Session         session;

    Producer(Graph graph, Connection conn) throws IOException, JMSException {
        this.graph = graph;
        // create a message producer
        this.session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination dest = session.createQueue(TspJms.TASK_QUEUE);
        this.messageProducer = session.createProducer(dest);
    }

    public Graph getGraph() {
        return graph;
    }

    @Override
    public void run() {
        // TODOd: enqueue each city from the TSP graph as tasks
        for (int i = 0; i < graph.getSize(); i++) {
            try {
                BytesMessage bytesMessage = session.createBytesMessage();
                bytesMessage.writeInt(i);
                messageProducer.send(bytesMessage);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Producer is done!");
    }
}
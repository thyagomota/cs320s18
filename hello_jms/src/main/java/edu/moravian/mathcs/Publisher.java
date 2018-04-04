package edu.moravian.mathcs;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
import java.util.Random;

public class Publisher {

    private TopicPublisher interestTopic, otherTopic;
    private TopicSession session;
    private int numberTasks;

    public Publisher(int numberTasks) throws JMSException {
        ConnectionFactory factory = new ActiveMQConnectionFactory(Configuration.BROKER_URL);
        TopicConnection conn = (TopicConnection) factory.createConnection();
        conn.start();
        System.out.println("Publisher connected to MQ broker at " + Configuration.BROKER_URL);
        this.session = conn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        // create publisher on topic of interest
        Topic topic = this.session.createTopic(Configuration.TOPIC_OF_INTEREST);
        this.interestTopic = this.session.createPublisher(topic);
        // create publisher on other topic
        topic = this.session.createTopic(Configuration.SOME_OTHER_TOPIC);
        this.otherTopic = this.session.createPublisher(topic);
        if (numberTasks <= 0)
            this.numberTasks = Configuration.DEFAULT_NUMBER_TASKS;
        else
            this.numberTasks = numberTasks;
    }

    public void run() throws JMSException {
        for (int i = 1; i <= this.numberTasks; i++) {
            System.out.println("Publisher published task #" + i);
            if (i % 2 == 0) {
                Message msg = this.session.createTextMessage("" + i + " on topic " + Configuration.TOPIC_OF_INTEREST);
                this.interestTopic.publish(msg);
            }
            else {
                Message msg = this.session.createTextMessage("" + i + " on topic " + Configuration.SOME_OTHER_TOPIC);
                this.otherTopic.publish(msg);
            }
        }
    }

    public static void main(String[] args) {
        try {
            Publisher publisher = new Publisher(5);
            publisher.run();
        }
        catch (JMSException ex) {
            System.out.println("Ops, something went wrong: " + ex);
        }
    }
}

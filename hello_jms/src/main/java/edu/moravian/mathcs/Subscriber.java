package edu.moravian.mathcs;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

public class Subscriber implements MessageListener {

    private TopicSubscriber subscriber;
    private TopicSession session;
    private int tasks;

    public Subscriber() throws JMSException {
        ConnectionFactory factory = new ActiveMQConnectionFactory(Configuration.BROKER_URL);
        TopicConnection conn = (TopicConnection) factory.createConnection();
        conn.start();
        System.out.println("Subscriber connected to MQ broker at " + Configuration.BROKER_URL);
        this.session = conn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = this.session.createTopic(Configuration.TOPIC_OF_INTEREST);
        this.subscriber = this.session.createSubscriber(topic);
        this.subscriber.setMessageListener(this);
    }

    public void onMessage(Message original) {
        try {
            TextMessage msg = (TextMessage) original;
            System.out.println("Subscriber received task #" + msg.getText());
        }
        catch (JMSException ex) {
            System.out.println("Ops, something went wrong: " + ex);
        }
    }

    public static void main(String[] args) {
        try {
            Subscriber subscriber = new Subscriber();
        }
        catch (JMSException ex) {
            System.out.println("Ops, something went wrong: " + ex);
        }
    }
}
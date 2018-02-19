import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

class Receiver {

    static final int PORT = 50000;
    static final int MAX_BYTES_SIZE = 1024;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // reading a datagram packet
        DatagramSocket datagramSocket = new DatagramSocket(PORT);
        System.out.println(Receiver.class + " listening on " + datagramSocket.getLocalAddress());
        byte bytes[] = new byte[MAX_BYTES_SIZE];
        DatagramPacket datagramPacket = new DatagramPacket(bytes, MAX_BYTES_SIZE);
        datagramSocket.receive(datagramPacket);
        datagramSocket.close();

        // deserialization starts here
        System.out.print("Message received! Deserialization starts now!");
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream in = new ObjectInputStream(bis);
        Contact aContact = (Contact) in.readObject();
        System.out.println("Object received: ");
        System.out.println(aContact);
    }
}

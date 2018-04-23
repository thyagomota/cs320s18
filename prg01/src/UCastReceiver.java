import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

/**
 * CSCI320 - Networking & Distributed Computing
 * Spring 2018
 * Defines a unicast receiver with a unicast socket address and a unicast message listener.
 * @author Thyago Mota
 */
class UCastReceiver extends AnyCastAgent implements Runnable {

    private UCastMessageListener listener;
    private DatagramSocket       ucastSocket;
    static final int MAX_PACKET_SIZE = 1024;

    public UCastReceiver(InetSocketAddress ucastSocketAddress, UCastMessageListener listener) throws SocketException {
        System.out.println(ucastSocketAddress);
        this.ucastSocket = new DatagramSocket(ucastSocketAddress);
        this.listener = listener;
    }

    private UCastMessage deserializeUCastMessage(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream in = new ObjectInputStream(bis);
        return (UCastMessage) in.readObject();
    }

    @Override
    void terminate() {
        this.ucastSocket.close();
    }

    @Override
    public void run() {
        byte[] bytes = new byte[MAX_PACKET_SIZE];
        DatagramPacket packet = new DatagramPacket(bytes, MAX_PACKET_SIZE);
        try {
            while (true) {
                ucastSocket.receive(packet);
                UCastMessage uCastMessage = deserializeUCastMessage(packet.getData());
                listener.messageArrived(uCastMessage);
            }
        }
        catch (Exception ex) {
        }
    }
}

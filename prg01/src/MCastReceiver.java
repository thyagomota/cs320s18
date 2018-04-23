import java.io.*;
import java.net.*;

/**
 * CSCI320 - Networking & Distributed Computing
 * Spring 2018
 * Defines a multicast receiver with a multicast socket address and a multicast message listener.
 * @author Thyago Mota
 */
class MCastReceiver extends MCastAgent {

    private MCastMessageListener listener;
    private MulticastSocket      mcastSocket;
    static final int MAX_PACKET_SIZE = 1024;

    public MCastReceiver(InetSocketAddress mcastSocketAddress, MCastMessageListener listener) throws SocketException {
        super(mcastSocketAddress);
        this.listener = listener;
    }

    private MCastMessage deserializeMCastMessage(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream in = new ObjectInputStream(bis);
        return (MCastMessage) in.readObject();
    }

    @Override
    void terminate() {
        this.mcastSocket.close();
    }

    @Override
    public void run() {
        byte[] bytes = new byte[MAX_PACKET_SIZE];
        DatagramPacket packet = new DatagramPacket(bytes, MAX_PACKET_SIZE);
        try {
            this.mcastSocket = new MulticastSocket(mcastSocketAddress.getPort());
            mcastSocket.joinGroup(mcastSocketAddress.getAddress());
            while (true) {
                mcastSocket.receive(packet);
                MCastMessage mCastMessage = deserializeMCastMessage(packet.getData());
                listener.messageArrived(mCastMessage);
            }
        }
        catch (Exception ex) {

        }
    }
}

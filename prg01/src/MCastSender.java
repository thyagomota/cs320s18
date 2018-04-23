import java.io.*;
import java.net.*;
import java.util.*;

/**
 * CSCI320 - Networking & Distributed Computing
 * Spring 2018
 * Defines a multicast sender with a multicast socket address, a unicast datagram socket, a multicast cycle, a multicast message, and a reference to a map containing questions and answers.
 * @author Thyago Mota
 */
class MCastSender extends MCastAgent {

    protected DatagramSocket      datagramSocket;
    protected MCastMessage        mcastMessage;
    protected int                 mcastCycle; // in seconds
    protected Map<String, String> qa;
    static final int DEFAULT_MCAST_CYCLE = 5;

    MCastSender(InetSocketAddress mcastSocketAddress, String address, int port, int mcastCycle, Map<String, String> qa) throws SocketException {
        super(mcastSocketAddress);
        if (mcastCycle <= 0)
            this.mcastCycle = DEFAULT_MCAST_CYCLE;
        else
            this.mcastCycle = mcastCycle;
        this.mcastMessage = new MCastMessage(address, port);
        datagramSocket    = new DatagramSocket();
        this.qa           = qa;
    }

    // reads mcastMessage
    private byte[] serializeMCastMessage() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(mcastMessage);
        return bos.toByteArray();
    }

    void sendMCastMessage() throws IOException {
        byte[] bytes = serializeMCastMessage();
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, mcastSocketAddress.getAddress(), mcastSocketAddress.getPort());
        datagramSocket.send(packet);
    }

    @Override
    void terminate() {
        this.datagramSocket.close();
    }

    // writes mcastMessage
    void setQuestion(String question) {
        mcastMessage.setQuestion(question);
    }

    // writes mcastMessage
    void setPlayers(List<Player> players) {
        mcastMessage.setPlayers(players);
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        int count = 0;
        Iterator<String> it = null;
        while (true) {
            try {
                sendMCastMessage();
                Thread.sleep(mcastCycle * 1000);
                count++;
                if (count % 2 == 0) {
                    count = 0;
                    if (it == null || !it.hasNext())
                        it = qa.keySet().iterator();
                    String q = it.next();
                    setQuestion(q);
                }
            } catch (Exception ex) {
                break;
            }
        }
    }
}

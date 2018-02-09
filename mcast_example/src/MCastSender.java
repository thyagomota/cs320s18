import java.net.*;
import java.util.Arrays;

class MCastSender extends MCastAgent implements Runnable {

    static final int MCAST_CYCLE = 3000;
    private final SocketAddress socketAddress;

    MCastSender(final SocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }

    @Override
    public void run() {
        byte[] buffer = ("" + socketAddress).getBytes();
        try {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MCAST_ADDRESS), MCAST_UDP_PORT);
            DatagramSocket socket = new DatagramSocket();
            while (true) {
                socket.send(packet);
                System.out.println("Mcast packet sent!");
                Thread.sleep(MCAST_CYCLE);
            }
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }
}

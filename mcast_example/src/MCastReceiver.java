import java.io.*;
import java.net.*;

class MCastReceiver extends MCastAgent implements Runnable {

    // this is the socket address announced by the server through the mcast channel
    private SocketAddress socketAddress;

    synchronized void setSocketAddress(final SocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }

    synchronized SocketAddress getSocketAddress() {
        return this.socketAddress;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[MAX_PACKET_SIZE];
        DatagramPacket packet = new DatagramPacket(buffer, MAX_PACKET_SIZE);
        try {
            MulticastSocket mcastSocket = new MulticastSocket(MCAST_UDP_PORT);
            mcastSocket.joinGroup(InetAddress.getByName(MCAST_ADDRESS));
            while (true) {
                mcastSocket.receive(packet);
                String msg = new String(buffer).substring(0, packet.getLength());
                String data[] = msg.split(":");
                String address = data[0];
                if (address.indexOf('/') != -1)
                    address = address.substring(address.indexOf('/') + 1);
                int port = Integer.parseInt(data[1]);
                SocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(address), port);
                setSocketAddress(socketAddress);
                System.out.println("Server announcement: " + socketAddress);
            }
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
    }
}

import java.io.IOException;
import java.net.*;
import java.util.Arrays;

public class UDPEchoServer {

    static final int PORT = 50000;
    static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) throws IOException {
        InetAddress serverAddress = InetAddress.getLocalHost();
        System.out.println(serverAddress);
        DatagramSocket datagramSocket = new DatagramSocket(PORT, serverAddress);
        System.out.println(UDPEchoServer.class + " listening on " + serverAddress);
        byte buffer[] = new byte[BUFFER_SIZE];
        DatagramPacket datagramPacket = new DatagramPacket(buffer, BUFFER_SIZE);
        datagramSocket.receive(datagramPacket);
        System.out.print("Message received: ");
        String msg = new String(buffer).substring(0, datagramPacket.getLength());
        System.out.println(msg);
        datagramSocket.close();
    }
}

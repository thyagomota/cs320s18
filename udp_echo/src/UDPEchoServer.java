import java.io.IOException;
import java.net.*;

public class UDPEchoServer {

    static final int PORT = 50000;
    static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(PORT);
        System.out.println(UDPEchoServer.class + " listening on " + datagramSocket.getLocalAddress());
        byte buffer[] = new byte[BUFFER_SIZE];
        DatagramPacket datagramPacket = new DatagramPacket(buffer, BUFFER_SIZE);
        datagramSocket.receive(datagramPacket);
        System.out.print("Message received: ");
        String msg = new String(buffer).substring(0, datagramPacket.getLength());
        System.out.println(msg);
        datagramSocket.close();
    }
}

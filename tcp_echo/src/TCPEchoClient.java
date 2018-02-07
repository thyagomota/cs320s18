import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

public class TCPEchoClient {

    static final int PORT = 50000;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Server address? ");
        String server = sc.nextLine();
        InetAddress serverAddress = InetAddress.getByName(server);
        SocketAddress serverSocketAddress = new InetSocketAddress(serverAddress, PORT);
        System.out.println(TCPEchoClient.class + " trying to connect to " + serverSocketAddress);
        Socket socket = new Socket();
        try {
            socket.connect(serverSocketAddress);
            System.out.println("Success!");
            System.out.print("Enter a message: ");
            String msg = sc.nextLine();
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println(msg); out.flush();
            socket.close();
        }
        catch (SocketException ex) {
            System.out.println(ex);
        }
    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class TCPEchoServer {

    static final int PORT = 50000;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Waiting for connections on PORT " + PORT + "...");
        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connection request received from " + clientSocket.getInetAddress());
        Scanner in = new Scanner(clientSocket.getInputStream());
        while (true) {
            if (in.hasNextLine()) {
                String msg = in.nextLine();
                System.out.print("Message received: ");
                System.out.println(msg);
                break;
            }
            else
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException ex) {

                }
        }
        serverSocket.close();
    }
}

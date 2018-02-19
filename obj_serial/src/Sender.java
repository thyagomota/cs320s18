import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Scanner;

class Sender {

    static final int PORT = 50000;

    public static void main(String[] args) throws IOException {
        // set receiver's address
        Scanner sc = new Scanner(System.in);
        System.out.print("Receiver\'s address? ");
        String receiver = sc.nextLine();
        InetAddress receiverAddress = InetAddress.getByName(receiver);

        // create object to be sent
        Contact aContact = new Contact("Sam Mai-Tai", 123456789);
        aContact.setEmail("sammaitai@gmail.com");
        aContact.setFamily(true);
        System.out.println("Object to be serialized: " + aContact);

        // serialization starts here
        System.out.println("Object will be sent to " + receiverAddress);
        SocketAddress receiverSocketAddress = new InetSocketAddress(receiverAddress, PORT);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(aContact);
        byte bytes[] = bos.toByteArray();

        // sending the object (bye-bye)
        DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length, receiverSocketAddress);
        DatagramSocket datagramSocket = new DatagramSocket();
        datagramSocket.send(datagramPacket);
        System.out.println("Object was sent... Hopefully it arrived at the destination...");
        datagramSocket.close();
    }
}

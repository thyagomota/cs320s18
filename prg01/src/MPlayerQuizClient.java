import java.io.*;
import java.net.*;
import java.util.*;

/**
 * CSCI320 - Networking & Distributed Computing
 * Spring 2018
 * Implements the client side of the multiplayer quiz game.
 * @author Thyago Mota
 */
class MPlayerQuizClient extends MPlayerQuiz implements MCastMessageListener {

    static final String MCAST_ADDRESS    = "230.0.0.1";
    static final int    MCAST_UDP_PORT   = 50000;
    private String name;
    private MCastMessage mcastMessage;
    private DatagramSocket datagramSocket;
    private MCastReceiver  mcastReceiver;

    public MPlayerQuizClient(String name) throws SocketException {
        this.name = name;
        this.datagramSocket = new DatagramSocket();

        // start MCastReceiver
        InetSocketAddress mcastSocketAddress = new InetSocketAddress(MCAST_ADDRESS, MCAST_UDP_PORT);
        this.mcastReceiver = new MCastReceiver(mcastSocketAddress, this);
        Thread mCastReceiverThread = new Thread(mcastReceiver);
        mCastReceiverThread.start();
    }

    @Override
    public void messageArrived(MCastMessage mCastMessage) {
        this.mcastMessage = mCastMessage;
        System.out.println(mCastMessage);
    }

    private byte[] serializeUCastMessage(UCastMessage uCastMessage) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(uCastMessage);
        return bos.toByteArray();
    }

    void sendUCastMessage(String question, String answer) throws IOException {
        UCastMessage uCastMessage = new UCastMessage(name, question, answer);
        byte[] bytes = serializeUCastMessage(uCastMessage);
        String uCastAddress = mcastMessage.getAddress();
        uCastAddress = uCastAddress.substring(uCastAddress.indexOf('/') + 1);
        InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getByName(uCastAddress), mcastMessage.getPort());
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, inetSocketAddress);
        datagramSocket.send(packet);
    }

    void run() throws IOException {
        String question, answer = "";
        Scanner sc = new Scanner(System.in);
        System.out.println("Write your answer and hit ENTER - just ENTER quits!");
        while (true) {
            answer = sc.nextLine();
            if (answer.isEmpty())
                break;
            if (mcastMessage != null) {
                System.out.println("Your answer is \"" + answer + "\"; sending it now!");
                question = mcastMessage.getQuestion();
                sendUCastMessage(question, answer);
            }
        }
        mcastReceiver.terminate();
        datagramSocket.close();
    }

    public static void main(String[] args) throws IOException {
        // command-line parameters
        String name = "unamed";
        if (args.length == 0)
            System.out.println("Forgot to inform the name using the command-line...");
        else
            name = args[0];

        // start the client
        MPlayerQuizClient mPlayerQuizClient = new MPlayerQuizClient(name);
        mPlayerQuizClient.run();
    }
}

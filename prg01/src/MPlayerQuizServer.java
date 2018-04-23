import java.io.*;
import java.net.*;
import java.util.*;

/**
 * CSCI320 - Networking & Distributed Computing
 * Spring 2018
 * Implements the server side of the multiplayer quiz game.
 * @author Thyago Mota
 */
class MPlayerQuizServer extends MPlayerQuiz implements UCastMessageListener {

    static final int    UCAST_UDP_PORT   = 60000;
    static final int    MCAST_CYCLE      = 5; // seconds
    private List<Player> players;
    private Map<String, String> qa;
    MCastSender   mCastSender;
    UCastReceiver uCastReceiver;
    static final String QA_FILE_NAME = "qa.txt";

    MPlayerQuizServer() throws UnknownHostException, SocketException {
        this.players = new ArrayList<>();
        readQA();

        // ucast socket address
        InetSocketAddress ucastSocketAddress = new InetSocketAddress("0.0.0.0", UCAST_UDP_PORT);

        // starts UCastReceiver
        this.uCastReceiver = new UCastReceiver(ucastSocketAddress, this);
        Thread uCastReceiverThread = new Thread(uCastReceiver);
        uCastReceiverThread.start();

        // start MCastSender
        InetSocketAddress mcastSocketAddress = new InetSocketAddress(MCAST_ADDRESS, MCAST_UDP_PORT);
        this.mCastSender = new MCastSender(mcastSocketAddress, ucastSocketAddress.getAddress().toString(), ucastSocketAddress.getPort(), MCAST_CYCLE, qa);
        Thread mCastSenderThread = new Thread(mCastSender);
        mCastSenderThread.start();
    }

    private void readQA() {
        this.qa = new HashMap<>();
        try {
            Scanner sc = new Scanner(new FileInputStream(QA_FILE_NAME));
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String data[] = line.split(";");
                String q = data[0];
                String a = data[1];
                qa.put(q, a);
            }
            sc.close();
        }
        catch (Exception ex) {

        }
    }

    private Player getPlayerByName(String name) {
        for (Player p : players)
            if (p.getName().equals(name))
                return p;
        // new player -> add it
        Player player = new Player(name);
        players.add(player);
        return player;
    }

    @Override
    public void messageArrived(UCastMessage uCastMessage) {
        System.out.println(uCastMessage);
        String name = uCastMessage.getName();
        Player player = getPlayerByName(name);
        String question = uCastMessage.getQuestion();
        String answer = uCastMessage.getAnswer();
        if (qa.get(question).equals(answer))
            player.score();
        this.mCastSender.setPlayers(players);
    }

    void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("ENTER quits!");
        while (true) {
            String line = sc.nextLine();
            if (line.isEmpty())
                break;
        }
        // System.out.println("Closing sockets...");
        uCastReceiver.terminate();
        mCastSender.terminate();
        // System.out.println("Done!");
    }

    public static void main(String[] args) throws UnknownHostException, SocketException {
        MPlayerQuizServer mPlayerQuizServer = new MPlayerQuizServer();
        mPlayerQuizServer.run();
    }
}

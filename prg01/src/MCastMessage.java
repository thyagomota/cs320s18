import java.util.*;

/**
 * CSCI320 - Networking & Distributed Computing
 * Spring 2018
 * Defines a multicast message containing a question, an address and port, and a list of players.
 * @author Thyago Mota
 */
class MCastMessage extends Message {

    private String       address;
    private int          port;
    private List<Player> players;

    MCastMessage(String address, int port) {
        super();
        this.address  = address;
        this.port     = port;
        this.players  = new ArrayList<>();
    }

    List<Player> getPlayers() {
        return players;
    }

    synchronized void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "MCastMessage{" +
                "address='" + address + '\'' +
                ", port=" + port +
                ", question='" + question + '\'' +
                ", players=" + players +
                '}';
    }
}

    /*boolean add(Player player) {
        for (Player p : players)
            if (p.equals(player))
                return false;
        players.add(player);
        return true;
    }

    boolean remove(Player player) {
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            if (p.equals(player)) {
                players.remove(i);
                return true;
            }
        }
        return false;
    }*/

import java.io.*;

/**
 * CSCI320 - Networking & Distributed Computing
 * Spring 2018
 * Defines a player with a name and a number of points.
 * @author Thyago Mota
 */
class Player implements Serializable {

    private String name;
    private int    points;
    static final String UNAMED = "unamed";

    public Player(String name) {
        this.name = name;
        this.points = 0;
    }

    Player() {
        this(UNAMED);
    }

    void setName(String name) {
        this.name = name;
    }

    void score() {
        this.points++;
    }

    String getName() {
        return name;
    }

    int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", points=" + points +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        Player other = (Player) obj;
        return name.equals(other.getName());
    }
}

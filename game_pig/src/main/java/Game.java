import com.gigaspaces.annotation.pojo.SpaceId;

import java.util.LinkedList;
import java.util.List;

public class Game {

    public String       id;
    public List<Player> players;
    public Integer      status;
    public Integer      round;

    public static int GAME_NOT_STARTED = 0;
    public static int GAME_STARTED = 1;
    public static int GAME_ENDED = 2;

    @SpaceId(autoGenerate = true)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player) {
        if (players == null)
            players = new LinkedList<Player>();
        players.add(player);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public int size() {
        if (players != null)
            return players.size();
        return 0;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id='" + id + '\'' +
                ", players=" + players +
                ", status=" + status +
                ", round=" + round +
                '}';
    }
}

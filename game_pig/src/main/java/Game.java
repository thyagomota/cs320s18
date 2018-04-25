import com.gigaspaces.annotation.pojo.SpaceId;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;

import java.util.LinkedList;
import java.util.List;

public class Game {

    public String       id;
    public List<Player> players;
    public Integer      status;
    public Integer      round;

    public static int GAME_NOT_STARTED = 0;
    public static int GAME_STARTED     = 1;
    public static int GAME_ENDED       = 2;
    public static String DEFAULT_GAME_ID  = "1";

    Game() {
        id = DEFAULT_GAME_ID;
    }

    @SpaceId
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getPlayerByName(String name) {
        if (players == null)
            return null;

        for (Player player: players)
            if (player.getName().equals(name))
                return player;
        return null;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player) {
        if (players == null)
            players = new LinkedList<Player>();
        players.add(player);
    }

    public boolean hasPlayer(Player player) {
        if (players == null)
            return false;
        return players.contains(player);
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

    public static void main(String[] args) {
        GigaSpaceConfigurer conf = new GigaSpaceConfigurer(new UrlSpaceConfigurer(Configuration.SPACE_URL));
        GigaSpace space = conf.create();

        Game game = new Game();
        game.setStatus(GAME_NOT_STARTED);
        space.write(game);
    }
}

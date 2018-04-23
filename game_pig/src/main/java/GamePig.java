import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;

import javax.swing.*;

public class GamePig {

    private GigaSpace space;
    private Player player;
    public static final String SPACE_URL = "jini://localhost/*/game_pig";
    public static final int LONG_TIMEOUT = 5 * 60 * 1000; // 5 minutes
    public static final int MIN_NUMBER_PLAYERS = 3;

    public GamePig(String name) {
        player = new Player(name);
        GigaSpaceConfigurer conf = new GigaSpaceConfigurer(new UrlSpaceConfigurer(SPACE_URL));
        space = conf.create();
    }

    // TODO: implement the game logic
    public void run() {

    }

    public static void main(String[] args) {
        String name = JOptionPane.showInputDialog(null, "Name?", "", JOptionPane.QUESTION_MESSAGE);
        GamePig gamePig = new GamePig(name);
        gamePig.run();
    }
}

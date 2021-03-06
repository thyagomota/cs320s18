import com.j_spaces.core.LeaseContext;
import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;
import sun.awt.WindowClosingListener;

import javax.swing.*;
import java.awt.event.*;

public class Game extends JFrame implements KeyListener, WindowListener {

    private Player[] players;
    private Player   player;
    private int      worldWidth, worldHeight;

    public static final int    WORLD_WIDTH  = 800;
    public static final int    WORLD_HEIGHT = WORLD_WIDTH;
    public static final String TITLE        = "CSCI320: Energy Mplay";
    public static final int    TITLE_HEIGHT = 15;

    // JavaSpace specific
    static final String SPACE_URL = "jini://localhost/*/energy_mplay";
    private GigaSpace space;

    Game(String name, int worldWidth, int worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.player = new Player(name, worldWidth, worldHeight);
        setSize(worldWidth, worldHeight + TITLE_HEIGHT);
        setTitle(TITLE);
        GamePanel gamePanel = new GamePanel(this);
        setContentPane(gamePanel);
        addKeyListener(this);
        addWindowListener(this);
        setVisible(true);

        // JavaSpace specific
        // TODO: instantiate a GigaSpaceConfigurer using the SPACE_URL


        // TODO: connect to the space


        // TODO: save the player in the space


    }

    synchronized Player[] getPlayers() {
        return players;
    }

    synchronized void setPlayers(Player players[]) {
        this.players = players;
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }

    public void run() {
        while (player.getAlive()) {

            // TODO: move the player


            // TODO: rewrite the player in the space


            // TODO: get a list of all players currently in the space using a template object

            // TODO: for each player, check if there is collision; if yes, determine whether the player should continue on the game or not

            // TODO: update the list of players so GUI can be repainted

            validate(); repaint();
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e) {

            }
        }
        System.exit(0);
    }

    public void keyTyped(KeyEvent e) {
        player.switchDirection();
    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }

    public void windowOpened(WindowEvent e) {

    }

    // TODO: determine what you should do when the players wants to quit the game
    public void windowClosing(WindowEvent e) {

    }

    public void windowClosed(WindowEvent e) {

    }

    public void windowIconified(WindowEvent e) {

    }

    public void windowDeiconified(WindowEvent e) {

    }

    public void windowActivated(WindowEvent e) {

    }

    public void windowDeactivated(WindowEvent e) {

    }

    public static void main(String[] args) {
        String name = JOptionPane.showInputDialog(null, "Name?", TITLE, JOptionPane.QUESTION_MESSAGE);
        Game game = new Game(name, WORLD_WIDTH, WORLD_HEIGHT);
        game.run();
    }
}
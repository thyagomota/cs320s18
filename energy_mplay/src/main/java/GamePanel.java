import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private Game game;
    private int worldWidth, worldHeight;

    GamePanel(Game game) {
        this.game = game;
        this.worldWidth = game.getWorldWidth();
        this.worldHeight = game.getWorldHeight();
        setPreferredSize(new Dimension(this.worldWidth, this.worldHeight));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Player players[] = game.getPlayers();
        for (int i = 0; players != null && i < players.length; i++) {
            Player player = players[i];
            if (player != null) {
                Color oldColor = g.getColor();
                g.setColor(player.getColor());
                int x = player.getX();
                int y = player.getY();
                g.fillOval(x - player.getSize() / 2, y - player.getSize() / 2, player.getSize() * 2, player.getSize() * 2);
                g.setColor(oldColor);
                g.drawString(player.toString(), x + 5, y + 15);
            }
        }
    }
}

import org.openspaces.core.GigaSpace;
import org.openspaces.core.GigaSpaceConfigurer;
import org.openspaces.core.space.UrlSpaceConfigurer;

import javax.swing.*;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GamePig {

    private GigaSpace space;
    private String name;

    public GamePig(String name) {
        this.name = name;
        GigaSpaceConfigurer conf = new GigaSpaceConfigurer(new UrlSpaceConfigurer(Configuration.SPACE_URL));
        space = conf.create();
    }

    int rollDie() {
        return new Random().nextInt(6) + 1;
    }

    int playRound(Player player) {
        int points = 0;
        while (true) {
            int die = rollDie();
            System.out.println("Die: " + die);

            String msg = player.toString() + "\n" + "You got a " + die + "\n";
            if (die == 1) {
                points = 0;
                JOptionPane.showMessageDialog(null, msg + "You got pigged!");
                break;
            }
            points += die;
            int choice = JOptionPane.showConfirmDialog(null, msg + "You have " + points + " points in this round. Roll die again? ");
            if (choice != 0)
                break;
        }
        return points;
    }

    // TODO: implement the game logic
    public void run() {

        while (true) {

            // player tries to get game status
            Game game = space.takeById(Game.class, Game.DEFAULT_GAME_ID);

            if (game != null) {
                System.out.println(name + " got game status: " + game);
                Player player = game.getPlayerByName(name);

                // if game hasn't started yet
                if (game.getStatus() == Game.GAME_NOT_STARTED) {

                    // am I in?
                    if (player == null) {
                        System.out.println(name + " joined the game now!");
                        player = new Player(name);
                        game.addPlayer(player);
                    }

                    // should the game start?
                    if (game.size() >= Configuration.MIN_NUMBER_PLAYERS) {
                        game.setStatus(Game.GAME_STARTED);
                        game.setRound(1);
                        int points = playRound(player);
                        player.setPoints(points);
                        player.setRound(1);
                    }
                }
                else if (game.getStatus() == Game.GAME_STARTED) {
                    // is it my turn to play?
                    if (game.getRound() > player.getRound()) {
                        int points = playRound(player);
                        player.setPoints(player.getPoints() + points);
                        player.setRound(player.getRound() + 1);

                        // am I the last one playing the round?
                        boolean lastOne = true;
                        for (Player p : game.getPlayers())
                            if (p.getRound() < game.getRound()) {
                                lastOne = false;
                                break;
                            }
                        if (lastOne) {
                            game.setRound(game.getRound() + 1);

                            // check if reached the max number of rounds
                            if (game.getRound() > Configuration.MAX_ROUNDS)
                                game.setStatus(Game.GAME_ENDED);
                            else {
                                boolean winner = false;
                                for (Player p : game.getPlayers())
                                    if (p.getPoints() >= Configuration.MAX_POINTS) {
                                        winner = true;
                                        break;
                                    }
                                if (winner)
                                    game.setStatus(Game.GAME_ENDED);
                            }
                        }
                    }
                }
                space.write(game);
            }
            else
                System.out.println("Couldn't get game status!");

            try {
                Thread.sleep(1500);
            }
            catch (InterruptedException ex) {

            }
        }
    }

    public static void main(String[] args) {
        String name = JOptionPane.showInputDialog(null, "Name?", "", JOptionPane.QUESTION_MESSAGE);
        GamePig gamePig = new GamePig(name);
        gamePig.run();
    }
}

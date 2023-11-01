package com.group09.playit.simulation;

import com.group09.playit.controller.GameController;
import com.group09.playit.model.Game;
import com.group09.playit.model.Player;

import java.util.Comparator;

public class Simulation {

    public static void main(String[] args) {
        Game game = new Game(1, "player1", "player2", "player3", "player4");
        GameController gameController = new GameController(game);

        for (Player player : game.getPlayers()) {
            new SimpleAgent(gameController, player);
        }

        gameController.startRound();

        game.getPlayers().sort(Comparator.comparingInt(Player::getTotalScore));
        Player losingPlayer = game.getPlayers().get(game.getPlayers().size() -1);

        System.out.println("The losing player is " + losingPlayer.getName() + " with a score of " + losingPlayer.getTotalScore());

        for (Player player : game.getPlayers()) {
            System.out.println(player.getName() + " has a score of " + player.getTotalScore());
        }
    }
}

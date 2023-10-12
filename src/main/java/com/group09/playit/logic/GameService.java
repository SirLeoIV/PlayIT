package com.group09.playit.logic;

import com.group09.playit.model.Game;
import com.group09.playit.model.Player;
import com.group09.playit.model.Round;

import java.util.ArrayList;

/**
 * The type Game service.
 */
public class GameService {

    /**
     * Check if the game is over.
     *
     * @param game current game
     * @return boolean
     */
    public static boolean isGameOver(Game game) {
        ArrayList<Player> players = game.getPlayers();
        for (Player player : players) {
            if (player.getTotalScore() >= game.getLosingScore()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Start a new round.
     *
     * @param game current game
     */
    public static void newRound(Game game) {
        game.getRounds().add(new Round(game.getPlayers()));
    }
}

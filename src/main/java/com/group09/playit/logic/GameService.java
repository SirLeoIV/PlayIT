package com.group09.playit.logic;

import com.group09.playit.model.Game;
import com.group09.playit.model.Player;
import com.group09.playit.model.Round;

import java.util.ArrayList;

public class GameService {

    public static boolean isGameOver(Game game) {
        ArrayList<Player> players = game.getPlayers();
        for (Player player : players) {
            if (player.getTotalScore() >= game.getLosingScore()) {
                return true;
            }
        }
        return false;
    }

    public static void newRound(Game game) {
        game.getRounds().add(new Round(game.getPlayers()));
    }
}

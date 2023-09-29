package com.group09.playit.logic;

import com.group09.playit.model.Round;
import com.group09.playit.model.Trick;

public class RoundService {

    public static void nextTrick(Round round) {
        Trick trick = new Trick(round.getCurrentStartingPlayer());
        round.getTricks().add(trick);
    }

    public static boolean isRoundOver(Round currentRound) {
        return currentRound.getPlayers().stream().allMatch(player -> player.getHand().getCards().isEmpty());
    }

    public static void endRound(Round round) {
        round.getPlayers().forEach(player -> {
            player.getScores().add(player.getCurrentScore());
            player.setCurrentScore(0);
        });
    }
}

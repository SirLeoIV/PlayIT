package com.group09.playit.logic;

import com.group09.playit.model.Card;
import com.group09.playit.model.Player;
import com.group09.playit.model.Round;
import com.group09.playit.model.Trick;

public class RoundService {

    public static void nextTrick(Round round) {
        Trick trick = new Trick(round.getCurrentStartingPlayer(), round);
        round.getTricks().add(trick);
    }

    public static boolean checkCurrentTrick(Round round) {
        Trick currentTrick = round.getTricks().get(round.getTricks().size()-1);
        if (TrickService.trickFull(currentTrick, round)) {
            Card winningCard = TrickService.winningCard(currentTrick);
            Player winningPlayer = round.getPlayers().stream().filter(player -> player.cardPlayed == winningCard).findFirst().get();
            winningPlayer.addScore(currentTrick.getValue());
            round.setCurrentStartingPlayer(winningPlayer);
            nextTrick(round);
            return true;
        }
        return false;
    }

}

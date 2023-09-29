package com.group09.playit.logic;

import com.group09.playit.model.Card;
import com.group09.playit.model.Player;
import com.group09.playit.model.Round;
import com.group09.playit.model.Trick;

public class RoundService {

    public static void nextTrick(Round round) {
        Trick trick = new Trick(round.getCurrentStartingPlayer());
        round.getTricks().add(trick);
    }

    public static boolean checkCurrentTrick(Round round) {
        Trick currentTrick = round.getTricks().get(round.getTricks().size()-1);
        if (TrickService.trickFull(currentTrick, round)) {
            Card winningCard = TrickService.winningCard(currentTrick, round);
            Player winningPlayer = round.getPlayers().stream().filter(player -> player.getCardPlayed() == winningCard).findFirst().get();
            winningPlayer.addScore(TrickService.getValue(currentTrick, round));
            round.setCurrentStartingPlayer(winningPlayer);
            nextTrick(round);
            return true;
        }
        return false;
    }

    public static boolean isRoundOver(Round currentRound) {
        return currentRound.getPlayers().stream().allMatch(player -> player.hand.getCards().isEmpty());
    }
}

package com.group09.playit.logic;

import com.group09.playit.model.Card;
import com.group09.playit.model.Deck;
import com.group09.playit.model.Round;
import com.group09.playit.model.Trick;
import com.group09.playit.state.RoundState;

import java.util.ArrayList;

/**
 * The type Round service.
 */
public class RoundService {

    /**
     * Start a new trick.
     *
     * @param round current round
     */
    public static void nextTrick(Round round) {
        Trick trick = new Trick(round.getCurrentStartingPlayer());
        round.getTricks().add(trick);
    }

    /**
     * Check if the round is over.
     *
     * @param currentRound current round
     * @return true if the round is over, false otherwise
     */
    public static boolean isRoundOver(Round currentRound) {
        return currentRound.getPlayers().stream().allMatch(player -> player.getHand().getCards().isEmpty());
    }

    /**
     * Check if the round is over.
     *
     * @param roundState current round state
     * @return true if the round is over, false otherwise
     */
    public static boolean isRoundOver(RoundState roundState) {
        Deck deck = new Deck(roundState.getPlayerNames().size());
        ArrayList<Card> availableCards = deck.getCards();
        for (ArrayList<Card> playedCards : roundState.getPlayedCards()) {
            availableCards.removeAll(playedCards);
        }
        return availableCards.isEmpty();
    }

    /**
     * End the round.
     * Add the current score to the total score of each player.
     * Set the current score of each player to 0.
     *
     * @param round current round
     */
    public static void endRound(Round round) {
        round.getPlayers().forEach(player -> {
            player.getScores().add(player.getCurrentScore());
            player.setCurrentScore(0);
        });
    }
}

package com.group09.playit.model;

import com.group09.playit.logic.RoundService;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Round.
 */
public class Round {

    private final ArrayList<Player> players;

    private Player currentStartingPlayer;

    private final ArrayList<Trick> tricks = new ArrayList<>();

    private boolean heartsBroken = false;

    /**
     * Create a new round.
     * Shuffle the deck and deal the cards to the players.
     * Determine the first starting player.
     * Start the first trick.
     *
     * @param players players in the round
     */
    public Round(ArrayList<Player> players) {
        this.players = players;
        Deck deck = new Deck(players.size());
        deck.shuffle();

        for (int i = 0; i < players.size(); i++) {
            int numberOfCards = deck.size() / players.size();
            List<Card> cards = deck.getCards().subList(i * numberOfCards, (i + 1) * numberOfCards);
            players.get(i).setHand(new Hand(new ArrayList<>(cards)));
        }

        currentStartingPlayer = determineFirstStartingPlayer();
        RoundService.nextTrick(this);
    }

    /**
     * Determine the first starting player.
     * The player with the 2 of clubs starts.
     * @return first starting player
     */
    private Player determineFirstStartingPlayer() {
        for (Player player : players) {
            if (player.getHand().contains(new Card(Card.Suit.CLUBS, Card.Rank.TWO))) {
                return player;
            }
        }
        return players.get(0);
    }

    /**
     * Gets current trick.
     *
     * @return the current trick
     */
    public Trick getCurrentTrick() {
        return tricks.get(tricks.size()-1);
    }

    /**
     * Is hearts broken boolean.
     *
     * @return the boolean
     */
    public boolean isHeartsBroken() {
        return heartsBroken;
    }

    /**
     * Gets players.
     *
     * @return the players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Gets current starting player.
     *
     * @return the current starting player
     */
    public Player getCurrentStartingPlayer() {
        return currentStartingPlayer;
    }

    /**
     * Gets tricks.
     *
     * @return the tricks
     */
    public ArrayList<Trick> getTricks() {
        return tricks;
    }

    /**
     * Sets current starting player.
     *
     * @param winningPlayer the winning player
     */
    public void setCurrentStartingPlayer(Player winningPlayer) {
        currentStartingPlayer = winningPlayer;
    }

    /**
     * Sets hearts broken.
     *
     * @param b the b
     */
    public void setHeartsBroken(boolean b) {
        heartsBroken = b;
    }
}

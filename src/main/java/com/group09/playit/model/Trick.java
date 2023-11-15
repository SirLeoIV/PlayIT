package com.group09.playit.model;

import java.util.ArrayList;
import java.util.Objects;

/**
 * The type Trick.
 */
public class Trick {
    
    private Player currentPlayer;
    private final ArrayList<Card> cards = new ArrayList<>();
    private Card.Suit suit;

    /**
     * Instantiates a new Trick.
     *
     * @param startingPlayer the starting player
     */
    public Trick(Player startingPlayer) {
        this.currentPlayer = startingPlayer;
    }

    public Trick(Player player, ArrayList<Card> cards, Card.Suit suit) {
        this.currentPlayer = player;
        this.cards.addAll(new ArrayList<>(cards.stream().filter(Objects::nonNull).toList()));
        this.suit = suit;
    }

    public Card.Suit getSuit() {
        return (suit != null) ? suit : cards.get(0).suit();
    }

    /**
     * Gets current player.
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public String toString() {
        StringBuilder cards = new StringBuilder();
        for (Card card : this.cards) {
            cards.append(card.toString()).append(", ");
        }
        return "[" + cards + "]";
    }

    /**
     * Gets cards.
     *
     * @return the cards
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Sets current player.
     *
     * @param player the player
     */
    public void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }
}

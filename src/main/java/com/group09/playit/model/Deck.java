package com.group09.playit.model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The type Deck.
 */
public class Deck {

    private final ArrayList<Card> cards;

    /**
     * Create a new deck of cards.
     * The deck contains 52 cards.
     * If there are 3 players, the deck does not contain the 2 of spades.
     * If there are 5 players, the deck does not contain the 2 of spades and the 2 of diamonds.
     *
     * @param numberOfPlayers number of players in the game
     */
    public Deck(int numberOfPlayers) {
        cards = new ArrayList<>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }

        // Remove 2 of spades if 3 players
        if (numberOfPlayers == 3) {
            cards.remove(new Card(Card.Suit.SPADES, Card.Rank.TWO));
        }

        // Remove 2 of spades and 2 of diamonds if 5 players
        if (numberOfPlayers == 5) {
            cards.remove(new Card(Card.Suit.SPADES, Card.Rank.TWO));
            cards.remove(new Card(Card.Suit.DIAMONDS, Card.Rank.TWO));
        }
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
     * Shuffle.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Size int.
     *
     * @return the int
     */
    public int size() {
        return cards.size();
    }
}

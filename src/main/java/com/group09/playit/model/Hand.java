package com.group09.playit.model;

import java.util.ArrayList;

/**
 * The type Hand.
 */
public class Hand {

    private final ArrayList<Card> cards;

    /**
     * Create a new hand of cards.
     * Sort the cards by suit and rank ascending.
     *
     * @param cards cards in the hand
     */
    public Hand(ArrayList<Card> cards) {
        this.cards = cards;

        // sort cards by suit and rank ascending
        cards.sort((card1, card2) -> {
            if (card1.suit() == card2.suit()) {
                return card2.rank().compareTo(card1.rank());
            }
            return card1.suit().compareTo(card2.suit());
        });
    }

    /**
     * Play card.
     *
     * @param card the card
     * @return the card
     */
    public Card playCard(Card card) {
        return cards.remove(cards.indexOf(card));
    }

    /**
     * Contains boolean.
     *
     * @param card the card
     * @return the boolean
     */
    public boolean contains(Card card) {
        return cards.contains(card);
    }

    /**
     * Contains boolean.
     *
     * @param rank the rank
     * @param suit the suit
     * @return the boolean
     */
    public boolean contains(Card.Rank rank, Card.Suit suit) {
        return contains(new Card(suit, rank));
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
}

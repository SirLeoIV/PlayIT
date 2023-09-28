package com.group09.playit.model;

import java.util.ArrayList;

public class Hand {

    final ArrayList<Card> cards;

    public Hand(ArrayList<Card> cards) {
        this.cards = cards;

        // sort cards by suit and rank ascending
        cards.sort((card1, card2) -> {
            if (card1.getSuit() == card2.getSuit()) {
                return card2.getRank().compareTo(card1.getRank());
            }
            return card1.getSuit().compareTo(card2.getSuit());
        });
    }

    public Card playCard(Card card) {
        return cards.remove(cards.indexOf(card));
    }

    public boolean contains(Card card) {
        return cards.contains(card);
    }

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

    public ArrayList<Card> getCards() {
        return cards;
    }
}

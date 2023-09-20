package com.group09.playit.model;

import java.util.ArrayList;

public class Hand {

    final ArrayList<Card> cards;

    public Hand(ArrayList<Card> cards) {
        this.cards = cards;
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

}

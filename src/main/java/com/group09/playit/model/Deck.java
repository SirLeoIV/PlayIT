package com.group09.playit.model;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    final ArrayList<Card> cards;

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

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public static void main(String[] args) {
        Deck deck = new Deck(4);
        for (Card card : deck.cards) {
            System.out.println(card);
        }
        System.out.println("Shuffling...");
        deck.shuffle();
        for (Card card : deck.cards) {
            System.out.println(card);
        }

    }

    public int size() {
        return cards.size();
    }
}

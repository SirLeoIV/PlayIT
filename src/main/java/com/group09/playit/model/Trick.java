package com.group09.playit.model;

import java.util.ArrayList;

public class Trick {
    
    private Player currentPlayer;
    private final ArrayList<Card> cards = new ArrayList<>();

    public Trick(Player startingPlayer) {
        this.currentPlayer = startingPlayer;
    }

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

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }
}

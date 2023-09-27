package com.group09.playit.model;

import java.util.ArrayList;

public class Trick {
    
    private Player currentPlayer;
    private final Round round;
    
    public Trick(Player startingPlayer, Round round) {
        this.currentPlayer = startingPlayer;
        this.round = round;
    }

    ArrayList<Card> cards = new ArrayList<>();

    public int getValue() {
        int points = 0;
        for (int i = 0; i < 4; i++) {
            points += cards.get(i).getValue();
        }
        return points;
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

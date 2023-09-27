package com.group09.playit.model;

import java.util.ArrayList;

public class Player {

    private final String name;
    public int currentScore;
    ArrayList<Integer> scores;

    public Player(String name) {
        this.name = name;
        this.currentScore = 0;
        this.scores = new ArrayList<>();
    }

    public void addScore(int score) {
        this.currentScore += score;
    }

    public Hand hand;
    public Card cardPlayed;

    public void playCard(Card card) {
        cardPlayed = hand.playCard(card);
    }

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }
}

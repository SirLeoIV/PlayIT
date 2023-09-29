package com.group09.playit.model;

import java.util.ArrayList;

public class Player {

    private final String name;
    private int currentScore;
    private ArrayList<Integer> scores;

    public Player(String name) {
        this.name = name;
        this.currentScore = 0;
        this.scores = new ArrayList<>();
    }

    public void addScore(int score) {
        this.currentScore += score;
    }

    private Hand hand;
    private Card cardPlayed;

    public void playCard(Card card) {
        cardPlayed = hand.playCard(card);
    }

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Card getCardPlayed() {
        return cardPlayed;
    }

    public void setCardPlayed(Card card) {
        cardPlayed = card;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public int getTotalScore() {
        return scores.stream().mapToInt(i -> i).sum();
    }

    public ArrayList<Integer> getScores() {
        return scores;
    }

    public void setCurrentScore(int score) {
        currentScore = score;
    }
}

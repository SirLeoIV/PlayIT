package com.group09.playit.model;

import java.util.ArrayList;

/**
 * The type Player.
 */
public class Player {

    private final String name;
    private int currentScore;
    private ArrayList<Integer> scores;

    /**
     * Instantiates a new Player.
     *
     * @param name the name
     */
    public Player(String name) {
        this.name = name;
        this.currentScore = 0;
        this.scores = new ArrayList<>();
    }

    /**
     * Add score.
     *
     * @param score the score
     */
    public void addScore(int score) {
        this.currentScore += score;
    }

    private Hand hand;
    private Card cardPlayed;

    /**
     * Play card.
     *
     * @param card the card
     */
    public void playCard(Card card) {
        cardPlayed = hand.playCard(card);
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets hand.
     *
     * @return the hand
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Sets hand.
     *
     * @param hand the hand
     */
    public void setHand(Hand hand) {
        this.hand = hand;
    }

    /**
     * Gets card played.
     *
     * @return the card played
     */
    public Card getCardPlayed() {
        return cardPlayed;
    }

    /**
     * Sets card played.
     *
     * @param card the card
     */
    public void setCardPlayed(Card card) {
        cardPlayed = card;
    }

    /**
     * Gets current score.
     *
     * @return the current score
     */
    public int getCurrentScore() {
        return currentScore;
    }

    /**
     * Gets total score.
     *
     * @return the total score
     */
    public int getTotalScore() {
        return scores.stream().mapToInt(i -> i).sum();
    }

    /**
     * Gets scores.
     *
     * @return the scores
     */
    public ArrayList<Integer> getScores() {
        return scores;
    }

    /**
     * Sets current score.
     *
     * @param score the score
     */
    public void setCurrentScore(int score) {
        currentScore = score;
    }
}

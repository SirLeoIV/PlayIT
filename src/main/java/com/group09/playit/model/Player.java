package com.group09.playit.model;

import java.util.ArrayList;

public class Player {

    String name;
    int currentScore;
    ArrayList<Integer> scores;

    public Player(String name) {
        this.name = name;
        this.currentScore = 0;
        this.scores = new ArrayList<>();
    }

    public void addScore(int score) {
        this.currentScore += score;
    }

    Hand hand;
    Card cardPlayed;

}

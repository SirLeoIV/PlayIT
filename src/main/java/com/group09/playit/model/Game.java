package com.group09.playit.model;

import java.util.ArrayList;

public class Game {

    private final ArrayList<Player> players = new ArrayList<>();

    private final int losingScore;

    private final ArrayList<Round> rounds;

    public Game(int losingScore, String... playerNames) {
        for (String playerName : playerNames) {
            players.add(new Player(playerName));
        }
        this.losingScore = losingScore;
        this.rounds = new ArrayList<>();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Round getCurrentRound() {
        return rounds.get(rounds.size() - 1);
    }

    public int getLosingScore() {
        return losingScore;
    }

    public ArrayList<Round> getRounds() {
        return rounds;
    }
}

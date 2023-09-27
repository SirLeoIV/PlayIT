package com.group09.playit.model;

import java.util.ArrayList;

public class Game {

    public ArrayList<Player> players = new ArrayList<>();

    int losingScore;

    public ArrayList<Round> rounds;

    public Game(int losingScore, String... playerNames) {
        for (String playerName : playerNames) {
            players.add(new Player(playerName));
        }
        this.losingScore = losingScore;
        this.rounds = new ArrayList<>();
        rounds.add(new Round(players));
    }

    public boolean isGameOver() {
        for (Player player : players) {
            if (player.currentScore >= losingScore) {
                return true;
            }
        }
        return false;
    }

    public Round getCurrentRound() {
        return rounds.get(rounds.size() - 1);
    }

}

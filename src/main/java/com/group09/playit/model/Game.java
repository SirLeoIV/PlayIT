package com.group09.playit.model;

import java.util.ArrayList;

/**
 * The type Game.
 */
public class Game {

    private final ArrayList<Player> players = new ArrayList<>();

    private final int losingScore;

    private final ArrayList<Round> rounds;

    /**
     * Instantiates a new Game.
     *
     * @param losingScore the losing score
     * @param playerNames the player names
     */
    public Game(int losingScore, String... playerNames) {
        for (String playerName : playerNames) {
            players.add(new Player(playerName));
        }
        this.losingScore = losingScore;
        this.rounds = new ArrayList<>();
    }

    /**
     * Gets players.
     *
     * @return the players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Gets current round.
     *
     * @return the current round
     */
    public Round getCurrentRound() {
        return rounds.get(rounds.size() - 1);
    }

    /**
     * Gets losing score.
     *
     * @return the losing score
     */
    public int getLosingScore() {
        return losingScore;
    }

    /**
     * Gets rounds.
     *
     * @return the rounds
     */
    public ArrayList<Round> getRounds() {
        return rounds;
    }
}

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

    public static void main(String[] args) {
        Game game = new Game(100, "Player 1", "Player 2", "Player 3", "Player 4");
        System.out.println(game.players.get(0).getName());
        System.out.println(game.players.get(1).getName());
        System.out.println(game.players.get(2).getName());
        System.out.println(game.players.get(3).getName());

        Round round1 = new Round(game.players);

        System.out.println("player 1 cards");
        for (Card card : game.players.get(0).hand.cards) {
            System.out.println(card);
        }
        System.out.println("-----------------");
        System.out.println("player 2 cards");
        for (Card card : game.players.get(1).hand.cards) {
            System.out.println(card);
        }

        System.out.println("-----------------");
        System.out.println("Starting player: " + round1.currentStartingPlayer.getName());
        for (Card card : round1.currentStartingPlayer.hand.cards) {
            System.out.println(card);
        }

    }
}

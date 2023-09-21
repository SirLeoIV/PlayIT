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
    }

    public boolean isGameOver() {
        for (Player player : players) {
            if (player.currentScore >= losingScore) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Game game = new Game(100, "Player 1", "Player 2", "Player 3", "Player 4");
        System.out.println(game.players.get(0).name);
        System.out.println(game.players.get(1).name);
        System.out.println(game.players.get(2).name);
        System.out.println(game.players.get(3).name);

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
        System.out.println("Starting player: " + round1.currentStartingPlayer.name);
        for (Card card : round1.currentStartingPlayer.hand.cards) {
            System.out.println(card);
        }

    }
}

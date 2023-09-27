package com.group09.playit.controller;

import com.group09.playit.model.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

public class GameController {

    Game game = new Game(100, "Player 1", "Player 2", "Player 3", "Player 4");

    public void startGame() {
        game.rounds.add(new Round(game.players));
    }

    public void makeTurn() {
        Round currentRound = game.rounds.get(game.rounds.size() - 1);
        Trick currentTrick = currentRound.tricks.get(currentRound.tricks.size() - 1);
        Player currentPlayer = currentTrick.getCurrentPlayer();
        System.out.println("Current player: " + currentPlayer.getName());
        System.out.println("Current trick: " + currentTrick);
        System.out.println("Player hand: " + currentPlayer.hand);
        ArrayList<Card> legalCards = currentTrick.legalCardsToPlay(currentPlayer);
        StringBuilder legalCardsString = new StringBuilder();
        for (int i = 0; i < legalCards.size(); i++) {
            legalCardsString.append(legalCards.get(i)).append(" (").append(i).append(")").append(", ");
        }
        System.out.println("--");
        System.out.println("Legal moves: [" + legalCardsString + "]");
        System.out.println("Enter card to play: ");
        Integer cardIndex = readInt();
        while (cardIndex == null || cardIndex < 0 || cardIndex >= legalCards.size()) {
            System.out.println("Invalid card index. Enter card to play: ");
            cardIndex = readInt();
        }
        Card cardToPlay = legalCards.get(cardIndex);
        System.out.println("Playing card: " + cardToPlay);
        currentPlayer.playCard(cardToPlay);
        currentTrick.playCard(currentPlayer, cardToPlay);
        System.out.println("-----------------");
    }

    public static void main(String[] args) {
        GameController gameController = new GameController();
        gameController.startGame();
        while (!gameController.game.isGameOver()) {
            gameController.makeTurn();
            Round currentRound = gameController.game.rounds.get(gameController.game.rounds.size() - 1);
            Trick currentTrick = currentRound.tricks.get(currentRound.tricks.size() - 1);
            if (currentRound.checkCurrentTrick()) {
                Card winningCard = currentTrick.winningCard();
                Player winningPlayer = gameController.game.players.stream().filter(player -> player.cardPlayed == winningCard).findFirst().get();
                System.out.println("Trick is over. " + winningPlayer.getName() + " won the trick with " + winningCard);
                System.out.println("They get " + currentTrick.getValue() + " points.");
                System.out.println("Scores: ");
                for (Player player : gameController.game.players) {
                    System.out.println(player.getName() + ": " + player.currentScore);
                }
                System.out.println("-----------------");
            }
        }
    }





    private static final Scanner scanner = new Scanner(System.in);

    public static String readString() {
        return readInput();
    }

    public static Integer readInt() {
        try {
            return Integer.parseInt(readAnyInput());
        } catch (NumberFormatException e) {
            return null;
        }
    }


    // tries to get input 3 times
    private static String readInput() {
        for (int i = 0; i < 2; i++) {
            System.out.print("> ");
            String input = scanner.nextLine();
            if (!input.isBlank()) return input;
        }
        System.out.print("> ");
        return scanner.nextLine();
    }

    public static String readAnyInput() {
        System.out.print("> ");
        return scanner.nextLine();
    }

    public static String readInputOptions(String[] options) {
        String result = "";
        while(!Stream.of(options).toList().contains(result)) {
            result = readAnyInput();
        }
        return result;
    }
}

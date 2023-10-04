package com.group09.playit.controller;

import com.group09.playit.logic.GameService;
import com.group09.playit.logic.RoundService;
import com.group09.playit.logic.TrickService;
import com.group09.playit.model.*;

import java.util.ArrayList;
import java.util.Scanner;

public class CommandLineGameController {

    Game game = new Game(100, "Player 1", "Player 2", "Player 3", "Player 4");

    /**
     * Start a new game.
     */
    public void startGame() {
        game.getRounds().add(new Round(game.getPlayers()));
    }

    /**
     * Make a turn in the game.
     */
    public void makeTurn() {
        Round currentRound = game.getRounds().get(game.getRounds().size() - 1);
        Trick currentTrick = currentRound.getTricks().get(currentRound.getTricks().size() - 1);
        Player currentPlayer = currentTrick.getCurrentPlayer();
        System.out.println("Current player: " + currentPlayer.getName());
        System.out.println("Current trick: " + currentTrick);
        System.out.println("Player hand: " + currentPlayer.getHand());
        ArrayList<Card> legalCards = TrickService.legalCardsToPlay(currentTrick, currentRound, currentPlayer);
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
        TrickService.playCard(currentTrick, currentRound, cardToPlay);
        System.out.println("-----------------");
    }

    public static void main(String[] args) {
        CommandLineGameController gameController = new CommandLineGameController();
        gameController.startGame();
        while (!GameService.isGameOver(gameController.game)) {
            gameController.makeTurn();
            Round currentRound = gameController.game.getRounds().get(gameController.game.getRounds().size() - 1);
            Trick currentTrick = currentRound.getTricks().get(currentRound.getTricks().size() - 1);
            if (TrickService.trickFull(currentTrick, currentRound)) {
                TrickService.endTrick(currentTrick, currentRound);
                RoundService.nextTrick(currentRound);
                Card winningCard = TrickService.winningCard(currentTrick, currentRound);
                Player winningPlayer = gameController.game.getPlayers().stream().filter(player -> player.getCardPlayed() == winningCard).findFirst().get();
                System.out.println("Trick is over. " + winningPlayer.getName() + " won the trick with " + winningCard);
                System.out.println("They get " + TrickService.getValue(currentTrick, currentRound) + " points.");
                System.out.println("Scores: ");
                for (Player player : gameController.game.getPlayers()) {
                    System.out.println(player.getName() + ": " + player.getCurrentScore());
                }
                System.out.println("-----------------");
            }
        }
        System.out.println("Game over!");
        System.out.println("Scores: ");
        for (Player player : gameController.game.getPlayers()) {
            System.out.println(player.getName() + ": " + player.getTotalScore());
        }
    }

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Reads an integer from the command line.
     * @return the integer, or null if the input was not an integer
     */
    private static Integer readInt() {
        try {
            return Integer.parseInt(readAnyInput());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Reads a line from the command line.
     * @return the line
     */
    private static String readAnyInput() {
        System.out.print("> ");
        return scanner.nextLine();
    }
}

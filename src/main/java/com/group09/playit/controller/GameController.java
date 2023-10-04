package com.group09.playit.controller;

import com.group09.playit.logic.GameService;
import com.group09.playit.logic.RoundService;
import com.group09.playit.logic.TrickService;
import com.group09.playit.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The class Game controller.
 * This class is used to control the game from the UI or other components.
 * This class functions as the interface between the UI and the game logic.
 */
public class GameController {

    /**
     * The interface Game observer.
     * This interface is used to notify observers that the game state has changed.
     */
    public interface GameObserver {
        void update();
    }

    private final List<GameObserver> observers = new ArrayList<>();

    private final Game game;

    private GameStatus gameStatus;

    /**
     * Instantiates a new Game controller and start the first round.
     *
     * @param game the game instance
     */
    public GameController(Game game) {
        this.game = game;
        GameService.newRound(game);
        gameStatus = GameStatus.WAITING_FOR_PLAYER;
    }


    /**
     * Attach new observer to the list of observers.
     *
     * @param observer the observer
     */
    public void attach(GameObserver observer) {
        observers.add(observer);
    }

    /**
     * Notify all observers that they have to update their state of the game.
     */
    public void notifyAllObservers(){
        for (GameObserver observer : observers) observer.update();
    }

    /**
     * The current player plays a card. <br>
     * The card is added to the current trick. <br>
     * If the trick is full, the trick is ended and a new trick starts. <br>
     * If the round is over, the round is ended and a new round starts. <br>
     * If the game is over, the game ends. <br>
     * The game status is updated accordingly. <br>
     *
     * @param card the card to be played
     */
    public void playCard(Card card) {
        Round round = game.getCurrentRound();
        Trick trick = round.getCurrentTrick();

        trick.getCurrentPlayer().playCard(card);
        TrickService.playCard(trick, round, card);
        System.out.println("Played card: " + card.toString());

        if (TrickService.trickFull(trick, round)) {
            TrickService.endTrick(trick, round);
            RoundService.nextTrick(round);
        }
        if (RoundService.isRoundOver(round)) {
            gameStatus = GameStatus.ROUND_OVER;
            RoundService.endRound(round);
            if (GameService.isGameOver(game)) gameStatus = GameStatus.GAME_OVER;
        } else {
            gameStatus = GameStatus.WAITING_FOR_PLAYER;
        }
        notifyAllObservers();
    }

    /**
     * Legal cards to play for the current player.
     *
     * @return the array list of legal cards to play
     */
    public ArrayList<Card> legalCardsToPlay() {
        return TrickService.legalCardsToPlay(
                game.getCurrentRound().getCurrentTrick(),
                game.getCurrentRound(),
                game.getCurrentRound().getCurrentTrick().getCurrentPlayer());
    }

    /**
     * The current player confirms that they are the active player.
     * The game status is updated accordingly.
     *
     * @param player the player
     */
    public void confirmActivePlayer(Player player) {
        if (game.getCurrentRound().getCurrentTrick().getCurrentPlayer().equals(player)) {
            gameStatus = GameStatus.ACTIVE_TURN;
        }
        notifyAllObservers();
    }

    /**
     * Starts a new round.
     */
    public void startRound() {
        GameService.newRound(game);
        gameStatus = GameStatus.WAITING_FOR_PLAYER;
        notifyAllObservers();
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    /**
     * Possible game statuses. <br>
     * ACTIVE_TURN: the current player is active and the game is waiting for the player to play a card. <br>
     * WAITING_FOR_PLAYER: the current player is not active and the game is waiting for the player to confirm that they are the active player. <br>
     * ROUND_OVER: the round is over and the game is waiting for the player to start a new round. <br>
     * GAME_OVER: the game is over. <br>
     */
    public enum GameStatus {
        ACTIVE_TURN,
        WAITING_FOR_PLAYER,
        ROUND_OVER,
        GAME_OVER
    }

}

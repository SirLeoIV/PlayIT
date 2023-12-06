package com.group09.playit.controller;

import com.group09.playit.logic.GameService;
import com.group09.playit.logic.RoundService;
import com.group09.playit.logic.TrickService;
import com.group09.playit.model.*;
import com.group09.playit.state.RoundState;

import java.util.ArrayList;
import java.util.List;

/**
 * The class Game controller.
 * This class is used to control the game from the UI or other components.
 * This class functions as the interface between the UI and the game logic.
 */
public class GameController {

    boolean debug = true;

    private void log(String message) {
        if (debug) System.out.println(message);
    }

    public Game getGame() {
        return game;
    }

    public RoundState getCurrentRoundState() {
        return currentRoundState;
    }

    public Card getLastPlayedCard() {
        return lastPlayedCard;
    }

    /**
     * The interface Game observer.
     * This interface is used to notify observers that the game state has changed.
     */
    public interface GameObserver {
        /**
         * Update.
         */
        void update();
    }

    private final List<GameObserver> observers = new ArrayList<>();

    private final Game game;

    private RoundState currentRoundState;

    private GameStatus gameStatus;

    private Card lastPlayedCard;

    /**
     * Instantiates a new Game controller and start the first round.
     *
     * @param game the game instance
     */
    public GameController(Game game) {
        this.game = game;
        GameService.newRound(game);
        currentRoundState = new RoundState(
                new ArrayList<>(game.getPlayers().stream().map(p -> p.getHand().getCards()).toList()),
                game.getPlayers().stream().map(Player::getName).toList().toArray(new String[0]));
        currentRoundState.setStartedPlayer(currentRoundState.getPlayerNames().indexOf(game.getCurrentRound().getCurrentStartingPlayer().getName()));
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

        log("player " + trick.getCurrentPlayer().getName() + " plays card " + card.toString());

        trick.getCurrentPlayer().playCard(card);
        TrickService.playCard(trick, round, card);
        TrickService.playCard(currentRoundState, card);
        if (TrickService.trickFull(currentRoundState, currentRoundState.getCurrentTrickId())) {
            TrickService.endTrick(currentRoundState);
        }
        lastPlayedCard = card;

        if (TrickService.trickFull(trick, round)) {
            TrickService.endTrick(trick, round);
            RoundService.nextTrick(round);
        }
        if (RoundService.isRoundOver(round)) {
            gameStatus = GameStatus.ROUND_OVER;
            log("round over");
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
     * Gets the current player.
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return game.getCurrentRound().getCurrentTrick().getCurrentPlayer();
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
        currentRoundState = new RoundState(
                new ArrayList<>(game.getPlayers().stream().map(p -> p.getHand().getCards()).toList()),
                game.getPlayers().stream().map(Player::getName).toList().toArray(new String[0]));
        currentRoundState.setStartedPlayer(currentRoundState.getPlayerNames().indexOf(game.getCurrentRound().getCurrentStartingPlayer().getName()));
        lastPlayedCard = null;
        log("new round started");
        notifyAllObservers();
    }

    /**
     * Gets game status.
     *
     * @return the game status
     */
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
        /**
         * Active turn game status.
         */
        ACTIVE_TURN,
        /**
         * Waiting for player game status.
         */
        WAITING_FOR_PLAYER,
        /**
         * Round over game status.
         */
        ROUND_OVER,
        /**
         * Game over game status.
         */
        GAME_OVER
    }

}

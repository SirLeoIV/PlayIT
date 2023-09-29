package com.group09.playit.controller;

import com.group09.playit.logic.GameService;
import com.group09.playit.logic.RoundService;
import com.group09.playit.logic.TrickService;
import com.group09.playit.model.*;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    public interface GameObserver {
        void update();
    }

    private final List<GameObserver> observers = new ArrayList<>();

    private Game game;

    private GameStatus gameStatus;

    public GameController(Game game) {
        this.game = game;
        GameService.newRound(game);
        gameStatus = GameStatus.WAITING_FOR_PLAYER;
    }

    public void attach(GameObserver observer) {
        observers.add(observer);
    }

    public void notifyAllObservers(){
        for (GameObserver observer : observers) observer.update();
    }

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
        if (GameService.isGameOver(game)) {
            gameStatus = GameStatus.GAME_OVER;
        } else if (RoundService.isRoundOver(round)) {
            gameStatus = GameStatus.ROUND_OVER;
        } else {
            gameStatus = GameStatus.WAITING_FOR_PLAYER;
        }
        notifyAllObservers();
    }

    public ArrayList<Card> legalCardsToPlay() {
        return TrickService.legalCardsToPlay(
                game.getCurrentRound().getCurrentTrick(),
                game.getCurrentRound(),
                game.getCurrentRound().getCurrentTrick().getCurrentPlayer());
    }

    public void confirmActivePlayer(Player player) {
        if (game.getCurrentRound().getCurrentTrick().getCurrentPlayer().equals(player)) {
            gameStatus = GameStatus.ACTIVE_TURN;
        }
        notifyAllObservers();
    }

    public void startRound() {
        GameService.newRound(game);
        gameStatus = GameStatus.WAITING_FOR_PLAYER;
        notifyAllObservers();
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public enum GameStatus {
        ACTIVE_TURN,
        WAITING_FOR_PLAYER,
        ROUND_OVER,
        GAME_OVER
    }

}

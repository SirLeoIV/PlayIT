package com.group09.playit.controller;

import com.group09.playit.logic.RoundService;
import com.group09.playit.logic.TrickService;
import com.group09.playit.model.Card;
import com.group09.playit.model.Game;
import com.group09.playit.model.Player;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    private final List<GameObserver> observers = new ArrayList<>();

    private Game game;

    public GameController(Game game) {
        this.game = game;
    }

    public void attach(GameObserver observer) {
        observers.add(observer);
    }

    public void notifyAllObservers(){
        for (GameObserver observer : observers) observer.update();
    }

    public void playCard(Card card) {
        game.getCurrentRound().getCurrentTrick().getCurrentPlayer().playCard(card);
        TrickService.playCard(game.getCurrentRound().getCurrentTrick(), game.getCurrentRound(), card);
        System.out.println("Played card: " + card.toString());
        if (RoundService.checkCurrentTrick(game.getCurrentRound())) {
            for (Player player : game.getCurrentRound().getPlayers()) {
                player.setCardPlayed(null);
            }
        }
        notifyAllObservers();
    }

    public ArrayList<Card> legalCardsToPlay() {
        return TrickService.legalCardsToPlay(
                game.getCurrentRound().getCurrentTrick(),
                game.getCurrentRound(),
                game.getCurrentRound().getCurrentTrick().getCurrentPlayer());
    }

    public interface GameObserver {
        void update();
    }

}

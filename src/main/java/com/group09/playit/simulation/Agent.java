package com.group09.playit.simulation;

import com.group09.playit.controller.GameController;
import com.group09.playit.model.Player;

public abstract class Agent implements GameController.GameObserver {

    protected final GameController gameController;

    protected final Player player;

    public Agent(GameController gameController, Player player) {
        this.gameController = gameController;
        this.player = player;
        gameController.attach(this);
    }

    public void update() {
        if (gameController.getCurrentPlayer() == player) {
            if (gameController.getGameStatus() == GameController.GameStatus.WAITING_FOR_PLAYER
                    || gameController.getGameStatus() == GameController.GameStatus.ACTIVE_TURN) play();
        }
    };

    abstract public void play();
}

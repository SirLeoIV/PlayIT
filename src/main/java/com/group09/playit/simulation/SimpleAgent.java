package com.group09.playit.simulation;

import com.group09.playit.controller.GameController;
import com.group09.playit.model.Player;

public class SimpleAgent extends Agent {

    public SimpleAgent(GameController gameController, Player player) {
        super(gameController, player);
    }

    @Override
    public void play() {
        gameController.playCard(gameController.legalCardsToPlay().get(0));
    }
}

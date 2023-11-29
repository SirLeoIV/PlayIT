package com.group09.playit.simulation;

import com.group09.playit.controller.RoundController;
import com.group09.playit.model.Card;

import java.util.ArrayList;
import java.util.Collections;

public class RandomAgent implements Agent {

    RoundController roundController;
    int agentId;

    public RandomAgent(int agentId, RoundController roundController) {
        this.agentId = agentId;
        this.roundController = roundController;
    }

    @Override
    public void playCard() throws NoCardsAvailableException {
        try {
            ArrayList<Card> legalCards = new ArrayList<>(roundController.legalCardsToPlay());
            Collections.shuffle(legalCards);
            roundController.playCard(legalCards.get(0));
        } catch (Exception e) {
            // System.out.println("No cards available");
             throw new NoCardsAvailableException();
        }
    }

    @Override
    public int getAgentId() {
        return agentId;
    }
}

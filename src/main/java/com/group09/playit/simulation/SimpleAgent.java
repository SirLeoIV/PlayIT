package com.group09.playit.simulation;

import com.group09.playit.controller.RoundController;

public class SimpleAgent implements Agent {

    RoundController roundController;
    int agentId;

    public SimpleAgent(int agentId, RoundController roundController) {
        this.agentId = agentId;
        this.roundController = roundController;
    }

    /**
     * This agent plays the first card that is legal to play.
     * @throws NoCardsAvailableException the no cards available exception
     */
    @Override
    public void playCard() throws NoCardsAvailableException {
        try {
            roundController.playCard(roundController.legalCardsToPlay().get(0));
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

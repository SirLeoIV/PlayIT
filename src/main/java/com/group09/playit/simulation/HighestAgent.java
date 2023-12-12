package com.group09.playit.simulation;

import com.group09.playit.controller.RoundController;
import com.group09.playit.model.Card;

import java.util.ArrayList;

public class HighestAgent implements Agent {

    RoundController roundController;
    int agentId;

    public HighestAgent(int agentId, RoundController roundController) {
        this.agentId = agentId;
        this.roundController = roundController;
    }

    /**
     * This agent plays the highest card that is legal to play.
     * @throws NoCardsAvailableException the no cards available exception
     */
    @Override
    public void playCard() throws NoCardsAvailableException {
        try {
            ArrayList<Card> legalCards = new ArrayList<>(roundController.legalCardsToPlay());
            if (!legalCards.isEmpty()) {
                Card cardToPlay = maxCard(legalCards);
                roundController.playCard(cardToPlay);
            }
        } catch (Exception e) {
            // System.out.println("No cards available");
            throw new NoCardsAvailableException();
        }
    }

    @Override
    public int getAgentId() {
        return agentId;
    }

    /**
     * Returns the card with the lowest value.
     * @param legalCards the legal cards
     * @return the card with the lowest value
     */
    public Card maxCard(ArrayList<Card> legalCards) {
        Card maxCard = legalCards.get(0);
        for (int i = 1; i < legalCards.size(); i++) {
            if (legalCards.get(i).higherThen(maxCard)) {
                maxCard = legalCards.get(i);
            }
        }
        return maxCard;
    }

}

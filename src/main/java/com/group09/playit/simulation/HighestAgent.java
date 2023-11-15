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

    @Override
    public void playCard() {
        try {
            ArrayList<Card> legalCards = new ArrayList<>(roundController.legalCardsToPlay());
            if (!legalCards.isEmpty()) {
                Card cardToPlay = maxCard(legalCards);
                roundController.playCard(cardToPlay);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getAgentId() {
        return agentId;
    }

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

package com.group09.playit.simulation;

import com.group09.playit.controller.RoundController;
import com.group09.playit.model.Card;
import com.group09.playit.model.Trick;
import com.group09.playit.state.RoundState;

import java.util.ArrayList;
import java.util.Optional;

public class SmartAgent implements Agent {

    RoundController roundController;
    int agentId;

    public SmartAgent(int agentId, RoundController roundController) {
        this.agentId = agentId;
        this.roundController = roundController;
    }

    @Override
    public void playCard() throws NoCardsAvailableException {
        try {
            RoundState roundState = roundController.getRoundState();
            Trick currentTrick = roundState.getTrickModel(roundState.getCurrentTrickId());

            if (currentTrick.getCards().isEmpty()) {
                roundController.playCard(minCard(roundController.legalCardsToPlay()));
                return;
            }

            if (canPlaySameSuit(roundController.legalCardsToPlay(), currentTrick.getSuit())) {
                Optional<Card> highestCardToLoseTheTrick = maxCardLowerThanGivenCard(roundController.legalCardsToPlay(), maxCard(currentTrick.getCards()));
                if (highestCardToLoseTheTrick.isPresent()) {
                    roundController.playCard(highestCardToLoseTheTrick.get());
                    return;
                }

                roundController.playCard(maxCard(roundController.legalCardsToPlay()));
                return;
            }

            roundController.playCard(maxCard(maxValueCards(roundController.legalCardsToPlay())));
        } catch (Exception e) {
            // System.out.println("No cards available");
            throw new NoCardsAvailableException();
        }
    }

    @Override
    public int getAgentId() {
        return agentId;
    }


    private boolean canPlaySameSuit(ArrayList<Card> legalCards, Card.Suit suit) {
        return legalCards.stream().anyMatch(card -> card.suit() == suit);
    }

    public Card minCard(ArrayList<Card> legalCards) {
        Card minCard = legalCards.get(0);
        for (int i = 1; i < legalCards.size(); i++) {
            if (!legalCards.get(i).higherThen(minCard)) {
                minCard = legalCards.get(i);
            }
        }
        return minCard;
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

    private Optional<Card> maxCardLowerThanGivenCard(ArrayList<Card> legalCards, Card card) {
        Optional<Card> maxCard = Optional.empty();
        for (Card legalCard : legalCards) {
            if (!legalCard.higherThen(card) && (maxCard.isEmpty() || legalCard.higherThen(maxCard.get()))) {
                maxCard = Optional.of(legalCard);
            }
        }
        return maxCard;
    }

    private ArrayList<Card> maxValueCards(ArrayList<Card> legalCards) {
        int maxValue = legalCards.stream().map(Card::getValue).max(Integer::compareTo).orElseThrow();
        return new ArrayList<>(legalCards.stream().filter(card -> card.getValue() == maxValue).toList());
    }

}

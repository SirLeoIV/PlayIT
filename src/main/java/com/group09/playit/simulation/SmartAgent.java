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

    /**
     * Use some rules to determine which card to play.
     * <p>
     *     + If the agent is the first to play a card in the trick, play the lowest card. (To not win the trick)
     *     <br>
     *     + If the agent can play a card of the same suit as the suit of the first card in the trick play the highest card that is lower than the highest card in the trick. (To lose the trick)
     *     <br>
     *     + If the agent can't play a card of the same suit as the suit of the first card in the trick, play the highest card. (To give as many points as possible to the other players)
     * @throws NoCardsAvailableException
     */
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
                Optional<Card> highestCardToLoseTheTrick = maxCardLowerThanGivenCard(
                        roundController.legalCardsToPlay(), maxCard(currentTrick.getCards()));
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

    /**
     * Returns whether the agent can play a card of the same suit as the given suit.
     *
     * @param legalCards the legal cards
     * @param suit       the suit
     * @return the boolean
     */
    private boolean canPlaySameSuit(ArrayList<Card> legalCards, Card.Suit suit) {
        return legalCards.stream().anyMatch(card -> card.suit() == suit);
    }

    /**
     * Returns the lowest card in the list of legal cards.
     *
     * @param legalCards the legal cards
     * @return the card
     */
    public Card minCard(ArrayList<Card> legalCards) {
        Card minCard = legalCards.get(0);
        for (int i = 1; i < legalCards.size(); i++) {
            if (!legalCards.get(i).higherThen(minCard)) {
                minCard = legalCards.get(i);
            }
        }
        return minCard;
    }

    /**
     * Returns the highest card in the list of legal cards.
     *
     * @param legalCards the legal cards
     * @return the card
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

    /**
     * Returns the highest card in the list of legal cards that is still lower than the given card.
     *
     * @param legalCards the legal cards
     * @param card       the card
     * @return the optional card
     */
    private Optional<Card> maxCardLowerThanGivenCard(ArrayList<Card> legalCards, Card card) {
        Optional<Card> maxCard = Optional.empty();
        for (Card legalCard : legalCards) {
            if (!legalCard.higherThen(card) && (maxCard.isEmpty() || legalCard.higherThen(maxCard.get()))) {
                maxCard = Optional.of(legalCard);
            }
        }
        return maxCard;
    }

    /**
     * Returns a list of cards with the highest value in terms of points to win/lose the round.
     *
     * @param legalCards the legal cards
     * @return the array list
     */
    private ArrayList<Card> maxValueCards(ArrayList<Card> legalCards) {
        int maxValue = legalCards.stream().map(Card::getValue).max(Integer::compareTo).orElseThrow();
        return new ArrayList<>(legalCards.stream().filter(card -> card.getValue() == maxValue).toList());
    }

}

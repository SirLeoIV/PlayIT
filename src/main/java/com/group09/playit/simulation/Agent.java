package com.group09.playit.simulation;

/**
 * The interface Agent.
 * This interface is used to create agents that can play cards in a round.
 * The agent can be used to simulate rounds from any given RoundState.
 */
public interface Agent {

    /**
     * Play a card.
     * Usually this method will be called by the RoundController.
     * The agent subscribes to the RoundController and will be notified when it is their turn to play a card.
     * The agent can then use the RoundController to play a card.
     * @throws NoCardsAvailableException the no cards available exception
     */
    void playCard() throws NoCardsAvailableException;

    /**
     * Gets agent id.
     * Every agent has an id that corresponds to the id of the player that the agent represents.
     * @return the agent id
     */
    int getAgentId();
}

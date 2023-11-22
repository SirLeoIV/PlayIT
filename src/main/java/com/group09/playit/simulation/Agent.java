package com.group09.playit.simulation;

public interface Agent {

    void playCard() throws NoCardsAvailableException;
    int getAgentId();
}

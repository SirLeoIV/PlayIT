package com.group09.playit.simulation;

import com.group09.playit.controller.RoundController;
import com.group09.playit.logic.RoundService;
import com.group09.playit.state.RoundState;

public class Simulation {

    private final RoundState roundState;
    Agent agentType;

    public Simulation(RoundState roundState, Agent agentType) {
        this.roundState = roundState;
        this.agentType = agentType;
    }


    public RoundState getRoundState() {
        return roundState;
    }

    public void simulate() throws NoCardsAvailableException {
        RoundController roundController = new RoundController(roundState);
        for (int i = 0; i < roundState.getPlayerNames().size(); i++) {
            if(agentType instanceof SimpleAgent) {
                roundController.addAgent(new SimpleAgent(i, roundController));
            } else if (agentType instanceof RandomAgent) {
                roundController.addAgent(new RandomAgent(i, roundController));
            } else if (agentType instanceof HighestAgent) {
                roundController.addAgent(new HighestAgent(i, roundController));
            } else if (agentType instanceof LowestAgent) {
                roundController.addAgent(new LowestAgent(i, roundController));
            } else if (agentType instanceof MCTSAgent) {
                roundController.addAgent(new MCTSAgent(i, roundController));
            } else if (agentType instanceof SmartAgent) {
                roundController.addAgent(new SmartAgent(i, roundController));
            } else {
                throw new IllegalArgumentException("Agent type not supported");
            }
        }
        while (!RoundService.isRoundOver(roundState)) {
            roundController.nextAction();
        }
    }

    public void simulateWithMCTS() throws NoCardsAvailableException{
        RoundController roundController = new RoundController(roundState);
        roundController.addAgent(new MCTSAgent(0, roundController));
        for (int i = 1; i < roundState.getPlayerNames().size(); i++) {
            if(agentType instanceof SimpleAgent) {
                roundController.addAgent(new SimpleAgent(i, roundController));
            } else if (agentType instanceof RandomAgent) {
                roundController.addAgent(new RandomAgent(i, roundController));
            } else if (agentType instanceof HighestAgent) {
                roundController.addAgent(new HighestAgent(i, roundController));
            } else if (agentType instanceof LowestAgent) {
                roundController.addAgent(new LowestAgent(i, roundController));
            } else if (agentType instanceof MCTSAgent) {
                roundController.addAgent(new MCTSAgent(i, roundController));
            } else if (agentType instanceof SmartAgent) {
                roundController.addAgent(new SmartAgent(i, roundController));
            } else {
                throw new IllegalArgumentException("Agent type not supported");
            }
        }
        while (!RoundService.isRoundOver(roundState)) {
            roundController.nextAction();
        }
    }
}

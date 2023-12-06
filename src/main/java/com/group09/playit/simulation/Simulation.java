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

    public void simulateAgentAgainstAnotherAgent(Agent agentType1, Agent agentType2) throws NoCardsAvailableException {
        RoundController roundController = new RoundController(roundState);
        roundController.addAgent(getAgentOfType(agentType1, 0, roundController));

        for (int i = 1; i < roundState.getPlayerNames().size(); i++) {
            roundController.addAgent(getAgentOfType(agentType2, i, roundController));
        }

        while (!RoundService.isRoundOver(roundState)) {
            roundController.nextAction();
        }
    }

    private Agent getAgentOfType(Agent agentType, int agentId, RoundController roundController) {
        if (agentType instanceof SimpleAgent) {
            return new SimpleAgent(agentId, roundController);
        } else if (agentType instanceof RandomAgent) {
            return new RandomAgent(agentId, roundController);
        } else if (agentType instanceof HighestAgent) {
            return new HighestAgent(agentId, roundController);
        } else if (agentType instanceof LowestAgent) {
            return new LowestAgent(agentId, roundController);
        } else if (agentType instanceof MCTSAgent) {
            return new MCTSAgent(agentId, roundController);
        } else if (agentType instanceof SmartAgent) {
            return new SmartAgent(agentId, roundController);
        } else {
            throw new IllegalArgumentException("Agent type not supported");
        }
    }

    public void simulate() throws NoCardsAvailableException {
        RoundController roundController = new RoundController(roundState);
        for (int i = 0; i < roundState.getPlayerNames().size(); i++) {
            roundController.addAgent(getAgentOfType(agentType, i, roundController));
        }
        while (!RoundService.isRoundOver(roundState)) {
            roundController.nextAction();
        }
    }

    public void simulateWithMCTS() throws NoCardsAvailableException{
        RoundController roundController = new RoundController(roundState);
        roundController.addAgent(new MCTSAgent(0, roundController));
        for (int i = 1; i < roundState.getPlayerNames().size(); i++) {
            roundController.addAgent(getAgentOfType(agentType, i, roundController));
        }
        while (!RoundService.isRoundOver(roundState)) {
            roundController.nextAction();
        }
    }
}

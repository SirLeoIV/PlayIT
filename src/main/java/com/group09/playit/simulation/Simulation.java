package com.group09.playit.simulation;

import com.group09.playit.controller.RoundController;
import com.group09.playit.logic.RoundService;
import com.group09.playit.state.RoundState;

/**
 * The class Simulation.
 * This class is used to run a round automatically with agents attached for each player.
 * This class functions as the interface between the agents, and the game logic.
 * It can be used to simulate rounds from any given RoundState.
 */
public class Simulation {

    private final RoundState roundState;
    Agent agentType;

    public Simulation(RoundState roundState, Agent agentType) {
        this.roundState = roundState;
        this.agentType = agentType;
    }

    /**
     * Gets round state.
     *
     * @return the round state
     */
    public RoundState getRoundState() {
        return roundState;
    }

    /**
     * Simulate a round with the given agents.
     * The agents will play cards until the round is over.
     * The roundState will be updated after every action.
     *
     * @param agentType1 the agent type 1
     * @param agentType2 the agent type 2
     * @throws NoCardsAvailableException
     */
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

    /**
     * Creates an agent of the given type.
     *
     * @param agentType       the agent type
     * @param agentId         the agent id
     * @param roundController the round controller
     * @return the agent of the given type
     */
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

    /**
     * Simulate a round with the agent type specified in the constructor.
     * The agents will play cards until the round is over.
     * The roundState will be updated after every action.
     *
     * @throws NoCardsAvailableException
     */
    public void simulate() throws NoCardsAvailableException {
        RoundController roundController = new RoundController(roundState);
        for (int i = 0; i < roundState.getPlayerNames().size(); i++) {
            roundController.addAgent(getAgentOfType(agentType, i, roundController));
        }
        while (!RoundService.isRoundOver(roundState)) {
            roundController.nextAction();
        }
    }
}

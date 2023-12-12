package com.group09.playit.simulation;

import com.group09.playit.controller.RoundController;
import com.group09.playit.logic.TrickService;
import com.group09.playit.model.Card;
import com.group09.playit.monteCarlo.MCTS;
import com.group09.playit.monteCarlo.Node;
import com.group09.playit.state.NodeState;
import com.group09.playit.state.RoundState;

public class MCTSAgent implements Agent{

    RoundController roundController;
    int agentId;
    public static int maxDepth = 55;
    public static double time = 3;

    public MCTSAgent(int agentId, RoundController roundController) {
        this.agentId = agentId;
        this.roundController = roundController;
    }

    /**
     * This agent plays a card that is determined by the MCTS algorithm.
     * @throws NoCardsAvailableException the no cards available exception
     */
    @Override
    public void playCard() throws NoCardsAvailableException {
        if (TrickService.legalCardsToPlay(roundController.getRoundState()).size() <= 1) {
            roundController.playCard(TrickService.legalCardsToPlay(roundController.getRoundState()).get(0));
            return;
        }
        try {
            RoundState roundState = roundController.getRoundState();
            Node root = new Node(
                    NodeState.createRoundStateBasedOn(
                            roundState.getPlayedCards(),
                            roundState.getPlayerHands().get(0),
                            roundState.getWinningPlayerIds(),
                            roundState.getPlayerNames(),
                            roundState.getStartedPlayerId(),
                            agentId
                    ),
                    null,
                    new SmartAgent(0, null), maxDepth, agentId); // Change agent that is used for rollout here
            MCTS mcts = new MCTS(root);
            Card card = mcts.traverse(time);
            roundController.playCard(card);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getAgentId() {
        return agentId;
    }
}

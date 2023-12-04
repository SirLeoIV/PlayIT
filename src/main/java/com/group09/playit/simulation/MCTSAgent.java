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

    public MCTSAgent(int agentId, RoundController roundController) {
        this.agentId = agentId;
        this.roundController = roundController;
    }

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
                            roundState.getStartedPlayerId()
                    ),
                    null,
                    new RandomAgent(0, null));
            System.out.println("Current trick: " + roundState.trickToString(roundState.getCurrentTrickId()));
            int time = 3;
            MCTS mcts = new MCTS(root);
            Card card = mcts.traverse(time);
            roundController.playCard(card);
            System.out.println("Card to play:");
            System.out.println(card);
            System.out.println("--------------------");
        } catch (Exception e) {
            // System.out.println("No cards available");
            e.printStackTrace();
        }
    }

    @Override
    public int getAgentId() {
        return agentId;
    }
}

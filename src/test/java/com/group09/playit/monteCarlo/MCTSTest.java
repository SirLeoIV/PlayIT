package com.group09.playit.monteCarlo;

import com.group09.playit.logic.DeckService;
import com.group09.playit.model.Card;
import com.group09.playit.simulation.NoCardsAvailableException;
import com.group09.playit.simulation.RandomAgent;
import com.group09.playit.simulation.SimpleAgent;
import com.group09.playit.simulation.Simulation;
import com.group09.playit.state.NodeState;
import com.group09.playit.state.RoundState;
import org.junit.jupiter.api.Test;

class MCTSTest {

    @Test
    void testWithDifferentScores() throws NoCardsAvailableException {
        for (int i = 0; i<20; i++) {

            String[] playerNames = {"player0", "player1", "player2", "player3"};
            RoundState roundState = new RoundState(DeckService.dealCards(playerNames.length), playerNames);
            while (!roundState.getPlayerHands().get(0).contains(new Card(Card.Suit.SPADES, Card.Rank.QUEEN))) {
                // System.out.println("try again");
                roundState = new RoundState(DeckService.dealCards(playerNames.length), playerNames);
            }
            Simulation simulation = new Simulation(roundState, new RandomAgent(0, null));
            simulation.simulate();

            roundState = simulation
                    .getRoundState()
                    .getRoundStateUpToGivenCardPlayed(new Card(Card.Suit.SPADES, Card.Rank.QUEEN), false);
            System.out.println("Current hand: " + roundState.getPlayerHands().get(0));
            System.out.println("Current trick: " + roundState.trickToString(roundState.getCurrentTrickId()));
            Node root = new Node(
                    NodeState.createRoundStateBasedOn(
                            roundState.getPlayedCards(),
                            roundState.getPlayerHands().get(0),
                            roundState.getWinningPlayerIds(),
                            roundState.getPlayerNames(),
                            roundState.getStartedPlayerId()
                    ),
                    null,
                    new SimpleAgent(0, null));
            // root.rollout();

            int time = 6;
            System.out.println("Run for " + time + " seconds: ");
            MCTS mcst = new MCTS(root);
            Card card = mcst.traverse(time);
            for (Node child : root.getChildren()) {
                System.out.println(child.getCardPlayed() + " " + child.averageScore());
            }
            System.out.println("Card to play:");
            System.out.println(card);
            System.out.println("--------------------");
        }

    }

}
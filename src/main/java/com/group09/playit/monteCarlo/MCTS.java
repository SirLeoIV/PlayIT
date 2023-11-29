package com.group09.playit.monteCarlo;

import com.group09.playit.logic.DeckService;
import com.group09.playit.model.Card;
import com.group09.playit.simulation.NoCardsAvailableException;
import com.group09.playit.simulation.RandomAgent;
import com.group09.playit.simulation.Simulation;
import com.group09.playit.state.NodeState;
import com.group09.playit.state.RoundState;

import java.util.ArrayList;
import java.util.Comparator;

public class MCTS {

    // constructer that takes in a state for the root node
    // initialize children based on legal moves

    // traversal
    // selection
    // rollout --> type of agent
    // backpropagation

    public static int nodeIds = 0;

    boolean debug = true;

    Node root;

    private void log(String message) {
        if (debug) System.out.println(message);
    }

    public MCTS(Node root){
        this.root = root;
        this.root.expand();
    }

    public Card traverse(int seconds) {
        nodeIds = 0;
        Node tree = root;
        long startTime = System.currentTimeMillis();
        long runtime = seconds * 1000;
        if (root.getChildren().size() == 1) return root.getChildren().get(0).getCardPlayed();

        int iterations = 0;
        while(System.currentTimeMillis() < startTime + runtime || root.getChildren().isEmpty()) {
            Node current = getNextLeaf(tree);
            if (current.getNumberVisits() == 0 || current.getState().getPlayerHands().stream().allMatch(ArrayList::isEmpty)) {
                int score = current.rollout();
                current.backpropagate(score);
            } else {
                current.expand();
                int score = current.getChildren().get(0).rollout();
                if (score >= 0) current.getChildren().get(0).backpropagate(score);
            }
            iterations++;
        }

        log("iterations: " + iterations);
        log("number of nodes: " + nodeIds);
        root.getChildren().sort(Comparator.comparingDouble(Node::averageScore));

        return root.getChildren().get(0).getCardPlayed();
    }

    public Node getNextLeaf(Node node) {
        if (node.getChildren().isEmpty()) {
            return node;
        } else {
            return getNextLeaf(node.select());
        }
    }

    public static void main(String[] args) throws NoCardsAvailableException {
        String[] playerNames = {"player0", "player1", "player2", "player3"};
        RoundState roundState = new RoundState(DeckService.dealCards(playerNames.length), playerNames);
        Simulation simulation = new Simulation(roundState, new RandomAgent(0, null));
        simulation.simulate();

        // get round state up to the first card of player 0
        roundState = simulation
                .getRoundState()
                .getRoundStateUpToGivenCardPlayed(
                        simulation.getRoundState()
                                .getPlayedCards()
                                .get(0)
                                .get(0),
                        false);
        RoundState roundState2 = roundState.clone();
        System.out.println("Full hand: " + roundState.getPlayerHands().get(0));
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
        // root.rollout();

        System.out.println("Run for 10 seconds: ");
        MCTS mcst = new MCTS(root);
        Card card = mcst.traverse(10);
        for (Node child : root.getChildren()) {
            System.out.println(child.getCardPlayed() + " " + child.averageScore());
        }
        System.out.println("Card to play:");
        System.out.println(card);
        System.out.println("--------------------");

        System.out.println("Run for 3 second: ");
        Node root2 = new Node(
                NodeState.createRoundStateBasedOn(
                        roundState2.getPlayedCards(),
                        roundState2.getPlayerHands().get(0),
                        roundState2.getWinningPlayerIds(),
                        roundState2.getPlayerNames(),
                        roundState2.getStartedPlayerId()
                ), null, new RandomAgent(0, null));
        MCTS mcst2 = new MCTS(root2);
        Card card2 = mcst2.traverse(3);
        for (Node child : root.getChildren()) {
            System.out.println(child.getCardPlayed() + " " + child.averageScore());
        }
        System.out.println("Card to play:");
        System.out.println(card2);
        System.out.println("--------------------");
    }
}

package com.group09.playit.monteCarlo;

import com.group09.playit.logic.DeckService;
import com.group09.playit.model.Card;
import com.group09.playit.simulation.SimpleAgent;
import com.group09.playit.simulation.Simulation;
import com.group09.playit.state.RoundState;

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

    public Card traverse() {
        Node tree = root;
        long startTime = System.currentTimeMillis();
        long runtime = 100000 * 1000;

        for (int i = 0; i < 100; i++) {
            log("iteration: " + i);
            Node current = getNextLeaf(tree);
            log("current node: " + current);
            log("\tall tricks: ");
            for (int j = 0; j <= current.getState().getWinningPlayerIds().size(); j++) {
                log("\t\ttrick " + j + ": " + current.getState().trickToString(j));
            }
            if (current.getNumberVisits() == 0) {
                int score = current.rollout();
                current.backpropagate(score);
            } else {
                current.expand();
                int score = current.getChildren().get(0).rollout();
                current.getChildren().get(0).backpropagate(score);
            }
        }
        while(System.currentTimeMillis() < startTime + runtime || root.getChildren().isEmpty()) {
        }

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

    public static void main(String[] args) {
        String[] playerNames = {"player0", "player1", "player2", "player3"};
        RoundState roundState = new RoundState(DeckService.dealCards(playerNames.length), playerNames);
        Simulation simulation = new Simulation(roundState, new SimpleAgent(0, null));
        simulation.simulate();

        // get round state up to the first card of player 0
        roundState = simulation.getRoundState().getRoundStateUpToGivenCardPlayed(simulation.getRoundState().getPlayedCards().get(0).get(0), false);
        System.out.println("Full hand: " + roundState.getPlayerHands().get(0));
        Node root = new Node(roundState, null, new SimpleAgent(0, null));
        // root.rollout();
        MCTS mcst = new MCTS(root);
        Card card = mcst.traverse();
        System.out.println(card);
    }
}

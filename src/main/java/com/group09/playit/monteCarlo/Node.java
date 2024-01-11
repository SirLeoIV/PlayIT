package com.group09.playit.monteCarlo;

import com.group09.playit.controller.RoundController;
import com.group09.playit.logic.TrickService;
import com.group09.playit.model.Card;
import com.group09.playit.simulation.Agent;
import com.group09.playit.simulation.NoCardsAvailableException;
import com.group09.playit.simulation.Simulation;
import com.group09.playit.state.RoundState;

import java.util.ArrayList;

/**
 * This class represents a node in the MCTS tree
 * It contains all the information needed to evaluate the node and run the MCTS algorithm.
 */
public class Node {

    boolean debug = false;

    private void log(String message) {
        if (debug) {
            System.out.println(message);
        }
    }

    private RoundState state;
    private int totalScore = 0;
    private int numberVisits = 0;
    private Node parent;
    private final Card cardPlayed;
    private final Agent agentType;

    private int playerId = 0;

    private final int id;

    private final int depthLeft;

    private final ArrayList<Node> children = new ArrayList<>();

    public static double EXPLORATION_CONSTANT = 2.0;

    /**
     * Constructor for the root node
     * @param state state of the node
     * @param agentType agent type of the node
     * @param depthLeft depth left of the node
     * @param playerId player id of the node
     */
    public Node(RoundState state, Card cardPlayed, Agent agentType, int depthLeft, int playerId) {
        this.state = state;
        this.cardPlayed = cardPlayed;
        this.agentType = agentType;
        this.id = MCTS.nodeIds++;
        this.depthLeft = depthLeft;
        this.playerId = playerId;
    }

    /**
     * Constructor for all the children nodes
     * @param state state of the node
     * @param agentType agent type of the node
     * @param depthLeft depth left of the node
     * @param playerId player id of the node
     */
    public Node(RoundState state, Node parent, Card cardPlayed, Agent agentType, int depthLeft, int playerId) {
        this.state = state;
        this.parent = parent;
        this.cardPlayed = cardPlayed;
        this.agentType = agentType;
        this.id = MCTS.nodeIds++;
        this.depthLeft = depthLeft;
        this.playerId = playerId;
    }

    /**
     * Initializes all children of the node
     * If the node is a leaf node, we initialize the children based on the possible cards to play
     *
     * @param state state of the node
     */
    private void initializeChildren(RoundState state) {
        for (Card card : TrickService.legalCardsToPlay(state)) {
            log("Initializing child for card " + card.toString());
            RoundState childState = state.clone();

            RoundController childController = new RoundController(childState);
            childController.playCard(card);

            if (TrickService.trickFull(childState, childState.getCurrentTrickId())) {
                TrickService.endTrick(childState);
            }

            Node child = new Node(childState, this, card, agentType, depthLeft - 1, playerId);
            children.add(child);
        }
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getNumberVisits() {
        return numberVisits;
    }

    public void setNumberVisits(int numberVisits) {
        this.numberVisits = numberVisits;
    }

    public Node getParent() {
        return parent;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public Card getCardPlayed() {
        return cardPlayed;
    }

    /**
     * Rolls out the node by simulating a game.
     * If there are no cards available we remove the node from the tree
     * @return score of the rollout
     */
    public int rollout() {
        Simulation simulation = new Simulation(state.clone(), agentType);
        try {
            simulation.simulate();
            numberVisits++;
            totalScore = simulation.getRoundState().getPlayerScores().get(playerId);
            return totalScore;
        } catch (NoCardsAvailableException e) {
            // If there are no cards available we remove the node from the tree
            try {
                parent.children.remove(this);
            } catch (NullPointerException exception) {
                // If there are no children to remove from the parent we try to run the simulation again
                System.out.println("Parent is null");
                try {
                    simulation.simulate();
                    numberVisits++;
                    totalScore = simulation.getRoundState().getPlayerScores().get(playerId);
                    return totalScore;
                } catch (NoCardsAvailableException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return -1;
    }

    /**
     * Backpropagates the score of the rollout to the root node
     * @param score score of the rollout
     */
    public void backpropagate(int score) {
        Node currentNode = this;
        while(currentNode.getParent() != null){
            currentNode = currentNode.getParent();
            currentNode.setNumberVisits(currentNode.getNumberVisits() + 1);
            currentNode.setTotalScore(currentNode.getTotalScore() + score);
        }
    }

    /**
     * Calculates the average score of the node
     * @return average score
     */
    public double averageScore() {
        return (double) totalScore / (double) numberVisits;
    }

    /**
     * Expands the node by initializing all children
     */
    public void expand() {
        initializeChildren(state);
    }

    /**
     * Selects a child node based on the MINIMUM UCB1 score, or if their number of visits is = 0
     * @return minimum UCB1 score child
     */
    public Node select() {
        Node minChild = children.get(0);
        for(Node child : children)
        {
            if(child.getNumberVisits() == 0)
            {
                return child;
            }
            else if(child.UCB1formula() > minChild.UCB1formula()){
                minChild = child;
            }
        }
        return minChild;
    }

    /**
     * Calculates the UCB1 score of the node
     * @return UCB1 score
     */
    private double UCB1formula(){
        double averageScore = 26 - averageScore();
        double explorationTerm = EXPLORATION_CONSTANT *
                Math.sqrt(Math.log(getParent().getNumberVisits()) / (double) getNumberVisits());
        return averageScore + explorationTerm;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", totalScore=" + totalScore +
                ", numberVisits=" + numberVisits +
                ", cardPlayed=" + cardPlayed +
                ", childrenCount=" + children.size() +
                '}';
    }

    public RoundState getState() {
        return state;
    }

    public void setParent(Node root) {
        this.parent = root;
    }

    public int getDepthLeft() {
        return depthLeft;
    }
}

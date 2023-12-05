package com.group09.playit.monteCarlo;

import com.group09.playit.controller.RoundController;
import com.group09.playit.logic.TrickService;
import com.group09.playit.model.Card;
import com.group09.playit.simulation.Agent;
import com.group09.playit.simulation.NoCardsAvailableException;
import com.group09.playit.simulation.Simulation;
import com.group09.playit.state.RoundState;

import java.util.ArrayList;

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

    private final int id;

    private final int depthLeft;

    private final ArrayList<Node> children = new ArrayList<>();

    private final double EXPLORATION_CONSTANT = 2.0;

    public Node(RoundState state, Card cardPlayed, Agent agentType, int depthLeft) {
        this.state = state;
        this.cardPlayed = cardPlayed;
        this.agentType = agentType;
        this.id = MCTS.nodeIds++;
        this.depthLeft = depthLeft;
    }

    public Node(RoundState state, Node parent, Card cardPlayed, Agent agentType, int depthLeft) {
        this.state = state;
        this.parent = parent;
        this.cardPlayed = cardPlayed;
        this.agentType = agentType;
        this.id = MCTS.nodeIds++;
        this.depthLeft = depthLeft;
    }

    private void initializeChildren(RoundState state) {
        for (Card card : TrickService.legalCardsToPlay(state)) {
            log("Initializing child for card " + card.toString());
            RoundState childState = state.clone();

            RoundController childController = new RoundController(childState);
            childController.playCard(card);

            if (TrickService.trickFull(childState, childState.getCurrentTrickId())) {
                TrickService.endTrick(childState);
            }

            Node child = new Node(childState, this, card, agentType, depthLeft - 1);
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

    public int rollout() {
        Simulation simulation = new Simulation(state.clone(), agentType);
        try {
            simulation.simulate();
            numberVisits++;
            totalScore = simulation.getRoundState().getPlayerScores().get(0);
            return totalScore;
        } catch (NoCardsAvailableException e) {
            // System.out.println("No cards available exception");
            parent.children.remove(this);
        }
        return -1;
    }

    public void backpropagate(int score) {
        Node currentNode = this;
        while(currentNode.getParent() != null){
            currentNode = currentNode.getParent();
            currentNode.setNumberVisits(currentNode.getNumberVisits() + 1);
            currentNode.setTotalScore(currentNode.getTotalScore() + score);
        }
    }

    public double averageScore() {
        return (double) totalScore / (double) numberVisits;
    }

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

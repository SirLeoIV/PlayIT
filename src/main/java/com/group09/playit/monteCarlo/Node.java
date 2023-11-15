package com.group09.playit.monteCarlo;

import com.group09.playit.controller.RoundController;
import com.group09.playit.logic.TrickService;
import com.group09.playit.model.Card;
import com.group09.playit.simulation.Agent;
import com.group09.playit.simulation.Simulation;
import com.group09.playit.state.RoundState;

import java.util.ArrayList;

public class Node {

    private RoundState state;
    private int totalScore = 0;
    private int numberVisits = 0;
    private Node parent;
    private final Card cardPlayed;
    private final Agent agentType;

    private final int id;

    private final ArrayList<Node> children = new ArrayList<>();

    public Node(RoundState state, Card cardPlayed, Agent agentType) {
        this.state = state;
        this.cardPlayed = cardPlayed;
        this.agentType = agentType;
        this.id = MCTS.nodeIds++;
    }

    public Node(RoundState state, Node parent, Card cardPlayed, Agent agentType) {
        this.state = state;
        this.parent = parent;
        this.cardPlayed = cardPlayed;
        this.agentType = agentType;
        this.id = MCTS.nodeIds++;
    }

    private void initializeChildren(RoundState state) {
        for (Card card : TrickService.legalCardsToPlay(state)) {
            RoundState childState = state.clone();

            RoundController childController = new RoundController(childState);
            childController.playCard(card);

            Node child = new Node(childState, this, card, agentType);
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
        simulation.simulate();
        numberVisits++;
        totalScore = simulation.getRoundState().getPlayerScores().get(0);

        RoundState newState = simulation.getRoundState();
        Card nextCard = newState.getPlayedCards().get(0).get(newState.trickIdOfCard(cardPlayed) + 1);
        state = newState.getRoundStateUpToGivenCardPlayed(nextCard, false);
        return totalScore;
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

    public Node select() {
        return children.get(0); // TODO formula
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
}
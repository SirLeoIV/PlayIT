package com.group09.playit.controller;

import com.group09.playit.logic.DeckService;
import com.group09.playit.logic.RoundService;
import com.group09.playit.logic.TrickService;
import com.group09.playit.model.Card;
import com.group09.playit.simulation.Agent;
import com.group09.playit.simulation.NoCardsAvailableException;
import com.group09.playit.simulation.SimpleAgent;
import com.group09.playit.state.RoundState;

import java.util.ArrayList;
import java.util.List;

/**
 * The class Round controller.
 * This class is used to run a round automatically with agents attached for each player.
 * This class functions as the interface between the agents, and the game logic.
 * It can be used to simulate rounds from any given RoundStateV2.
 */
public class RoundController {

    boolean debug = false;

    private void log(String message) {
        if (debug) System.out.println(message);
    }

    private final List<Agent> agents = new ArrayList<>();

    private final RoundState roundState;

    public RoundController(RoundState roundState) {
        this.roundState = roundState;
    }

    public RoundController(RoundState roundState, boolean debug) {
        this.roundState = roundState;
        this.debug = debug;
    }

    public void addAgent(Agent agent) {
        agents.add(agent);
    }

    public RoundState nextAction() throws NoCardsAvailableException {
        log("Next action started");
        log("Player " + getCurrentPlayerId() + " is playing");
        agents.stream().filter(a -> a.getAgentId() == getCurrentPlayerId()).findFirst().orElseThrow().playCard();
        if (TrickService.trickFull(roundState, roundState.getCurrentTrickId())) {
            log("Trick is full");
            TrickService.endTrick(roundState);
            log("trick ended");
        }
        return roundState;
    }

    /**
     * The current player plays a card. <br>
     *
     * @param card the card to be played
     */
    public void playCard(Card card) {
        log("Player " + getCurrentPlayerId() + " plays " + card);
        TrickService.playCard(roundState, card);
    }

    /**
     * Legal cards to play for the current player.
     *
     * @return the array list of legal cards to play
     */
    public ArrayList<Card> legalCardsToPlay() {
        return TrickService.legalCardsToPlay(roundState);
    }

    /**
     * Gets the current playerId.
     * @return the current playerId
     */
    public int getCurrentPlayerId() {
        return roundState.getCurrentPlayerId();
    }

    public RoundState getRoundState() {
        return roundState.clone();
    }

    public static void main(String[] args) throws NoCardsAvailableException {
        ArrayList<Integer> results = new ArrayList<>();
        int numberOfIterations = 10000;
        for (int i = 0; i < 4; i++) {
            results.add(0);
        }
        for (int j = 0; j < numberOfIterations; j++) {

            if (j % 1000 == 0) System.out.println("Iteration " + j);

            String[] playerNames = {"Alice", "Bob", "Charlie", "David"};

            ArrayList<ArrayList<Card>> playerHands = DeckService.dealCards(playerNames.length);

            RoundState roundState = new RoundState(playerHands, playerNames);
            RoundController roundController = new RoundController(roundState);
            for (int i = 0; i < playerNames.length; i++) {
                roundController.addAgent(new SimpleAgent(i, roundController));
            }
            while (!RoundService.isRoundOver(roundState)) {
                roundController.nextAction();
            }

            for (int i = 0; i < playerNames.length; i++) {
                roundController.log(playerNames[i] + " " + roundState.getPlayerScores().get(i));
                results.set(i, results.get(i) + roundState.getPlayerScores().get(i));
            }
        }
        for (int i = 0; i < results.size(); i++) {
            System.out.println("Player " + i + " total score: " + results.get(i));
            System.out.println("Player " + i + " average score: " + results.get(i) / numberOfIterations);
        }
    }
}

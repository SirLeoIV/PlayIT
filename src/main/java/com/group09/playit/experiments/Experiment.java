package com.group09.playit.experiments;

import com.group09.playit.logic.DeckService;
import com.group09.playit.model.Card;
import com.group09.playit.monteCarlo.MCTS;
import com.group09.playit.monteCarlo.Node;
import com.group09.playit.simulation.*;
import com.group09.playit.state.NodeState;
import com.group09.playit.state.RoundState;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Experiment {
    public static void main (String[] args)throws NoCardsAvailableException {

        Experiment e = new Experiment();
        ArrayList<Integer>  scores = e.getScoresAfterRound();
        System.out.println(scores);
        double mcts_score = scores.get(0);
        storeResult(mcts_score, "test.txt");
    }

    public ArrayList<Integer>  getScoresAfterRound() throws NoCardsAvailableException
    {
        String[] playerNames = {"player0", "player1", "player2", "player3"};
        RoundState roundState = new RoundState(DeckService.dealCards(playerNames.length), playerNames);
        Simulation simulation = new Simulation(roundState, new RandomAgent(0,null));
        simulation.simulateWithMCTS();

        return simulation.getRoundState().getPlayerScores();
    }

    private static void storeResult(double result, String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

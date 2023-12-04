package com.group09.playit.experiments;

import com.group09.playit.logic.DeckService;
import com.group09.playit.simulation.NoCardsAvailableException;
import com.group09.playit.simulation.RandomAgent;
import com.group09.playit.simulation.Simulation;
import com.group09.playit.state.RoundState;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Experiment {
    public static void main (String[] args)throws NoCardsAvailableException {

        for (int i = 0; i < 1000; i++) {
            System.out.println("Experiment " + i);
            Experiment e = new Experiment();
            ArrayList<Integer>  scores = e.getScoresAfterRound();
            System.out.println(scores);
            double mctsScore = scores.get(0);
            storeResult(mctsScore,""); //ADD FILE PATH HERE
        }
    }


    public ArrayList<Integer>  getScoresAfterRound() throws NoCardsAvailableException
    {
        String[] playerNames = {"player0", "player1", "player2", "player3"};
        RoundState roundState = new RoundState(DeckService.dealCards(playerNames.length), playerNames);
        Simulation simulation = new Simulation(roundState, new RandomAgent(0,null));
        simulation.simulate();

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

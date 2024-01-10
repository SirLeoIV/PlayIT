package com.group09.playit.experiments;

import com.group09.playit.logic.DeckService;
import com.group09.playit.simulation.NoCardsAvailableException;
import com.group09.playit.simulation.RandomAgent;
import com.group09.playit.simulation.Simulation;
import com.group09.playit.state.RoundState;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is used to run an experiment.
 * It runs a simulation with a given number of iterations and stores the results in a csv file.
 * The results can be found in the results folder.
 *
 * Change the following variables to change the experiment:
 * playerNames: The names of the players that will be used in the experiment.
 * fileName: The name of the file that will be created.
 * agent1: The agent that will be used for the first player.
 * agent2: The agent that will be used for the other three players.
 * Node.EXPLORATION_CONSTANT: The exploration constant that will be used for the MCTS algorithm.
 * MCTSAgent.maxDepth: The max depth that will be used for the MCTS algorithm.
 * MCTSAgent.time: The time that will be used for the MCTS algorithm.
 * numberOfIterations: The number of iterations that will be run.
 */
public class Experiment2 {

    public static void main(String[] args) throws NoCardsAvailableException {
        String[] playerNames = new String[]{"MCTSAgent_Smart", "SmartAgent 1", "SmartAgent 2", "SmartAgent 3"}; // Change player names here

        Simulation simulation = new Simulation(
                new RoundState(DeckService.dealCards(4), playerNames),
                new RandomAgent(0,null));

        System.out.println(Arrays.toString(simulation.getRoundState().convertToInputLayer(0)));

        simulation.simulate();
        ArrayList<Integer> scores = simulation.getRoundState().getPlayerScores();
        ArrayList<ArrayList<int[]>> layers = simulation.getHistoryOfInputLayersWithExpectedPrediction();
    }

}

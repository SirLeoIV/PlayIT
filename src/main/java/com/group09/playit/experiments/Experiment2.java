package com.group09.playit.experiments;

import com.group09.playit.logic.DeckService;
import com.group09.playit.simulation.NoCardsAvailableException;
import com.group09.playit.simulation.RandomAgent;
import com.group09.playit.simulation.Simulation;
import com.group09.playit.simulation.SmartAgent;
import com.group09.playit.state.RoundState;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

        String fileName = "test-training-data";
        ArrayList<ArrayList<int[]>> layers = new ArrayList<>();

        for (int i = 0; i<20; i++) {
            System.out.println("Experiment " + i);
            Simulation simulation = new Simulation(
                    new RoundState(DeckService.dealCards(4), playerNames),
                    new RandomAgent(0,null));

            simulation.simulateAgentAgainstAnotherAgent(new SmartAgent(0,null), new SmartAgent(0,null));
            ArrayList<ArrayList<int[]>> layersThisRound = simulation.getHistoryOfInputLayersWithExpectedPrediction();
            layers.addAll(layersThisRound);
        }

        // SAVE TO CSV:
        String[] headerNumbers = new String[294];
        for (int i = 0; i < 293; i++) {
            headerNumbers[i] = "" + i;
        }
        headerNumbers[293] = "Y";
        String header = String.join(",", headerNumbers);
        createFile(fileName, header);
        for (ArrayList<int[]> playerLayers : layers) {
            for (int[] layer : playerLayers) {
                storeSingleResultInExistingFile(
                        new ArrayList<>(Arrays.stream(layer).boxed().toList()),
                        fileName);
            }
        }
    }


    private static void createFile(String fileName, String header) {
        String path = "src/main/java/com/group09/playit/experiments/training_data/" + fileName + ".csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(path, true))) {
            writer.println(header);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void storeSingleResultInExistingFile(ArrayList<Integer> result, String fileName) {
        String path = "src/main/java/com/group09/playit/experiments/training_data/" + fileName + ".csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(path, true))) {
            String line = String.join(",", result.stream().map(Object::toString).toArray(String[]::new));
            writer.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

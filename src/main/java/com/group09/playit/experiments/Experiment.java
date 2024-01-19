package com.group09.playit.experiments;

import com.group09.playit.logic.DeckService;
import com.group09.playit.monteCarlo.Node;
import com.group09.playit.simulation.*;
import com.group09.playit.state.RoundState;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
public class Experiment {

    public static void main(String[] args) {
        String[] playerNames = new String[]{"MCTSAgent_ANN", "MCTSAgent_Basic 1", "MCTSAgent_Basic 2", "MCTSAgent_Basic 3"}; // Change player names here
        String fileName = "MCTSAgent_ANN_2Seconds-MCTSAgent_Basic_1Second"; // Change file name here
        Agent agent1 = new MCTSAgentANN(0,null);
        Agent agent2 = new MCTSAgentBasic(0,null); // Change agent here
        Node.EXPLORATION_CONSTANT = 2; // Change exploration constant here
        MCTSAgentBasic.maxDepth = 55; // Change max depth here
        MCTSAgentBasic.time = 1; // Change time here
        MCTSAgentANN.maxDepth = 16; // Change max depth here
        MCTSAgentANN.time = 2; // Change time here
        int numberOfIterations = 500; // Change number of iterations here

        ArrayList<ArrayList<Integer>> scores = new ArrayList<>();
        createFile(fileName,
                String.join(",", playerNames));
        for (int i = 1; i<=numberOfIterations; i++) {
            if (i % 1 == 0) System.out.println("Experiment " + i);
            try {
                Simulation simulation = new Simulation(
                        new RoundState(DeckService.dealCards(4), playerNames),
                        new RandomAgent(0,null));
                simulation.simulateAgentAgainstAnotherAgent(agent1, agent2);
                ArrayList<Integer> scoresOfThisRound = simulation.getRoundState().getPlayerScores();

                scores.add(scoresOfThisRound);
                storeSingleResultInExistingFile(scoresOfThisRound, fileName);

            } catch (NoCardsAvailableException noCardsAvailableException) {
                noCardsAvailableException.printStackTrace();
            }
        }
        System.out.println("Average scores: ");
        for (int i = 0; i < playerNames.length; i++) {
            int finalI = i;
            System.out.println(playerNames[i] + ": " + scores.stream().map(scorePerRound -> scorePerRound.get(finalI)).mapToInt(Integer::intValue).average().getAsDouble());
        }
    }

    private static void createFile(String fileName, String header) {
        String path = "src/main/java/com/group09/playit/experiments/results/" + fileName + ".csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(path, true))) {
            writer.println(header);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void storeSingleResultInExistingFile(ArrayList<Integer> result, String fileName) {
        String path = "src/main/java/com/group09/playit/experiments/results/" + fileName + ".csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(path, true))) {
            String line = String.join(",", result.stream().map(Object::toString).toArray(String[]::new));
            writer.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void storeMultipleResults(ArrayList<ArrayList<Integer>> results, List<String> names, String fileName) {
        String path = "src/main/java/com/group09/playit/experiments/results/" + fileName + ".csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(path, true))) {
            String header = String.join(",", names.stream().map(Object::toString).toArray(String[]::new));
            writer.println(header);
            for (ArrayList<Integer> result : results) {
                String line = String.join(",", result.stream().map(Object::toString).toArray(String[]::new));
                writer.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

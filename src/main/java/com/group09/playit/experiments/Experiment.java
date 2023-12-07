package com.group09.playit.experiments;

import com.group09.playit.logic.DeckService;
import com.group09.playit.simulation.*;
import com.group09.playit.state.RoundState;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Experiment {
    // public static void main (String[] args)throws NoCardsAvailableException {

    //     for (int i = 0; i < 1000; i++) {
    //         System.out.println("Experiment " + i);
    //         Experiment e = new Experiment();
    //         ArrayList<Integer>  scores = e.getScoresAfterRound();
    //         System.out.println(scores);
    //         double mctsScore = scores.get(0);
    //         storeResult(mctsScore,""); //ADD FILE PATH HERE
    //     }
    // }

    public static void main(String[] args) {
        String[] playerNames = new String[]{"MCTSAgent", "RandomAgent 1", "RandomAgent 2", "RandomAgent 3"}; // Change player names here
        String fileName = "MCTSAgent-RandomAgent"; // Change file name here
        Agent agent1 = new MCTSAgent(0,null);
        Agent agent2 = new RandomAgent(0,null); // Change agent here

        ArrayList<ArrayList<Integer>> scores = new ArrayList<>();
        createFile(fileName,
                String.join(",", playerNames));
        for (int i = 0; i<1000; i++) {
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
        // storeMultipleResults(scores, List.of(playerNames), "MCTSAgent-XXXXAgent");
        System.out.println("Average scores: ");
        for (int i = 0; i < playerNames.length; i++) {
            int finalI = i;
            System.out.println(playerNames[i] + ": " + scores.stream().map(scorePerRound -> scorePerRound.get(finalI)).mapToInt(Integer::intValue).average().getAsDouble());
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

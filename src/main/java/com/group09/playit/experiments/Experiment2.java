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
        String[] headerNumbers = new String[295];
        for (int i = 0; i < 294; i++) {
            headerNumbers[i] = "" + i;
        }
        headerNumbers[294] = "Y";
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

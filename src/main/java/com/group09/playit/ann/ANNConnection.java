package com.group09.playit.ann;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;

public class ANNConnection {


    /**
     * This method sends an input layer to the ANN and returns the predicted value.
     * @param inputLayer the input layer
     * @return the predicted value
     * @throws IOException when the connection fails
     */
    public static double predict(int[] inputLayer) throws IOException {
        String[] inputLayerString = Arrays.stream(inputLayer).mapToObj(String::valueOf).toArray(String[]::new);
        String input = String.join(",", inputLayerString);

        URL url = new URL("http://localhost:8080/predict/"+input);

        double result = -1;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            for (String line; (line = reader.readLine()) != null;) {
                result = Double.parseDouble(line);
            }
        }
        return result;
    }
}
package com.group09.playit.ann;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Connection {
    public static void main(String[] args) {
        Process proc;

        try {
            String pythonScript = "python /Users/apple/Desktop/PlayIT/src/main/java/com/group09/playit/ann/ANN.py";
            proc = Runtime.getRuntime().exec(pythonScript);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            proc.waitFor();
            int exitCode = proc.exitValue();
            System.out.println("Exit Code: " + exitCode);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
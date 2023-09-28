package com.group09.playit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HeartsApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("templates/menu-view.fxml"));
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Hearts!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
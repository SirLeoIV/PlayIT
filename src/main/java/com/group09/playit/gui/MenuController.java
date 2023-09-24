package com.group09.playit.gui;

import com.group09.playit.HeartsApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MenuController {

    @FXML
    private Button addPlayer;

    @FXML
    private ListView<String> listOfPlayers;

    @FXML
    private TextField playerName;

    @FXML
    private Button removePlayer;

    @FXML
    private Button rules;

    @FXML
    private Button startGame;

    private Dialog<String> rulesDialog;

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;


    @FXML
    void addPlayer(ActionEvent event) {
        listOfPlayers.getItems().add(playerName.getText());
        System.out.println("addPlayer");
        if (listOfPlayers.getItems().size() >= 5) {
            addPlayer.setDisable(true);
        }
        if (listOfPlayers.getItems().size() > 0) {
            removePlayer.setDisable(false);
        }
        if (listOfPlayers.getItems().size() >= 3) {
            startGame.setDisable(false);
        }
    }

    @FXML
    void removePlayer(ActionEvent event) {
        listOfPlayers.getItems().remove(listOfPlayers.getItems().size()-1);
        System.out.println("removePlayer");
        if (listOfPlayers.getItems().size() < 5) {
            addPlayer.setDisable(false);
        }
        if (listOfPlayers.getItems().size() < 3) {
            startGame.setDisable(true);
        }
        if (listOfPlayers.getItems().size() <= 0) {
            removePlayer.setDisable(true);
        }
    }

    @FXML
    void openRules(ActionEvent event) {
        rulesDialog = new Dialog<>();
        rulesDialog.setTitle("Rules");
        rulesDialog.setContentText("These are the rules of the game Hearts.");
        rulesDialog.getDialogPane().getScene().getWindow().setOnCloseRequest(e -> {
            rulesDialog.close();
            System.out.println("Dialog was closed");
        });
        rulesDialog.show();
    }


    @FXML
    void switchToScene1(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(HeartsApplication.class.getResource("menu-view.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void switchToScene2(ActionEvent event) throws IOException {
        Parent root= FXMLLoader.load(HeartsApplication.class.getResource("scene2.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
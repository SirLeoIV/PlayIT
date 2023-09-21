package com.group09.playit.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

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

}
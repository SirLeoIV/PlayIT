package com.group09.playit.gui;

import com.group09.playit.model.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * The type Menu scene controller.
 */
public class MenuSceneController {

    @FXML
    private Button addPlayer;

    @FXML
    private ListView<String> listOfPlayers;

    @FXML
    private TextField playerName;

    @FXML
    private Button removePlayer;

    @FXML
    private Label pointsToLose;

    @FXML
    private Slider pointsSlider;

    @FXML
    private Button rules;

    @FXML
    private Button startGame;

    private Dialog<String> rulesDialog;

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;

    /**
     * Initializes the menu scene.
     * Sets the points to lose label to the value of the slider.
     */
    public void initialize() {
        pointsSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            pointsToLose.setText("Points to lose: " + (int) pointsSlider.getValue());
        });
    }

    /**
     * Adds a player to the list of players.
     *
     * @param event the event
     */
    @FXML
    void addPlayer(ActionEvent event) {
        String name = playerName.getText();
        if (name.isBlank()) name = "Player " + (listOfPlayers.getItems().size() + 1);
        listOfPlayers.getItems().add(name);
        if (listOfPlayers.getItems().size() >= 5) {
            addPlayer.setDisable(true);
        }
        if (!listOfPlayers.getItems().isEmpty()) {
            removePlayer.setDisable(false);
        }
        if (listOfPlayers.getItems().size() >= 3) {
            startGame.setDisable(false);
        }
        playerName.requestFocus();
    }

    /**
     * Removes a player from the list of players.
     *
     * @param event the event
     */
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
        playerName.requestFocus();
    }

    /**
     * Opens a dialog with the rules of the game.
     *
     * @param event the event
     */
    @FXML
    void openRules(ActionEvent event) {
        rulesDialog = new Dialog<>();
        rulesDialog.setTitle("Rules");
        rulesDialog.setContentText("Welcome to our Game of Hearts.\n\n" +
                "The objective of the game is to be the player with the lowest score. Once a player’s score is higher than the losing score, the game is over, and the player with the lowest score wins.\n\n" +
                "You play in rounds. All cards are dealt in the beginning of each round. The player who has the 2 of clubs begins. Each player must follow the suit of the first card played, if possible. " +
                "If not, then the player may discard any card of any suit. The highest card of the suit led wins the trick, and the winner of that trick leads next. \n\n" +
                "Each time you win a trick, the trick is collected and placed face down in front of you. You add points to your score if there are hearts (1 point each) or the queen of spades (13 points) present. \n\n" +
                "You cannot lead a trick with hearts until a heart has been discarded (hearts is broken). The queen can be led any time. " +
                "If you have a card of the leading suit, you must play it. You can set the losing score to your liking. \n\n" +
                "For multiplayer: Each player must confirm they are playing before taking a turn. Turn the laptop facing away from the other players. Have fun!!");
        rulesDialog.setResizable(true);
        rulesDialog.setWidth(400);
        rulesDialog.setHeight(600);
        rulesDialog.getDialogPane().getScene().getWindow().setOnCloseRequest(e -> {
            rulesDialog.close();
            System.out.println("Dialog was closed");
        });
        rulesDialog.show();
    }

    /**
     * Switches to the game scene.
     *
     * @param event the event
     */
    @FXML
    void switchToGame(ActionEvent event) {
        Parent root = new GameGUI(new Game((int) pointsSlider.getValue(), listOfPlayers.getItems().toArray(new String[0])));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
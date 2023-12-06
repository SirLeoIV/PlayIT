package com.group09.playit.gui;

import com.group09.playit.model.Player;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

/**
 * The type Player details gui.
 */
public class NewPlayerGUI extends Parent {

    Player player;

    PlayerType playerType;

    public enum PlayerType {
        HUMAN,
        COMPUTER
    }


    /**
     * Instantiates a new Player details GUI.
     * The player details GUI shows the name and points of the player.
     *
     * @param player the player
     */
    public NewPlayerGUI(String name) {
        this.player = new Player(name);
        this.playerType = PlayerType.HUMAN;

        Text playerName = new Text(player.getName());
        ComboBox comboBox = new ComboBox();
        comboBox.getItems().addAll(PlayerType.COMPUTER, PlayerType.HUMAN);
        comboBox.setValue(PlayerType.HUMAN);
        comboBox.setOnAction(event -> {
            playerType = (PlayerType) comboBox.getValue();
        });

        playerName.setLayoutX(0);
        playerName.setLayoutY(0);


        comboBox.setLayoutX(100);
        comboBox.setLayoutY(-15);

        getChildren().addAll(playerName, comboBox);
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

}

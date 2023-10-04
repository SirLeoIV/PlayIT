package com.group09.playit.gui;

import com.group09.playit.model.Player;
import javafx.scene.Parent;
import javafx.scene.text.Text;

public class PlayerDetailsGUI extends Parent {

    /**
     * Instantiates a new Player details GUI.
     * The player details GUI shows the name and points of the player.
     *
     * @param player the player
     */
    public PlayerDetailsGUI(Player player) {

        Text playerName = new Text(player.getName());
        Text playerPoints = new Text("Points: " + player.getCurrentScore());

        playerName.setLayoutY(0);
        playerPoints.setLayoutY(20);

        getChildren().add(playerName);
        getChildren().add(playerPoints);
    }

}

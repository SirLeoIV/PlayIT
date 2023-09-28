package com.group09.playit.gui;

import com.group09.playit.model.Player;
import javafx.scene.Parent;
import javafx.scene.text.Text;

public class PlayerDetailsGUI extends Parent {

    private Player player;

    public PlayerDetailsGUI(Player player) {

        this.player = player;

        Text playerName = new Text(player.getName());
        Text playerPoints = new Text("Points: " + player.currentScore);

        playerName.setLayoutY(0);
        playerPoints.setLayoutY(20);

        getChildren().add(playerName);
        getChildren().add(playerPoints);
    }

}

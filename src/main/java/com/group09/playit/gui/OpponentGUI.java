package com.group09.playit.gui;

import com.group09.playit.model.Player;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class OpponentGUI extends Parent {

    private Player player;

    private Label playerCard;

    private Label playerName;

    private AnchorPane playerPane;

    private AnchorPane card;

    private Label playerPoints;

    public OpponentGUI(Player player) {
        this.player = player;

        playerName = new Label();
        playerCard = new Label();
        playerPoints = new Label();
        card = new AnchorPane();
        playerPane = new AnchorPane();
        getChildren().add(playerPane);
        playerPane.getChildren().add(playerName);
        playerPane.getChildren().add(playerPoints);
        playerPane.getChildren().add(card);
        card.getChildren().add(playerCard);

        playerName.setLayoutX(38.0);
        playerName.setLayoutY(22.0);
        playerName.setPrefHeight(17.0);

        playerPoints.setLayoutX(38.0);
        playerPoints.setLayoutY(50.0);
        playerPoints.setPrefHeight(17.0);

        card.setLayoutX(14.0);
        card.setLayoutY(77.0);
        card.setPrefHeight(130.0);
        card.setPrefWidth(100.0);

        playerCard.setLayoutX(14);
        playerCard.setLayoutY(14);

        playerName.setText(player.getName());
        updatePlayerCard();
    }

    public void updatePlayerCard() {
        if (player.cardPlayed != null) {
            playerCard.setText(player.cardPlayed.toString());
        }
        playerPoints.setText("Points: " + player.currentScore);
    }
}

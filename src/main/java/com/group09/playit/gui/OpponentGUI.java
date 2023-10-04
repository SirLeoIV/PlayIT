package com.group09.playit.gui;

import com.group09.playit.model.Player;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import static javafx.scene.text.Font.font;

public class OpponentGUI extends Parent {

    private final Player player;

    private final ImageView card;

    /**
     * Instantiates a new Opponent GUI.
     * The opponent GUI shows the name, points and card of the opponent.
     *
     * @param player the player
     * @param x      the x position
     * @param y      the y position
     */
    public OpponentGUI(Player player, int x, int y) {
        this.player = player;

        Label playerName = new Label();
        Label playerPoints = new Label();
        card = new ImageView();
        VBox vBox = new VBox();
        vBox.setMinHeight(210);
        vBox.setMaxHeight(210);
        vBox.setPrefHeight(210);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(5);
        getChildren().add(vBox);
        vBox.getChildren().add(playerName);
        vBox.getChildren().add(playerPoints);
        vBox.getChildren().add(card);

        vBox.setLayoutX(x);
        vBox.setLayoutY(y);

        playerName.setPrefHeight(17.0);
        playerPoints.setPrefHeight(17.0);

        playerName.setFont(font(20));
        playerPoints.setFont(font(20));

        card.setFitHeight(110.0);
        card.setFitWidth(75.0);

        playerName.setText(player.getName());
        playerPoints.setText("Points: " + player.getCurrentScore());
        updatePlayerCard();
    }

    /**
     * Update player card.
     * Updates the card of the player.
     */
    public void updatePlayerCard() {
        if (player.getCardPlayed() != null) {
            CardImage cardImage = new CardImage(player.getCardPlayed());
            card.setPreserveRatio(true);
            card.setImage(cardImage.getImage());
        }
    }
}

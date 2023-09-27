package com.group09.playit.gui;

import com.group09.playit.model.Game;
import com.group09.playit.model.Player;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class GameGUI extends Parent {

    Game game;

    private final AnchorPane gamePane;

    private final HBox players;

    private final AnchorPane currentPlayer;

    public GameGUI(Game game) throws IOException {
        this.game = game;
        currentPlayer = new AnchorPane();
        players = new HBox();
        gamePane = new AnchorPane();
        getChildren().add(gamePane);
        gamePane.getChildren().add(players);
        gamePane.getChildren().add(currentPlayer);

        players.setPrefHeight(174);
        players.setPrefWidth(510);
        players.setLayoutX(45);
        players.setLayoutY(71);
        players.setAlignment(javafx.geometry.Pos.CENTER);
        AnchorPane.setLeftAnchor(players, 45.0);
        AnchorPane.setRightAnchor(players, 45.0);

        currentPlayer.setPrefHeight(100);
        currentPlayer.setPrefWidth(397);
        currentPlayer.setLayoutX(108);
        currentPlayer.setLayoutY(286);
        AnchorPane.setBottomAnchor(currentPlayer, 15.0);
        AnchorPane.setLeftAnchor(currentPlayer, 45.0);
        AnchorPane.setRightAnchor(currentPlayer, 45.0);

        currentPlayer.getChildren().add(new HandGUI(game.getCurrentRound().getCurrentTrick().getCurrentPlayer(), game.getCurrentRound().getCurrentTrick(), game.getCurrentRound()));


        updatePlayers();
        System.out.println("GameSceneController");
    }

    public void updatePlayers() throws IOException {
        for (Player player : game.players) {
            System.out.println("add " + player.getName());
            if (!player.equals(game.getCurrentRound().getCurrentTrick().getCurrentPlayer())) {
                players.getChildren().add(new OpponentGUI(player));
            }
        }
    }

    public void updateHand() {

    }

}
package com.group09.playit.gui;

import com.group09.playit.controller.GameController;
import com.group09.playit.model.Game;
import com.group09.playit.model.Player;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import static javafx.scene.text.Font.font;

public class GameGUI extends Parent implements GameController.GameObserver {

    Game game;

    GameController controller;

    private final Pane table = new Pane();

    private final VBox currentPlayer = new VBox();

    private final Text heartsBroken = new Text("Hearts broken: no");

    public GameGUI(Game game) {
        this.game = game;
        controller = new GameController(game);
        controller.attach(this);

        AnchorPane gamePane = new AnchorPane();
        getChildren().add(gamePane);
        VBox vBox = new VBox();
        vBox.getChildren().add(table);
        vBox.getChildren().add(currentPlayer);
        gamePane.getChildren().add(vBox);

        // gamepane to full width of window
        gamePane.setPrefWidth(1200);

        // center elements in anchor pane
        AnchorPane.setLeftAnchor(vBox, 0.0);
        AnchorPane.setRightAnchor(vBox, 0.0);
        AnchorPane.setTopAnchor(vBox, 0.0);
        AnchorPane.setBottomAnchor(vBox, 0.0);
        gamePane.setStyle("-fx-border-color: blue; -fx-border-width: 1px;");

        // set border visible
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setStyle("-fx-border-color: red; -fx-border-width: 1px;");

        table.setMaxHeight(400);
        table.setMinHeight(400);

        table.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        table.setStyle("-fx-background-color: green;");

        currentPlayer.setPrefHeight(200);
        currentPlayer.setSpacing(15);

        currentPlayer.setAlignment(javafx.geometry.Pos.CENTER);
        currentPlayer.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

        update();

        System.out.println("GameSceneController");
    }

    private void updateCurrentPlayer() {
        currentPlayer.getChildren().clear();
        PlayerDetailsGUI playerDetailsGUI = new PlayerDetailsGUI(
                game.getCurrentRound().getCurrentTrick().getCurrentPlayer());
        HandGUI handGUI = new HandGUI(
                game.getCurrentRound().getCurrentTrick().getCurrentPlayer(),
                controller);
        currentPlayer.getChildren().addAll(playerDetailsGUI, handGUI);
    }

    public void updateTable() {
        table.getChildren().clear();
        int numberOpponents = game.players.size() - 1;

        for (int i = 0; i < numberOpponents; i++) {
            int currentPlayer = game.players.indexOf(game.getCurrentRound().getCurrentTrick().getCurrentPlayer());
            int playerIndex = (currentPlayer + i + 1) % game.players.size();
            Player player = game.players.get(playerIndex);

            double angle = 90.0 / (numberOpponents - 1);
            double currentAngle = Math.toRadians(-225 + angle * (i));
            int x = (int) (Math.sin(currentAngle) * 400 + 550);
            int y = (int) (Math.cos(currentAngle) * 400 + 450);

            OpponentGUI opponent = new OpponentGUI(player, x, y);

            table.getChildren().add(opponent);
        }

        if (game.getCurrentRound().isHeartsBroken()) heartsBroken.setText("Hearts broken: yes");
        else heartsBroken.setText("Hearts broken: no");
        heartsBroken.setX(20);
        heartsBroken.setY(20);
        heartsBroken.setFont(font(20));

        table.getChildren().add(heartsBroken);

    }

    @Override
    public void update() {
        updateCurrentPlayer();
        updateTable();
    }
}
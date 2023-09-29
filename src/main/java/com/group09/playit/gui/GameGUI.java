package com.group09.playit.gui;

import com.group09.playit.controller.GameController;
import com.group09.playit.model.Game;
import com.group09.playit.model.Player;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Comparator;

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
        if (controller.getGameStatus().equals(GameController.GameStatus.ACTIVE_TURN)) {
            PlayerDetailsGUI playerDetailsGUI = new PlayerDetailsGUI(
                    game.getCurrentRound().getCurrentTrick().getCurrentPlayer());
            HandGUI handGUI = new HandGUI(
                    game.getCurrentRound().getCurrentTrick().getCurrentPlayer(),
                    controller);
            currentPlayer.getChildren().addAll(playerDetailsGUI, handGUI);
        }
    }

    public void updateTable() {
        table.getChildren().clear();
        int numberOpponents = game.getPlayers().size() - 1;

        for (int i = 0; i < numberOpponents; i++) {
            int currentPlayer = game.getPlayers().indexOf(game.getCurrentRound().getCurrentTrick().getCurrentPlayer());
            int playerIndex = (currentPlayer + i + 1) % game.getPlayers().size();
            Player player = game.getPlayers().get(playerIndex);

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
        switch (controller.getGameStatus()){
            case WAITING_FOR_PLAYER -> showWaitingForPlayerDialog();
            case ROUND_OVER -> showRoundOverDialog();
            case GAME_OVER -> showGameOverDialog();
        }
    }

    private void showWaitingForPlayerDialog() {
        Player player = game.getCurrentRound().getCurrentTrick().getCurrentPlayer();
        Dialog<Object> waitingForPlayer = new Dialog<>();
        waitingForPlayer.setTitle("Waiting for player: " + player.getName());
        VBox content = new VBox();
        content.setAlignment(Pos.CENTER);
        content.setSpacing(10);
        Button confirm = new Button("Confirm");

        confirm.setOnAction(event -> {
            controller.confirmActivePlayer(player);
            waitingForPlayer.setResult(true);
            waitingForPlayer.close();
        });
        content.getChildren().add(new Text("Please confirm that you are " + player.getName() + " by clicking the button below."));
        content.getChildren().add(confirm);
        waitingForPlayer.getDialogPane().setContent(content);

        waitingForPlayer.show();
    }

    private void showRoundOverDialog() {
        Dialog<Object> roundOver = new Dialog<>();
        roundOver.setTitle("Round over");
        VBox content = new VBox();
        content.setAlignment(Pos.CENTER);
        content.setSpacing(10);

        roundOver.getDialogPane().getScene().getWindow().setOnCloseRequest(e -> {
            controller.startRound();
            roundOver.close();
        });

        content.getChildren().add(new Text("The round is over. These are the current scores:"));
        for (Player player : game.getPlayers()) {
            content.getChildren().add(new Text(player.getName() + ": " + player.getCurrentScore()));
        }

        roundOver.getDialogPane().setContent(content);
        roundOver.show();
    }

    private void showGameOverDialog() {
        Dialog<Object> gameOver = new Dialog<>();
        gameOver.setTitle("Game over");
        VBox content = new VBox();
        content.setAlignment(Pos.CENTER);
        content.setSpacing(10);

        gameOver.getDialogPane().getScene().getWindow().setOnCloseRequest(e -> gameOver.close());

        game.getPlayers().sort(Comparator.comparingInt(Player::getCurrentScore));
        Player winner = game.getPlayers().get(0);

        content.getChildren().add(new Text("The game is over. These are the final scores:"));
        for (Player player : game.getPlayers()) {
            content.getChildren().add(new Text(player.getName() + ": " + player.getCurrentScore()));
        }
        content.getChildren().add(new Text("The winner is: " + winner.getName()));

        gameOver.getDialogPane().setContent(content);
        gameOver.show();
    }
}
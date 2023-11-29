package com.group09.playit.gui;

import com.group09.playit.controller.GameController;
import com.group09.playit.model.Card;
import com.group09.playit.model.Player;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * The type Hand gui.
 */
public class AiPlayerGUI extends Parent {

    private final GameController controller;
    private Card card = null;

    /**
     * Instantiates a new Hand GUI.
     * The hand GUI shows the cards of the player.
     * The cards are clickable and can be played.
     *
     * @param player     the current player
     * @param controller the game controller
     */
    public AiPlayerGUI(Player player, GameController controller) {
        this.controller = controller;

        Text playerName = new Text(player.getName() + " (AI) is playing");
        playerName.setLayoutY(0);
        getChildren().add(playerName);

        Button determineCard = new Button("Determine card");
        determineCard.setLayoutY(30);

        determineCard.setOnAction(e -> {
            card = controller.legalCardsToPlay().get(0);

            Text cardText = new Text("AI plays: " + card.toString());
            cardText.setLayoutY(100);
            getChildren().add(cardText);

            Button nextPlayer = new Button("Play Card + Next player");
            nextPlayer.setLayoutY(30);
            nextPlayer.setOnAction(e2 -> controller.playCard(card));

            getChildren().addAll(nextPlayer);
            getChildren().remove(determineCard);
        });

        getChildren().addAll(determineCard);
    }

    /**
     * Play card.
     * Plays the card that was clicked.
     *
     * @param card the card
     */
    public void playCard(Card card) {
        controller.playCard(card);
    }
}

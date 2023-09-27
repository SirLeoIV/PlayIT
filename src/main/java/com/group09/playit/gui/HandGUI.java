package com.group09.playit.gui;

import com.group09.playit.logic.TrickService;
import com.group09.playit.model.Card;
import com.group09.playit.model.Player;
import com.group09.playit.model.Round;
import com.group09.playit.model.Trick;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class HandGUI extends Parent {

    private Player player;

    private final HBox handBox;


    public HandGUI(Player player, Trick trick, Round round) {
        this.player = player;

        handBox = new HBox();
        getChildren().add(handBox);

        for (Card card : player.getHand().getCards()) {
            Button cardButton = new Button();
            cardButton.setMaxWidth(50);
            cardButton.setMaxHeight(73);

            // enable linebreaks in button text
            cardButton.setWrapText(true);

            CardImage cardImage = new CardImage(card);
            cardImage.setPreserveRatio(true);

            cardButton.setGraphic(cardImage);

            cardButton.setDisable(!TrickService.legalCardsToPlay(trick, round, player).contains(card));

            cardButton.setOnAction(event -> {
                playCard(card);
                setDisable(true); // TODO remove after implementing game logic
            });
            handBox.getChildren().add(cardButton);
            System.out.println("Added card: " + card);
        }

    }

    public void playCard(Card card) {
        System.out.println("Playing card: " + card.toString());
        player.playCard(card);
    }
}

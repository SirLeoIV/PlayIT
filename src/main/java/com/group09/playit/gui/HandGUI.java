package com.group09.playit.gui;

import com.group09.playit.controller.GameController;
import com.group09.playit.model.Card;
import com.group09.playit.model.Player;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

public class HandGUI extends Parent {

    private final GameController controller;

    public HandGUI(Player player, GameController controller) {
        this.controller = controller;


        int i = 0;
        for (Card card : player.getHand().getCards()) {
            Button cardButton = new Button();
            cardButton.setMaxWidth(50);
            cardButton.setMaxHeight(73);

            int fullWidth = 60 * player.getHand().getCards().size();
            double offset = 1200 - fullWidth;

            cardButton.setLayoutX(offset / 2 + i * 60);


            CardImage cardImage = new CardImage(card);
            cardImage.setPreserveRatio(true);

            cardButton.setGraphic(cardImage);
            cardButton.setTooltip(new Tooltip(card.toString()));

            cardButton.setDisable(!controller.legalCardsToPlay().contains(card));

            cardButton.setOnAction(event -> playCard(card));
            getChildren().add(cardButton);
            i++;
        }
    }

    public void playCard(Card card) {
        controller.playCard(card);
    }
}

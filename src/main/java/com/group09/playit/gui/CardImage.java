package com.group09.playit.gui;

import com.group09.playit.model.Card;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardImage extends ImageView {

    public CardImage(Card card) {
        String fileName = card.getRank() + "_" + card.getSuit();
        try {
            setImage(new Image(
                    "cards_pngs/" + fileName + ".png"));
        } catch (IllegalArgumentException e) {
            System.out.println("Card image not found: " + fileName);
        }
        setFitHeight(73);
        setFitWidth(50);
    }
}

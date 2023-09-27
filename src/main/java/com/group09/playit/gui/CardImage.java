package com.group09.playit.gui;

import com.group09.playit.model.Card;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardImage extends ImageView {

    private final String fileName;

    public CardImage(Card card) {
        this.fileName = card.getRank() + "_" + card.getSuit();
        setImage(new Image(
                "cards_pngs/" + fileName + ".png"));
        setFitHeight(73);
        setFitWidth(50);
    }
}

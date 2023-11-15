package com.group09.playit.gui;

import com.group09.playit.model.Card;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The class Card image.
 * Used to display a card as an image.
 */
public class CardImage extends ImageView {

    /**
     * Instantiates a new Card image.
     * Loads the image from the cards_pngs folder.
     *
     * @param card the card
     */
    public CardImage(Card card) {
        String fileName = card.rank() + "_" + card.suit();
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

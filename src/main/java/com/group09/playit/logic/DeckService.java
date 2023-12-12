package com.group09.playit.logic;

import com.group09.playit.model.Card;
import com.group09.playit.model.Deck;

import java.util.ArrayList;

public class DeckService {

    /**
     * Deal cards to the players.
     * @param playerCount the player count
     * @return the array list of array lists of cards (player hands)
     */
    public static ArrayList<ArrayList<Card>> dealCards(int playerCount) {
        ArrayList<ArrayList<Card>> playerHands = new ArrayList<>();
        for (int i = 0; i < playerCount; i++) {
            playerHands.add(new ArrayList<>());
        }
        Deck deck = new Deck(playerCount);
        deck.shuffle();
        int playerIndex = 0;
        for (Card card : deck.getCards()) {
            playerHands.get(playerIndex % playerCount).add(card);
            playerIndex = (playerIndex + 1)% playerCount;
        }
        return playerHands;
    }
}

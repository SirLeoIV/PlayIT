package com.group09.playit.model;

import java.util.ArrayList;
import java.util.List;

public class Round {

    ArrayList<Player> players;

    Player currentStartingPlayer;

    public ArrayList<Trick> tricks = new ArrayList<>();

    boolean heartsBroken = false;


    public Round(ArrayList<Player> players) {
        this.players = players;
        Deck deck = new Deck(players.size());
        deck.shuffle();

        for (int i = 0; i < players.size(); i++) {
            int numberOfCards = deck.size() / players.size();
            List<Card> cards = deck.cards.subList(i * numberOfCards, (i + 1) * numberOfCards);
            players.get(i).hand = new Hand(new ArrayList<>(cards));
        }

        currentStartingPlayer = determineFirstStartingPlayer();
        nextTrick();
    }

    private Player determineFirstStartingPlayer() {
        for (Player player : players) {
            if (player.hand.contains(new Card(Card.Suit.CLUBS, Card.Rank.TWO))) {
                return player;
            }
        }
        return players.get(0);
    }

    void nextTrick() {
        Trick trick = new Trick(currentStartingPlayer, this);
        tricks.add(trick);
    }

    public boolean checkCurrentTrick() {
        Trick currentTrick = tricks.get(tricks.size()-1);
        if (currentTrick.trickFull()) {
            Card winningCard = currentTrick.winningCard();
            Player winningPlayer = players.stream().filter(player -> player.cardPlayed == winningCard).findFirst().get();
            winningPlayer.addScore(currentTrick.getValue());
            currentStartingPlayer = winningPlayer;
            nextTrick();
            return true;
        }
        return false;
    }

}

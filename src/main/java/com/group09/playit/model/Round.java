package com.group09.playit.model;

import com.group09.playit.logic.RoundService;

import java.util.ArrayList;
import java.util.List;

public class Round {

    private ArrayList<Player> players;

    private Player currentStartingPlayer;

    private ArrayList<Trick> tricks = new ArrayList<>();

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
        RoundService.nextTrick(this);
    }

    private Player determineFirstStartingPlayer() {
        for (Player player : players) {
            if (player.hand.contains(new Card(Card.Suit.CLUBS, Card.Rank.TWO))) {
                return player;
            }
        }
        return players.get(0);
    }

    public Trick getCurrentTrick() {
        return tricks.get(tricks.size()-1);
    }

    public boolean isHeartsBroken() {
        return heartsBroken;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getCurrentStartingPlayer() {
        return currentStartingPlayer;
    }

    public ArrayList<Trick> getTricks() {
        return tricks;
    }

    public void setCurrentStartingPlayer(Player winningPlayer) {
        currentStartingPlayer = winningPlayer;
    }

    public void setHeartsBroken(boolean b) {
        heartsBroken = b;
    }
}

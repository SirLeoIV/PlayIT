package com.group09.playit.model;

import java.util.ArrayList;

public class Trick {
    
    private Player currentPlayer;
    private Round round;
    
    public Trick(Player startingPlayer, Round round) {
        this.currentPlayer = startingPlayer;
        this.round = round;
    }

    ArrayList<Card> cards = new ArrayList<>();

    public int getValue() {
        int points = 0;
        for (int i = 0; i < 4; i++) {
            points += cards.get(i).getValue();
        }
        return points;
    }

    public Card winningCard() {
        Card winningCard = cards.get(0);
        for (int i = 1; i < 4; i++) {
            if (cards.get(i).getSuit() == winningCard.getSuit()) {
                if (cards.get(i).getRank().points > winningCard.getRank().points) {
                    winningCard = cards.get(i);
                }
            }
        }
        return winningCard;
    }
    
    public ArrayList<Card> legalCardsToPlay(Player player) {
        ArrayList<Card> legalCards = new ArrayList<>();
        if (cards.isEmpty()) {
            if (player.hand.contains(new Card(Card.Suit.CLUBS, Card.Rank.TWO))) {
                legalCards.add(new Card(Card.Suit.CLUBS, Card.Rank.TWO));
            } else {
                for (Card card : player.hand.cards) {
                    if (card.getSuit() != Card.Suit.HEARTS || round.heartsBroken) {
                        legalCards.add(card);
                    }
                }
            }
        } else {
            Card.Suit suit = cards.get(0).getSuit();
            for (Card card : player.hand.cards) {
                if (card.getSuit() == suit) {
                    legalCards.add(card);
                }
            }
            if (legalCards.isEmpty()) {
                for (Card card : player.hand.cards) {
                    legalCards.add(card);
                }
            }
        }
        return legalCards;
    }

    public void playCard(Player player, Card card) {
        cards.add(card);
        currentPlayer = round.players.get((round.players.indexOf(currentPlayer) + 1) % round.players.size());
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean trickFull() {
        return cards.size() == round.players.size();
    }

    @Override
    public String toString() {
        String cards = "";
        for (Card card : this.cards) {
            cards += card.toString() + ", ";
        }
        return "[" + cards + "]";
    }
}

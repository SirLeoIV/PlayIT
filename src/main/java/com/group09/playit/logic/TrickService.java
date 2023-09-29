package com.group09.playit.logic;

import com.group09.playit.model.Card;
import com.group09.playit.model.Player;
import com.group09.playit.model.Round;
import com.group09.playit.model.Trick;

import java.util.ArrayList;

public class TrickService {

    public static int getValue(Trick trick, Round round) {
        int points = 0;
        for (int i = 0; i < round.getPlayers().size(); i++) {
            points += trick.getCards().get(i).getValue();
        }
        return points;
    }

    public static Card winningCard(Trick trick, Round round) {
        ArrayList<Card> cards = trick.getCards();
        Card winningCard = cards.get(0);
        for (int i = 1; i < round.getPlayers().size(); i++) {
            if (cards.get(i).getSuit() == winningCard.getSuit()) {
                if (cards.get(i).getRank().getPoints() > winningCard.getRank().getPoints()) {
                    winningCard = cards.get(i);
                }
            }
        }
        return winningCard;
    }

    public static ArrayList<Card> legalCardsToPlay(Trick trick, Round round, Player player) {
        ArrayList<Card> cardsInTrick = trick.getCards();
        ArrayList<Card> legalCards = new ArrayList<>();
        if (cardsInTrick.isEmpty()) {
            if (player.getHand().contains(new Card(Card.Suit.CLUBS, Card.Rank.TWO))) {
                legalCards.add(new Card(Card.Suit.CLUBS, Card.Rank.TWO));
            } else {
                for (Card card : player.getHand().getCards()) {
                    if (card.getSuit() != Card.Suit.HEARTS
                            || round.isHeartsBroken()
                            || player.getHand().getCards().stream().noneMatch(c -> c.getSuit() != Card.Suit.HEARTS)) {
                        legalCards.add(card);
                    }
                }
            }
        } else {
            Card.Suit suit = cardsInTrick.get(0).getSuit();
            for (Card card : player.getHand().getCards()) {
                if (card.getSuit() == suit) {
                    legalCards.add(card);
                }
            }
            if (legalCards.isEmpty()) {
                legalCards.addAll(player.getHand().getCards());
            }
        }
        return legalCards;
    }

    public static void playCard(Trick trick, Round round, Card card) {
        trick.getCards().add(card);
        if (card.getSuit() == Card.Suit.HEARTS) {
            round.setHeartsBroken(true);
        }
        trick.setCurrentPlayer(
                round.getPlayers()
                        .get((round.getPlayers().indexOf(trick.getCurrentPlayer()) + 1) % round.getPlayers().size()));
    }

    public static boolean trickFull(Trick trick, Round round) {
        return trick.getCards().size() == round.getPlayers().size();
    }

    public static void endTrick(Trick trick, Round round) {
        Card winningCard = winningCard(trick, round);
        Player winningPlayer = round.getPlayers().stream().filter(player -> player.getCardPlayed() == winningCard).findFirst().get();
        winningPlayer.addScore(getValue(trick, round));
        round.setCurrentStartingPlayer(winningPlayer);
        for (Player player : round.getPlayers()) {
            player.setCardPlayed(null);
        }
    }
}

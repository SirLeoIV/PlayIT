package com.group09.playit.logic;

import com.group09.playit.model.Card;
import com.group09.playit.model.Player;
import com.group09.playit.model.Round;
import com.group09.playit.model.Trick;
import com.group09.playit.state.RoundState;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

import static com.group09.playit.model.Card.Rank;
import static com.group09.playit.model.Card.Suit;

/**
 * The type Trick service.
 */
public class TrickService {

    /**
     * Get the value of the cards in the current trick.
     *
     * @param trick current trick
     * @return value of the cards
     */
    public static int getValue(Trick trick) {
        return trick.getCards().stream().mapToInt(Card::getValue).sum();
    }

    /**
     * Get the winning card in the current trick.
     * The winning card is the card with the highest rank of the same suit as the first card in the trick.
     *
     * @param trick current trick
     * @return winning card
     */
    public static Card winningCard(Trick trick) {
        ArrayList<Card> cards = trick.getCards();
        Card winningCard = cards.get(0);
        for (Card card : cards) {
            if (card.suit() == winningCard.suit()) {
                if (card.rank().getPoints() > winningCard.rank().getPoints()) {
                    winningCard = card;
                }
            }
        }
        return winningCard;
    }

    /**
     * Get the winning card in the current trick.
     * The winning card is the card with the highest rank of the same suit as the first card in the trick.
     *
     * @param roundState current round state
     * @param trickId    current trick id
     * @return winning card
     */
    private static Card winningCard(RoundState roundState, int trickId) {

        Suit suit = roundState.getTrickModel(trickId).getSuit();

        ArrayList<Card> cardsOfSuit = new ArrayList<>();
        for (Card card : roundState.getTrickById(trickId)) {
            if (card.suit() == roundState.getTrickModel(trickId).getSuit()) {
                cardsOfSuit.add(card);
            }
        }

        return cardsOfSuit.stream()
                .max(Comparator.comparingInt(c -> {
                    return c.rank().getPoints();
                }))
                .orElseThrow();
    }

    /**
     * Get the legal cards to play for the current player in the current trick.
     * If the trick is empty, the legal cards are all cards except hearts if the hearts are not broken.
     * If the trick is not empty, the legal cards are all cards of the same suit as the first card in the trick.
     * If the player does not have any cards of the same suit, the legal cards are all cards.
     *
     * @param trick  current trick
     * @param round  current round
     * @param player current player
     * @return list of legal cards to play
     */
    public static ArrayList<Card> legalCardsToPlay(Trick trick, Round round, Player player) {
        ArrayList<Card> cardsInTrick = trick.getCards();
        ArrayList<Card> legalCards = new ArrayList<>();
        if (cardsInTrick.isEmpty()) {
            if (player.getHand().contains(new Card(Suit.CLUBS, Rank.TWO))) {
                legalCards.add(new Card(Suit.CLUBS, Rank.TWO));
            } else {
                for (Card card : player.getHand().getCards()) {
                    if (card.suit() != Suit.HEARTS
                            || round.isHeartsBroken()
                            || player.getHand().getCards().stream().allMatch(c -> c.suit() == Suit.HEARTS)) {
                        legalCards.add(card);
                    }
                }
            }
        } else {
            Suit suit = cardsInTrick.get(0).suit();
            for (Card card : player.getHand().getCards()) {
                if (card.suit() == suit) {
                    legalCards.add(card);
                }
            }
            if (legalCards.isEmpty()) {
                legalCards.addAll(player.getHand().getCards());
            }
        }
        return legalCards;
    }

    /**
     * Get the legal cards to play for the current player in the current trick.
     * If the trick is empty, the legal cards are all cards except hearts if the hearts are not broken.
     * If the trick is not empty, the legal cards are all cards of the same suit as the first card in the trick.
     * If the player does not have any cards of the same suit, the legal cards are all cards.
     *
     * @param roundState current round state
     * @return list of legal cards to play
     */
    public static ArrayList<Card> legalCardsToPlay(RoundState roundState) {
        int playerId = roundState.getCurrentPlayerId();
        ArrayList<Card> cardsInTrick = roundState.getTrickById(roundState.getCurrentTrickId());
        ArrayList<Card> legalCards = new ArrayList<>();
        if (cardsInTrick.isEmpty() || cardsInTrick.stream().allMatch(Objects::isNull)) {
            if (roundState.getPlayerHands().get(playerId).contains(new Card(Suit.CLUBS, Rank.TWO))) {
                legalCards.add(new Card(Suit.CLUBS, Rank.TWO));
            } else {
                boolean heartsBroken = roundState.isHeartsBroken();
                for (Card card : roundState.getPlayerHands().get(playerId)) {
                    if (card.suit() != Suit.HEARTS
                            || heartsBroken
                            || roundState.getPlayerHands().get(playerId).stream().allMatch(c -> c.suit() == Suit.HEARTS)) {
                        legalCards.add(card);
                    }
                }
            }
        } else {
            Suit suit = cardsInTrick.get(roundState.trickStartingPlayerId(roundState.getCurrentTrickId())).suit();
            for (Card card : roundState.getPlayerHands().get(playerId)) {
                if (card.suit() == suit) {
                    legalCards.add(card);
                }
            }
            if (legalCards.isEmpty()) {
                legalCards.addAll(roundState.getPlayerHands().get(playerId));
            }
        }
        return legalCards;
    }

    /**
     * Play a card in the current trick.
     * The current player is the next player in the round.
     * The hearts are broken if a heart is played.
     *
     * @param trick current trick
     * @param round current round
     * @param card  card to play
     */
    public static void playCard(Trick trick, Round round, Card card) {
        trick.getCards().add(card);
        if (card.suit() == Suit.HEARTS) {
            round.setHeartsBroken(true);
        }
        trick.setCurrentPlayer(
                round.getPlayers()
                        .get((round.getPlayers().indexOf(trick.getCurrentPlayer()) + 1) % round.getPlayers().size()));
    }

    public static void playCard(RoundState roundState, Card card) {
        int playerId = roundState.getCurrentPlayerId();
        for (ArrayList<Card> playerHand : roundState.getPlayerHands()) {
            playerHand.remove(card);
        }
        roundState.getPlayedCards().get(playerId).add(card);
    }

    /**
     * Check if the trick is full.
     *
     * @param trick current trick
     * @param round current round
     * @return true if the trick is full, false otherwise
     */
    public static boolean trickFull(Trick trick, Round round) {
        return trick.getCards().size() == round.getPlayers().size();
    }

    public static boolean trickFull(RoundState roundState, int trickId) {
        return roundState.getTrickById(trickId).stream().noneMatch(Objects::isNull);
    }

    /**
     * End the current trick.
     * The winning player gets the points of the trick.
     * The starting player of the next trick is the winning player.
     * The cards played of the players are reset.
     *
     * @param trick current trick
     * @param round current round
     */
    public static void endTrick(Trick trick, Round round) {
        Card winningCard = winningCard(trick);
        Player winningPlayer = round.getPlayers().stream().filter(player -> player.getCardPlayed() == winningCard).findFirst().get();
        winningPlayer.addScore(getValue(trick));
        round.setCurrentStartingPlayer(winningPlayer);
        for (Player player : round.getPlayers()) {
            player.setCardPlayed(null);
        }
    }

    public static void endTrick(RoundState roundState) {
        int trickId = roundState.getCurrentTrickId();
        Card winningCard = winningCard(roundState, trickId);
        int winningPlayerId = roundState.getPlayedCards().indexOf(
                roundState.getPlayedCards().stream().filter(cardsByPlayer -> cardsByPlayer.contains(winningCard)).findFirst().orElseThrow());
        roundState.getWinningPlayerIds().add(winningPlayerId);
    }
}

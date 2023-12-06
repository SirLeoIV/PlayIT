package com.group09.playit.state;

import com.group09.playit.model.Card;
import com.group09.playit.model.Deck;

import java.util.ArrayList;
import java.util.List;

public class NodeState {

    private final RoundState roundState;

    public NodeState (
            ArrayList<ArrayList<Card>> playedCards,
            ArrayList<Card> player0Hand,
            ArrayList<Integer> winningPlayerIds,
            ArrayList<String> playerNames,
            int starterPlayerId,
            int playingPlayerId
    ) {

        Deck deck = new Deck(playedCards.size());
        ArrayList<Card> availableCards = deck.getCards();
        availableCards.removeAll(player0Hand);
        for (ArrayList<Card> playerHand : playedCards) {
            availableCards.removeAll(playerHand);
        }

        ArrayList<ArrayList<Card>> playerHands = new ArrayList<>();
        //playerHands.add(player0Hand);

        for (int i = 0; i < playerNames.size(); i++) {
            if (i == playingPlayerId) {
                playerHands.add(player0Hand);
                continue;
            }

            boolean playerMightHaveClubs = true;
            boolean playerMightHaveDiamonds = true;
            boolean playerMightHaveSpades = true;
            boolean playerMightHaveHearts = true;

            for (int trickId = 1; trickId < winningPlayerIds.size(); trickId++) {
                int startingPlayerId = winningPlayerIds.get(trickId - 1);
                Card.Suit suit = playedCards.get(startingPlayerId).get(trickId).suit();
                if (suit != playedCards.get(i).get(trickId).suit()) {
                    switch (suit) {
                        case CLUBS -> playerMightHaveClubs = false;
                        case DIAMONDS -> playerMightHaveDiamonds = false;
                        case SPADES -> playerMightHaveSpades = false;
                        case HEARTS -> playerMightHaveHearts = false;
                    }
                }
            }
            List<Card> playerHand = new ArrayList<>(availableCards);

            boolean finalPlayerMightHaveClubs = playerMightHaveClubs;
            boolean finalPlayerMightHaveDiamonds = playerMightHaveDiamonds;
            boolean finalPlayerMightHaveSpades = playerMightHaveSpades;
            boolean finalPlayerMightHaveHearts = playerMightHaveHearts;

            playerHand = playerHand.stream()
                    .filter(c -> (c.suit() != Card.Suit.CLUBS) || finalPlayerMightHaveClubs)
                    .filter(c -> (c.suit() != Card.Suit.DIAMONDS) || finalPlayerMightHaveDiamonds)
                    .filter(c -> (c.suit() != Card.Suit.SPADES) || finalPlayerMightHaveSpades)
                    .filter(c -> (c.suit() != Card.Suit.HEARTS) || finalPlayerMightHaveHearts)
                    .toList();

            playerHands.add(new ArrayList<>(playerHand));
        }

        roundState = new RoundState(playerNames, playerHands, playedCards, winningPlayerIds);
        roundState.setStartedPlayer(starterPlayerId);
    }

    public static RoundState createRoundStateBasedOn(
            ArrayList<ArrayList<Card>> playedCards,
            ArrayList<Card> player0Hand,
            ArrayList<Integer> winningPlayerIds,
            ArrayList<String> playerNames,
            int startedPlayerId,
            int playingPlayerId
    ) {
        NodeState nodeState = new NodeState(playedCards, player0Hand, winningPlayerIds, playerNames, startedPlayerId, playingPlayerId);
        return nodeState.getRoundState();
    }

    private RoundState getRoundState() {
        return roundState;
    }

}

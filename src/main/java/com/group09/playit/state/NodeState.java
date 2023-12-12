package com.group09.playit.state;

import com.group09.playit.model.Card;
import com.group09.playit.model.Deck;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to create a RoundState based on the current state of the game from the perspective of the player.
 * This class is used to create a RoundState for the root node of the MCTS.
 */
public class NodeState {

    private final RoundState roundState;

    /**
     * Creates a RoundState based on the current state of the game from the perspective of the player.
     * Keeps track of the cards that have been played so far, the cards that the player has in their hand,
     * the ids of the players that have won a trick so far, the names of the players, the id of the player
     * that started the round and the id of the player that is currently playing.
     * <p>
     * The player's hand and cards played are used to determine which cards are still available in the deck.
     * The cards that are still available in the deck are used to determine which cards the other players might have.
     * Every player has four boolean variables that indicate whether they might have a certain suit.
     *
     * @param playedCards The cards that have been played so far.
     * @param player0Hand The cards that the player has in their hand.
     * @param winningPlayerIds The ids of the players that have won a trick so far.
     * @param playerNames The names of the players.
     * @param starterPlayerId The id of the player that started the round.
     * @param playingPlayerId The id of the player that is currently playing.
     */
    public NodeState (
            ArrayList<ArrayList<Card>> playedCards,
            ArrayList<Card> player0Hand,
            ArrayList<Integer> winningPlayerIds,
            ArrayList<String> playerNames,
            int starterPlayerId,
            int playingPlayerId
    ) {

        // Create a deck with the cards that are still available in the deck.
        Deck deck = new Deck(playedCards.size());
        ArrayList<Card> availableCards = deck.getCards();
        availableCards.removeAll(player0Hand);
        for (ArrayList<Card> playerHand : playedCards) {
            availableCards.removeAll(playerHand);
        }

        // Create a list of hands for every player.
        ArrayList<ArrayList<Card>> playerHands = new ArrayList<>();

        for (int i = 0; i < playerNames.size(); i++) {
            // If the player is the player that is currently playing, add their actual hand.
            if (i == playingPlayerId) {
                playerHands.add(player0Hand);
                continue;
            }

            // If the player is not the player that is currently playing, add their possible hand.
            // A possible hand is a hand that does not contain any cards that have been played so far.
            // Every player has four boolean variables that indicate whether they might have a certain suit.
            // These variables are used to filter out cards that the player might not have.
            boolean playerMightHaveClubs = true;
            boolean playerMightHaveDiamonds = true;
            boolean playerMightHaveSpades = true;
            boolean playerMightHaveHearts = true;

            // go through every trick that has been played so far
            for (int trickId = 1; trickId < winningPlayerIds.size(); trickId++) {
                int startingPlayerId = winningPlayerIds.get(trickId - 1);
                Card.Suit suit = playedCards.get(startingPlayerId).get(trickId).suit();

                // switch the boolean variables to false if the player couldn't play the suit of the trick
                if (suit != playedCards.get(i).get(trickId).suit()) {
                    switch (suit) {
                        case CLUBS -> playerMightHaveClubs = false;
                        case DIAMONDS -> playerMightHaveDiamonds = false;
                        case SPADES -> playerMightHaveSpades = false;
                        case HEARTS -> playerMightHaveHearts = false;
                    }
                }
            }
            // initialize empty hand
            List<Card> playerHand = new ArrayList<>(availableCards);

            boolean finalPlayerMightHaveClubs = playerMightHaveClubs;
            boolean finalPlayerMightHaveDiamonds = playerMightHaveDiamonds;
            boolean finalPlayerMightHaveSpades = playerMightHaveSpades;
            boolean finalPlayerMightHaveHearts = playerMightHaveHearts;

            // filter out cards that the player might not have
            playerHand = playerHand.stream()
                    .filter(c -> (c.suit() != Card.Suit.CLUBS) || finalPlayerMightHaveClubs)
                    .filter(c -> (c.suit() != Card.Suit.DIAMONDS) || finalPlayerMightHaveDiamonds)
                    .filter(c -> (c.suit() != Card.Suit.SPADES) || finalPlayerMightHaveSpades)
                    .filter(c -> (c.suit() != Card.Suit.HEARTS) || finalPlayerMightHaveHearts)
                    .toList();

            playerHands.add(new ArrayList<>(playerHand));
        }

        // Create a RoundState based on the newly created playerHands.
        roundState = new RoundState(playerNames, playerHands, playedCards, winningPlayerIds);
        roundState.setStartedPlayer(starterPlayerId);
    }

    /**
     * Creates a RoundState based on the current state of the game from the perspective of the player.
     * @param playedCards The cards that have been played so far.
     * @param player0Hand The cards that the player has in their hand.
     * @param winningPlayerIds The ids of the players that have won a trick so far.
     * @param playerNames The names of the players.
     * @param startedPlayerId The id of the player that started the round.
     * @param playingPlayerId The id of the player that is currently playing.
     * @return A RoundState based on the current state of the game from the perspective of the player.
     */
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

    /**
     * Gets the RoundState.
     * @return The RoundState.
     */
    private RoundState getRoundState() {
        return roundState;
    }

}

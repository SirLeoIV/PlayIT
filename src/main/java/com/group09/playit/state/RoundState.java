package com.group09.playit.state;

import com.group09.playit.logic.TrickService;
import com.group09.playit.model.Card;
import com.group09.playit.model.Hand;
import com.group09.playit.model.Player;
import com.group09.playit.model.Trick;

import java.util.ArrayList;
import java.util.Objects;

/**
 * The class Round state.
 * It is used to store the state of a round and to create a new round from a given state.
 * It stores the player names, the player hands, the played cards and the winning player ids.
 */
public class RoundState {

    private ArrayList<String> playerNames;

    private ArrayList<ArrayList<Card>> playerHands;

    private ArrayList<ArrayList<Card>> playedCards;

    private ArrayList<Integer> winningPlayerIds;

    private Integer startedPlayer;

    /**
     * Gets player names.
     *
     * @return the player names
     */
    public ArrayList<String> getPlayerNames() {
        return playerNames;
    }

    /**
     * Gets player hands.
     *
     * @return the player hands
     */
    public ArrayList<ArrayList<Card>> getPlayerHands() {
        return playerHands;
    }

    /**
     * Gets played cards.
     *
     * @return the played cards
     */
    public ArrayList<ArrayList<Card>> getPlayedCards() {
        return playedCards;
    }

    /**
     * Gets winning player ids.
     *
     * @return the winning player ids
     */
    public ArrayList<Integer> getWinningPlayerIds() {
        return winningPlayerIds;
    }

    @Deprecated // Use the other constructor instead. This leads to empty hands.
    public RoundState(String... names) {
        this.playerNames = new ArrayList<>();
        this.playerHands = new ArrayList<>();
        this.playedCards = new ArrayList<>();
        this.winningPlayerIds = new ArrayList<>();
        for (String name : names) {
            this.playerNames.add(name);
            this.playerHands.add(new ArrayList<>());
            this.playedCards.add(new ArrayList<>());
        }
    }

    /**
     * Use this constructor to create a RoundState for a new round.
     * @param playerHands
     * @param names
     */
    public RoundState(ArrayList<ArrayList<Card>> playerHands, String... names) {
        if (playerHands.size() != names.length) throw new IllegalArgumentException("Number of player hands does not match number of player names");
        this.playerNames = new ArrayList<>();
        this.playerHands = playerHands;
        this.playedCards = new ArrayList<>();
        this.winningPlayerIds = new ArrayList<>();
        for (String name : names) {
            this.playerNames.add(name);
            this.playedCards.add(new ArrayList<>());
        }
    }

    /**
     * Use this constructor to create a RoundState for a round that has already started.
     * @param playerNames
     * @param playerHands
     * @param playedCards
     * @param winningPlayerIds
     */
    public RoundState(ArrayList<String> playerNames, ArrayList<ArrayList<Card>> playerHands, ArrayList<ArrayList<Card>> playedCards, ArrayList<Integer> winningPlayerIds) {
        if (playerNames.size() != playerHands.size() || playerNames.size() != playedCards.size())
            throw playerNumberDoesNotMatchException();
        this.playerNames = playerNames;
        this.playerHands = playerHands;
        this.playedCards = playedCards;
        this.winningPlayerIds = winningPlayerIds;
    }

    /**
     * Use this IllegalArgumentException if the number of player names does not match the
     * number of player hands or played cards
     * @return IllegalArgumentException
     */
    private static IllegalArgumentException playerNumberDoesNotMatchException() {
        return new IllegalArgumentException("Number of player names does not match number of player hands or played cards");
    }

    public void setStartedPlayer(Integer startedPlayer) {
        this.startedPlayer = startedPlayer;
    }

    /**
     * Returns the scores of all players.
     * The scores are calculated by adding up the values of the cards the player won.
     * @return ArrayList<Integer> playerScores
     */
    public ArrayList<Integer> getPlayerScores() {
        ArrayList<Integer> playerScores = new ArrayList<>();
        for (int i = 0; i < playerNames.size(); i++) {
            playerScores.add(0);
        }
        for (int i = 0; i < winningPlayerIds.size(); i++) {
            int trickId = i;
            int playerId = winningPlayerIds.get(i);
            playerScores.set(playerId, playerScores.get(playerId) + playedCards.stream().mapToInt(cards -> cards.get(trickId).getValue()).sum());
        }
        return playerScores;
    }

    /**
     * determines if hearts are broken
     * @return boolean heartsBroken
     */
    public boolean isHeartsBroken() {
        return playedCards.stream().flatMap(ArrayList::stream).anyMatch(card -> card.suit() == Card.Suit.HEARTS);
    }

    /**
     * get the id of the last completed trick
     * @return int lastCompletedTrickId
     */
    public int getLastCompletedTrickId() {
        return winningPlayerIds.size() - 1;
    }

    /**
     * get the id of the current trick
     * @return int currentTrickId
     */
    public int getCurrentTrickId() {
        return getLastCompletedTrickId() + 1;
    }

    /**
     * get the cards of a trick by its id
     * @param id trickId
     * @return ArrayList<Card> cards of the trick
     */
    public ArrayList<Card> getTrickById(int id) {
        ArrayList<Card> trick = new ArrayList<>();
        for (ArrayList<Card> cardsByPlayer : playedCards) {
            try {
                trick.add(cardsByPlayer.get(id));
            } catch (IndexOutOfBoundsException ignore) {
                trick.add(null);
            }
        }
        return trick;
    }

    /**
     * get the trick-model class instance by its id
     * @param trickId
     * @return Trick trick-model
     */
    public Trick getTrickModel(int trickId) {
        Player player = getPlayerModel(trickStartingPlayerId(trickId));
        if (getTrickById(trickId).stream().allMatch(Objects::isNull) || getTrickById(trickId).isEmpty()) {
            return new Trick(player);
        }
        Card.Suit suit = (!winningPlayerIds.isEmpty()) ?
                getTrickById(trickId).get(trickStartingPlayerId(trickId)).suit()
                : Card.Suit.CLUBS;
        Trick trick = new Trick(
                player,
                getTrickById(trickId),
                suit);
        return trick;
    }

    /**
     * get the player-model class instance by its id
     * @param playerId
     * @return Player player-model
     */
    public Player getPlayerModel(int playerId) {
        String name = playerNames.get(playerId);
        int score = getPlayerScores().get(playerId);
        Hand hand = new Hand(playerHands.get(playerId));

        return new Player(name, score, hand);
    }

    /**
     * determines the starting player of a trick by its id
     * @param trickId
     * @return int playerId
     */
    public int trickStartingPlayerId(int trickId) {
        if (trickId == 0) {
            if (startedPlayer == null) {
                startedPlayer = playerHands.indexOf(playerHands.stream().filter(cards -> cards.contains(new Card(Card.Suit.CLUBS, Card.Rank.TWO))).findFirst().orElseThrow());
            }
            return startedPlayer;
        }
        return winningPlayerIds.get(trickId - 1);
    }

    /**
     * get the id of the current player
     * @return int currentPlayerId
     */
    public int getCurrentPlayerId() {
        boolean newTrick = playedCards.stream().allMatch(cardsByPlayer -> cardsByPlayer.size() == getCurrentTrickId());
        if (newTrick) {
             return trickStartingPlayerId(getCurrentTrickId());
        } else {
            int playerId = trickStartingPlayerId(getCurrentTrickId());
            while (playedCards.get(playerId).size() == getCurrentTrickId() + 1) {
                playerId = (playerId + 1) % playedCards.size();
            }
            return playerId;
        }
    }

    /**
     * more readable toString method
     * contains a ot of debug information
     * @param trickId
     * @return String trickString
     */
    public String trickToString(int trickId) {
        StringBuilder trickString = new StringBuilder();
        trickString.append("Trick ").append(trickId).append(" {");
        trickString.append("Started by: ").append(playerNames.get(trickStartingPlayerId(trickId))).append("; ");
        String[] nameCardPairs = new String[playerNames.size()];
        for (int i = 0; i < playedCards.size(); i++) {
            try {
                nameCardPairs[i] = playerNames.get(i) + ": " + playedCards.get(i).get(trickId);
            } catch (IndexOutOfBoundsException ignore) {
                nameCardPairs[i] = playerNames.get(i) + ": " + null;
            }
        }
        trickString.append(String.join(", ", nameCardPairs));
        trickString.append("}");
        return trickString.toString();
    }

    /**
     * clone the round state
     * @return RoundState clone
     */
    @Override
    public RoundState clone() {
        ArrayList<String> playerNamesClone = new ArrayList<>(playerNames);

        ArrayList<ArrayList<Card>> playerHandsClone = new ArrayList<>();
        for (ArrayList<Card> cards : playerHands) {
            playerHandsClone.add(new ArrayList<>(cards));
        }

        ArrayList<ArrayList<Card>> playedCardsClone = new ArrayList<>();
        for (ArrayList<Card> cards : playedCards) {
            playedCardsClone.add(new ArrayList<>(cards));
        }

        ArrayList<Integer> winningPlayerIdsClone = new ArrayList<>(winningPlayerIds);

        RoundState clone = new RoundState(playerNamesClone, playerHandsClone, playedCardsClone, winningPlayerIdsClone);
        clone.startedPlayer = startedPlayer;

        return clone;
    }

    public int trickIdOfCard(Card card) {
        for (int i = 0; i < playedCards.size(); i++) {
            if (playedCards.get(i).contains(card)) {
                return playedCards.get(i).indexOf(card);
            }
        }
        return -1;
    }

    /**
     * get the round state up to a given card
     * if the card is not played in this round, an IllegalArgumentException is thrown
     *
     * @param card
     * @param cardIncluded should the card already be played in the new round state
     * @return RoundState round state up to the given card
     */
    public RoundState getRoundStateUpToGivenCardPlayed(Card card, boolean cardIncluded) {

        // find id of trick the card was played in
        int trickId = -1;
        for (ArrayList<Card> cards : playedCards) {
            if (cards.contains(card)) {
                trickId = cards.indexOf(card);
                break;
            }
        }
        if (trickId == -1) throw new IllegalArgumentException("Card was not played in this round");

        // initialize new lists that represent the new round state
        // we only keep the winning player ids to the trick the card was played in
        ArrayList<Integer> winningPlayerIdsNew = new ArrayList<>(this.winningPlayerIds.subList(0, trickId));
        ArrayList<ArrayList<Card>> playedCardsNew = new ArrayList<>();
        ArrayList<ArrayList<Card>> playerHandsNew = new ArrayList<>();

        // copy the player hands to the new round state
        for (ArrayList<Card> cards : playerHands) {
            playerHandsNew.add(new ArrayList<>(cards));
        }

        // add all the cards before the trick of the given card to the list of played cards
        // and all the cards after the trick of the given card to the list of player hands
        for (int i = 0; i < playedCards.size(); i++) {
            playedCardsNew.add(new ArrayList<>(playedCards.get(i).subList(0, trickId)));
            if (playedCards.get(i).size() > trickId) {
                playerHandsNew.get(i).addAll(playedCards.get(i).subList(trickId + 1, playedCards.get(i).size()));
            }
        }

        // get the id of the player that started the trick the card was played in
        int playerId = trickStartingPlayerId(trickId);
        boolean beforeCardPlayed = true;

        // add all the cards before the given card to the current trick
        for (int i = 0; i < playedCards.size(); i++) {
            try {
                Card cardInTrick = playedCards.get(playerId).get(trickId);
                if (cardInTrick.equals(card)) {
                    beforeCardPlayed = false;
                }
                if (beforeCardPlayed) {
                    playedCardsNew.get(playerId).add(cardInTrick);
                } else {
                    playerHandsNew.get(playerId).add(cardInTrick);
                }
            } catch (IndexOutOfBoundsException ignore) {}
            playerId = (playerId + 1) % playedCards.size();
        }

        // initialize the new round state
        RoundState roundStateNew = new RoundState(new ArrayList<>(playerNames), playerHandsNew, playedCardsNew, winningPlayerIdsNew);
        roundStateNew.startedPlayer = startedPlayer;

        // play the given card if we want to include it
        if (cardIncluded) {
            TrickService.playCard(roundStateNew, card);
            if (TrickService.trickFull(roundStateNew, trickId)) {
                TrickService.endTrick(roundStateNew);
            }
        }
        return roundStateNew;
    }

    /**
     * get the id of the player that started the round
     * @return int startedPlayerId
     */
    public int getStartedPlayerId() {
        return startedPlayer;
    }
}

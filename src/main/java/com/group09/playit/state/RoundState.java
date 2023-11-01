package com.group09.playit.state;

import com.group09.playit.model.*;

import java.util.ArrayList;

public class RoundState {

    private final ArrayList<Player> players;

    private Player currentPlayer;

    private ArrayList<ArrayList<Card>> playedCards;

    private ArrayList<Card> playableCards;


    public RoundState(ArrayList<Player> players, Player currentPlayer) {
        this.players = players;
        this.currentPlayer = currentPlayer;
        this.playedCards = new ArrayList<>();
        for (Player player : players) {
            this.playedCards.add(new ArrayList<>());
        }
        this.playableCards = new ArrayList<>();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public ArrayList<ArrayList<Card>> getPlayedCards() {
        return playedCards;
    }

    public ArrayList<Card> getPlayableCards() {
        return playableCards;
    }

    public void setPlayableCards(ArrayList<Card> playableCards) {
        this.playableCards = playableCards;
    }

    public void addCardToPlayedCardsForCurrentPlayer(Card card) {
        playedCards.get(players.indexOf(currentPlayer)).add(card);
    }

    @Override
    public RoundState clone() {
        RoundState gameState = new RoundState(players, currentPlayer);
        gameState.playedCards = new ArrayList<>();
        for (ArrayList<Card> playedCards : playedCards) {
            gameState.playedCards.add(new ArrayList<>(playedCards));
        }
        gameState.playableCards = new ArrayList<>(playableCards);
        return gameState;
    }

    public void initializeGame() {
        Game game = new Game(1, players.stream().map(Player::getName).toList().toArray(new String[0]));
        game.getPlayers().forEach(player -> player.setHand(new Hand(new ArrayList<>(player.getHand().getCards()))));

        game.getCurrentRound().setCurrentStartingPlayer(currentPlayer);
        game.getCurrentRound().getCurrentTrick().setCurrentPlayer(currentPlayer);

        for (ArrayList<Card> cardsPlayedByPlayer : playedCards) {
            if (game.getCurrentRound().isHeartsBroken()) break;
            for (Card card : cardsPlayedByPlayer) {
                if (card.getSuit() == Card.Suit.HEARTS) {
                    game.getCurrentRound().setHeartsBroken(true);
                    break;
                }
            }
        }

        boolean trickInProgress = false;
        int numberOfTricks = playedCards.get(0).size();
        for (ArrayList<Card> cardsByPlayer : playedCards) {
            if (cardsByPlayer.size() != numberOfTricks) {
                if (cardsByPlayer.size() < numberOfTricks) numberOfTricks = cardsByPlayer.size();
                trickInProgress = true;
                break;
            }
        }

        if (trickInProgress) {
            Trick trick = new Trick(currentPlayer);
            for (ArrayList<Card> cardsByPlayer : playedCards) {
                if (cardsByPlayer.size() > numberOfTricks) {
                    trick.getCards().add(cardsByPlayer.get(numberOfTricks));
                }
            }
            game.getCurrentRound().getTricks().add(trick);
        }
    }
}

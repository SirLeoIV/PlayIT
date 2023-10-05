package com.group09.playit.logic;

import com.group09.playit.model.Card;
import com.group09.playit.model.Player;
import com.group09.playit.model.Round;
import com.group09.playit.model.Trick;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TrickServiceTest {

    @Test
    void testGetValue() {
        // given
        Trick trick = new Trick(new Player("Player1"));
        trick.getCards().add(new Card(Card.Suit.HEARTS, Card.Rank.TWO));
        trick.getCards().add(new Card(Card.Suit.HEARTS, Card.Rank.THREE));
        trick.getCards().add(new Card(Card.Suit.SPADES, Card.Rank.THREE));
        trick.getCards().add(new Card(Card.Suit.SPADES, Card.Rank.QUEEN));

        // when + then
        assertEquals(15, TrickService.getValue(trick));
    }

    @Test
    void testWinningCard() {
        // given
        Trick trick = new Trick(new Player("Player1"));
        Card winningCard = new Card(Card.Suit.HEARTS, Card.Rank.THREE);
        trick.getCards().add(new Card(Card.Suit.HEARTS, Card.Rank.TWO));
        trick.getCards().add(winningCard);
        trick.getCards().add(new Card(Card.Suit.SPADES, Card.Rank.THREE));
        trick.getCards().add(new Card(Card.Suit.SPADES, Card.Rank.QUEEN));

        // when + then
        assertEquals(winningCard, TrickService.winningCard(trick));
    }

    @Test
    void testLegalCardsToPlayWithCardsOfTheSameSuitAvailable() {
        // given
        Player player = new Player("Player1");
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        Round round = new Round(players);
        Trick trick = new Trick(player);
        trick.getCards().add(new Card(Card.Suit.DIAMONDS, Card.Rank.TWO));
        player.getHand().getCards().clear();
        player.getHand().getCards().add(new Card(Card.Suit.DIAMONDS, Card.Rank.THREE));
        player.getHand().getCards().add(new Card(Card.Suit.SPADES, Card.Rank.THREE));
        player.getHand().getCards().add(new Card(Card.Suit.SPADES, Card.Rank.QUEEN));

        // when + then
        assertEquals(1, TrickService.legalCardsToPlay(trick, round, player).size());
        assertEquals(
                new Card(Card.Suit.DIAMONDS, Card.Rank.THREE),
                TrickService.legalCardsToPlay(trick, round, player).get(0));

    }

    @Test
    void testLegalCardsToPlayWithoutCardsOfTheSameSuitAvailable() {
        // given
        Player player = new Player("Player1");
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        Round round = new Round(players);
        Trick trick = new Trick(player);
        trick.getCards().add(new Card(Card.Suit.DIAMONDS, Card.Rank.TWO));
        player.getHand().getCards().clear();
        player.getHand().getCards().add(new Card(Card.Suit.SPADES, Card.Rank.THREE));
        player.getHand().getCards().add(new Card(Card.Suit.SPADES, Card.Rank.QUEEN));
        player.getHand().getCards().add(new Card(Card.Suit.HEARTS, Card.Rank.TWO));

        // when + then
        assertEquals(3, TrickService.legalCardsToPlay(trick, round, player).size());
        assertEquals(
                new Card(Card.Suit.SPADES, Card.Rank.THREE),
                TrickService.legalCardsToPlay(trick, round, player).get(0));
        assertEquals(
                new Card(Card.Suit.SPADES, Card.Rank.QUEEN),
                TrickService.legalCardsToPlay(trick, round, player).get(1));
        assertEquals(
                new Card(Card.Suit.HEARTS, Card.Rank.TWO),
                TrickService.legalCardsToPlay(trick, round, player).get(2));
    }

    @Test
    void testLegalCardsToPlayFirstCardOfTheTrickWithTwoOfClubsInHand() {
        // given
        Player player = new Player("Player1");
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        Round round = new Round(players);
        Trick trick = new Trick(player);
        player.getHand().getCards().clear();
        player.getHand().getCards().add(new Card(Card.Suit.CLUBS, Card.Rank.TWO));
        player.getHand().getCards().add(new Card(Card.Suit.SPADES, Card.Rank.THREE));
        player.getHand().getCards().add(new Card(Card.Suit.SPADES, Card.Rank.QUEEN));
        player.getHand().getCards().add(new Card(Card.Suit.HEARTS, Card.Rank.TWO));

        // when + then
        assertEquals(1, TrickService.legalCardsToPlay(trick, round, player).size());
        assertEquals(
                new Card(Card.Suit.CLUBS, Card.Rank.TWO),
                TrickService.legalCardsToPlay(trick, round, player).get(0));
    }

    @Test
    void testLegalCardsToPlayFirstCardOfTheTrickWithHeartsNotBroken() {
        // given
        Player player = new Player("Player1");
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        Round round = new Round(players);
        Trick trick = new Trick(player);
        player.getHand().getCards().clear();
        player.getHand().getCards().add(new Card(Card.Suit.SPADES, Card.Rank.THREE));
        player.getHand().getCards().add(new Card(Card.Suit.SPADES, Card.Rank.QUEEN));
        player.getHand().getCards().add(new Card(Card.Suit.HEARTS, Card.Rank.TWO));

        // when + then
        assertEquals(2, TrickService.legalCardsToPlay(trick, round, player).size());
        assertEquals(
                new Card(Card.Suit.SPADES, Card.Rank.THREE),
                TrickService.legalCardsToPlay(trick, round, player).get(0));
        assertEquals(
                new Card(Card.Suit.SPADES, Card.Rank.QUEEN),
                TrickService.legalCardsToPlay(trick, round, player).get(1));
    }

    @Test
    void testLegalCardsToPlayFirstCardOfTheTrickWithHeartsBroken() {
        // given
        Player player = new Player("Player1");
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        Round round = new Round(players);
        round.setHeartsBroken(true);
        Trick trick = new Trick(player);
        player.getHand().getCards().clear();
        player.getHand().getCards().add(new Card(Card.Suit.SPADES, Card.Rank.THREE));
        player.getHand().getCards().add(new Card(Card.Suit.SPADES, Card.Rank.QUEEN));
        player.getHand().getCards().add(new Card(Card.Suit.HEARTS, Card.Rank.TWO));

        // when + then
        assertEquals(3, TrickService.legalCardsToPlay(trick, round, player).size());
        assertEquals(
                new Card(Card.Suit.SPADES, Card.Rank.THREE),
                TrickService.legalCardsToPlay(trick, round, player).get(0));
        assertEquals(
                new Card(Card.Suit.SPADES, Card.Rank.QUEEN),
                TrickService.legalCardsToPlay(trick, round, player).get(1));
        assertEquals(
                new Card(Card.Suit.HEARTS, Card.Rank.TWO),
                TrickService.legalCardsToPlay(trick, round, player).get(2));
    }

    @Test
    void testPlayCardAndBreakHearts() {
        // given
        Player player = new Player("Player1");
        Player player2 = new Player("Player2");
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        players.add(player2);
        Round round = new Round(players);
        Trick trick = new Trick(player);
        Card card = new Card(Card.Suit.HEARTS, Card.Rank.TWO);

        // when
        TrickService.playCard(trick, round, card);

        // then
        assertEquals(card, trick.getCards().get(0));
        assertTrue(round.isHeartsBroken());
        assertEquals(player2, trick.getCurrentPlayer());
    }

    @Test
    void testTrickFullWhenTrickIsFull() {
        // given
        Player player = new Player("Player1");
        Player player2 = new Player("Player2");
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        players.add(player2);
        Round round = new Round(players);
        Trick trick = new Trick(player);
        trick.getCards().add(new Card(Card.Suit.HEARTS, Card.Rank.TWO));
        trick.getCards().add(new Card(Card.Suit.HEARTS, Card.Rank.THREE));

        // when + then
        assertTrue(TrickService.trickFull(trick, round));
    }

    @Test
    void testTrickFullWhenTrickIsNotFull() {
        // given
        Player player = new Player("Player1");
        Player player2 = new Player("Player2");
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        players.add(player2);
        Round round = new Round(players);
        Trick trick = new Trick(player);
        trick.getCards().add(new Card(Card.Suit.HEARTS, Card.Rank.TWO));

        // when + then
        assertFalse(TrickService.trickFull(trick, round));
    }

    @Test
    void testEndTrick() {
        // given
        Card winningCard = new Card(Card.Suit.HEARTS, Card.Rank.THREE);
        Player winningPlayer = new Player("Player1");
        winningPlayer.setCardPlayed(winningCard);

        Card losingCard = new Card(Card.Suit.HEARTS, Card.Rank.TWO);
        Player losingPLayer = new Player("Player2");
        losingPLayer.setCardPlayed(losingCard);

        ArrayList<Player> players = new ArrayList<>();
        players.add(winningPlayer);
        players.add(losingPLayer);
        Round round = new Round(players);

        Trick trick = new Trick(winningPlayer);
        trick.getCards().add(winningCard);
        trick.getCards().add(losingCard);

        // when
        TrickService.endTrick(trick, round);

        // then
        assertEquals(winningPlayer, round.getCurrentStartingPlayer());
        assertEquals(2, winningPlayer.getCurrentScore());
        assertNull(winningPlayer.getCardPlayed());
        assertNull(losingPLayer.getCardPlayed());
    }
}
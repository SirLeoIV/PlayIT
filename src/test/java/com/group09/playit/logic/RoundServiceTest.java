package com.group09.playit.logic;

import com.group09.playit.model.Game;
import com.group09.playit.model.Round;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoundServiceTest {

    @Test
    void testNextTrick() {
        // given
        Game game = new Game(50, "Player1", "Player2", "Player3", "Player4");
        Round round = new Round(game.getPlayers());
        assertEquals(1, round.getTricks().size());

        // when
        RoundService.nextTrick(round);

        // then
        assertEquals(2, round.getTricks().size());
    }

    @Test
    void testIsRoundOverWhenRoundIsOver() {
        // given
        Game game = new Game(50, "Player1", "Player2", "Player3", "Player4");
        Round round = new Round(game.getPlayers());
        round.getPlayers().forEach(player -> player.getHand().getCards().clear());

        // when + then
        assertTrue(RoundService.isRoundOver(round));
    }

    @Test
    void testIsRoundOverWhenRoundIsNotOver() {
        // given
        Game game = new Game(50, "Player1", "Player2", "Player3", "Player4");
        Round round = new Round(game.getPlayers());

        // when + then
        assertFalse(RoundService.isRoundOver(round));
    }

    @Test
    void testEndRound() {
        // given
        Game game = new Game(50, "Player1", "Player2", "Player3", "Player4");
        Round round = new Round(game.getPlayers());
        round.getPlayers().forEach(player -> player.setCurrentScore(10));

        // when
        RoundService.endRound(round);

        // then
        round.getPlayers().forEach(player -> {
            assertEquals(1, player.getScores().size());
            assertEquals(10, player.getScores().get(0));
            assertEquals(0, player.getCurrentScore());
        });
    }
}
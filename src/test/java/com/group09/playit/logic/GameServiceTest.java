package com.group09.playit.logic;

import com.group09.playit.model.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    @Test
    void testIsGameOverWhenGameIsOver() {
        // given
        Game game = new Game(50, "Player1", "Player2", "Player3", "Player4");
        game.getPlayers().get(0).getScores().add(50);

        // when + then
        assertTrue(GameService.isGameOver(game));
    }

    @Test
    void testIsGameOverWhenGameIsNotOver() {
        // given
        Game game = new Game(50, "Player1", "Player2", "Player3", "Player4");
        game.getPlayers().get(0).getScores().add(49);

        // when + then
        assertFalse(GameService.isGameOver(game));
    }

    @Test
    void testNewRound() {
        // given
        Game game = new Game(50, "Player1", "Player2", "Player3", "Player4");
        assertTrue(game.getRounds().isEmpty());

        // when
        GameService.newRound(game);

        // then
        assertEquals(1, game.getRounds().size());
    }
}
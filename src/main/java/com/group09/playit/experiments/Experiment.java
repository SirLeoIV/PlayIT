package com.group09.playit.experiments;

import com.group09.playit.logic.DeckService;
import com.group09.playit.model.Card;
import com.group09.playit.monteCarlo.MCTS;
import com.group09.playit.monteCarlo.Node;
import com.group09.playit.simulation.*;
import com.group09.playit.state.NodeState;
import com.group09.playit.state.RoundState;

import java.util.ArrayList;

public class Experiment {
    public static void main (String[] args)throws NoCardsAvailableException {

        Experiment e = new Experiment();
        ArrayList<Integer>  scores = e.getScoresAfterRound();
        System.out.println(scores);
    }

    public ArrayList<Integer>  getScoresAfterRound() throws NoCardsAvailableException
    {
        String[] playerNames = {"player0", "player1", "player2", "player3"};
        RoundState roundState = new RoundState(DeckService.dealCards(playerNames.length), playerNames);
        Simulation simulation = new Simulation(roundState, new RandomAgent(0,null));
        simulation.simulateWithMCTS();

        return simulation.getRoundState().getPlayerScores();
    }
}

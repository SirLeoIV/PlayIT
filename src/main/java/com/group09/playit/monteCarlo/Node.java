package com.group09.playit.monteCarlo;

import com.group09.playit.model.Card;
import com.group09.playit.state.RoundState;
import java.util.ArrayList;

public class Node {
    //state
    //total score
    //number visits
    //parent
    //children

    private RoundState state;
    private int totalScore = 0;
    private int numberVisits = 0;
    private Node parent;

    private ArrayList<Node> children;
    public Node(RoundState state, ArrayList<Card> playableCards){
        this.state = state;
    }

    public Node(RoundState state, ArrayList<Card> playableCards, Node parent){
        this.state = state;
        this.parent = parent;
    }

    private void inititalizeChildren(RoundState state, Node parent, ArrayList<Card> playableCards){
        //do a trick to determine the next starting player for the childState
        for (int i = 0; i < playableCards.size();i++){
            RoundState childState = state.clone();
            ArrayList<Card> tempCards = (ArrayList<Card>) playableCards.clone();
            tempCards.remove(i);
            childState.setPlayableCards(tempCards);
        }
    }

}

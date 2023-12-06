package com.group09.playit.monteCarlo;

import com.group09.playit.simulation.RandomAgent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NodeTest {

    @Test
    void testWithDifferentScores() {
        Node tree = createTree(1, 0, 1, 0);
        assertEquals(tree.getChildren().get(1), tree.select());
    }

    @Test
    void testWithEqualScore() {
        Node tree = createTree(1, 1, 1, 1);
        assertEquals(tree.getChildren().get(0), tree.select());
    }

    @Test
    void testWithDifferentScoreAndSameVisit() {
        Node tree = createTree(1, 2, 1, 1);
        assertEquals(tree.getChildren().get(0), tree.select());
    }

    @Test
    void testWithEqualScoreAndDifferentVisit() {
        Node tree = createTree(1, 1, 2, 1);
        assertEquals(tree.getChildren().get(1), tree.select());
    }

    private Node createTree(int score1, int score2, int visits1, int visits2) {
        Node root = emptyNode();

        Node child1 = emptyNode();
        child1.setTotalScore(score1);
        child1.setNumberVisits(visits1);
        child1.setParent(root);

        Node child2 = emptyNode();
        child2.setTotalScore(score2);
        child2.setNumberVisits(visits2);
        child2.setParent(root);

        root.getChildren().add(child1);
        root.getChildren().add(child2);
        root.setNumberVisits(visits1 + visits2);

        return root;
    }

    private Node emptyNode() {
        return new Node(null, null, null, new RandomAgent(0, null), 12,0);
    }
}
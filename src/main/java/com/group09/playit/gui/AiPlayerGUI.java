package com.group09.playit.gui;

import com.group09.playit.controller.GameController;
import com.group09.playit.model.Card;
import com.group09.playit.model.Player;
import com.group09.playit.monteCarlo.MCTS;
import com.group09.playit.monteCarlo.Node;
import com.group09.playit.simulation.SimpleAgent;
import com.group09.playit.state.NodeState;
import com.group09.playit.state.RoundState;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * The type Hand gui.
 */
public class AiPlayerGUI extends Parent {

    private final GameController controller;
    private Card card = null;

    /**
     * Instantiates a new Hand GUI.
     * The hand GUI shows the cards of the player.
     * The cards are clickable and can be played.
     *
     * @param player     the current player
     * @param controller the game controller
     */
    public AiPlayerGUI(Player player, GameController controller) {
        this.controller = controller;

        Text playerName = new Text(player.getName() + " (AI) is playing");
        playerName.setLayoutY(0);
        getChildren().add(playerName);

        Button determineCard = new Button("Determine card");
        determineCard.setLayoutY(30);

        determineCard.setOnAction(e -> {

            determineCard.setDisable(true);
            playerName.setText(player.getName() + " (AI) is deciding");

            card = determineCard(player);

            playerName.setText(player.getName() + " (AI) decided");

            Text cardText = new Text("AI plays: " + card.toString());
            cardText.setLayoutY(100);
            getChildren().add(cardText);

            Button nextPlayer = new Button("Play Card + Next player");
            nextPlayer.setLayoutY(30);
            nextPlayer.setOnAction(e2 -> controller.playCard(card));

            getChildren().addAll(nextPlayer);
            getChildren().remove(determineCard);
        });

        getChildren().addAll(determineCard);
    }

    private Card determineCard(Player player) {
        if (controller.legalCardsToPlay().size() <= 1) {
            return controller.legalCardsToPlay().get(0);
        }
        RoundState roundState = controller.getCurrentRoundState().clone(); // .getRoundStateUpToGivenCardPlayed(controller.getLastPlayedCard(), true);

        Node root = new Node(
                NodeState.createRoundStateBasedOn(
                        roundState.getPlayedCards(),
                        roundState.getPlayerHands().get(roundState.getPlayerNames().indexOf(player.getName())),
                        roundState.getWinningPlayerIds(),
                        roundState.getPlayerNames(),
                        roundState.getStartedPlayerId(),
                        roundState.getPlayerNames().indexOf(player.getName())
                ),
                null,
                new SimpleAgent(0, null),
                6, roundState.getPlayerNames().indexOf(player.getName()), false);

        MCTS mcts = new MCTS(root);
        return mcts.traverse(3);
    }

    /**
     * Play card.
     * Plays the card that was clicked.
     *
     * @param card the card
     */
    public void playCard(Card card) {
        controller.playCard(card);
    }
}

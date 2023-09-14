package com.group09.playit;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HeartsController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
package src.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StartScreenView extends VBox {

    private final Button playButton = new Button("Play");
    private final Button highscoreButton = new Button("Highscore");
    private final Button optionsButton = new Button("Options");
    private final Button helpButton = new Button("Help");
    private final Button exitButton = new Button("Exit");

    public StartScreenView() {
        setSpacing(10);
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: rgba(0,0,0,0.6);");

        playButton.getStyleClass().add("game-button");

        highscoreButton.getStyleClass().add("game-button");

        helpButton.getStyleClass().add("game-button");

        optionsButton.getStyleClass().add("game-button");

        exitButton.getStyleClass().add("game-button");
        exitButton.getStyleClass().add("exit-button");

        Label title = new Label("Snake");
        title.getStyleClass().add("game-title");

        getChildren().addAll(title, playButton, highscoreButton, helpButton, optionsButton, exitButton);
        setVisible(true);
    }

    public Button getPlayButton() {
        return playButton;
    }

    public Button getHighscoreButton() {
        return highscoreButton;
    }

    public Button getOptionsButton() {
        return optionsButton;
    }

    public Button getHelpButton() {
        return helpButton;
    }

    public Button getExitButton() {
        return exitButton;
    }
}
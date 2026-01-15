package src.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GameOverView extends VBox {

    private final Button restartButton = new Button("Restart?");
    private final Button exitButton = new Button("Exit");

    public GameOverView() {
        setSpacing(10);
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: rgba(0,0,0,0.6);");

        restartButton.getStyleClass().add("game-button");
        exitButton.getStyleClass().add("game-button");
        exitButton.getStyleClass().add("exit-button");

        Label title = new Label("Game Over");
        title.getStyleClass().add("game-title");

        getChildren().addAll(title, restartButton, exitButton);
        setVisible(false); // hidden by default
    }

    public Button getRestartButton() {
        return restartButton;
    }

    public Button getExitButton() {
        return exitButton;
    }
}

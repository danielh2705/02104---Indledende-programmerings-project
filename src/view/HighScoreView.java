package src.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HighScoreView extends VBox {

    private final Button backButton = new Button("Back");

    public HighScoreView() {
        setSpacing(10);
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: rgba(0,0,0,0.6);");

        backButton.getStyleClass().add("game-button");
        backButton.getStyleClass().add("exit-button");

        Label title = new Label("Highscore");
        title.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");

        getChildren().addAll(title, backButton);
        setVisible(false); // hidden by default
    }

    public Button getBackButton() {
        return backButton;
    }
}

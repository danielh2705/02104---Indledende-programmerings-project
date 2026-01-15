package src.view;

import java.util.List;

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
        backButton.getStyleClass().add("exit-button");;

        Label title = new Label("Highscores");
        title.getStyleClass().add("title");
        getChildren().addAll(title, backButton);

        setVisible(false); // hidden by default
    }

    public void showScores(List<Integer> scores) {
        getChildren().clear();

        Label title = new Label("Highscores");
        title.getStyleClass().add("game-title");
        getChildren().add(title);

        for (int i = 0; i < scores.size(); i++) {
            Label label = new Label((i + 1) + ". " + scores.get(i));
            label.getStyleClass().add("score-entry");
            getChildren().add(label);
        }

        getChildren().add(backButton);
    }

    public Button getBackButton() {
        return backButton;
    }
}

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Label;

// THIS CLASS IS ONLY USED FOR THE LOOSE POPUP WINDOW

public class LoseController {
    // IMPORTS ELEMENTS FROM GUI FILE
    @FXML
    private Button exitButton;

    @FXML
    private Pane losePane;

    @FXML
    private Label highscoreLabel;

    @FXML
    // written by Daniel
    // CLOSES POPUP
    void exit(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();

    }

    // written by Daniel
    // MAKES THE HIGHSCORE LABEL AVAILABLE TO OTHER CLASSES
    public Label getHighscoreLabel() {
        return highscoreLabel;
    }
}

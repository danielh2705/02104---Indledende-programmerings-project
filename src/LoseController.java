package src;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class LoseController {
    @FXML
    private Button exitButton;

    @FXML
    private Pane losePane;
    
    @FXML
    private Label highscoreLabel;

    @FXML
    void exit(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();

    }

    public Label getHighscoreLabel(){
        return highscoreLabel;
    }
}

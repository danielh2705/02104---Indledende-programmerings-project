package src;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class Snakecontroller {
    private Snakeveiw viewer;
    private Snakemodel model;
    private EventHandler<KeyEvent> eventHandler;

    @FXML
    private Pane losePane;

    @FXML
    private Button button1;

    @FXML
    private Pane gameField;

    @FXML
    private Text scoreLabel;

    @FXML
    void move(KeyEvent event) {
        model.moveSnake(event.getCode().toString());
        if (checkLost() == true) {
            System.out.println("YOU LOST!");
            looseGame();
        }
        if (model.getSnake().get(0).equals(model.getApple())) {
            model.consumedApple();
        }
        viewer.update();

    }

    public boolean checkLost() {

        ArrayList<Point> headlessSnake = new ArrayList<Point>(model.getSnake());
        headlessSnake.removeFirst();
        for (Point bodyPart : headlessSnake) {
            if (model.getSnake().get(0).equals(bodyPart)) {
                return true;
            }
        }
        return false;
    }

    public void looseGame() {
        // viewer.exit(button1);
        try {
            viewer.switchToLoseScreen(model.getScore());
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    public Pane getGamePane() {
        return this.gameField;
    }

    public Text getScoreLabel() {
        return scoreLabel;
    }

    public EventHandler<KeyEvent> getEventHandler() {
        return eventHandler;
    }

    public void setEventHandler(EventHandler<KeyEvent> eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void setModelAndView(Snakemodel model, Snakeveiw viewer) {
        this.model = model;
        this.viewer = viewer;
    }

    public Button getButton1() {
        return button1;
    }
}
package src;

import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.*;
import javafx.event.*;
import javafx.util.*;
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


    public void startGameLoop() {
        // THE GAME LOOP / TIMER
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(150), e -> {
                    model.moveSnake();
                    // CHECKS IF THE PLAYER LOST
                    if (checkLost() == true) {
                        System.out.println("YOU LOST!");
                        looseGame();
                    }
                    // CHECKS IS THE SNAKES HEAD IS ON THE APPLE IF SO GROWS THE SNAKE
                    if (model.getSnake().get(0).equals(model.getApple())) {
                        model.consumedApple();
                    }
                    viewer.update();

                }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }
    // MOVES THE SNAKE WITH SET DIRECTION
    public void moveWithDirection(){
        model.moveSnake();
    }
    // CHANGES THE DIRECITON THE SNAKE IS MOVING
    @FXML
    void changeDirection(KeyEvent event) {
        model.changeDirection(event.getCode().toString());
    }

    public boolean checkLost() {
        ArrayList<Point> headlessSnake = new ArrayList<Point>(model.getSnake());
        headlessSnake.removeFirst();
        // CHEACKES IF THE HEAD IS TOUCHING ANY OTHER PART OF THE BODY
        for (Point bodyPart : headlessSnake) {
            if (model.getSnake().get(0).equals(bodyPart)) {
                return true;
            }
        }
        return false;
    }

    public void looseGame() {
        // SWITCHES TO THE LOSE SCREEN IF GAME IS LOST
        try {
            viewer.switchToLoseScreen(model.getScore());
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    // THE REST GIVES ACCES TO OTHER CLASSES TO THE UI ELEMENTS
    // AND OTHER SETUP

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
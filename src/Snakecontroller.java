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
    private Timeline badAppleLife;
    private Timeline badAppleRespawn;
    private Timeline bomb;
    private Timeline bombRespawn;
    private Timeline timeline;
    private Timeline speedBoostTimer;
    private Timeline speedAppleRespawn;
    private Timeline goldenAppleSpawner;
    private Timeline goldenAppleLife;
    private Timeline bonusApplesLife;
    private EventHandler<KeyEvent> eventHandler;
    private boolean controlsReversed = false;
    private Timeline funkyAppleTimer;
    private Timeline funkyAppleRespawn;
    private Timeline funkyAppleLife;

    @FXML
    private Pane losePane;

    @FXML
    private Button button1;

    @FXML
    private Pane gameField;

    @FXML
    private Text scoreLabel;

    // written by Daniel & Adel & Adrian
    public void startGameLoop() {
        // THE GAME LOOP / TIMER
        startBombCycle();
        startBadAppleCycle();
        startSpeedAppleCycle();
        startGoldenAppleCycle();
        startFunkyAppleCycle();

        timeline = new Timeline(
                new KeyFrame(Duration.millis(150), e -> {
                    model.moveSnake();
                    model.snakeCanTurn();
                    if (model.getBomb() != null && model.getSnake().get(0).equals(model.getBomb())) {
                        looseGame();
                        return;
                    }
                    // CHECKS IF THE PLAYER LOST
                    if (checkLost() == true) {
                        System.out.println("YOU LOST!");
                        looseGame();
                    }
                    // CHECKS IS THE SNAKES HEAD IS ON THE APPLE IF SO GROWS THE SNAKE
                    if (model.getSnake().get(0).equals(model.getApple())) {
                        model.consumedApple();
                    }
                    // BONUS APPLES (FROM GOLDENAPPLE)
                    for (int i = 0; i < model.getBonusApples().size(); i++) {
                        if (model.getSnake().get(0).equals(model.getBonusApples().get(i))) {
                            model.getSnakeObject().grow();
                            model.increaseScore(1);
                            model.getBonusApples().remove(i);
                            break;
                        }
                    }

                    if (model.getSpeedApple() != null && model.getSnake().get(0).equals(model.getSpeedApple())) {
                        model.consumedSpeedApple();
                        activateSpeedBoost();
                    }
                    if (model.getGoldenApple() != null && model.getSnake().get(0).equals(model.getGoldenApple())) {
                        model.consumedGoldenApple();
                        activateGoldenEffect();
                    }
                    // FUNKY APPLE
                    if (model.getFunkyApple() != null && model.getSnake().get(0).equals(model.getFunkyApple())) {
                        model.consumedFunkyApple();
                        activateFunkyControls();
                    }

                    if (model.getBadApple() != null && model.getSnake().get(0).equals(model.getBadApple())) {

                        // calculate new score first
                        int newScore = model.getScore() - 5;

                        // if score would drop below 2 → die
                        if (newScore < 2) {
                            looseGame();
                            return;
                        }

                        // apply penalty
                        model.setScore(newScore);
                        model.getSnakeObject().shrink(5);
                        model.consumedBadApple();
                    }
                    viewer.update();

                }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }

    // written by Daniel
    // MOVES THE SNAKE WITH SET DIRECTION
    public void moveWithDirection() {
        model.moveSnake();
    }

    // written by Daniel
    // CHANGES THE DIRECITON THE SNAKE IS MOVING
    @FXML
    void changeDirection(KeyEvent event) {
        String direction = event.getCode().toString();

        if (controlsReversed) {
            direction = reverseDirection(direction);
        }

        model.changeDirection(direction);
    }

    // written by Adel
    private String reverseDirection(String dir) {
        switch (dir) {
            case "UP":
                return "DOWN";
            case "DOWN":
                return "UP";
            case "LEFT":
                return "RIGHT";
            case "RIGHT":
                return "LEFT";
            default:
                return dir;
        }
    }

    // written by Daniel
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

    // written by Adel
    private void startBadAppleCycle() {
        model.spawnBadApple();

        badAppleLife = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> {
                    model.consumedBadApple();
                    scheduleBadAppleRespawn();
                }));
        badAppleLife.play();
    }

    // written by Adel
    private void startSpeedAppleCycle() {
        model.spawnSpeedApple();

        speedAppleRespawn = new Timeline(
                new KeyFrame(Duration.seconds(11), e -> startSpeedAppleCycle()));
        speedAppleRespawn.play();
    }

    // written by Adel
    private void scheduleBadAppleRespawn() {
        badAppleRespawn = new Timeline(
                new KeyFrame(Duration.seconds(10), e -> startBadAppleCycle()));
        badAppleRespawn.play();
    }

    // written by Adel
    private void startBombCycle() {
        model.spawnBomb(); // spawn immediately

        bomb = new Timeline(
                new KeyFrame(Duration.seconds(10), e -> model.spawnBomb()));
        bomb.setCycleCount(Timeline.INDEFINITE);
        bomb.play();
    }

    // written by Adel
    private void activateSpeedBoost() {
        timeline.setRate(2.0); // 2x speed

        if (speedBoostTimer != null)
            speedBoostTimer.stop();

        speedBoostTimer = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> timeline.setRate(1.0)));
        speedBoostTimer.play();
    }

    // written by Adel
    private void activateGoldenEffect() {
        model.spawnBonusApples(10);

        if (bonusApplesLife != null)
            bonusApplesLife.stop();
        bonusApplesLife = new Timeline(
                new KeyFrame(Duration.seconds(15), e -> model.clearBonusApples()));
        bonusApplesLife.play();
    }

    // written by Adel
    private void startGoldenAppleCycle() {
        spawnGoldenNow();

        goldenAppleSpawner = new Timeline(
                new KeyFrame(Duration.seconds(60), e -> spawnGoldenNow()));
        goldenAppleSpawner.setCycleCount(Timeline.INDEFINITE);
        goldenAppleSpawner.play();
    }

    // written by Adel
    private void activateFunkyControls() {
        controlsReversed = true;

        if (funkyAppleTimer != null)
            funkyAppleTimer.stop();

        funkyAppleTimer = new Timeline(
                new KeyFrame(Duration.seconds(7), e -> controlsReversed = false));
        funkyAppleTimer.play();
    }

    // written by Daniel
    public void looseGame() {
        // SWITCHES TO THE LOSE SCREEN IF GAME IS LOST
        try {
            viewer.switchToLoseScreen(model.getScore());
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    // written by Adel
    private void spawnGoldenNow() {
        model.spawnGoldenApple();

        if (goldenAppleLife != null)
            goldenAppleLife.stop();
        goldenAppleLife = new Timeline(
                new KeyFrame(Duration.seconds(6), e -> model.consumedGoldenApple()));
        goldenAppleLife.play();
    }

    // written by Adel
    private void startFunkyAppleCycle() {
        model.spawnFunkyApple();

        funkyAppleLife = new Timeline(
                new KeyFrame(Duration.seconds(6), e -> {
                    model.consumedFunkyApple();
                    scheduleFunkyAppleRespawn();
                }));
        funkyAppleLife.play();
    }

    // written by Adel
    private void scheduleFunkyAppleRespawn() {
        funkyAppleRespawn = new Timeline(
                new KeyFrame(Duration.seconds(20), e -> startFunkyAppleCycle()));
        funkyAppleRespawn.play();
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
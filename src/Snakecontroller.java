package src;

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
    private Snakeview viewer;
    private Snakemodel model;
    private Timeline poisonAppleLife;
    private Timeline poisonAppleRespawn;
    private Timeline bomb;
    private Timeline timeline;
    private Timeline speedBoostTimer;
    private Timeline coconutRespawn;
    private Timeline starSpawner;
    private Timeline starLife;
    private Timeline bonusApplesLife;
    private EventHandler<KeyEvent> eventHandler;
    private boolean controlsReversed = false;
    private Timeline mushroomTimer;
    private Timeline mushroomRespawn;
    private Timeline mushroomLife;

    @FXML
    private Pane losePane;

    @FXML
    private Button button1;

    @FXML
    private Pane gameField;

    @FXML
    private Text scoreLabel;

    // written by Daniel & Adel & Adrian & Aran
    public void startGameLoop() {
        // THE GAME LOOP / TIMER
        startBombCycle();
        startPoisonAppleCycle();
        startCoconutCycle();
        startStarCycle();
        startMusroomCycle();

        timeline = new Timeline(
                new KeyFrame(Duration.millis(150), e -> {
                    model.moveSnake();
                    model.snakeCanTurn();
                    if (model.getBomb() != null && model.getSnake().get(0).equals(model.getBomb())) {
                        SfxPlayer.audioPlayer("EXPLOSION", 1.0);
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
                            SfxPlayer.audioPlayer("APPLECONSUME", 1.0);
                            model.getSnakeObject().grow();
                            model.increaseScore(1);
                            model.getBonusApples().remove(i);
                            break;
                        }
                    }

                    if (model.getCoconut() != null && model.getSnake().get(0).equals(model.getCoconut())) {
                        model.consumedCoconut();
                        activateSpeedBoost();
                    }
                    if (model.getStar() != null && model.getSnake().get(0).equals(model.getStar())) {
                        SfxPlayer.audioPlayer("STARCONSUME", 1.0);
                        model.consumedStar();
                        activateStarEffect();
                    }
                    // FUNKY APPLE
                    if (model.getMushroom() != null && model.getSnake().get(0).equals(model.getMushroom())) {
                        model.consumedMushroom();
                        activateMushroomControls();
                    }

                    if (model.getPoisonApple() != null && model.getSnake().get(0).equals(model.getPoisonApple())) {

                        // calculate new score first
                        int newScore = model.getScore() - 5;

                        // if score would drop below 2 → die
                        if (newScore < 2) {
                            SfxPlayer.audioPlayer("POISONSHROOMCONSUME", 1.0);
                            looseGame();
                            return;
                        }

                        // apply penalty
                        SfxPlayer.audioPlayer("POISONSHROOMCONSUME", 1.0);
                        model.setScore(newScore);
                        model.getSnakeObject().shrink(5);
                        model.consumedPoisonApple();
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
    private void startPoisonAppleCycle() {
        model.spawnPoisonApple();

        poisonAppleLife = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> {
                    model.consumedPoisonApple();
                    schedulePoisonAppleRespawn();
                }));
        poisonAppleLife.play();
    }

    // written by Adel
    private void startCoconutCycle() {
        model.spawnCoconut();

        coconutRespawn = new Timeline(
                new KeyFrame(Duration.seconds(11), e -> startCoconutCycle()));
        coconutRespawn.play();
    }

    // written by Adel
    private void schedulePoisonAppleRespawn() {
        poisonAppleRespawn = new Timeline(
                new KeyFrame(Duration.seconds(10), e -> startPoisonAppleCycle()));
        poisonAppleRespawn.play();
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
    private void activateStarEffect() {
        model.spawnBonusApples(10);

        if (bonusApplesLife != null)
            bonusApplesLife.stop();
        bonusApplesLife = new Timeline(
                new KeyFrame(Duration.seconds(15), e -> model.clearBonusApples()));
        bonusApplesLife.play();
    }

    // written by Adel
    private void startStarCycle() {
        spawnStarNow();

        starSpawner = new Timeline(
                new KeyFrame(Duration.seconds(60), e -> spawnStarNow()));
        starSpawner.setCycleCount(Timeline.INDEFINITE);
        starSpawner.play();
    }

    // written by Adel
    private void activateMushroomControls() {
        controlsReversed = true;

        if (mushroomTimer != null)
            mushroomTimer.stop();

        mushroomTimer = new Timeline(
                new KeyFrame(Duration.seconds(7), e -> controlsReversed = false));
        mushroomTimer.play();
    }

    // written by Daniel
    public void looseGame() {
        // SWITCHES TO THE LOSE SCREEN IF GAME IS LOST
        SfxPlayer.audioPlayer("GAMEOVER", 1.0);
        try {
            viewer.switchToLoseScreen(model.getScore());
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    // written by Adel
    private void spawnStarNow() {
        model.spawnStar();

        if (starLife != null)
            starLife.stop();
        starLife = new Timeline(
                new KeyFrame(Duration.seconds(6), e -> model.consumedStar()));
        starLife.play();
    }

    // written by Adel
    private void startMusroomCycle() {
        model.spawnMushroom();

        mushroomLife = new Timeline(
                new KeyFrame(Duration.seconds(6), e -> {
                    model.consumedMushroom();
                    scheduleMusroomRespawn();
                }));
        mushroomLife.play();
    }

    // written by Adel
    private void scheduleMusroomRespawn() {
        mushroomRespawn = new Timeline(
                new KeyFrame(Duration.seconds(20), e -> startMusroomCycle()));
        mushroomRespawn.play();
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

    public void setModelAndView(Snakemodel model, Snakeview viewer) {
        this.model = model;
        this.viewer = viewer;
    }

    public Button getButton1() {
        return button1;
    }

}
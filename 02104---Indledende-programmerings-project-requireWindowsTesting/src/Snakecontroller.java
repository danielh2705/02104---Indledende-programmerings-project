package src;

import java.util.ArrayList;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.*;
import javafx.util.*;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;

public class Snakecontroller {
    private Snakeveiw viewer;
    private Snakemodel model;

    private Timeline poisonAppleLife;
    private Timeline poisonAppleRespawn;
    private Timeline bomb;
    private Timeline bombRespawn;
    private Timeline timeline;
    private Timeline coconutTimer;
    private Timeline coconutRespawn;
    private Timeline starSpawner;
    private Timeline starLife;
    private Timeline bonusApplesLife;
    private Timeline mushroomTimer;
    private Timeline mushroomRespawn;
    private Timeline mushroomLife;

    private EventHandler<KeyEvent> eventHandler;

    private boolean controlsReversed = false;
    private boolean lateGameActivated = false;

    @FXML
    private Pane startScreen;
    @FXML
    private Pane gameScreen;
    @FXML
    private Pane highscoreScreen;
    @FXML
    private Pane helpScreen;
    @FXML
    private Pane optionsScreen;
    @FXML
    private Pane gameOverScreen;
    @FXML
    private Pane selectScreen;

    @FXML
    private Pane gameField;

    @FXML
    private Text scoreLabel;

    public void startGameLoop() {
        // THE GAME LOOP / TIMER
        stopAllTimelines();
                lateGameActivated = false;
        // Delay the spawn of the consumables at the beginning
        if (!model.isLateGame()) {
            new Timeline(new KeyFrame(Duration.seconds(1.5), e -> startPoisonAppleCycle())).play();
            new Timeline(new KeyFrame(Duration.seconds(3), e -> startCoconutCycle())).play();
            new Timeline(new KeyFrame(Duration.seconds(4.5), e -> startStarCycle())).play();
            new Timeline(new KeyFrame(Duration.seconds(6), e -> startMushroomCycle())).play();
            if ("medium".equals(model.getLevelSize()) || "large".equals(model.getLevelSize())) {
                new Timeline(new KeyFrame(Duration.seconds(7.5), e -> startBombCycle())).play();
            }
        }


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

                    if (model.getCoconut() != null && model.getSnake().get(0).equals(model.getCoconut())) {
                        model.consumedCoconut();
                        activateCoconut();
                    }
                    if (model.getStar() != null && model.getSnake().get(0).equals(model.getStar())) {
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
                            looseGame();
                            return;
                        }

                        // apply penalty
                        model.setScore(newScore);
                        model.getSnakeObject().shrink(5);
                        model.consumedPoisonApple();
                    }
                    if (model.isLateGame() && !lateGameActivated) {
                        lateGameActivated = true;

                        // stop special spawn-cycles (men ikke selve game-loopet)
                        if (poisonAppleLife != null) poisonAppleLife.stop();
                        if (poisonAppleRespawn != null) poisonAppleRespawn.stop();
                        if (bomb != null) bomb.stop();
                        if (coconutRespawn != null) coconutRespawn.stop();
                        if (starSpawner != null) starSpawner.stop();
                        if (starLife != null) starLife.stop();
                        if (bonusApplesLife != null) bonusApplesLife.stop();
                        if (mushroomRespawn != null) mushroomRespawn.stop();
                        if (mushroomLife != null) mushroomLife.stop();

                        // fjern items der allerede ligger
                        model.consumedPoisonApple();
                        model.consumedBomb();
                        model.consumedCoconut();
                        model.consumedStar();
                        model.clearBonusApples();
                        model.consumedMushroom();
                    }

                    viewer.update();

                }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    // MOVES THE SNAKE WITH SET DIRECTION
    public void moveWithDirection() {
        model.moveSnake();
    }

    // CHANGES THE DIRECITON THE SNAKE IS MOVING
    @FXML
    void changeDirection(KeyEvent event) {
        String direction = event.getCode().toString();

        if (controlsReversed) {
            direction = reverseDirection(direction);
        }

        model.changeDirection(direction);
    }

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

    private void startPoisonAppleCycle() {
        model.spawnPoisonApple();

        poisonAppleLife = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> {
                    model.consumedPoisonApple();
                    schedulePoisonAppleRespawn();
                }));
        poisonAppleLife.play();
    }

    private void startCoconutCycle() {
        model.spawnCoconut();

        coconutRespawn = new Timeline(
                new KeyFrame(Duration.seconds(11), e -> startCoconutCycle()));
        coconutRespawn.play();
    }

    private void schedulePoisonAppleRespawn() {
        poisonAppleRespawn = new Timeline(
                new KeyFrame(Duration.seconds(10), e -> startPoisonAppleCycle()));
        poisonAppleRespawn.play();
    }

    private void startBombCycle() {
        model.spawnBomb(); // spawn immediately

        bomb = new Timeline(
                new KeyFrame(Duration.seconds(10), e -> model.spawnBomb()));
        bomb.setCycleCount(Timeline.INDEFINITE);
        bomb.play();
        
    }

    private void activateCoconut() {
        timeline.setRate(2.0); // 2x speed

        if (coconutTimer != null)
            coconutTimer.stop();

        coconutTimer = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> timeline.setRate(1.0)));
        coconutTimer.play();
    }

    private void activateStarEffect() {
        model.spawnBonusApples(10);

        if (bonusApplesLife != null)
            bonusApplesLife.stop();
        bonusApplesLife = new Timeline(
                new KeyFrame(Duration.seconds(15), e -> model.clearBonusApples()));
        bonusApplesLife.play();
    }

    private void startStarCycle() {
        spawnStarNow();

        starSpawner = new Timeline(
                new KeyFrame(Duration.seconds(60), e -> spawnStarNow()));
        starSpawner.setCycleCount(Timeline.INDEFINITE);
        starSpawner.play();
    }

    private void activateMushroomControls() {
        controlsReversed = true;

        if (mushroomTimer != null)
            mushroomTimer.stop();

        mushroomTimer = new Timeline(
                new KeyFrame(Duration.seconds(7), e -> controlsReversed = false));
        mushroomTimer.play();
    }

    public void looseGame() {
        // SWITCHES TO THE LOSE SCREEN IF GAME IS LOST
        timeline.pause();
        gameOverScreen.setVisible(true);
    }

    private void spawnStarNow() {
        model.spawnStar();

        if (starLife != null)
            starLife.stop();
        starLife = new Timeline(
                new KeyFrame(Duration.seconds(6), e -> model.consumedStar()));
        starLife.play();
    }

    private void startMushroomCycle() {
        model.spawnMushroom();

        mushroomLife = new Timeline(
                new KeyFrame(Duration.seconds(6), e -> {
                    model.consumedMushroom();
                    scheduleMushroomRespawn();
                }));
        mushroomLife.play();
    }

    private void scheduleMushroomRespawn() {
        mushroomRespawn = new Timeline(
                new KeyFrame(Duration.seconds(20), e -> startMushroomCycle()));
        mushroomRespawn.play();
    }

    //Makes sure to stop all timelines that has been started
    private void stopAllTimelines() {
        if (timeline != null)
            timeline.stop();

        if (poisonAppleLife != null)
            poisonAppleLife.stop();

        if (poisonAppleRespawn != null)
            poisonAppleRespawn.stop();

        if (bomb != null)
            bomb.stop();

        if (bombRespawn != null)
            bombRespawn.stop();

        if (coconutTimer != null)
            coconutTimer.stop();

        if (coconutRespawn != null)
            coconutRespawn.stop();

        if (starSpawner != null)
            starSpawner.stop();

        if (starLife != null)
            starLife.stop();

        if (bonusApplesLife != null)
            bonusApplesLife.stop();

        if (mushroomTimer != null)
            mushroomTimer.stop();

        if (mushroomRespawn != null)
            mushroomRespawn.stop();

        if (mushroomLife != null)
            mushroomLife.stop();
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

    //Setting the size of the Level
    //titles and TileSize are kinda hard-coded to fit in perfectly, not optimal solution 
    @FXML
    public void setSmall() {
        int tiles = 14;
        viewer.setTileSize(36);

        model.setLevelSize("small");

        model.setGridSize(tiles, tiles);
        gameField.setPrefSize(tiles * viewer.tileSize, tiles * viewer.tileSize);
        showGameScreen(); 
    }

    @FXML
    public void setMedium() {
        int tiles = 28;
        viewer.setTileSize(18);

        model.setLevelSize("medium");

        model.setGridSize(tiles, tiles);
        gameField.setPrefSize(tiles * viewer.tileSize, tiles * viewer.tileSize);
        showGameScreen();
    }

    @FXML
    public void setLarge() {
        int tiles = 42;
        viewer.setTileSize(12);

        model.setLevelSize("large");

        model.setGridSize(tiles, tiles);
        gameField.setPrefSize(tiles * viewer.tileSize, tiles * viewer.tileSize);
        showGameScreen();
    }

    //Makes sure that the "focus" switches from screen to screen
    @FXML
    public void initialize() {
        gameScreen.setFocusTraversable(true);
        gameScreen.setOnKeyPressed(this::changeDirection);
        
    }

    //Resets game
    public void gameReset() {
        model.reset();
        timeline.play();
        showGameScreen();
    }

    @FXML
    private void showGameScreen() {
        showOnly(gameScreen);
        startGameLoop();
        Platform.runLater(() -> gameScreen.requestFocus());
    }

    @FXML
    private void showSelectScreen() {
        showOnly(selectScreen);
    }

    @FXML
    private void showHighscoreScreen() {
        showOnly(highscoreScreen);
    }

    @FXML
    private void showHelpScreen() {
        showOnly(helpScreen);
    }

    @FXML
    private void showOptionsScreen() {
        showOnly(optionsScreen);
    }

    @FXML
    private void back() {
        showOnly(startScreen);
    }

    @FXML
    private void quit() {
        Platform.exit();
    }

    //Not optimal but makes writing code less repetitive 
    private void showOnly(Pane paneToShow) {
        startScreen.setVisible(false);
        gameScreen.setVisible(false);
        highscoreScreen.setVisible(false);
        helpScreen.setVisible(false);
        optionsScreen.setVisible(false);
        gameOverScreen.setVisible(false);
        selectScreen.setVisible(false);

        paneToShow.setVisible(true);
    }
}
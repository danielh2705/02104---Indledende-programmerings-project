import java.util.ArrayList;
import java.util.List;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.*;
import javafx.util.*;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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
    private Pane gameOverScreen;
    @FXML
    private Pane selectScreen;
    @FXML
    private VBox highscoreBox;

    @FXML
    private Pane gameField;

    @FXML
    private Text scoreLabel;

    public void startGameLoop() {
        // THE GAME LOOP / TIMER
        stopAllTimelines();
        if (model.getLevelSize() == "medium" || model.getLevelSize() == "large") {
            startBombCycle();
        }
        startPoisonAppleCycle();
        startCoconutCycle();
        startStarCycle();
        startMushroomCycle();

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
                    //coconut consumption -> temporary speed boost
                    if (model.getCoconut() != null && model.getSnake().get(0).equals(model.getCoconut())) {
                        model.consumedCoconut();
                        activateSpeedBoost();
                    }
                    //star consumption → spawns bonus apples temporarily
                    if (model.getStar() != null && model.getSnake().get(0).equals(model.getStar())) {
                        SfxPlayer.audioPlayer("STARCONSUME", 1.0);
                        model.consumedStar();
                        activateStarEffect();
                    }
                    //mushroom consumption → reversed controls temporarily
                    if (model.getMushroom() != null && model.getSnake().get(0).equals(model.getMushroom())) {
                        SfxPlayer.audioPlayer("MUSHROOMCONSUME", 1.0);
                        model.consumedMushroom();
                        activateMushroomControls();
                    }
                    //poison apple consumption → score penalty + snake shrink
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
    //written by Daniel
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
    // Spawns a poison apple and starts its lifetime timer.
    // Input: none & output: a poison apple appears on the game field
    // If it is not consumed, it is removed and a respawn
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
    //Input: None 
    // output: The snake moves at double the speed for a limited duration
    // and reset back to normal after a fixed delay.
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
    //a bomb is spawned 
    // a repeating Timeline respawns the bomb every 10 sec.
    private void startBombCycle() {
        model.spawnBomb(); // spawn immediately

        bomb = new Timeline(
                new KeyFrame(Duration.seconds(10), e -> model.spawnBomb()));
        bomb.setCycleCount(Timeline.INDEFINITE);
        bomb.play();

    }

    // written by Adel
    //The coconut effect 
    private void activateSpeedBoost() {
        timeline.setRate(2.0); // 2x speed

        if (speedBoostTimer != null)
            speedBoostTimer.stop();

        speedBoostTimer = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> timeline.setRate(1.0)));
        speedBoostTimer.play();
    }

    // written by Adel
    // spawn 10 red apples
    private void activateStarEffect() {
        model.spawnBonusApples(10);

        if (bonusApplesLife != null)
            bonusApplesLife.stop();
        bonusApplesLife = new Timeline(
                new KeyFrame(Duration.seconds(15), e -> model.clearBonusApples()));
        bonusApplesLife.play();
    }


  

    public void looseGame() {
        // SWITCHES TO THE LOSE SCREEN IF GAME IS LOST
        timeline.pause();
        SfxPlayer.audioPlayer("GAMEOVER", 1.0);
        HighscoreManager.addScore(model.getScore());

        gameOverScreen.setVisible(true);
    }

    //Written by Adrian
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

        // if (bombRespawn != null)
        //     bombRespawn.stop();

        if (speedBoostTimer != null)
            speedBoostTimer.stop();

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


    // written by Adel
    //star spawns now and then again every 60 seconds
    private void startStarCycle() {
        spawnStarNow();

        starSpawner = new Timeline(
                new KeyFrame(Duration.seconds(60), e -> spawnStarNow()));
        starSpawner.setCycleCount(Timeline.INDEFINITE);
        starSpawner.play();
    }

    // written by Adel
    //Activates reversed controls temporarily (mushroom effect).
    private void activateMushroomControls() {
        controlsReversed = true;

        if (mushroomTimer != null)
            mushroomTimer.stop();

        mushroomTimer = new Timeline(
                new KeyFrame(Duration.seconds(7), e -> controlsReversed = false));
        mushroomTimer.play();
    }

    // written by Daniel

    // written by Adel
    // star appears, then disappears after 6 seconds if not collected
    private void spawnStarNow() {
        model.spawnStar();

        if (starLife != null)
            starLife.stop();
        starLife = new Timeline(
                new KeyFrame(Duration.seconds(6), e -> model.consumedStar()));
        starLife.play();
    }

    // written by Adel
    // player controls are inverted for 7 seconds
    private void startMushroomCycle() {
        model.spawnMushroom();

        mushroomLife = new Timeline(
                new KeyFrame(Duration.seconds(6), e -> {
                    model.consumedMushroom();
                    scheduleMushroomRespawn();
                }));
        mushroomLife.play();
    }

    // written by Adel
    private void scheduleMushroomRespawn() {
        mushroomRespawn = new Timeline(
                new KeyFrame(Duration.seconds(20), e -> startMushroomCycle()));
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

    //Everything below this line: Written by Adrian 
    // Setting the size of the Level
    // titles and TileSize are kinda hard-coded to fit in perfectly, not optimal
    // solution
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

    private void updateHighscores() {
        highscoreBox.getChildren().clear();

        List<Integer> scores = HighscoreManager.loadScores();

        int rank = 1;
        for (int score : scores) {
            Text t = new Text(rank + ". " + score);

            t.getStyleClass().add("highscore-entry");

            if (rank <= 3) {
                t.getStyleClass().add("highscore-top3");
            }

            highscoreBox.getChildren().add(t);
            rank++;
        }
    }

    // Makes sure that the "focus" switches from screen to screen
    @FXML
    public void initialize() {
        gameScreen.setFocusTraversable(true);
        gameScreen.setOnKeyPressed(this::changeDirection);

    }

    // Resets game
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
        updateHighscores();
    }

    @FXML
    private void showHelpScreen() {
        showOnly(helpScreen);
    }

    @FXML
    private void back() {
        showOnly(startScreen);
    }

    @FXML
    private void quit() {
        Platform.exit();
    }

    // Not optimal but makes writing code less repetitive
    private void showOnly(Pane paneToShow) {
        startScreen.setVisible(false);
        gameScreen.setVisible(false);
        highscoreScreen.setVisible(false);
        helpScreen.setVisible(false);
        gameOverScreen.setVisible(false);
        selectScreen.setVisible(false);

        paneToShow.setVisible(true);
    }
}
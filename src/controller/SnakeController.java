package src.controller;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.util.*;
import src.model.SnakeModel;
import src.view.GameOverView;
import src.view.HelpScreenView;
import src.view.HighScoreView;
import src.view.OptionsScreenView;
import src.view.SnakeView;
import src.view.StartScreenView;
import javafx.scene.input.KeyCode;
import javafx.scene.Scene;
import src.model.Direction;
import src.model.HighScoreService;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class SnakeController {
    private StartScreenView startScreenView;
    private SnakeView snakeView;
    private HighScoreView highScoreView;
    private HelpScreenView helpScreenView;
    private OptionsScreenView optionsScreenView;
    private GameOverView gameOverView;

    private SnakeModel model;

    private boolean canTurn;
    private boolean funkyControls;

    private Timeline timeline;

    public SnakeController(Scene scene,
            StartScreenView startScreenView,
            SnakeView snakeView,
            HighScoreView highScoreView,
            HelpScreenView helpScreenView,
            OptionsScreenView optionsScreenView,
            GameOverView gameOverView,
            SnakeModel model) {
        this.startScreenView = startScreenView;
        this.snakeView = snakeView;
        this.highScoreView = highScoreView;
        this.helpScreenView = helpScreenView;
        this.optionsScreenView = optionsScreenView;
        this.gameOverView = gameOverView;

        this.model = model;

        this.canTurn = true;
        this.funkyControls = false;

        setupInput(scene);
        startGameLoop();
        poisonousAppleCycle();
        bombCycle();
        coconutCycle();
        starCycle();
        mushroomCycle();
        setupButtons();
    }

    private void setupInput(Scene scene) {
        scene.setOnKeyPressed(e -> {
            KeyCode code = e.getCode();

            if (model.isGameOver())
                return;

            switch (code) {
                case UP -> {
                    if (!model.getDirection().equals(Direction.DOWN) && canTurn && !funkyControls) {
                        model.setDirection(Direction.UP);
                        canTurn = false;
                    } else if (!model.getDirection().equals(Direction.UP) && canTurn && funkyControls) {
                        model.setDirection(Direction.DOWN);
                        canTurn = false;
                    }
                }
                case DOWN -> {
                    if (!model.getDirection().equals(Direction.UP) && canTurn && !funkyControls) {
                        model.setDirection(Direction.DOWN);
                        canTurn = false;
                    } else if (!model.getDirection().equals(Direction.DOWN) && canTurn && funkyControls) {
                        model.setDirection(Direction.UP);
                        canTurn = false;
                    }
                }
                case LEFT -> {
                    if ((!model.getDirection().equals(Direction.RIGHT)) && canTurn && !funkyControls) {
                        model.setDirection(Direction.LEFT);
                        canTurn = false;
                    } else if (!model.getDirection().equals(Direction.LEFT) && canTurn && funkyControls) {
                        model.setDirection(Direction.RIGHT);
                        canTurn = false;
                    }
                }
                case RIGHT -> {
                    if (!model.getDirection().equals(Direction.LEFT) && canTurn && !funkyControls) {
                        model.setDirection(Direction.RIGHT);
                        canTurn = false;
                    } else if (!model.getDirection().equals(Direction.RIGHT) && canTurn && funkyControls) {
                        model.setDirection(Direction.LEFT);
                        canTurn = false;
                    }
                }

                default -> {
                }
            }
        });
    }

    private void setupButtons() {

        // START SCREEN:
        startScreenView.getPlayButton().setOnAction(e -> {
            model.reset();
            snakeView.setVisible(true);
            startScreenView.setVisible(false);
            timeline.play();
        });

        startScreenView.getHighscoreButton().setOnAction(e -> {
            List<Integer> highscores = new ArrayList<>(HighScoreService.loadTopScores(10));
            Collections.sort(highscores, Collections.reverseOrder());
            highScoreView.showScores(highscores);
            startScreenView.setVisible(false);
            highScoreView.setVisible(true);
        });

        startScreenView.getHelpButton().setOnAction(e -> {
            startScreenView.setVisible(false);
            helpScreenView.setVisible(true);
        });

        startScreenView.getOptionsButton().setOnAction(e -> {
            startScreenView.setVisible(false);
            optionsScreenView.setVisible(true);
        });

        startScreenView.getExitButton().setOnAction(e -> {
            Platform.exit();
        });

        // HIGHSCORE:
        highScoreView.getBackButton().setOnAction(e -> {
            startScreenView.setVisible(true);
            highScoreView.setVisible(false);
        });

        // HELP(SCREEN):
        helpScreenView.getBackButton().setOnAction(e -> {
            startScreenView.setVisible(true);
            helpScreenView.setVisible(false);
        });

        // OPTIONS:
        optionsScreenView.getBackButton().setOnAction(e -> {
            startScreenView.setVisible(true);
            optionsScreenView.setVisible(false);
        });

        // GAMEOVER:
        gameOverView.getRestartButton().setOnAction(e -> {
            model.reset();
            gameOverView.setVisible(false);
            timeline.play();
        });

        gameOverView.getExitButton().setOnAction(e -> {
            Platform.exit();
        });
    }

    private void startGameLoop() {
        // THE GAME LOOP / TIMER
        timeline = new Timeline(
                new KeyFrame(Duration.millis(150), e -> {
                    model.update();
                    snakeView.render(model);
                    canTurn = true;

                    if (model.ateCoconut()) {
                        speedBoost();
                        model.doneEatingCoconut();

                    } else if (model.ateStar()) {
                        for (int i = 0; i < 10; i++) {
                            model.spawnApple();
                        }
                        starEffectTimer();
                        model.doneEatingCoconut();

                    } else if (model.ateMushroom()) {
                        activateFunkyControls();
                    }

                    if (model.isGameOver()) {
                        timeline.pause();
                        HighScoreService.saveScore(model.getScore());
                        gameOverView.setVisible(true);
                    }
                }));

        timeline.setCycleCount(Animation.INDEFINITE);
    }

    //Cycles

    private void poisonousAppleCycle() {
        Timeline poisonousAppleTimeline = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> model.spawnPoisonousApple()));
        poisonousAppleTimeline.setCycleCount(Timeline.INDEFINITE);
        poisonousAppleTimeline.play();
    }

    private void bombCycle() {
        Timeline bombTimeline = new Timeline(
                new KeyFrame(Duration.seconds(10), e -> model.spawnBomb()));
        bombTimeline.setCycleCount(Timeline.INDEFINITE);
        bombTimeline.play();
    }

    private void coconutCycle() {
        Timeline coconutTimeline = new Timeline(
                new KeyFrame(Duration.seconds(11), e -> model.spawnCoconut()));
        coconutTimeline.setCycleCount(Timeline.INDEFINITE);
        coconutTimeline.play();
    }

    
    private void starCycle() {
        Timeline starTimeline = new Timeline(
                new KeyFrame(Duration.seconds(10), e -> model.spawnStar()));
        starTimeline.setCycleCount(Timeline.INDEFINITE);
        starTimeline.play();
    }

    private void mushroomCycle() {
        Timeline mushroomTimeline = new Timeline(
                new KeyFrame(Duration.seconds(10), e -> model.spawnMushroom()));
        mushroomTimeline.setCycleCount(Timeline.INDEFINITE);
        mushroomTimeline.play();
    }

    

    //Effects

    private void speedBoost() {
        timeline.setRate(timeline.getRate() * 2);

        Timeline speedBoostTimer = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> timeline.setRate(timeline.getRate() / 2)));
        speedBoostTimer.play();
    }

    private void starEffectTimer() {
        Timeline starEffect = new Timeline(
                new KeyFrame(Duration.seconds(15), e -> model.clearAppleBasket()));
        starEffect.play();
    }

    private void activateFunkyControls() {
        funkyControls = true;

        Timeline funkyControlsTimer = new Timeline(
                new KeyFrame(Duration.seconds(7), e -> funkyControls = false));
        funkyControlsTimer.play();
    }
}
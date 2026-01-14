package src.controller;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.util.*;
import src.model.Snakemodel;
import src.view.GameOverView;
import src.view.HelpScreenView;
import src.view.HighScoreView;
import src.view.OptionsScreenView;
import src.view.Snakeview;
import src.view.StartScreenView;
import javafx.scene.input.KeyCode;
import javafx.scene.Scene;
import src.model.Direction;

public class Snakecontroller {
    private StartScreenView startScreenView;
    private Snakeview snakeView;
    private HighScoreView highScoreView;
    private HelpScreenView helpScreenView;
    private OptionsScreenView optionsScreenView;
    private GameOverView gameOverView;

    private Snakemodel model;

    private boolean canTurn;

    private Timeline timeline;

    public Snakecontroller(Scene scene, 
                           StartScreenView startScreenView, 
                           Snakeview snakeView, 
                           HighScoreView highScoreView,
                           HelpScreenView helpScreenView,
                           OptionsScreenView optionsScreenView, 
                           GameOverView gameOverView, 
                           Snakemodel model) 
                           {
        this.startScreenView = startScreenView;
        this.snakeView = snakeView;
        this.highScoreView = highScoreView;
        this.helpScreenView = helpScreenView;
        this.optionsScreenView = optionsScreenView;
        this.gameOverView = gameOverView;

        this.model = model;

        this.canTurn = true;

        setupInput(scene);
        startGameLoop();
        setupButtons();
    }

    private void setupInput(Scene scene) {
        scene.setOnKeyPressed(e -> {
            KeyCode code = e.getCode();

            if (model.isGameOver()) return;

            switch (code) {
                case UP -> {
                    if (!model.getDirection().equals(Direction.DOWN) && canTurn) {
                        model.setDirection(Direction.UP);
                        canTurn = false;
                    }
                }
                case DOWN -> {
                    if (!model.getDirection().equals(Direction.UP) && canTurn) {
                        model.setDirection(Direction.DOWN);
                        canTurn = false;
                    }
                }
                case LEFT -> {
                    if ((!model.getDirection().equals(Direction.RIGHT)) && canTurn) {
                        model.setDirection(Direction.LEFT);
                        canTurn = false;
                    }
                }
                case RIGHT -> {
                    if (!model.getDirection().equals(Direction.LEFT) && canTurn) {
                        model.setDirection(Direction.RIGHT);
                        canTurn = false;
                    }
                }

                default -> {
                }
            }
        });
    }

    private void setupButtons() {

        //START SCREEN:
        startScreenView.getPlayButton().setOnAction(e -> {
            model.reset();
            snakeView.setVisible(true);
            startScreenView.setVisible(false);
            timeline.play();
        });

        startScreenView.getHighscoreButton().setOnAction(e -> {
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

        //HIGHSCORE:
        highScoreView.getBackButton().setOnAction(e -> {
            startScreenView.setVisible(true);
            highScoreView.setVisible(false);
        });

        //HELP(SCREEN):
        helpScreenView.getBackButton().setOnAction(e -> {
            startScreenView.setVisible(true);
            helpScreenView.setVisible(false);
        });

        //OPTIONS:
        optionsScreenView.getBackButton().setOnAction(e -> {
            startScreenView.setVisible(true);
            optionsScreenView.setVisible(false);
        });
        
        //GAMEOVER:
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

                    if (model.isGameOver()) {
                        timeline.pause();
                        gameOverView.setVisible(true);
                    }
                }));

        timeline.setCycleCount(Animation.INDEFINITE);
    }
}
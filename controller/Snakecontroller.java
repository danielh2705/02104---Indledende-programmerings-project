package controller;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.util.*;
import model.Snakemodel;
import view.GameOverView;
import view.Snakeview;
import javafx.scene.input.KeyCode;
import javafx.scene.Scene;
import model.Direction;

public class Snakecontroller {
    private Snakeview view;
    private GameOverView gameOverView;
    private Snakemodel model;
    private boolean canTurn;
    private Timeline timeline;

    public Snakecontroller(Scene scene, Snakeview view, GameOverView gameOver, Snakemodel model) {
        this.model = model;
        this.view = view;
        this.gameOverView = gameOver;
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
                    view.render(model);
                    canTurn = true;

                    if (model.isGameOver()) {
                        timeline.pause();
                        gameOverView.setVisible(true);
                    }
                }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }
}
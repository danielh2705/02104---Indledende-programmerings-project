package controller;

import javafx.animation.*;
import javafx.util.*;
import model.Direction;
import model.Model;
import view.View;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class Controller {
        private Model model;
        private View view;

        public Controller(Scene scene, View view, Model model) {
                this.model = model;
                this.view = view;

                setupInput(scene);
                startGameLoop();
        }

        private void setupInput(Scene scene) {
                scene.setOnKeyPressed(e -> {
                        KeyCode code = e.getCode();
                        switch(code) {

                                case UP -> {
                                        if (!model.getDirection().equals(Direction.DOWN)) {
                                                model.setDirection(Direction.UP);
                                        }
                                }
                                case DOWN -> {
                                        if (!model.getDirection().equals(Direction.UP)) {
                                                model.setDirection(Direction.DOWN);
                                        }
                                }
                                case LEFT -> {
                                        if (!model.getDirection().equals(Direction.RIGHT)) {
                                                model.setDirection(Direction.LEFT);
                                        }
                                }
                                case RIGHT -> {
                                        if (!model.getDirection().equals(Direction.LEFT)) {
                                                model.setDirection(Direction.RIGHT);
                                        }
                                }

                                default -> { }
                        }
                });
        }

        private void startGameLoop() {
                Timeline timeline = new Timeline( 
                        new KeyFrame(Duration.millis(150), e -> {
                                model.update();
                                view.render(model);
                        })
                );
                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.play();
        }
}

package src;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.text.*;

public class Snakeveiw extends Application {
    private Snakemodel model;
    private Snakecontroller controller;
    private Text scoreLabel;
    private int n;
    private int m;
    private LoseController loseController;

    @Override
    public void start(Stage primaryStage) {
        // SCANNER INPUT FOR SIZE, NEEDS TO BE CHANGED FOR ARGS
        try {
            Scanner console = new Scanner(System.in);
            while (m < 5) {
                System.out.print("Please enter a x value bigger than 5: ");
                m = console.nextInt();
                if (m >= 5) {
                    break;
                }
            }
            while (n < 5) {
                System.out.print("Please enter a y value bigger than 5: ");
                n = console.nextInt();
                if (n >= 5) {
                    break;
                }
            }
            console.close();

            // LOADS THE GUI FROM FXML FILE
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gui.fxml"));
            BorderPane root = loader.load();

            model = new Snakemodel(m, n);
            controller = (Snakecontroller) loader.getController();
            controller.setModelAndView(model, this);
            // SHOWS THE SCENE AND CALLS UPDATE, SUCH THAT SNAKE AND APPLE IS DRAWN
            scoreLabel = controller.getScoreLabel();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DRAWS ALL THE VISUALS ASWELL AS CLEARS OLD ONES
    public void update() {
        controller.getGamePane().getChildren().clear();
        updateScore();
        drawSnake();
        drawApple();
    }

    // DRAWS THE APPLE WITH CIRCLE FROM JAVAFX
    // GETS THE POSITION FROM MODEL
    // AND USES SIMPLE MATH TO PLACE IN THE MIDDLE OF A GRID SPACE
    private void drawApple() {
        Point apple = model.getApple();
        Circle circ = new Circle((Math.floor(controller.getGamePane().getWidth() / n) / 2));
        circ.setCenterX(circ.getRadius() * 2 * apple.x + circ.getRadius());
        circ.setCenterY(circ.getRadius() * 2 * apple.y + circ.getRadius());
        controller.getGamePane().getChildren().add(circ);
        circ.setFill(javafx.scene.paint.Color.RED);
    }

    // DRAWS ALL THE SEGMENTS OF THE SNAKE
    // CHANGES THE SIZE DEPENDING ON THE GRID SPECEFIED WHEN PROGRAM IS RUN
    private void drawSnake() {
        Random random = new Random();
        for (Point point : model.getSnake()) {

            Rectangle rect = new Rectangle(Math.floor(controller.getGamePane().getWidth() / m),
                    Math.floor(controller.getGamePane().getHeight() / n));
            controller.getGamePane().getChildren().add(rect);
            rect.setX(rect.getWidth() * point.x);
            rect.setY(rect.getHeight() * point.y);

            // DRAWS THE HEAD A DIFFERENT COLOR
            if (point.equals(model.getSnake().get(0))) {
                rect.setFill(javafx.scene.paint.Color.PURPLE);
            } else {
                rect.setFill(javafx.scene.paint.Color.BLUEVIOLET);
            }

            // ADDS CURVES TO THE SEGMENTS
            rect.setArcWidth(30.0);
            rect.setArcHeight(20.0);
        }
    }

    // UPDATES SCORE LABEL ON UI
    private void updateScore() {
        scoreLabel.setText("Score: " + model.getScore());
    }

    // CREATES THE GAME OVER POPUP AND CLOSES THE GAME ITSELF
    public void switchToLoseScreen(int score) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("losepopup.fxml"));
        Pane root = loader.load();
        loseController = loader.getController();
        loseController.getHighscoreLabel().setText("Score: " + score);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        // CLOSES OLD GAME
        exit(controller.getButton1());
    }

    // OUTDATED FUNCTION THAT CLOSES THE APPLICATION
    public void exit(Button button1) {
        Stage stage = (Stage) button1.getScene().getWindow();
        stage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

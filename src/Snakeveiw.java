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

            FXMLLoader loader = new FXMLLoader(getClass().getResource("gui.fxml"));
            BorderPane root = loader.load();

            model = new Snakemodel(m, n);
            controller = (Snakecontroller) loader.getController();
            controller.setModelAndView(model, this);
            scoreLabel = controller.getScoreLabel();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        controller.getGamePane().getChildren().clear();
        updateScore();
        drawSnake();
        drawApple();
    }

    private void drawApple() {
        Point apple = model.getApple();
        Circle circ = new Circle((Math.floor(controller.getGamePane().getWidth() / n) / 2));
        circ.setCenterX(circ.getRadius() * 2 * apple.x + circ.getRadius());
        circ.setCenterY(circ.getRadius() * 2 * apple.y + circ.getRadius());
        controller.getGamePane().getChildren().add(circ);
        circ.setFill(javafx.scene.paint.Color.RED);
    }

    private void drawSnake() {
        Random random = new Random();
        for (Point point : model.getSnake()) {

            Rectangle rect = new Rectangle(Math.floor(controller.getGamePane().getWidth() / m),
                    Math.floor(controller.getGamePane().getHeight() / n));
            controller.getGamePane().getChildren().add(rect);
            rect.setX(rect.getWidth() * point.x);
            rect.setY(rect.getHeight() * point.y);
            if (point.equals(model.getSnake().get(0))) {
                rect.setFill(javafx.scene.paint.Color.PURPLE);
            } else {
                rect.setFill(javafx.scene.paint.Color.BLUEVIOLET);
            }
            rect.setArcWidth(30.0);
            rect.setArcHeight(20.0);
        }
    }

    private void updateScore() {
        scoreLabel.setText("Score: " + model.getScore());
    }

    public void switchToLoseScreen(int score) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("losepopup.fxml"));
        Pane root = loader.load();
        loseController = loader.getController();
        loseController.getHighscoreLabel().setText("Score: " + score);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        exit(controller.getButton1());
    }

    public void exit(Button button1) {
        Stage stage = (Stage) button1.getScene().getWindow();
        stage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

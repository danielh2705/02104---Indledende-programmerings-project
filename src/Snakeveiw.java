package src;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
            Pane root = loader.load();

            model = new Snakemodel(m, n);
            controller = (Snakecontroller) loader.getController();
            controller.setModelAndView(model, this);
            // SHOWS THE SCENE AND CALLS UPDATE, SUCH THAT SNAKE AND APPLE IS DRAWN
            scoreLabel = controller.getScoreLabel();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            controller.startGameLoop();
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
        drawBadApple();
        drawBomb();
        drawSpeedApple();
        drawGoldenApple();
        drawBonusApples();
        drawFunkyApple();
    }

    // DRAWS THE APPLE WITH CIRCLE FROM JAVAFX
    // GETS THE POSITION FROM MODEL
    // AND USES SIMPLE MATH TO PLACE IN THE MIDDLE OF A GRID SPACE
    private void drawApple() {
        Point apple = model.getApple();
        Image applePng = new Image("recourses\\apple.png");
        ImageView image = new ImageView();
        image.setImage(applePng);
        image.setFitWidth(Math.floor(controller.getGamePane().getWidth() / m));
        image.setFitHeight(Math.floor(controller.getGamePane().getHeight() / n));
        image.setX((Math.floor(controller.getGamePane().getWidth() / m)) * apple.x);
        image.setPreserveRatio(false);
        image.setY(Math.floor(controller.getGamePane().getHeight() / n) * apple.y);
        controller.getGamePane().getChildren().add(image);
    }

    private void drawBadApple() {
        if (model.getBadApple() == null)
            return;

        Image badApplePng = new Image("recourses\\poisonApple.png");
        ImageView image = new ImageView();
        image.setImage(badApplePng);
        image.setFitWidth(Math.floor(controller.getGamePane().getWidth() / m));
        image.setFitHeight(Math.floor(controller.getGamePane().getHeight() / n));
        image.setX((Math.floor(controller.getGamePane().getWidth() / m)) * model.getBadApple().x);
        image.setPreserveRatio(false);
        image.setY(Math.floor(controller.getGamePane().getHeight() / n) * model.getBadApple().y);
        controller.getGamePane().getChildren().add(image);
    }

    private void drawBomb() {
        if (model.getBomb() == null)
            return;

        Image bombPng = new Image("recourses\\bomb.png");
        ImageView image = new ImageView();
        image.setImage(bombPng);
        image.setFitWidth(Math.floor(controller.getGamePane().getWidth() / m));
        image.setFitHeight(Math.floor(controller.getGamePane().getHeight() / n));
        image.setX((Math.floor(controller.getGamePane().getWidth() / m)) * model.getBomb().x);
        image.setPreserveRatio(false);
        image.setY(Math.floor(controller.getGamePane().getHeight() / n) * model.getBomb().y);
        controller.getGamePane().getChildren().add(image);
    }
    

    private void drawSpeedApple() {
        if (model.getSpeedApple() == null)
            return;

        Image speedApplePng = new Image("recourses\\coconut.png");
        ImageView image = new ImageView();
        image.setImage(speedApplePng);
        image.setFitWidth(Math.floor(controller.getGamePane().getWidth() / m));
        image.setFitHeight(Math.floor(controller.getGamePane().getHeight() / n));
        image.setX((Math.floor(controller.getGamePane().getWidth() / m)) * model.getSpeedApple().x);
        image.setPreserveRatio(false);
        image.setY(Math.floor(controller.getGamePane().getHeight() / n) * model.getSpeedApple().y);
        controller.getGamePane().getChildren().add(image);
    }

    private void drawGoldenApple() {
        if (model.getGoldenApple() == null)
            return;

        Image goldenApplePng = new Image("recourses\\star.png");
        ImageView image = new ImageView();
        image.setImage(goldenApplePng);
        image.setFitWidth(Math.floor(controller.getGamePane().getWidth() / m));
        image.setFitHeight(Math.floor(controller.getGamePane().getHeight() / n));
        image.setX((Math.floor(controller.getGamePane().getWidth() / m)) * model.getGoldenApple().x);
        image.setPreserveRatio(false);
        image.setY(Math.floor(controller.getGamePane().getHeight() / n) * model.getGoldenApple().y);
        controller.getGamePane().getChildren().add(image);
    }

    private void drawBonusApples() {
        Image applePng = new Image("recourses\\redApplePic.png");
        for (Point apple : model.getBonusApples()) {
            ImageView image = new ImageView();
            image.setImage(applePng);
            image.setFitWidth(Math.floor(controller.getGamePane().getWidth() / m));
            image.setFitHeight(Math.floor(controller.getGamePane().getHeight() / n));
            image.setX((Math.floor(controller.getGamePane().getWidth() / m)) * apple.x);
            image.setPreserveRatio(false);
            image.setY(Math.floor(controller.getGamePane().getHeight() / n) * apple.y);
            controller.getGamePane().getChildren().add(image);
        }
    }

    private void drawFunkyApple() {
        if (model.getFunkyApple() == null)
            return;

        Image funkyApplePng = new Image("recourses\\brownMushroom.png");
        ImageView image = new ImageView();
        image.setImage(funkyApplePng);
        image.setFitWidth(Math.floor(controller.getGamePane().getWidth() / m));
        image.setFitHeight(Math.floor(controller.getGamePane().getHeight() / n));
        image.setX((Math.floor(controller.getGamePane().getWidth() / m)) * model.getGoldenApple().x);
        image.setPreserveRatio(false);
        image.setY(Math.floor(controller.getGamePane().getHeight() / n) * model.getGoldenApple().y);
        controller.getGamePane().getChildren().add(image);
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
                rect.setFill(javafx.scene.paint.Color.valueOf("#8B4513"));
            } else {
                rect.setFill(javafx.scene.paint.Color.valueOf("#D2B48C"));
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

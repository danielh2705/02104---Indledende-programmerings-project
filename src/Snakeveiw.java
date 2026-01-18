package src;

import java.io.IOException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.text.*;

public class Snakeveiw extends Application {
    private Snakemodel model;
    private Snakecontroller controller;
    private Text scoreLabel;
    private int n;
    private int m;
    private LoseController loseController;
    private static double TILE_SIZE = 15;

    // This is where the whole game Starts.
    // Written by Daniel
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
            root.setPrefSize(20 + TILE_SIZE * m + 20, 50 + TILE_SIZE * n + 20);
            model = new Snakemodel(m, n);
            controller = (Snakecontroller) loader.getController();
            controller.setModelAndView(model, this);
            controller.getGamePane().setPrefSize(m * TILE_SIZE, n * TILE_SIZE);
            controller.getGamePane().setCenterShape(true);
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
    // Written by Daniel & Adel & Adrian
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
    // Written by Daniel

    private void drawApple() {
        Point apple = model.getApple();
        Image applePng = new Image("recourses\\apple.png");
        ImageView image = new ImageView();
        image.setImage(applePng);
        image.setFitWidth(TILE_SIZE);
        image.setFitHeight(TILE_SIZE);
        image.setX(TILE_SIZE * apple.x);
        image.setPreserveRatio(false);
        image.setY(TILE_SIZE * apple.y);
        controller.getGamePane().getChildren().add(image);
    }

    // Written by Daniel & Adel
    // Draws the poisoned apple on the screen
    private void drawBadApple() {
        if (model.getBadApple() == null)
            return;

        Image badApplePng = new Image("recourses\\poisonApple.png");
        ImageView image = new ImageView();
        image.setImage(badApplePng);
        image.setFitWidth(TILE_SIZE);
        image.setFitHeight(TILE_SIZE);
        image.setX(TILE_SIZE * model.getBadApple().x);
        image.setPreserveRatio(false);
        image.setY(TILE_SIZE * model.getBadApple().y);
        controller.getGamePane().getChildren().add(image);
    }

    // Written by Daniel & Adel
    // Draws the bomb on the screen
    private void drawBomb() {
        if (model.getBomb() == null)
            return;

        Image bombPng = new Image("recourses\\bomb.png");
        ImageView image = new ImageView();
        image.setImage(bombPng);
        image.setFitWidth(TILE_SIZE);
        image.setFitHeight(TILE_SIZE);
        image.setX(TILE_SIZE * model.getBomb().x);
        image.setPreserveRatio(false);
        image.setY(TILE_SIZE * model.getBomb().y);
        controller.getGamePane().getChildren().add(image);
    }

    // Written by Daniel & Adel
    // Draws the coconut on the screen
    private void drawSpeedApple() {
        if (model.getSpeedApple() == null)
            return;

        Image speedApplePng = new Image("recourses\\coconut.png");
        ImageView image = new ImageView();
        image.setImage(speedApplePng);
        image.setFitWidth(TILE_SIZE);
        image.setFitHeight(TILE_SIZE);
        image.setX(TILE_SIZE * model.getSpeedApple().x);
        image.setPreserveRatio(false);
        image.setY(TILE_SIZE * model.getSpeedApple().y);
        controller.getGamePane().getChildren().add(image);
    }

    // Written by Daniel & Adel
    // Draws the Star on the screen
    private void drawGoldenApple() {
        if (model.getGoldenApple() == null)
            return;

        Image goldenApplePng = new Image("recourses\\star.png");
        ImageView image = new ImageView();
        image.setImage(goldenApplePng);
        image.setFitWidth(TILE_SIZE);
        image.setFitHeight(TILE_SIZE);
        image.setX(TILE_SIZE * model.getGoldenApple().x);
        image.setPreserveRatio(false);
        image.setY(TILE_SIZE * model.getGoldenApple().y);
        controller.getGamePane().getChildren().add(image);
    }

    // Written by Daniel & Adel
    // Draws the apples generated by the star on the screen
    private void drawBonusApples() {
        Image applePng = new Image("recourses\\apple.png");
        for (Point apple : model.getBonusApples()) {
            ImageView image = new ImageView();
            image.setImage(applePng);
            image.setFitWidth(TILE_SIZE);
            image.setFitHeight(TILE_SIZE);
            image.setX(TILE_SIZE * apple.x);
            image.setPreserveRatio(false);
            image.setY(TILE_SIZE * apple.y);
            controller.getGamePane().getChildren().add(image);
        }
    }

    // Written by Daniel & Adel
    // Draws the Mushroom on the screen
    private void drawFunkyApple() {
        if (model.getFunkyApple() == null)
            return;

        Image funkyApplePng = new Image("recourses\\brownMushroom.png");
        ImageView image = new ImageView();
        image.setImage(funkyApplePng);
        image.setFitWidth(TILE_SIZE);
        image.setFitHeight(TILE_SIZE);
        image.setX(TILE_SIZE * model.getFunkyApple().x);
        image.setPreserveRatio(false);
        image.setY(TILE_SIZE * model.getFunkyApple().y);
        controller.getGamePane().getChildren().add(image);
    }

    // DRAWS ALL THE SEGMENTS OF THE SNAKE
    // CHANGES THE SIZE DEPENDING ON THE GRID SPECEFIED WHEN PROGRAM IS RUN
    // Written by Daniel
    private void drawSnake() {
        for (Point point : model.getSnake()) {

            // DRAWS THE HEAD A DIFFERENT COLOR
            if (point.equals(model.getSnake().get(0))) {
                Image snakeHead = new Image("recourses/snakeSprite/" + model.getBodyDirections().get(0) + "-head.png");
                ImageView image = new ImageView(snakeHead);
                image.setFitWidth(TILE_SIZE);
                image.setFitHeight(TILE_SIZE);
                image.setX((Math.floor(TILE_SIZE * point.x)));
                image.setPreserveRatio(false);
                image.setY(Math.floor(TILE_SIZE * point.y));
                image.setCache(true);
                controller.getGamePane().getChildren().add(image);

            } else if (point.equals(model.getSnake().get(model.getSnake().size() - 1))) {
                Image snakeTail = new Image("recourses/snakeSprite/"
                        + model.getBodyDirections().get(model.getBodyDirections().size() - 2) + "-tail.png");
                ImageView image = new ImageView(snakeTail);
                image.setFitWidth(TILE_SIZE);
                image.setFitHeight(TILE_SIZE);
                image.setX((Math.floor(TILE_SIZE * point.x)));
                image.setPreserveRatio(false);
                image.setY(Math.floor(TILE_SIZE * point.y));
                image.setCache(true);
                controller.getGamePane().getChildren().add(image);
            } else {
                Image snakeBody = new Image(
                        "recourses/snakeSprite/" + model.getBodyDirections().get(model.getSnake().indexOf(point))
                                + model.getBodyDirections().get(model.getSnake().indexOf(point) - 1) + "-body.png");
                ImageView image = new ImageView(snakeBody);
                image.setFitWidth(TILE_SIZE);
                image.setFitHeight(TILE_SIZE);
                image.setX((Math.floor(TILE_SIZE * point.x)));
                image.setPreserveRatio(false);
                image.setY(Math.floor(TILE_SIZE * point.y));
                image.setCache(true);
                controller.getGamePane().getChildren().add(image);
            }

            // ADDS CURVES TO THE SEGMENTS

        }
    }
    // Written by Daniel

    // UPDATES SCORE LABEL ON UI
    private void updateScore() {
        scoreLabel.setText("Score: " + model.getScore());
    }

    // Written by Daniel
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

    // Written by Daniel
    // OUTDATED FUNCTION THAT CLOSES THE APPLICATION
    public void exit(Button button1) {
        Stage stage = (Stage) button1.getScene().getWindow();
        stage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

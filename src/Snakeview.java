package src;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javafx.scene.text.*;

public class Snakeview extends Application {
    private Snakemodel model;
    private Snakecontroller controller;
    private Text scoreLabel;

    private int n;
    private int m;
    public double tileSize = 15;

    @FXML
    private StackPane root;

    // This is where the whole game Starts.
    // Written by Daniel
    @Override
    public void start(Stage primaryStage) {
        try {
            //Temp solution, just needed a value so the game would run, n and m value has no 
            // effect on the game.
            m = 20;
            n = 20;

            model = new Snakemodel(m, n);
            
            // LOADS THE GUI FROM FXML FILE
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gui.fxml"));
            root = loader.load();
            root.setPrefSize(20 + tileSize * m + 20, 50 + tileSize * n + 20);

            controller = (Snakecontroller) loader.getController();
            controller.setModelAndView(model, this);
            controller.getGamePane().setPrefSize(m * tileSize, n * tileSize);
            controller.getGamePane().setCenterShape(true);
            // SHOWS THE SCENE AND CALLS UPDATE, SUCH THAT SNAKE AND APPLE IS DRAWN
            scoreLabel = controller.getScoreLabel();
            Scene scene = new Scene(root);

            primaryStage.setScene(scene);
            primaryStage.show();
            // controller.startGameLoop();
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
        drawPoisonApple();
        drawBomb();
        drawCoconut();
        drawStar();
        drawBonusApples();
        drawMushroom();
    }

    //Not best MVC structure, but needed for resizing the game
    public void setTileSize(double size) {
        this.tileSize = size;
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
        image.setFitWidth(tileSize);
        image.setFitHeight(tileSize);
        image.setX(tileSize * apple.x);
        image.setPreserveRatio(false);
        image.setY(tileSize * apple.y);
        controller.getGamePane().getChildren().add(image);
    }

    // Written by Daniel & Adel
    // Draws the poisoned apple on the screen
    private void drawPoisonApple() {
        if (model.getPoisonApple() == null)
            return;

        Image poisonApplePng = new Image("recourses\\poisonApple.png");
        ImageView image = new ImageView();
        image.setImage(poisonApplePng);
        image.setFitWidth(tileSize);
        image.setFitHeight(tileSize);
        image.setX(tileSize * model.getPoisonApple().x);
        image.setPreserveRatio(false);
        image.setY(tileSize * model.getPoisonApple().y);
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
        image.setFitWidth(tileSize);
        image.setFitHeight(tileSize);
        image.setX(tileSize * model.getBomb().x);
        image.setPreserveRatio(false);
        image.setY(tileSize * model.getBomb().y);
        controller.getGamePane().getChildren().add(image);
    }

    // Written by Daniel & Adel
    // Draws the coconut on the screen
    private void drawCoconut() {
        if (model.getCoconut() == null)
            return;

        Image coconutPng = new Image("recourses\\coconut.png");
        ImageView image = new ImageView();
        image.setImage(coconutPng);
        image.setFitWidth(tileSize);
        image.setFitHeight(tileSize);
        image.setX(tileSize * model.getCoconut().x);
        image.setPreserveRatio(false);
        image.setY(tileSize * model.getCoconut().y);
        controller.getGamePane().getChildren().add(image);
    }

    // Written by Daniel & Adel
    // Draws the Star on the screen
    private void drawStar() {
        if (model.getStar() == null)
            return;

        Image starPng = new Image("recourses\\star.png");
        ImageView image = new ImageView();
        image.setImage(starPng);
        image.setFitWidth(tileSize);
        image.setFitHeight(tileSize);
        image.setX(tileSize * model.getStar().x);
        image.setPreserveRatio(false);
        image.setY(tileSize * model.getStar().y);
        controller.getGamePane().getChildren().add(image);
    }

    // Written by Daniel & Adel
    // Draws the apples generated by the star on the screen
    private void drawBonusApples() {
        Image applePng = new Image("recourses\\apple.png");
        for (Point apple : model.getBonusApples()) {
            ImageView image = new ImageView();
            image.setImage(applePng);
            image.setFitWidth(tileSize);
            image.setFitHeight(tileSize);
            image.setX(tileSize * apple.x);
            image.setPreserveRatio(false);
            image.setY(tileSize * apple.y);
            controller.getGamePane().getChildren().add(image);
        }
    }

    // Written by Daniel & Adel
    // Draws the Mushroom on the screen
    private void drawMushroom() {
        if (model.getMushroom() == null)
            return;

        Image mushroomPng = new Image("recourses\\brownMushroom.png");
        ImageView image = new ImageView();
        image.setImage(mushroomPng);
        image.setFitWidth(tileSize);
        image.setFitHeight(tileSize);
        image.setX(tileSize * model.getMushroom().x);
        image.setPreserveRatio(false);
        image.setY(tileSize * model.getMushroom().y);
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
                image.setFitWidth(tileSize);
                image.setFitHeight(tileSize);
                image.setX((Math.floor(tileSize * point.x)));
                image.setPreserveRatio(false);
                image.setY(Math.floor(tileSize * point.y));
                image.setCache(true);
                controller.getGamePane().getChildren().add(image);

            } else if (point.equals(model.getSnake().get(model.getSnake().size() - 1))) {
                Image snakeTail = new Image("recourses/snakeSprite/"
                        + model.getBodyDirections().get(model.getBodyDirections().size() - 2) + "-tail.png");
                ImageView image = new ImageView(snakeTail);
                image.setFitWidth(tileSize);
                image.setFitHeight(tileSize);
                image.setX((Math.floor(tileSize * point.x)));
                image.setPreserveRatio(false);
                image.setY(Math.floor(tileSize * point.y));
                image.setCache(true);
                controller.getGamePane().getChildren().add(image);
            } else {
                Image snakeBody = new Image(
                        "recourses/snakeSprite/" + model.getBodyDirections().get(model.getSnake().indexOf(point))
                                + model.getBodyDirections().get(model.getSnake().indexOf(point) - 1) + "-body.png");
                ImageView image = new ImageView(snakeBody);
                image.setFitWidth(tileSize);
                image.setFitHeight(tileSize);
                image.setX((Math.floor(tileSize * point.x)));
                image.setPreserveRatio(false);
                image.setY(Math.floor(tileSize * point.y));
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

    public static void main(String[] args) {
        launch(args);
    }

}

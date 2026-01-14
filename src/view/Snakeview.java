package src.view;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import src.model.Point;
import src.model.Snakemodel;
import javafx.geometry.VPos;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import javafx.scene.text.TextAlignment;

public class Snakeview extends Canvas {
    private final GraphicsContext gc = getGraphicsContext2D();
    private static final int TILE_SIZE = 15;

    public Snakeview(Snakemodel model) {
        super(
                model.getWidth() * TILE_SIZE,
                model.getHeight() * TILE_SIZE);

        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.BASELINE);

        setVisible(false);
    }

    public void render(Snakemodel model) {
        gc.setFill(Color.valueOf("#55c244"));
        gc.fillRect(0, 0, getWidth(), getHeight());

        gc.setFill(Color.BLACK);
        gc.fillText("" + model.getScore() + "", getWidth() / 6, getHeight() / 6);

        drawApple(model);
        drawSnake(model);
    }

    // DRAWS THE APPLE WITH CIRCLE FROM JAVAFX
    // GETS THE POSITION FROM MODEL
    // AND USES SIMPLE MATH TO PLACE IN THE MIDDLE OF A GRID SPACE
    private void drawApple(Snakemodel model) {
        gc.setFill(Color.RED);
        Point apple = model.getApple();
        gc.fillOval(apple.x() * TILE_SIZE, apple.y() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    // DRAWS ALL THE SEGMENTS OF THE SNAKE
    // CHANGES THE SIZE DEPENDING ON THE GRID SPECEFIED WHEN PROGRAM IS RUN
    private void drawSnake(Snakemodel model) {

        for (Point p : model.sendSnake()) {
            if (p.equals(model.sendSnake().getFirst())) {
                gc.setFill(Color.valueOf("#8B4513"));
            } else {
                gc.setFill(Color.valueOf("#D2B48C"));
            }
            gc.fillRoundRect(p.x() * TILE_SIZE, p.y() * TILE_SIZE, TILE_SIZE, TILE_SIZE, TILE_SIZE / 2,  TILE_SIZE / 2);
        }

        /*
         * for (Point point : model.getSnake()) {
         * 
         * Rectangle rect = new Rectangle(Math.floor(controller.getGamePane().getWidth()
         * / m),
         * Math.floor(controller.getGamePane().getHeight() / n));
         * controller.getGamePane().getChildren().add(rect);
         * rect.setX(rect.getWidth() * point.x);
         * rect.setY(rect.getHeight() * point.y);
         * 
         * // DRAWS THE HEAD A DIFFERENT COLOR
         * if (point.equals(model.getSnake().get(0))) {
         * rect.setFill(javafx.scene.paint.Color.PURPLE);
         * } else {
         * rect.setFill(javafx.scene.paint.Color.BLUEVIOLET);
         * }
         * 
         * // ADDS CURVES TO THE SEGMENTS
         * rect.setArcWidth(30.0);
         * rect.setArcHeight(20.0);
         * }
         */
    }

    /*
     * // UPDATES SCORE LABEL ON UI
     * private void updateScore() {
     * scoreLabel.setText("Score: " + model.getScore());
     * }
     */

    /*
     * // CREATES THE GAME OVER POPUP AND CLOSES THE GAME ITSELF
     * public void switchToLoseScreen(int score) throws IOException {
     * FXMLLoader loader = new FXMLLoader(getClass().getResource("losepopup.fxml"));
     * Pane root = loader.load();
     * loseController = loader.getController();
     * loseController.getHighscoreLabel().setText("Score: " + score);
     * Scene scene = new Scene(root);
     * Stage stage = new Stage();
     * stage.setScene(scene);
     * stage.show();
     * // CLOSES OLD GAME
     * exit(controller.getButton1());
     * }
     */

    // OUTDATED FUNCTION THAT CLOSES THE APPLICATION
    public void exit(Button button1) {
        Stage stage = (Stage) button1.getScene().getWindow();
        stage.close();
    }
}

package src.view;

import src.model.Point;
import src.model.Snakemodel;
import javafx.geometry.VPos;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import javafx.scene.text.TextAlignment;
import java.util.*;
import javafx.scene.image.*;
import src.model.Direction;

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

        drawSnake(model);
        drawApple(model);
        drawPoisonousApple(model);
        drawBomb(model);
        drawCoconut(model);
        drawStar(model);
        drawMushroom(model);
    }

    private Image getSnakeSprite(String fileName) {
        return new Image("file:src/resources/snakeSprite/" + fileName);
    }

    private Image getSprite(String fileName) {
        return new Image("file:src/resources/" + fileName);
    }

    private void drawSnake(Snakemodel model) {
        List<Point> snake = model.sendSnake();
        List<Direction> directions = model.sendBodyDirections();

        if (snake.isEmpty())
            return;

        for (int i = 0; i < snake.size(); i++) {
            Point p = snake.get(i);
            Image sprite;

            // HEAD
            if (i == 0) {
                sprite = getSnakeSprite(directions.get(0).name() + "-head.png");
            }
            // TAIL
            else if (i == snake.size() - 1) {
                sprite = getSnakeSprite(directions.get(i - 1).name() + "-tail.png");
            }
            // BODY
            else {
                String from = directions.get(i).name();
                String to = directions.get(i - 1).name();
                sprite = getSnakeSprite(from + to + "-body.png");
            }

            gc.drawImage(
                    sprite,
                    p.x() * TILE_SIZE,
                    p.y() * TILE_SIZE,
                    TILE_SIZE,
                    TILE_SIZE);
        }
    }

    private void drawApple(Snakemodel model) {
        List<Point> appleBasket = model.getAppleBasket();
        if (appleBasket == null)
            return;
        Image sprite = getSprite("apple.png");
        for (Point apple : appleBasket) {
            gc.drawImage(sprite, apple.x() * TILE_SIZE, apple.y() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }
    }

    private void drawPoisonousApple(Snakemodel model) {
        Point poisonousApple = model.getPoisonousApple();
        if (poisonousApple == null)
            return;

        Image sprite = getSprite("poisonApple.png");
        gc.drawImage(sprite, poisonousApple.x() * TILE_SIZE, poisonousApple.y() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    private void drawBomb(Snakemodel model) {
        Point bomb = model.getBomb();
        if (bomb == null)
            return;
        Image sprite = getSprite("bomb.png");
        gc.drawImage(sprite, bomb.x() * TILE_SIZE, bomb.y() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    private void drawCoconut(Snakemodel model) {
        Point coconut = model.getCoconut();
        if (coconut == null)
            return;
        Image sprite = getSprite("coconut.png");
        gc.drawImage(sprite, coconut.x() * TILE_SIZE, coconut.y() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    private void drawStar(Snakemodel model) {
        Point star = model.getStar();
        if (star == null)
            return;
        Image sprite = getSprite("star.png");
        gc.drawImage(sprite, star.x() * TILE_SIZE, star.y() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    private void drawMushroom(Snakemodel model) {
        Point mushroom = model.getMushroom();
        if (mushroom == null)
            return;
        Image sprite = getSprite("brownMushroom.png");
        gc.drawImage(sprite, mushroom.x() * TILE_SIZE, mushroom.y() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }
}

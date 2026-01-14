package src.model;

import java.util.*;

public class Snakemodel {
    private int score;
    private Snake snake;
    private Point apple;
    private boolean gameOver;
    private Direction direction;

    public final int height;
    public final int width;

    public Snakemodel(int h, int w) {
        this.height = h;
        this.width = w;

        reset();
    }

    public void reset() {
        snake = new Snake();
        snake.addPart(new Point((int) Math.floor(height / 2), (int) Math.floor(width / 2)));
        direction = Direction.LEFT;
        score = 0;
        spawnApple();
        gameOver = false;
    }

    public void update() {
        if (gameOver)
            return;

        Point head = snake.getHead();
        Point newHead = head.move(direction);

        if (snake.getSnake().contains(newHead) && !(newHead.equals(snake.getTail()))) {
            gameOver = true;
            return;
        }

        if (newHead.x() < 0 || newHead.x() >= width ||
                newHead.y() < 0 || newHead.y() >= height) {
            gameOver = true;
            return;
        }

        if (newHead.x() < 0) {
            newHead = new Point(width - 1, newHead.y());
        } else if (newHead.x() >= width) {
            newHead = new Point(0, newHead.y());
        } else if (newHead.y() < 0) {
            newHead = new Point(newHead.x(), height - 1);
        } else if (newHead.y() >= height) {
            newHead = new Point(newHead.x(), 0);
        }

        snake.grow(newHead);

        if (newHead.equals(apple)) {
            spawnApple();
            increaseScore(10);
            ;
        } else {
            snake.cutTail();
        }
    }

    public void spawnApple() {
        Random random = new Random();
        Point applePoint = new Point(random.nextInt(width), random.nextInt(height));
        if (snake.getSnake().contains(applePoint)) {
            spawnApple();
        } else {
            apple = applePoint;
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setDirection(Direction d) {
        direction = d;
    }

    public Direction getDirection() {
        return direction;
    }

    public LinkedList<Point> sendSnake() {
        return snake.getSnake();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Point getApple() {
        return apple;
    }

    public int getScore() {
        return score;
    }

    // INCREASES THE SCORE BY AN INCREMENT
    public void increaseScore(int increment) {
        score += increment;
    }
}

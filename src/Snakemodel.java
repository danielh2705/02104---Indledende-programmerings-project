package src;

import java.util.ArrayList;
import java.util.Random;

public class Snakemodel {
    private int score;
    private Snake snake;
    private Point apple;
    private int xSize;
    private int ySize;

    public Snakemodel(int m, int n) {
        this.score = 0;
        this.xSize = m;
        this.ySize = n;
        // MAKES A SNAKE THAT IS IN THE MIDDLE TWO POINTS OF THE GRID FACING LEFT
        this.snake = new Snake(new Point((int) Math.floor(xSize / 2), (int) Math.floor(ySize / 2)),
                new Point((int) Math.floor(xSize / 2) + 1, (int) Math.floor(ySize / 2)), m, n);
        spawnApple();
    }

    public void spawnApple() {
        Random random = new Random();
        // GETS EVERY POSSIBLE POINT IN THE GRID
        ArrayList<Point> availableSpawnPoints = new ArrayList<Point>();
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                availableSpawnPoints.add(new Point(i, j));
            }
        }
        // REMOVES THE POINTS OCCUPIED BY SNAKE
        ArrayList<Point> unAvailableSpawnPoints = new ArrayList<Point>();
        for (Point snakeBit : getSnake()) {
            for (Point spawnPoint : availableSpawnPoints) {
                if (snakeBit.equals(spawnPoint)) {
                    unAvailableSpawnPoints.add(spawnPoint);
                }
            }
        }
        availableSpawnPoints.removeAll(unAvailableSpawnPoints);
        // SPAWNS APPLE ON A RANDOM AVAILABLE SPACE
        this.apple = availableSpawnPoints.get(random.nextInt(0, availableSpawnPoints.size() - 1));
    }

    // CALLED IF THE APPLE IS EATEN
    public void consumedApple() {
        this.snake.grow();
        increaseScore(1);
        spawnApple();
    }

    // RETURNS THE FULL SNAKE, HEAD AND TAIL INCLUDED IN AN ARRAYLIST
    public ArrayList<Point> getSnake() {
        return this.snake.getSnake();
    }

    // GETS THE POSITION OF THE APPLE
    public Point getApple() {
        return this.apple;
    }

    // Accepts "Up", "Down", "Left", "Right"
    public void moveSnake(String direction) {
        this.snake.move(direction);
    }

    public int getScore() {
        return this.score;
    }

    // INCREASES THE SCORE BY AN INCREMENT
    public void increaseScore(int increment) {
        this.score += increment;
    }

    public void setScore(int value) {
        this.score = value;
    }

    // RETURNS HORIZONTAL LENGTH OF GRID, SPECIFIED BY USER
    public int getXSize() {
        return this.xSize;
    }

    // RETURNS VERTICAL LENGTH OF GRID, SPECIFIED BY USER
    public int getYSize() {
        return this.ySize;
    }
}

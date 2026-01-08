package src;

import java.util.ArrayList;
import java.util.Random;

public class Snakemodel {
    private int score;
    private Snake snake;
    private Point apple;
    private int x_size;
    private int y_size;

    public Snakemodel(int m, int n) {
        this.score = 0;
        this.x_size = m;
        this.y_size = n;
        // MAKES A SNAKE THAT IS IN THE MIDDLE TWO POINTS OF THE GRID FACING LEFT
        this.snake = new Snake(new Point((int) Math.floor(x_size / 2), (int) Math.floor(y_size / 2)),
                new Point((int) Math.floor(x_size / 2) + 1, (int) Math.floor(y_size / 2)), m, n);
        spawnApple();
    }

    public void spawnApple() {
        Random random = new Random();
        // GETS EVERY POSSIBLE POINT IN THE GRID
        ArrayList<Point> availableSpawnPoints = new ArrayList<Point>();
        for (int i = 0; i < x_size; i++) {
            for (int j = 0; j < y_size; j++) {
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
    public void moveSnake() {
        this.snake.move();
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
        return this.x_size;
    }

    // RETURNS VERTICAL LENGTH OF GRID, SPECIFIED BY USER
    public int getYSize() {
        return this.y_size;
    }
    public String getDirection(){
        return this.snake.getDirection();
    }
    public void changeDirection(String newDirection){
        this.snake.changeDirection(newDirection);
    }
}

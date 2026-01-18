package src;

import java.util.ArrayList;
import java.util.Random;

public class Snakemodel {
    private int score;
    private Snake snake;
    private Point apple;
    private Point badApple;
    private Point bomb;
    private Point speedApple;
    private Point goldenApple;
    private ArrayList<Point> bonusApples = new ArrayList<>();
    private Point funkyApple;
    private int x_size;
    private int y_size;

    // Written by Daniel
    public Snakemodel(int m, int n) {
        this.score = 0;
        this.x_size = m;
        this.y_size = n;
        // MAKES A SNAKE THAT IS IN THE MIDDLE TWO POINTS OF THE GRID FACING LEFT
        this.snake = new Snake(new Point((int) Math.floor(x_size / 2), (int) Math.floor(y_size / 2)),
                new Point((int) Math.floor(x_size / 2) + 1, (int) Math.floor(y_size / 2)), m, n);
        spawnApple();
    }

    // Written by Daniel
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

    // Written by Daniel
    // CALLED IF THE APPLE IS EATEN
    public void consumedApple() {
        this.snake.grow();
        increaseScore(1);
        spawnApple();
    }

    // Written by Adel
    public void spawnBadApple() {
        Random random = new Random();

        ArrayList<Point> availableSpawnPoints = new ArrayList<Point>();
        for (int i = 0; i < x_size; i++) {
            for (int j = 0; j < y_size; j++) {
                availableSpawnPoints.add(new Point(i, j));
            }
        }

        for (Point bodyPart : snake.getSnake()) {
            availableSpawnPoints.remove(bodyPart);
        }

        this.badApple = availableSpawnPoints.get(
                random.nextInt(0, availableSpawnPoints.size() - 1));
    }

    // Written by Adel
    public void spawnBomb() {
        Random random = new Random();

        ArrayList<Point> availableSpawnPoints = new ArrayList<Point>();
        for (int i = 0; i < x_size; i++) {
            for (int j = 0; j < y_size; j++) {
                availableSpawnPoints.add(new Point(i, j));
            }
        }
        for (Point bodyPart : snake.getSnake()) {
            availableSpawnPoints.remove(bodyPart);
        }

        // don't spawn on apple/bad apple
        if (apple != null)
            availableSpawnPoints.remove(apple);
        if (badApple != null)
            availableSpawnPoints.remove(badApple);
        if (bomb != null)
            availableSpawnPoints.remove(bomb); // don't respawn in the same spot

        this.bomb = availableSpawnPoints.get(
                random.nextInt(0, availableSpawnPoints.size() - 1));
    }

    // Written by Adel
    public void spawnSpeedApple() {
        Random random = new Random();
        ArrayList<Point> availableSpawnPoints = new ArrayList<Point>();
        for (int i = 0; i < x_size; i++) {
            for (int j = 0; j < y_size; j++) {
                availableSpawnPoints.add(new Point(i, j));
            }
        }
        for (Point bodyPart : snake.getSnake()) {
            availableSpawnPoints.remove(bodyPart);
        }

        // don't spawn on apple/bad apple
        if (apple != null)
            availableSpawnPoints.remove(apple);
        if (badApple != null)
            availableSpawnPoints.remove(badApple);
        if (bomb != null)
            availableSpawnPoints.remove(bomb); // don't respawn in the same spot
        if (availableSpawnPoints.isEmpty())
            return;

        speedApple = availableSpawnPoints.get(random.nextInt(availableSpawnPoints.size()));
    }

    // Written by Adel
    public void spawnGoldenApple() {
        Random random = new Random();
        ArrayList<Point> availableSpawnPoints = new ArrayList<Point>();
        for (int i = 0; i < x_size; i++) {
            for (int j = 0; j < y_size; j++) {
                availableSpawnPoints.add(new Point(i, j));
            }
        }
        for (Point bodyPart : snake.getSnake()) {
            availableSpawnPoints.remove(bodyPart);
        }

        // don't spawn on apple/bad apple
        if (apple != null)
            availableSpawnPoints.remove(apple);
        if (badApple != null)
            availableSpawnPoints.remove(badApple);
        if (bomb != null)
            availableSpawnPoints.remove(bomb); // don't respawn in the same spot
        if (speedApple != null)
            availableSpawnPoints.remove(speedApple);
        for (Point p : bonusApples)
            availableSpawnPoints.remove(p);

        if (availableSpawnPoints.isEmpty())
            return;

        goldenApple = availableSpawnPoints.get(random.nextInt(availableSpawnPoints.size()));
    }

    // Written by Adel
    public void spawnBonusApples(int count) {
        Random random = new Random();
        bonusApples.clear();

        ArrayList<Point> available = new ArrayList<Point>();
        for (int i = 0; i < x_size; i++) {
            for (int j = 0; j < y_size; j++) {
                available.add(new Point(i, j));
            }
        }

        for (Point bodyPart : snake.getSnake()) {
            available.remove(bodyPart);
        }

        if (apple != null)
            available.remove(apple);
        if (badApple != null)
            available.remove(badApple);
        if (bomb != null)
            available.remove(bomb);
        if (speedApple != null)
            available.remove(speedApple);
        if (goldenApple != null)
            available.remove(goldenApple);

        for (int i = 0; i < count && !available.isEmpty(); i++) {
            Point p = available.remove(random.nextInt(available.size()));
            bonusApples.add(p);
        }
    }

    // Written by Adel
    public void spawnFunkyApple() {
        Random random = new Random();
        ArrayList<Point> available = new ArrayList<>();

        for (int i = 0; i < x_size; i++) {
            for (int j = 0; j < y_size; j++) {
                available.add(new Point(i, j));
            }
        }

        for (Point bodyPart : snake.getSnake()) {
            available.remove(bodyPart);
        }

        if (apple != null)
            available.remove(apple);
        if (badApple != null)
            available.remove(badApple);
        if (bomb != null)
            available.remove(bomb);
        if (speedApple != null)
            available.remove(speedApple);
        if (goldenApple != null)
            available.remove(goldenApple);

        if (available.isEmpty())
            return;

        funkyApple = available.get(random.nextInt(available.size()));
    }

    // RETURNS THE FULL SNAKE, HEAD AND TAIL INCLUDED IN AN ARRAYLIST
    public ArrayList<Point> getSnake() {
        return this.snake.getSnake();
    }

    public Snake getSnakeObject() {
        return this.snake;
    }

    // GETS THE POSITION OF THE APPLE
    public Point getApple() {
        return this.apple;
    }

    public Point getBadApple() {
        return this.badApple;
    }

    public void consumedBadApple() {
        this.badApple = null;
    }

    public Point getBomb() {
        return this.bomb;
    }

    public void consumedBomb() {
        this.bomb = null;
    }

    public Point getSpeedApple() {
        return speedApple;
    }

    public void consumedSpeedApple() {
        speedApple = null;
    }

    public Point getGoldenApple() {
        return goldenApple;
    }

    public void consumedGoldenApple() {
        this.goldenApple = null;
    }

    public ArrayList<Point> getBonusApples() {
        return bonusApples;
    }

    public void clearBonusApples() {
        bonusApples.clear();
    }

    public Point getFunkyApple() {
        return funkyApple;
    }

    public void consumedFunkyApple() {
        funkyApple = null;
    }

    // Accepts "Up", "Down", "Left", "Right"
    public void moveSnake() {
        this.snake.move();
    }

    public void snakeCanTurn() {
        snake.canTurn();
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

    public String getDirection() {
        return this.snake.getDirection();
    }

    public void changeDirection(String newDirection) {
        this.snake.changeDirection(newDirection);
    }

    public ArrayList<String> getBodyDirections() {
        return this.snake.getBodyDirections();
    }
}

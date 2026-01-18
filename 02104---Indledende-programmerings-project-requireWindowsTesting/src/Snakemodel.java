package src;

import java.util.ArrayList;
import java.util.Random;

public class Snakemodel {
    private int score;
    private Snake snake;
    private Point apple;
    private Point poisonApple;
    private Point bomb;
    private Point coconut;
    private Point star;
    private ArrayList<Point> bonusApples = new ArrayList<>();
    private Point mushroom;
    private int x_size;
    private int y_size;
    private String levelSize;

    public Snakemodel(int m, int n) {
        this.score = 0;
        this.x_size = m;
        this.y_size = n;
        // MAKES A SNAKE THAT IS IN THE MIDDLE TWO POINTS OF THE GRID FACING LEFT
        this.snake = new Snake(new Point((int) Math.floor(x_size / 2), (int) Math.floor(y_size / 2)),
                new Point((int) Math.floor(x_size / 2) + 1, (int) Math.floor(y_size / 2)), x_size, y_size);
        spawnApple();
    }

    public void reset() {
        score = 0;
        snake = new Snake(new Point((int) Math.floor(x_size / 2), (int) Math.floor(y_size / 2)),
                new Point((int) Math.floor(x_size / 2) + 1, (int) Math.floor(y_size / 2)), x_size, y_size);
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
        this.apple = availableSpawnPoints.get(random.nextInt(availableSpawnPoints.size()));
    }

    // CALLED IF THE APPLE IS EATEN
    public void consumedApple() {
        this.snake.grow();
        increaseScore(1);
        spawnApple();
    }

    public void spawnPoisonApple() {
        if (isLateGame()) {         //stops special items in late game
            return;
        }


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

        if (availableSpawnPoints.isEmpty()) return;
        this.poisonApple = availableSpawnPoints.get(
                random.nextInt(availableSpawnPoints.size()));
    }

    public void spawnBomb() {
        if (isLateGame()) {
            return;
        }

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
        if (poisonApple != null)
            availableSpawnPoints.remove(poisonApple);
        if (bomb != null)
            availableSpawnPoints.remove(bomb); // don't respawn in the same spot
        if (availableSpawnPoints.isEmpty()) return;

        this.bomb = availableSpawnPoints.get(
                random.nextInt(availableSpawnPoints.size()));
    }

    public void spawnCoconut() {
        if (isLateGame()) {
            return;
        }

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
        if (poisonApple != null)
            availableSpawnPoints.remove(poisonApple);
        if (bomb != null)
            availableSpawnPoints.remove(bomb); // don't respawn in the same spot
        if (availableSpawnPoints.isEmpty())
            return;

        coconut = availableSpawnPoints.get(random.nextInt(availableSpawnPoints.size()));
    }

    public void spawnStar() {
        if (isLateGame()) {
            return;
        }

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
        if (poisonApple != null)
            availableSpawnPoints.remove(poisonApple);
        if (bomb != null)
            availableSpawnPoints.remove(bomb); // don't respawn in the same spot
        if (coconut != null)
            availableSpawnPoints.remove(coconut);
        for (Point p : bonusApples)
            availableSpawnPoints.remove(p);

        if (availableSpawnPoints.isEmpty())
            return;

        star = availableSpawnPoints.get(random.nextInt(availableSpawnPoints.size()));
    }

    public void spawnBonusApples(int count) {
        if (isLateGame()) {
            return;
        }

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
        if (poisonApple != null)
            available.remove(poisonApple);
        if (bomb != null)
            available.remove(bomb);
        if (coconut != null)
            available.remove(coconut);
        if (star != null)
            available.remove(star);

        for (int i = 0; i < count && !available.isEmpty(); i++) {
            Point p = available.remove(random.nextInt(available.size()));
            bonusApples.add(p);
        }
    }

    public void spawnMushroom() {
        if (isLateGame()) {
            return;
        }

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
        if (poisonApple != null)
            available.remove(poisonApple);
        if (bomb != null)
            available.remove(bomb);
        if (coconut != null)
            available.remove(coconut);
        if (star != null)
            available.remove(star);

        if (available.isEmpty())
            return;

        mushroom = available.get(random.nextInt(available.size()));
    }

    public boolean isLateGame() {
        int boardSize = x_size * y_size;
        int snakeLength = snake.getSnake().size();
        return snakeLength >= boardSize * 0.75;
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

    public Point getPoisonApple() {
        return this.poisonApple;
    }

    public void consumedPoisonApple() {
        this.poisonApple = null;
    }

    public Point getBomb() {
        return this.bomb;
    }

    public void consumedBomb() {
        this.bomb = null;
    }

    public Point getCoconut() {
        return coconut;
    }

    public void consumedCoconut() {
        coconut = null;
    }

    public Point getStar() {
        return star;
    }

    public void consumedStar() {
        this.star = null;
    }

    public ArrayList<Point> getBonusApples() {
        return bonusApples;
    }

    public void clearBonusApples() {
        bonusApples.clear();
    }

    public Point getMushroom() {
        return mushroom;
    }

    public void consumedMushroom() {
        mushroom = null;
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

    //Changes size of the level
    public void setGridSize(int width, int height) {
        this.x_size = width;
        this.y_size = height;
        reset();
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

    public String getLevelSize() {
        return levelSize;
    }

    public void setLevelSize(String size) {
        levelSize = size;
    }
}

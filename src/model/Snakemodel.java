package src.model;

import java.util.*;

public class Snakemodel {
    private int score;
    private Snake snake;

    private List<Point> appleBasket;
    private Point poisonousApple;
    private Point bomb;
    private Point coconut;
    private Point star;
    private Point mushroom;

    private boolean ateCoconut;
    private boolean ateStar;
    private boolean ateMushroom;

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
        direction = Direction.LEFT;

        snake.addPart(new Point((int) Math.floor(height / 2), (int) Math.floor(width / 2)), direction);
        snake.grow(new Point((int) Math.floor(height / 2), (int) Math.floor(width / 2)), direction);

        appleBasket = new LinkedList<>();

        // RESET ALL POWERUPS TO NULL
        poisonousApple = null;
        bomb = null;
        coconut = null;
        star = null;
        mushroom = null;

        score = 0;
        ateCoconut = false;
        ateStar = false;
        ateMushroom = false;
        gameOver = false;
        spawnApple();
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

        snake.grow(newHead, direction);

        if (appleBasket.contains(newHead)) {
            consumeApple(newHead);
        } else if (poisonousApple != null && poisonousApple.equals(newHead)) {
            consumePoisonousApple();
            snake.cutTail();
        } else if (bomb != null && bomb.equals(newHead)) {
            consumeBomb();
        } else if (coconut != null && coconut.equals(newHead)) {
            consumeCoconut();
            snake.cutTail();
        } else if (star != null && star.equals(newHead)) {
            consumeStar();
            snake.cutTail();
        } else if (mushroom != null && mushroom.equals(newHead)) {
            consumeMushroom();
            snake.cutTail();
        } else {
            snake.cutTail();
        }
    }

    public LinkedList<Point> occupiedPoint() {
        LinkedList<Point> occupiedPoint = new LinkedList<>();
        occupiedPoint.addAll(snake.getSnake());
        occupiedPoint.addAll(appleBasket);

        // Only add non-null items
        if (poisonousApple != null)
            occupiedPoint.add(poisonousApple);
        if (bomb != null)
            occupiedPoint.add(bomb);
        if (coconut != null)
            occupiedPoint.add(coconut);
        if (star != null)
            occupiedPoint.add(star);
        if (mushroom != null)
            occupiedPoint.add(mushroom);

        return occupiedPoint;
    }

    public void spawnApple() {
        Random random = new Random();
        Point apple = new Point(random.nextInt(width), random.nextInt(height));
        if (occupiedPoint().contains(apple)) {
            spawnApple();
        } else if (ateStar) {
            appleBasket.add(apple);
        } else {
            appleBasket.addFirst(apple);
        }
        if (appleBasket.size() >= 10) {
            ateStar = false;
        }
    }

    public void clearAppleBasket() {
        Point apple = appleBasket.getFirst();
        appleBasket.clear();
        appleBasket.add(apple);
    }

    public void consumeApple(Point head) {
        for (int i = 0; i < appleBasket.size(); i++) {
            if (appleBasket.get(i).equals(head)) {
                if (i == 0) {
                    appleBasket.remove(i);
                    spawnApple();
                    break;
                } else {
                    appleBasket.remove(i);
                    break;
                }
            }
        }
        increaseScore(10);
    }

    public void spawnPoisonousApple() {
        Random random = new Random();
        poisonousApple = new Point(random.nextInt(width), random.nextInt(height));
        if (occupiedPoint().contains(poisonousApple)) {
            spawnPoisonousApple();
        } else {
        }
    }

    public void consumePoisonousApple() {
        if (snake.getSnake().size() < 2) {
            gameOver = true;
            return;
        }
        poisonousApple = new Point(-1, -1);
        snake.cutTail();
        snake.cutTail();
        increaseScore(-50);
    }

    public void spawnBomb() {
        Random random = new Random();
        bomb = new Point(random.nextInt(width), random.nextInt(height));
        Point head = snake.getHead();

        if (occupiedPoint().contains(bomb) ||
                (head.x() + 2 <= bomb.x() ||
                        head.x() - 2 > bomb.x()) &&
                        (head.y() + 2 <= bomb.y() ||
                                head.y() - 2 > bomb.y())) {
            spawnBomb();
        }
    }

    public void consumeBomb() {
        gameOver = true;
        return;
    }

    public void spawnCoconut() {
        Random random = new Random();
        coconut = new Point(random.nextInt(width), random.nextInt(height));
        if (occupiedPoint().contains(coconut)) {
            spawnCoconut();
        }
    }

    public void consumeCoconut() {
        coconut = new Point(-1, -1);
        ateCoconut = true;
    }

    public boolean ateCoconut() {
        return ateCoconut;
    }

    public void doneEatingCoconut() {
        ateCoconut = false;
    }

    public void spawnStar() {
        Random random = new Random();
        star = new Point(random.nextInt(width), random.nextInt(height));
        if (occupiedPoint().contains(star)) {
            spawnStar();
        }
    }

    public void consumeStar() {
        star = new Point(-1, -1);
        ateStar = true;
    }

    public boolean ateStar() {
        return ateStar;
    }

    public void doneEatingStar() {
        ateStar = false;
    }

    public void spawnMushroom() {
        Random random = new Random();
        mushroom = new Point(random.nextInt(width), random.nextInt(height));
        if (occupiedPoint().contains(mushroom)) {
            spawnMushroom();
        }
    }

    public void consumeMushroom() {
        mushroom = new Point(-1, -1);
        ateMushroom = true;
    }

    public boolean ateMushroom() {
        return ateMushroom;
    }

    // public void doneEatingMushroom() {
    // ateMushroom = false;
    // }

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

    public List<Direction> sendBodyDirections() {
        return snake.getBodyDirections();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Point> getAppleBasket() {
        return appleBasket;
    }

    public Point getPoisonousApple() {
        return poisonousApple;
    }

    public Point getBomb() {
        return bomb;
    }

    public Point getCoconut() {
        return coconut;
    }

    public Point getStar() {
        return star;
    }

    public Point getMushroom() {
        return mushroom;
    }

    public int getScore() {
        return score;
    }

    // INCREASES THE SCORE BY AN INCREMENT
    public void increaseScore(int increment) {
        score += increment;
    }
}

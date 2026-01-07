package model;

import java.util.*;

public class Model {

        public final int height;
        public final int width;

        private LinkedList<Point> snake;
        private Point food;
        private Direction direction;
        private boolean gameOver;

        public Model(int height, int width) {
                this.height = height;
                this.width = width;
                reset();
        }

        public void update() {
                if (gameOver)
                        return;

                Point head = snake.getFirst();
                Point newHead = head.move(direction);

                if (snake.contains(newHead) && !(newHead.equals(snake.getLast()))) {
                        gameOver = true;
                        System.out.println("\nnewHead: " + newHead + "\ngetLast: " + snake.getLast());
                        if (newHead.equals(snake.getLast())) {
                                System.out.println("Weird...");
                        }
                        return;
                }

                /*
                if (newHead.x() < 0 || newHead.x() >= width ||
                                newHead.y() < 0 || newHead.y() >= height ||
                                snake.contains(newHead)) {
                        gameOver = true;
                        return;
                }
                */
                if (newHead.x() < 0) {
                        newHead = new Point(width - 1,newHead.y());
                } else if (newHead.x() >= width) {
                        newHead = new Point(0 ,newHead.y());
                } else if (newHead.y() < 0) {
                        newHead = new Point(newHead.x(), height - 1);
                } else if (newHead.y() >= height) {
                        newHead = new Point(newHead.x(), 0);
                }

                snake.addFirst(newHead);
                

                if (newHead.equals(food)) {
                        spawnFood();
                } else {
                        snake.removeLast();
                }

        }

        public void reset() {
                snake = new LinkedList<>();
                snake.add(new Point((int)Math.floor(height / 2), (int)Math.floor(width / 2)));
                direction = Direction.RIGHT;
                spawnFood();
                gameOver = false;
        }

        public void spawnFood() {
                Random random = new Random();
                food = new Point(random.nextInt(width), random.nextInt(height));
        }

        public LinkedList<Point> getSnake() {
                return snake;
        }

        public Point getFood() {
                return food;
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

        public int getWidth() {
                return width;
        }

        public int getHeight() {
                return height;
        }

}
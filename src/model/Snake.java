package src.model;

import java.util.LinkedList;

public class Snake {
    private LinkedList<Point> snake;
    private LinkedList<Direction> directions;

    public Snake() {
        this.snake = new LinkedList<Point>();
        this.directions = new LinkedList<Direction>();
    }

    public void addPart(Point newPart, Direction dir) {
        snake.add(newPart);
        directions.add(dir);
    }

    public void cutTail() {
        if (!snake.isEmpty()) {
            snake.removeLast();
            if (!directions.isEmpty()) {
                directions.removeLast();
            }
        }
    }

    public void grow(Point newHead, Direction dir) {
        snake.addFirst(newHead);
        directions.addFirst(dir);
    }

    public Point getHead() {
        return snake.getFirst();
    }

    public Point getTail() {
        return snake.getLast();
    }

    // RETURNS THE ENTIRE SNAKE IN AN ARRAYLIST<POINT>. INCLUDING THE HEAD AND TAIL
    public LinkedList<Point> getSnake() {
        return snake;
    }

    public LinkedList<Direction> getBodyDirections() {
        return directions;
    }
}
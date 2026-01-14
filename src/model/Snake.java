package src.model;

import java.util.LinkedList;

public class Snake {
    private LinkedList<Point> snake;

    public Snake() {
        this.snake = new LinkedList<Point>();
    }

    public void move() {
        
    }

    public void addPart(Point newPart) {
        snake.add(newPart);
    }

    public void cutTail() {
        snake.removeLast();
    }

    public void grow(Point newHead) {
        snake.addFirst(newHead);
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
}
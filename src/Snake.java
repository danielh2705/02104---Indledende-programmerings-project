package src;

import java.util.ArrayList;

public class Snake {
    private Point head;
    private Point tail;
    private ArrayList<Point> body;
    private String direction;
    private Point lastpositionPoint;
    private int x_size;
    private int y_size;

    Snake(Point head, Point tail, int x_size, int y_size) {
        this.head = head;
        this.tail = tail;
        this.direction = "LEFT";
        this.body = new ArrayList<Point>();
        this.x_size = x_size;
        this.y_size = y_size;
    }

    public Point getHeadPos() {
        return head;
    }

    public Point getTailPos() {
        return tail;
    }

    public String getDirection() {
        return direction;
    }

    public ArrayList<Point> getSnake() {
        ArrayList<Point> returnSnake = new ArrayList<Point>(this.body);
        returnSnake.addFirst(this.head);
        returnSnake.add(returnSnake.size(), this.tail);
        return returnSnake;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void grow() {
        this.body.addLast(this.tail);
        this.tail= this.lastpositionPoint;
        System.out.println(this.body.toString());
    }

    public void move(String direction) {
        switch (direction) {
            case "UP":
                if (this.direction != "DOWN") {
                    this.lastpositionPoint = this.tail;
                    this.body.addFirst(new Point(this.head.x, this.head.y));
                    this.head.y -= 1;
                    if (this.head.y < 0) {
                        this.head.y = this.y_size - 1;
                    }
                    this.tail = this.body.getLast();
                    this.body.remove(this.body.size() - 1);
                    this.direction = "UP";
                }
                break;
            case "LEFT":
                if (this.direction != "RIGHT") {
                    this.lastpositionPoint = this.tail;
                    this.body.addFirst(new Point(this.head.x, this.head.y));
                    this.head.x -= 1;
                    if (this.head.x < 0) {
                        this.head.x = x_size - 1;
                    }
                    this.tail = this.body.getLast();
                    this.body.remove(this.body.size() - 1);
                    this.direction = "LEFT";
                }
                break;
            case "RIGHT":
                if (this.direction != "LEFT") {
                    this.lastpositionPoint = this.tail;
                    this.body.addFirst(new Point(this.head.x, this.head.y));
                    this.head.x += 1;
                    if (this.head.x > x_size - 1) {
                        this.head.x = 0;
                    }
                    this.tail = this.body.getLast();
                    this.body.remove(this.body.size() - 1);
                    this.direction = "RIGHT";
                }
                break;
            case "DOWN":
                if (this.direction != "UP") {
                    this.lastpositionPoint = this.tail;
                    this.body.addFirst(new Point(this.head.x, this.head.y));
                    this.head.y += 1;
                    this.tail = this.body.getLast();
                    this.body.remove(this.body.size() - 1);
                    this.direction = "DOWN";
                    if (this.head.y > this.y_size - 1) {
                        this.head.y = 0;
                    }
                }
                break;
            default:
                break;
        }
    }
}
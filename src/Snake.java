package src;
import java.util.ArrayList;

public class Snake {
    private Point head;
    private Point tail;
    private ArrayList<Point> body;
    private String direction;
    private Point lastpositionPoint;

    Snake(Point head, Point tail) {
        this.head = head;
        this.tail = tail;
        this.direction = "Left";
        this.body = new ArrayList<Point>();
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
        ArrayList<Point> returnSnake = body;
        returnSnake.addFirst(head);
        returnSnake.addLast(tail);
        return returnSnake;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void grow() {
        this.body.addLast(lastpositionPoint);
    }

    public void move(String direction) {
        switch (direction) {
            case "UP":
                if (direction != "DOWN") {
                    if (this.body.isEmpty()) {
                        this.tail = this.head;
                        this.head.y += 1;

                    } else {
                        this.lastpositionPoint = this.tail;
                        this.tail = this.body.getLast();
                        this.body.removeLast();
                        this.body.addFirst(head);
                        this.head.y += 1;
                    }
                    this.direction = "UP";
                }
                break;
            case "LEFT":
                if (direction != "RIGHT") {
                    if (this.body.isEmpty()) {
                        this.tail = this.head;
                        this.head.x -= 1;
                    } else {
                        this.lastpositionPoint = this.tail;
                        this.tail = this.body.getLast();
                        this.body.removeLast();
                        this.body.addFirst(head);
                        this.head.x -= 1;
                    }
                    this.direction = "LEFT";
                }
                break;
            case "RIGHT":
                if (direction != "LEFT") {
                    if (this.body.isEmpty()) {
                        this.tail = this.head;
                        this.head.x += 1;
                    } else {
                        this.lastpositionPoint = this.tail;
                        this.tail = this.body.getLast();
                        this.body.removeLast();
                        this.body.addFirst(head);
                        this.head.x += 1;
                    }
                    this.direction = "RIGHT";
                }
                break;
            case "DOWN":
                if (direction != "UP") {
                    if (this.body.isEmpty()) {
                        this.tail = this.head;
                        this.head.y -= 1;
                    } else {
                        this.lastpositionPoint = this.tail;
                        this.tail = this.body.getLast();
                        this.body.removeLast();
                        this.body.addFirst(head);
                        this.head.y -= 1;
                    }
                    this.direction = "Down";
                }
                break;
            default:
                break;
        }
    }
}
package src;

import java.util.ArrayList;

public class Snake {
    private Point head;
    private Point tail;
    private ArrayList<Point> body;
    private ArrayList<String> bodyDirections;
    private String direction;
    private String previousTailDirection;
    private Point lastpositionPoint;
    private int x_size;
    private int y_size;
    private boolean canTurn = true;

    Snake(Point head, Point tail, int x_size, int y_size) {
        this.head = head;
        this.tail = tail;
        this.direction = "LEFT";
        this.body = new ArrayList<Point>();
        this.x_size = x_size;
        this.y_size = y_size;
        this.bodyDirections = new  ArrayList<String>();
        this.bodyDirections.add("LEFT");
        this.bodyDirections.add("LEFT");
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

    // RETURNS THE ENTIRE SNAKE IN AN ARRAYLIST<POINT>. INCLUDING THE HEAD AND TAIL
    public ArrayList<Point> getSnake() {
        ArrayList<Point> returnSnake = new ArrayList<Point>(this.body);
        returnSnake.addFirst(this.head);
        returnSnake.add(returnSnake.size(), this.tail);
        return returnSnake;
    }

    // SETS CURRENT DIRECTION
    public void setDirection(String direction) {
        // THIS MAKES SURE TWO DIRECTION CHANGES DOES NOT COME IN BEFORE IT HAS MOVED
        // WITH THE PREVIOUS ONE
        // THIS IS TO STOP THE HEAD FROM MAKING A 180 AND KILLING ITSELF IMMEDIATELY
        // COULD BE EXPADED WITH A "BUFFER" SYSTEM SO IT REMEMBERS OTHER KEY PRESSES FOR
        // A SHORT WHILE TO IMPROVE GAME FEEL
        if (this.canTurn == true) {
            this.direction = direction;
            this.canTurn = false;
        }
    }

    // INCREASES THE SNAKES LENGTH BY 1
    public void grow() {
        updateBodyDirections(this.previousTailDirection, true);
        this.body.addLast(this.tail);
        this.tail = this.lastpositionPoint;
        System.out.println(this.body.toString());
    }

    // TAKES "LEFT" "RIGHT" "UP" "DOWN" AS INPUTS
    public void move() {
        updateBodyDirections(this.direction, false);
        switch (this.direction) {
            case "UP":
                // MOVES SNAKE IN THE UP DIRECTION IF NOT MOVING DOWN
                // DOES THIS BY ASSIGNING A NEW HEAD, ADDING THE HEAD TO THE FRONT OF THE BODY
                // MAKES THE TAIL THE LAST PART OF THE BODY AND THEN REMOVES SAID LAST PART
                // ALSO REMEMBERS THE LAST POSITION OF THE TAIL FOR POTENTIAL GROWTH
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
                    this.canTurn = true;
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
                    this.canTurn = true;
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
                    this.canTurn = true;
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
                    this.canTurn = true;
                    if (this.head.y > this.y_size - 1) {
                        this.head.y = 0;
                    }
                }
                break;
            default:
                break;
        }

    }

    // CHANGES DIRECTION OF SNAKE IF NOT 180 DEGREE TURN
    public void changeDirection(String newDirection) {
        switch (newDirection) {
            case "LEFT":
                if (this.direction != "RIGHT") {
                    this.direction = "LEFT";
                }
                break;
            case "RIGHT":
                if (this.direction != "LEFT") {
                    this.direction = "RIGHT";
                }
                break;
            case "UP":
                if (this.direction != "DOWN") {
                    this.direction = "UP";
                }
                break;
            case "DOWN":
                if (this.direction != "UP") {
                    this.direction = "DOWN";
                }
                break;

            default:
                break;
        }
    }

    private void updateBodyDirections(String direction, boolean growing) {
        ArrayList<String> newBodyDirections = new ArrayList<String>(this.bodyDirections);
        if (growing != true) {
            this.previousTailDirection = newBodyDirections.getLast();
            newBodyDirections.removeLast();
            newBodyDirections.addFirst(direction);
            this.bodyDirections = newBodyDirections;
        } else{
            newBodyDirections.addLast(this.previousTailDirection);
            this.bodyDirections = newBodyDirections;
        }
    }

    public void shrink(int amount) {
        for (int i = 0; i < amount && body.size() > 1; i++) {
            body.remove(body.size() - 1);
        }
    }

    public ArrayList<String> getBodyDirections(){
        return this.bodyDirections;
    }
}
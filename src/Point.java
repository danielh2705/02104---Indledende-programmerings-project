package src;

public class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Point target) {
        if (this.x == target.x && this.y == target.y) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "x: " + this.x + "  y: " + this.y;
    }
}

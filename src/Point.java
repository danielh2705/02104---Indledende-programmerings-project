public class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // written by Daniel
    public boolean equals(Point target) {
        if (this.x == target.x && this.y == target.y) {
            return true;
        }
        return false;
    }

    // written by Daniel
    @Override
    public String toString() {
        return "x: " + this.x + "  y: " + this.y;
    }
}

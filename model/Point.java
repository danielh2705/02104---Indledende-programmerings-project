package model;

public record Point(int x, int y) {
        public Point move(Direction d) {
                return switch(d) {
                        case UP -> new Point(x, y-1);
                        case DOWN -> new Point(x, y+1);
                        case LEFT -> new Point(x-1, y);
                        case RIGHT -> new Point(x+1, y);
                };
        }
}

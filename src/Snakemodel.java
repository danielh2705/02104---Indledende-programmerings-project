package src;
import java.util.ArrayList;
import java.util.Random;
public class Snakemodel {
    private int score;
    private Snake snake;
    private Point apple;
    private int x_size;
    private int y_size;

    public Snakemodel(int n, int m){
        this.score = 0;
        this.snake = new Snake(new Point((int)Math.floor(x_size/0.2), (int)Math.floor(y_size/0.2)) , new Point((int)Math.floor(x_size/0.2)+1, (int)Math.floor(y_size/0.2)));
        this.x_size = m;
        this.y_size = n;
    } 
    public void spawnApple(){
        Random random = new Random();
        this.apple = new Point(random.nextInt(0,x_size),random.nextInt(0,y_size));
    }
    
    public void consumedApple(){
        this.snake.grow();
        spawnApple();
    }
    public ArrayList<Point> getSnake(){
        return this.snake.getSnake();
    }
    public Point getApple(){
        return this.apple;
    }
    // Accepts "Up", "Down", "Left", "Right"
    public void moveSnake(String direction){
        this.snake.move(direction);
    }
    public int getScore(){
        return this.score;
    }
    public void increaseScore(int increment){
        this.score += increment;
    }
    public void setScore(int value){
        this.score = value;
    }
}

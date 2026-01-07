package src;
import java.util.ArrayList;
import java.util.Random;
public class Snakemodel {
    private int score;
    private Snake snake;
    private Point apple;
    private int x_size;
    private int y_size;

    public Snakemodel(int m, int n){
        this.score = 0;
        this.x_size = m;
        this.y_size = n;
        this.snake = new Snake(new Point((int)Math.floor(x_size/2), (int)Math.floor(y_size/2)) , new Point((int)Math.floor(x_size/2)+1, (int)Math.floor(y_size/2)), m, n);
        spawnApple();
    } 
    public void spawnApple(){
        Random random = new Random();
        ArrayList<Point> availableSpawnPoints = new ArrayList<Point>();
        for (int i = 0; i < x_size; i++) {
            for (int j = 0; j < y_size; j++) {
                availableSpawnPoints.add(new Point(i,j));
            }
        }
        ArrayList<Point> unAvailableSpawnPoints = new ArrayList<Point>();
        for(Point snakeBit:getSnake()){
            for(Point spawnPoint : availableSpawnPoints){
                if(snakeBit.equals(spawnPoint)){
                    unAvailableSpawnPoints.add(spawnPoint);
                }
            }
        }
        availableSpawnPoints.removeAll(unAvailableSpawnPoints);
        this.apple = availableSpawnPoints.get(random.nextInt(0,availableSpawnPoints.size()-1));
    }
    
    public void consumedApple(){
        this.snake.grow();
        increaseScore(1);
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
    public int getXSize(){
        return this.x_size;
    }
    public int getYSize(){
        return this.y_size;
    }
}

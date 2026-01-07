package view;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import model.Model;
import model.Point;


public class View extends Canvas {
        
        private static final int TILE_SIZE = 15;


        public View(Model model) {
                super(
                        model.getWidth() * TILE_SIZE,
                        model.getHeight() * TILE_SIZE
                );
        }

        public void render(Model model) {
                GraphicsContext gc = getGraphicsContext2D();

                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, getWidth(), getHeight());

                gc.setFill(Color.RED);
                Point food = model.getFood();
                gc.fillRect(food.x() * TILE_SIZE, food.y() * TILE_SIZE, TILE_SIZE, TILE_SIZE);

                gc.setFill(Color.LIMEGREEN);
                for (Point p : model.getSnake()) {
                        gc.fillRect(p.x() * TILE_SIZE, p.y() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }

                if (model.isGameOver()) {
                        gc.setFill(Color.WHITE);
                        gc.fillText("Game Over", model.getWidth(), model.getHeight());
                }
        }
}

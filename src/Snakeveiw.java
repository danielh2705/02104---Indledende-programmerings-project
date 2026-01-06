package src;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.text.*;

public class Snakeveiw extends Application {
    private Snakemodel model;
    private Snakecontroller controller;
    private Text scoreLabel;
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gui.fxml"));
            BorderPane root = loader.load();
            
            model = new Snakemodel(5, 5);
            controller = (Snakecontroller) loader.getController();
            controller.setModelAndView(model, this);
            scoreLabel = controller.getScoreLabel();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void update() {
      updateScore();
      drawSnake();
      drawApple();
    }
    private void drawApple(){

    }
    private void drawSnake(){

    }
    private void updateScore(){
        scoreLabel.setText("Score: " + model.getScore());
    }
    public static void main(String[] args) {
        launch(args);
    }

}

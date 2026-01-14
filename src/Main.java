package src;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import src.controller.Snakecontroller;
import src.model.Snakemodel;
import src.view.GameOverView;
import src.view.HelpScreenView;
import src.view.HighScoreView;
import src.view.OptionsScreenView;
import src.view.Snakeview;
import src.view.StartScreenView;

public class Main extends Application {

        @Override
        public void start(Stage stage) {
                TextInputDialog widthDialog = new TextInputDialog("20");
                widthDialog.setHeaderText("Enter board width");
                int width = Integer.parseInt(widthDialog.showAndWait().orElse("20"));

                TextInputDialog heightDialog = new TextInputDialog("20");
                heightDialog.setHeaderText("Enter board height");
                int height = Integer.parseInt(heightDialog.showAndWait().orElse("20"));

                Snakemodel model = new Snakemodel(height, width);

                StartScreenView startScreenView = new StartScreenView();
                Snakeview snakeView = new Snakeview(model);
                HighScoreView highScoreView = new HighScoreView();
                HelpScreenView helpScreenView = new HelpScreenView();
                OptionsScreenView optionsScreenView = new OptionsScreenView();

                GameOverView gameOverView = new GameOverView();
                
                StackPane root = new StackPane(startScreenView, snakeView, highScoreView, helpScreenView, optionsScreenView, gameOverView);

                Scene scene = new Scene(root);
                scene.getStylesheets().add(
                        getClass().getResource("/src/resources/style.css").toExternalForm()
                );

                new Snakecontroller(scene, startScreenView, snakeView, highScoreView, helpScreenView, optionsScreenView, gameOverView, model);

                stage.setScene(scene);
                stage.setTitle("Snake MVC");
                stage.show();
        }

        public static void main(String[] args) {
                launch();
        }
}

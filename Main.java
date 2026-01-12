
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import view.GameOverView;
import view.Snakeview;
import controller.Snakecontroller;
import model.Snakemodel;

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

                Snakeview view = new Snakeview(model);
                GameOverView gameOverView = new GameOverView();
                
                StackPane root = new StackPane(view, gameOverView);
                Scene scene = new Scene(root);

                new Snakecontroller(scene, view, gameOverView, model);

                stage.setScene(scene);
                stage.setTitle("Snake MVC");
                stage.show();
        }

        public static void main(String[] args) {
                launch();
        }
}

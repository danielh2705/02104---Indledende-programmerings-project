import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import view.View;
import controller.Controller;
import model.Model;

public class Main extends Application {

        @Override
        public void start(Stage stage) {
                TextInputDialog widthDialog = new TextInputDialog("20");
                widthDialog.setHeaderText("Enter board width");
                int width = Integer.parseInt(widthDialog.showAndWait().orElse("20"));

                TextInputDialog heightDialog = new TextInputDialog("20");
                heightDialog.setHeaderText("Enter board height");
                int height = Integer.parseInt(heightDialog.showAndWait().orElse("20"));

                Model model = new Model(height, width);

                View view = new View(model);
                StackPane root = new StackPane(view);
                Scene scene = new Scene(root);

                new Controller(scene, view, model);

                stage.setScene(scene);
                stage.setTitle("Snake MVC");
                stage.show();
        }

        public static void main(String[] args) {
                launch();
        }
}

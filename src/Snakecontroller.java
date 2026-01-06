package src;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
public class Snakecontroller {
    
    private Snakeveiw viewer;
    private Snakemodel model;
    private EventHandler<KeyEvent> eventHandler;
     
    @FXML
    private Rectangle SnakeHead;

    @FXML
    private Button button1;

    @FXML
    private Text scoreLabel;

    @FXML
    void test(KeyEvent event) {
        System.out.println(event.getCode());
        viewer.update();
    }



    public Text getScoreLabel(){
        return scoreLabel;
    }

    public EventHandler<KeyEvent> getEventHandler() {
        return eventHandler;
    }

    public void setEventHandler(EventHandler<KeyEvent> eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void setModelAndView(Snakemodel model, Snakeveiw viewer){
        this.model = model;
        this.viewer = viewer;
    }
}
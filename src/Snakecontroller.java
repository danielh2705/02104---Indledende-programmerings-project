package src;
import java.util.ArrayList;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;
public class Snakecontroller {
    private Snakeveiw viewer;
    private Snakemodel model;
    private EventHandler<KeyEvent> eventHandler;

    @FXML
    private Pane gameField;

    @FXML
    private Button button1;

    @FXML
    private Text scoreLabel;

    @FXML
    void move(KeyEvent event){
        model.moveSnake(event.getCode().toString());
        if(model.getSnake().get(0).equals(model.getApple())){
            model.consumedApple();
        }
        viewer.update();
    }

    public Pane getGamePane(){
        return this.gameField;
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
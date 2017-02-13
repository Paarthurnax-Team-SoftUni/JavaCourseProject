package Controllers;

import DataHandler.CurrentDistance;
import DataHandler.CurrentPoints;
import DataHandler.CurrentTime;
import GameLogic.Game;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.util.Observable;
import java.util.Observer;

public class GamePlayController implements Initializable {

    @FXML
    private AnchorPane gamePlayPage;
    @FXML
    private AnchorPane menu;
    @FXML
    private Button pauseBtn;
    @FXML
    private Button quitBtn;
    @FXML
    private Label timeInfo;
    @FXML
    public Label scorePoints;
    @FXML
    public Label distance;


    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        CurrentPoints currentPlayerPoints = Game.getCurrentPoints();
        CurrentTime currentTime = Game.getCurrentTime();
        CurrentDistance currentDistance = Game.getCurrentDstance();
        scorePoints.textProperty().bind(Bindings.convert(currentPlayerPoints.valueProperty()));
        timeInfo.textProperty().bind(Bindings.convert(currentTime.valueProperty()));
        distance.textProperty().bind(Bindings.convert(currentDistance.valueProperty()));
    }

    private static Observer observer = new Observer() {
        @Override
        public void update(Observable o, Object arg) {
            
        }
    };


    public void pauseGame(ActionEvent actionEvent) {
        if (Game.isPaused) Game.isPaused = false;
        else Game.isPaused = true;
//        try {
//            Robot robot = new Robot();
//            // Simulate a key press
//            robot.keyPress(KeyEvent.VK_P);
//        } catch (AWTException e) {
//            e.printStackTrace();
//        }
    }

    public void quitGame(ActionEvent actionEvent) {
        Platform.exit();
    }
}

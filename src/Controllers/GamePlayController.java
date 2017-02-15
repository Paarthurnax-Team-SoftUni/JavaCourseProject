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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.util.Observable;
import java.util.Observer;

public class GamePlayController implements Initializable {

    @FXML
    public ImageView health100;
    @FXML
    public ImageView health75;
    @FXML
    public ImageView health50;
    @FXML
    public ImageView health25;
    @FXML
    public Rectangle healthBar;

    public static ImageView _health100;
    public static ImageView _health75;
    public static ImageView _health50;
    public static ImageView _health25;
    public static Rectangle _healthBar;
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
        _health100 = health100;
        _health75 = health75;
        _health50 = health50;
        _health25 = health25;
        _healthBar = healthBar;
    }

    public static void printHealthBar(Integer healthPoints) {

       // _healthBar.setWidth(healthPoints*1.56);  IF WE DECIDE TO SHOW IT BY PERCENTIGE

        if (healthPoints > 75 && healthPoints <= 100) {
            _health100.setVisible(true);
            _health75.setVisible(false);
            _health50.setVisible(false);
            _health25.setVisible(false);
        }
        else if (healthPoints <= 75 && healthPoints > 50) {
            _health100.setVisible(false);
            _health75.setVisible(true);
            _health50.setVisible(false);
            _health25.setVisible(false);
        }
        else if (healthPoints <= 50 && healthPoints > 25) {
            _health100.setVisible(false);
            _health75.setVisible(false);
            _health50.setVisible(true);
            _health25.setVisible(false);
        }
        else if (healthPoints <= 25) {
            _health100.setVisible(false);
            _health75.setVisible(false);
            _health50.setVisible(false);
            _health25.setVisible(true);
        }
    }

    private static Observer observer = new Observer() {
        @Override
        public void update(Observable o, Object arg) {
            
        }
    };


    public void pauseGame(ActionEvent actionEvent) {
        if (Game.isIsPaused()) Game.setIsPaused(false);
        else Game.setIsPaused(true);
    }

    public void quitGame(ActionEvent actionEvent) {
        Platform.exit();
    }
}

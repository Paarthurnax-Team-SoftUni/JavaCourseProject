package controllers;

import GameEngine.RunTrack;
import dataHandler.*;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class GameController implements Initializable {

    @FXML
    public ImageView healthFirst;
    @FXML
    public ImageView healthSecond;
    @FXML
    public ImageView healthThird;
    @FXML
    public ImageView healthFourth;


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
        CurrentPoints currentPlayerPoints = RunTrack.getCurrentPoints();
        CurrentTime currentTime = RunTrack.getCurrentTime();
        CurrentDistance currentDistance = RunTrack.getCurrentDistance();
        scorePoints.textProperty().bind(Bindings.convert(currentPlayerPoints.valueProperty()));
        timeInfo.textProperty().bind(Bindings.convert(currentTime.valueProperty()));
        distance.textProperty().bind(Bindings.convert(currentDistance.valueProperty()));
        new HealthBar(healthFirst, healthSecond, healthThird, healthFourth);
    }


    public void pauseGame(ActionEvent actionEvent) {
        if (RunTrack.isIsPaused()) RunTrack.setIsPaused(false);
        else RunTrack.setIsPaused(true);
    }

    public void quitGame(ActionEvent actionEvent) {

        PlayerData.getInstance().getCurrentPlayer().updateStatsAtEnd();

        Platform.exit();
    }
}

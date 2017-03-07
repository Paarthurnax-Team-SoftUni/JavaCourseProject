package controllers;

import gameEngine.RunTrack;
import dataHandler.*;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

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
    private Label highscore;
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
        this.scorePoints.textProperty().bind(Bindings.convert(currentPlayerPoints.valueProperty()));
        this.timeInfo.textProperty().bind(Bindings.convert(currentTime.valueProperty()));
        this.distance.textProperty().bind(Bindings.convert(currentDistance.valueProperty()));
        new HealthBar(this.healthFirst, this.healthSecond, this.healthThird, this.healthFourth);
    }

    public void quitGame(ActionEvent actionEvent) {
        PlayerData.getInstance().getCurrentPlayer().updateStatsAtEnd();
        Platform.exit();
    }
}

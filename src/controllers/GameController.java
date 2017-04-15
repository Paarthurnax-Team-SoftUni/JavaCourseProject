package controllers;

import dataHandler.CurrentHealth;
import dataHandler.CurrentStats;
import dataHandler.PlayerData;
import gameEngine.RunTrack;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class GameController implements Initializable {

    @FXML
    private Label scorePoints;
    @FXML
    private Label distance;
    @FXML
    private Label bullets;
    @FXML
    private ImageView healthFirst;
    @FXML
    private ImageView healthSecond;
    @FXML
    private ImageView healthThird;
    @FXML
    private ImageView healthFourth;
    @FXML
    private Label highScore;
    @FXML
    private Label timeInfo;

    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        CurrentStats currentStats = RunTrack.getCurrentStats();
        this.getScorePoints().textProperty().bind(Bindings.convert(currentStats.valuePoints()));
        this.timeInfo.textProperty().bind(Bindings.convert(currentStats.valueTime()));
        this.getDistance().textProperty().bind(Bindings.convert(currentStats.valueDistance()));
        this.getBullets().textProperty().bind(Bindings.convert(currentStats.valueBullets()));

        this.highScore.textProperty().setValue(PlayerData.getInstance().getHighscores());
        new CurrentHealth(this.healthFirst, this.healthSecond, this.healthThird, this.healthFourth);
    }

    public void quitGame(ActionEvent actionEvent) {
        PlayerData.getInstance().getCurrentPlayer().updateStatsAtEnd();
        Platform.exit();
    }

    private Label getScorePoints() {
        return this.scorePoints;
    }

    private Label getDistance() {
        return this.distance;
    }

    private Label getBullets() {
        return this.bullets;
    }
}

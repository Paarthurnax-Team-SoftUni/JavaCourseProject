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
    public Label scorePoints;
    @FXML
    public Label distance;
    @FXML
    public Label bullets;
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
        this.scorePoints.textProperty().bind(Bindings.convert(currentStats.valuePoints()));
        this.timeInfo.textProperty().bind(Bindings.convert(currentStats.valueTime()));
        this.distance.textProperty().bind(Bindings.convert(currentStats.valueDistance()));
        this.bullets.textProperty().bind(Bindings.convert(currentStats.valueBullets()));

        this.highScore.textProperty().setValue(PlayerData.getInstance().getHighscores());
        new CurrentHealth(this.healthFirst, this.healthSecond, this.healthThird, this.healthFourth);
    }

    public void quitGame(ActionEvent actionEvent) {
        PlayerData.getInstance().getCurrentPlayer().updateStatsAtEnd();
        Platform.exit();
    }
}

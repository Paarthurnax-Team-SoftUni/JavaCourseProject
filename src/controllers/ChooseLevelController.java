package controllers;

import dataHandler.Constants;
import dataHandler.PlayerData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
import mapHandlers.Levels.FirstLevel;
import mapHandlers.Track;
import models.Player;
import stageHandler.StageManager;
import stageHandler.StageManagerImpl;

public class ChooseLevelController {

    private static int level;
    public ImageView locked2;

    private Track track;

    @FXML
    private Button startBtn;
    @FXML
    private Ellipse backgroundBox1;
    @FXML
    private Ellipse backgroundBox2;
    @FXML

    public void initialize() {
        track = new FirstLevel();
        Player player = PlayerData.getInstance().returnPlayer(track.getRunTrack().getPlayer().getName());
        showUnlockedLevelsOnly(player.getMaxLevelPassed());
    }

    public int getLevel() {
        return level;
    }

    private void setLevel(int level) {
        this.level = level;
    }

    public void startGame(ActionEvent actionEvent) {
        Stage currentStage = (Stage)this.startBtn.getScene().getWindow();
        StageManager manager = new StageManagerImpl();
        FXMLLoader loader = manager.loadSceneToStage(currentStage, Constants.GAME_PLAY_VIEW_PATH,null);
    }

    public void chooseLevel(MouseEvent ev) {
        Node source = (Node) ev.getSource();
        int id = Integer.valueOf(source.getId().substring(5));

        backgroundFill(id);
        this.setLevel(id);
        this.startBtn.setVisible(true);
    }

    private void showUnlockedLevelsOnly(int level) {
        backgroundBox1.setStyle(null);
        backgroundBox2.setStyle(null);

        backgroundBox2.setStyle("-fx-fill: rgba(95, 88, 93, 0.7);");
        backgroundBox2.toFront();
        locked2.setVisible(true);
    }

    private void backgroundFill(int id) {
        Player player = PlayerData.getInstance().returnPlayer(track.getRunTrack().getPlayer().getName());
        showUnlockedLevelsOnly(player.getMaxLevelPassed());
        System.out.println(id);
        switch (id) {
            case 1:
                backgroundBox1.setStyle("-fx-fill: rgba(255,0,0, 0.55);");
                backgroundBox1.toFront();
                break;
            case 2:
                backgroundBox2.setStyle("-fx-fill: rgba(255,0,0, 0.55);");
                break;
        }
    }

}

package controllers;

import constants.StylesConstants;
import constants.ViewsConstants;
import dataHandler.PlayerData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
import mapHandlers.Track;
import mapHandlers.TrackHandler;
import mapHandlers.levels.TrackLevel;
import stageHandler.StageManager;
import stageHandler.StageManagerImpl;

import java.io.IOException;

public class ChooseLevelController {

    private Track track;
    private TrackHandler trackHandler;

    @FXML
    private Button startBtn;
    @FXML
    private Ellipse backgroundBox1;
    @FXML
    private Ellipse backgroundBox2;
    @FXML
    private ImageView locked2;

    public void initialize() throws IOException {
        showUnlockedLevelsOnly();
        trackHandler = new TrackHandler();
    }

    @FXML
    public void startGame(ActionEvent actionEvent) {
        PlayerData.getInstance().returnPlayer(track.getRunTrack().getPlayer().getName());
        Stage currentStage = (Stage) this.startBtn.getScene().getWindow();
        StageManager manager = new StageManagerImpl();

        manager.loadSceneToStage(currentStage, ViewsConstants.GAME_PLAY_VIEW_PATH);
        AnchorPane root = manager.getRoot();
        track.createBackground(root);
    }

    @FXML
    public void chooseLevel(MouseEvent ev) throws IOException {
        Node source = (Node) ev.getSource();
        int id = Integer.valueOf(source.getId().substring(5));
        backgroundFill(id);
        this.startBtn.setVisible(true);
    }

    private void showUnlockedLevelsOnly() {

        this.backgroundBox1.setStyle(null);
        this.backgroundBox2.setStyle(null);

        this.backgroundBox2.setStyle(StylesConstants.GREY_COLOUR);
        this.backgroundBox2.toFront();
        this.locked2.setVisible(true);

        int maxLevel = PlayerData.getInstance().getCurrentPlayer().getMaxLevelPassed();

        if (maxLevel >= 1) {
            this.backgroundBox2.setStyle(null);
            this.backgroundBox2.toBack();
            this.locked2.setVisible(false);
        }
    }

    private void backgroundFill(int id) throws IOException {
        showUnlockedLevelsOnly();

        switch (id) {
            case 1:
                this.backgroundBox1.setStyle(StylesConstants.RED_COLOUR);
                this.backgroundBox1.toFront();
                track = trackHandler.getLevel(TrackLevel.FIRST_LEVEL);
                break;
            case 2:
                this.backgroundBox2.setStyle(StylesConstants.RED_COLOUR);
                this.backgroundBox2.toFront();
                track = trackHandler.getLevel(TrackLevel.SECOND_LEVEL);
                break;
        }
    }
}

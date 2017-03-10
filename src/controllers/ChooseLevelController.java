package controllers;

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
import mapHandlers.TrackLevel;
import models.Player;
import stageHandler.StageManager;
import stageHandler.StageManagerImpl;
import utils.Constants;

import java.io.IOException;

public class ChooseLevelController {

    public ImageView locked2;

    private static int level;
    private Track track;
    private Player currentPlayer;
    private TrackHandler trackHandler;

    @FXML
    private Button startBtn;
    @FXML
    private Ellipse backgroundBox1;
    @FXML
    private Ellipse backgroundBox2;
    @FXML

    public void initialize() throws IOException {
        this.setCurrentPlayer(PlayerData.getInstance().getCurrentPlayer());
        showUnlockedLevelsOnly(this.getCurrentPlayer().getMaxLevelPassed());
        trackHandler = new TrackHandler();
    }

    public int getLevel() {
        return this.level;
    }

    private void setLevel(int level) {
        this.level = level;
    }

    private Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    private void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void startGame(ActionEvent actionEvent){
        PlayerData.getInstance().returnPlayer(track.getRunTrack().getPlayer().getName());
        Stage currentStage = (Stage)this.startBtn.getScene().getWindow();
        StageManager manager = new StageManagerImpl();

        manager.loadSceneToStage(currentStage, Constants.GAME_PLAY_VIEW_PATH,null);
        AnchorPane root = manager.getRoot();
        track.createBackground(root);
    }

    public void chooseLevel(MouseEvent ev) throws IOException {
        Node source = (Node) ev.getSource();
        int id = Integer.valueOf(source.getId().substring(5));
        this.backgroundFill(id);
        this.setLevel(id);
        this.startBtn.setVisible(true);
    }

    private void showUnlockedLevelsOnly(int level) {
        this.backgroundBox1.setStyle(null);
        this.backgroundBox2.setStyle(null);

        this.backgroundBox2.setStyle(Constants.GREY_COLOUR);
        this.backgroundBox2.toFront();
        this.locked2.setVisible(true);

        if (this.currentPlayer.getMaxLevelPassed() >= 1) {
            this.backgroundBox2.setStyle(null);
            this.backgroundBox2.toBack();
            this.locked2.setVisible(false);
        }
    }

    private void backgroundFill(int id) throws IOException {
        this.showUnlockedLevelsOnly(this.currentPlayer.getMaxLevelPassed());

        switch (id) {
            case 1:
                this.backgroundBox1.setStyle(Constants.RED_COLOUR);
                this.backgroundBox1.toFront();
                track = trackHandler.getLevel(TrackLevel.FIRST_LEVEL);
                break;
            case 2:
                this.backgroundBox2.setStyle(Constants.RED_COLOUR);
                this.backgroundBox2.toFront();
                track = trackHandler.getLevel(TrackLevel.SECOND_LEVEL);
                break;
        }
    }
}

package controllers;

import dataHandler.CurrentHealth;
import dataHandler.CurrentStats;
import dataHandler.PlayerData;
import interfaces.Track;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
import mapHandlers.TrackHandler;
import models.Cheat;
import models.sprites.Ammo;
import models.sprites.Obstacle;
import models.sprites.collectibles.Collectible;
import utils.constants.GameplayConstants;
import utils.constants.StylesConstants;
import utils.constants.ViewsConstants;
import utils.stages.StageManager;
import utils.stages.StageManagerImpl;

import java.io.IOException;

public class ChooseLevelController {

    private static final int FIRST_LEVEL = 1;
    private static final int SECOND_LEVEL = 2;

    private Track track;
    private TrackHandler trackHandler;
    CurrentHealth currentHealth;
    CurrentStats currentStats;
    Ammo ammo;
    Collectible collectible;
    Obstacle obstacle;
    Cheat cheat;

    @FXML
    private Button startBtn;
    @FXML
    private Ellipse backgroundBox1;
    @FXML
    private Ellipse backgroundBox2;
    @FXML
    private ImageView locked2;


    public void initialize() throws IOException {
        this.showUnlockedLevelsOnly();
        this.trackHandler = new TrackHandler();
        this.currentHealth = new CurrentHealth(PlayerData.getInstance().getCurrentPlayer());
        this.currentStats = new CurrentStats(GameplayConstants.INITIAL_STATS_VALUE, GameplayConstants.INITIAL_STATS_VALUE, GameplayConstants.INITIAL_STATS_VALUE, GameplayConstants.INITIAL_STATS_VALUE);
        this.ammo = new Ammo();
        this.collectible = new Collectible();
        this.obstacle = new Obstacle();
        this.cheat = new Cheat();
    }

    @FXML
    private void startGame(ActionEvent actionEvent) {
        PlayerData.getInstance().returnPlayer(track.getRunTrack().getPlayer().getName());
        Stage currentStage = (Stage) this.startBtn.getScene().getWindow();
        StageManager manager = new StageManagerImpl();

        manager.loadSceneToStage(currentStage, ViewsConstants.GAME_PLAY_VIEW_PATH);
        AnchorPane root = manager.getRoot();
        this.track.createBackground(root);
    }

    @FXML
    private void chooseLevel(MouseEvent ev) throws IOException {
        Node source = (Node) ev.getSource();
        int id = Integer.valueOf(source.getId().substring(5));
        this.backgroundFill(id);
        this.startBtn.setVisible(true);
    }

    @FXML
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
            case FIRST_LEVEL:
                this.backgroundBox1.setStyle(StylesConstants.RED_COLOUR);
                this.backgroundBox1.toFront();
                break;
            case SECOND_LEVEL:
                this.backgroundBox2.setStyle(StylesConstants.RED_COLOUR);
                this.backgroundBox2.toFront();
                break;
        }
        this.track = this.trackHandler.getLevel(id, this.currentHealth,
                this.currentStats, this.ammo, this.collectible, this.obstacle, this.cheat);
    }
}

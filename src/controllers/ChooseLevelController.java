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
import utils.RandomProvider;
import utils.constants.GameplayConstants;
import utils.constants.ImagesShortcutConstants;
import utils.constants.StylesConstants;
import utils.constants.ViewsConstants;
import utils.stages.StageManager;
import utils.stages.StageManagerImpl;

import java.io.IOException;
import java.lang.reflect.Field;

public class ChooseLevelController {


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
    private ImageView locked1;
    @FXML
    private ImageView locked2;
    @FXML
    public ImageView level1;
    @FXML
    public ImageView level2;


    public void initialize() throws IOException, NoSuchFieldException, IllegalAccessException {
        this.showUnlockedLevelsOnly();
        RandomProvider randomProvider = new RandomProvider();
        this.trackHandler = new TrackHandler();
        this.currentHealth = new CurrentHealth(PlayerData.getInstance().getCurrentPlayer());
        this.currentStats = new CurrentStats(GameplayConstants.INITIAL_STATS_VALUE, GameplayConstants.INITIAL_STATS_VALUE, GameplayConstants.INITIAL_STATS_VALUE, GameplayConstants.INITIAL_STATS_VALUE,GameplayConstants.INITIAL_STATS_VALUE);
        this.ammo = new Ammo();
        this.collectible = new Collectible(randomProvider);
        this.obstacle = new Obstacle(randomProvider);
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
    private void chooseLevel(MouseEvent ev) throws IOException, NoSuchFieldException, IllegalAccessException {
        Node source = (Node) ev.getSource();
        int id = Integer.valueOf(source.getId().substring(source.getId().length()-1));
        this.backgroundFill(id);
        this.startBtn.setVisible(true);
    }

    @FXML
    private void showUnlockedLevelsOnly() throws NoSuchFieldException, IllegalAccessException {

        int maxLevel = PlayerData.getInstance().getCurrentPlayer().getMaxLevelPassed();
        Class<ChooseLevelController> chooseLevelControllerClass = ChooseLevelController.class;

        for (int id = 1; id <= GameplayConstants.LEVELS_NUMBER; id++) {
            Field ellipseField = chooseLevelControllerClass.getDeclaredField(ImagesShortcutConstants.BACKGROUND_STRING + id);
            Ellipse ellipse = ((Ellipse) ellipseField.get(this));
            Field levelField = chooseLevelControllerClass.getDeclaredField(ImagesShortcutConstants.LEVEL_CAR_STRING + id);
            ImageView level = ((ImageView) levelField.get(this));
            Field lockedField = chooseLevelControllerClass.getDeclaredField(ImagesShortcutConstants.LOCKED_CAR_STRING + id);
            ImageView locked = ((ImageView) lockedField.get(this));

            if(maxLevel+1 < id){
                ellipse.setStyle(StylesConstants.GREY_COLOUR);
                ellipse.toFront();
                ellipse.setOnMouseClicked(null);
                level.setOnMouseClicked(null);
                locked.setVisible(true);
            } else {
                locked.setVisible(false);
                ellipse.setStyle(null);
            }
        }
    }

    private void backgroundFill(int id) throws IOException, NoSuchFieldException, IllegalAccessException {
        showUnlockedLevelsOnly();
        Class<ChooseLevelController> chooseLevelControllerClass = ChooseLevelController.class;

        for (int i = 1; i <= GameplayConstants.LEVELS_NUMBER; i++) {
            Field ellipseField = chooseLevelControllerClass.getDeclaredField(ImagesShortcutConstants.BACKGROUND_STRING + i);
            Ellipse ellipse = ((Ellipse) ellipseField.get(this));
            if(i == id){
                ellipse.setStyle(StylesConstants.RED_COLOUR);
                ellipse.toFront();
            }
        }

        this.track = this.trackHandler.getLevel(id, this.currentHealth,
                this.currentStats, this.ammo, this.collectible, this.obstacle, this.cheat);
    }
}

package gameEngine;

import dataHandler.CurrentHealth;
import dataHandler.CurrentStats;
import dataHandler.PlayerData;
import dataHandler.TrackParams;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import keyHandler.KeyHandlerOnPress;
import keyHandler.KeyHandlerOnRelease;
import mapHandlers.TrackMode;
import models.Cheat;
import models.Player;
import models.sprites.obstacles.Obstacle;
import models.sprites.PlayerCar;
import models.sprites.Weapon;
import models.sprites.collectibles.Collectible;
import utils.constants.*;
import utils.music.MusicPlayer;
import utils.notifications.Notification;
import utils.stages.StageManager;
import utils.stages.StageManagerImpl;

import java.util.Observer;

public class RunTrack {

    private TrackParams trackParams=TrackParams.getInstance();
    private Observer observerParams;
    private Observer observer;
    private long time;
    private int frame;
    private int y;
    private float currentFramesPerSecond;
    private TrackMode trackMode;
    private Player player;
    private CurrentHealth currentHealth;
    private Collectible collectible;
    private Obstacle obstacle;
    private Weapon weapon;
    private PlayerCar playerCar;

    public RunTrack(Player player, float velocityValue, TrackMode trackMode,
                    CurrentHealth currentHealth, CurrentStats currentStats, Weapon weapon,
                    Collectible collectible, Obstacle obstacle, Cheat cheat) {
        this.observerParams= (o, arg) -> {
        };
        this.observer = (o, arg) -> {
        };
        this.trackParams.addObserver(this.observerParams);
        this.frame = 0;
        this.time = 0;
        this.trackParams.updateVelocity(velocityValue);
        this.trackParams.updateCurrentStats(currentStats);
        this.trackParams.updateCheat(cheat);
        this.trackMode = trackMode;
        this.player = player;
        this.playerCar = this.getPlayer().getCar();
        this.currentHealth = currentHealth;
        this.weapon = weapon;
        this.collectible = collectible;
        this.obstacle = obstacle;
        this.currentFramesPerSecond = GeneralConstants.FRAMES_PER_SECOND;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void runGame(AnchorPane root, Image background, int drunkDrivers, int minLeftSide, int maxRightSide) {

        Canvas canvas = new Canvas(GeneralConstants.CANVAS_WIDTH, GeneralConstants.CANVAS_HEIGHT);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getScene().setOnKeyPressed(new KeyHandlerOnPress(this.getPlayer(), minLeftSide, maxRightSide));
        root.getScene().setOnKeyReleased(new KeyHandlerOnRelease(this.getPlayer(), minLeftSide, maxRightSide));

        this.playerCar.updateImage(ResourcesConstants.CAR_IMAGES_PATH + this.getCarId() + ImagesShortcutConstants.HALF_SIZE);
        this.playerCar.updatePosition(GameplayConstants.INITIAL_CAR_POSITION_X, GameplayConstants.INITIAL_CAR_POSITION_Y);
        this.playerCar.updateAmmunition(trackMode.getInitialAmmunition());
        this.player.updatePoints(GameplayConstants.INITIAL_STATS_VALUE);
        this.trackParams.getCurrentStats().addObserver(observer);


        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        //Update EndTruckTime
        this.trackParams.getCurrentStats().updateEndTruckTime(this.trackMode.getEndTruckTime());

        MusicPlayer.getInstance().play();
        MusicPlayer.getInstance().startStopPause();

        KeyFrame kf = new KeyFrame(
                Duration.seconds(this.currentFramesPerSecond),
                event -> {

                    //Check for pause
                    if (this.trackParams.isPaused()) {
                        PauseHandler pauseHandler = new PauseHandler(gameLoop, gc, background, this.y, this.player, this.obstacle.getObstacles(), this.collectible.getCollectibles());
                        pauseHandler.activatePause();
                    }

                    //Set movement params
                    y = Math.round(this.y + this.trackParams.getVelocity());
                    this.time++;
                    this.frame++;
                    if (Math.abs(this.y) >= GeneralConstants.CANVAS_HEIGHT) {
                        this.y = this.y - GeneralConstants.CANVAS_HEIGHT;
                        this.frame = 0;
                    }

                    //Update stats
                    this.collectible.updateStatus();
                    this.updatePlayerStats();
                    this.playerCar.setVelocity(0, 0);
                    this.trackParams.getCheat().useCheat(this.player);

                    //Generate items
                    if (this.frame == 0) {
                        this.obstacle.add(this.obstacle.generateObstacle(drunkDrivers, minLeftSide, maxRightSide));
                        try {
                            this.collectible.add(this.collectible.generateCollectible(minLeftSide, maxRightSide));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (this.player.getCar().getAmmunition() < 0) {
                        this.trackParams.updateShoot(false);
                    }
                    if (this.trackParams.isShoot()) {
                        this.weapon.addAmmo(this.weapon.generateAmmo(this.player));
                        this.trackParams.updateShoot(false);
                    }

                    //Draw background and playerCar
                    gc.clearRect(0, 0, GeneralConstants.CANVAS_WIDTH, GeneralConstants.CANVAS_HEIGHT);
                    gc.drawImage(background, 0, y - GeneralConstants.CANVAS_HEIGHT);
                    gc.drawImage(background, 0, y);
                    this.playerCar.update();
                    this.playerCar.render(gc);

                    //Render items
                    this.weapon.visualizeAmmo(gc, this.obstacle.getObstacles(), player);
                    this.obstacle.visualizeObstacle(gc, this.trackParams.getVelocity(), player);

                    String action = this.collectible.visualizeCollectible(gc, this.trackParams.getVelocity());
                    if (action != null && action.equals(CollectiblesAndObstaclesConstants.ARMAGEDDON_STRING)) {
                        startArmageddonsPower();
                    } else if (action != null && action.equals(CollectiblesAndObstaclesConstants.FUEL_BOTTLE_STRING)) {
                        this.time -= (GameplayConstants.FUEL_TANK_BONUS_TIME / this.currentFramesPerSecond);
                    }

                    //Check for end game
                    this.checkForEndGame(root, canvas, gameLoop);
                });

        gameLoop.getKeyFrames().add(kf);
        gameLoop.playFromStart();
    }

    private void checkForEndGame(AnchorPane root, Canvas canvas, Timeline gameLoop) {
        if (this.time >= this.trackMode.getEndTruckTime() || this.player.getHealthPoints() <= 0) {

            boolean win = this.player.getHealthPoints() > 0 && this.trackParams.getCurrentStats().getDistance() >= this.trackMode.getFinalExpectedDistance();

            if (win) {
                this.player.setMaxLevelPassed(this.player.getMaxLevelPassed() + 1);
            }

            PlayerData.getInstance().updatePlayer(PlayerData.getInstance().getCurrentPlayer());

            clearObstaclesAndCollectibles();
            gameLoop.stop();
            MusicPlayer.getInstance().stop();
            Notification.hidePopupMessage();
            this.time = 0;
            this.trackParams.updateVelocity(GameplayConstants.START_GAME_VELOCITY);
            this.trackParams.getCurrentStats().updateDistance(0);

            Stage currentStage = (Stage) canvas.getScene().getWindow();
            root.getChildren().remove(canvas);
            StageManager manager = new StageManagerImpl();
            manager.loadSceneToStage(currentStage, win ? ViewsConstants.GAME_WIN_VIEW_PATH : ViewsConstants.GAME_OVER_VIEW_PATH);

            this.player.updateStatsAtEnd();
        }
    }

    private void updatePlayerStats() {
        CurrentStats currentStats=this.trackParams.getCurrentStats();
        currentStats.updateTime((long) (this.time * this.currentFramesPerSecond));
        currentStats.updateDistance(currentStats.getDistance() + (long) this.trackParams.getVelocity() / 2);
        this.player.addPoints(this.trackMode.getPointsPerDistance());
        currentStats.updatePoints(this.player.getPoints());
        currentStats.updateBullets(this.playerCar.getAmmunition());
        this.observer.update(currentStats, this.observer);
        this.observerParams.update(this.trackParams, this.observerParams);
        try {
            currentHealth.update();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private String getCarId() {
        String carId = PlayerData.getInstance().getCarId();
        return carId == null ? CarConstants.DEFAULT_CAR : carId;
    }

    private void clearObstaclesAndCollectibles() {
        this.collectible.clearObstacles();
        this.obstacle.clearObstacles();
    }

    private void startArmageddonsPower() {
        for (Obstacle o : this.obstacle.getObstacles()) {
            o.handleImpactByCarPlayer(this.trackParams.getVelocity());
        }
    }

}
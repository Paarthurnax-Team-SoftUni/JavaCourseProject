package gameEngine;

import dataHandler.CurrentHealth;
import dataHandler.CurrentStats;
import dataHandler.PlayerData;
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
import models.sprites.Ammo;
import models.sprites.Obstacle;
import models.sprites.PlayerCar;
import models.sprites.collectibles.Collectible;
import utils.constants.*;
import utils.music.MusicPlayer;
import utils.notifications.Notification;
import utils.stages.StageManager;
import utils.stages.StageManagerImpl;

import java.util.Observer;

public class RunTrack {

    private static boolean isPaused;
    private static float velocity;
    private static boolean shoot;
    private static CurrentStats currentStats;
    private static Cheat cheat;
    private long time;
    private Observer observer;
    private int frame;
    private int y;
    private float currentFramesPerSecond;
    private TrackMode trackMode;
    private Player player;
    private CurrentHealth currentHealth;
    private Collectible collectible;
    private Obstacle obstacle;
    private Ammo ammo;
    private PlayerCar playerCar;

    public RunTrack(Player player, float velocityValue, TrackMode trackMode,
                    CurrentHealth currentHealth, CurrentStats currentStats, Ammo ammo,
                    Collectible collectible, Obstacle obstacle, Cheat cheat) {
        frame = 0;
        time = 0;
        velocity = velocityValue;
        observer = (o, arg) -> {};
        RunTrack.currentStats = currentStats;
        RunTrack.cheat = cheat;
        this.trackMode=trackMode;
        this.player = player;
        this.playerCar = this.getPlayer().getCar();
        this.currentHealth = currentHealth;
        this.ammo = ammo;
        this.collectible = collectible;
        this.obstacle = obstacle;
        this.currentFramesPerSecond = GeneralConstants.FRAMES_PER_SECOND;

    }

    public static Cheat getCheat() {
        return cheat;
    }

    public static CurrentStats getCurrentStats() {
        return currentStats;
    }

    public static float getVelocity() {
        return velocity;
    }

    public static void setVelocity(float v) {
        velocity = v;
    }

    public static boolean isPaused() {
        return RunTrack.isPaused;
    }

    public static void setIsPaused(boolean isPaused) {
        RunTrack.isPaused = isPaused;
    }

    public static void setShoot(boolean isShooting) {
        shoot = isShooting;
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

        this.playerCar.setImage(ResourcesConstants.CAR_IMAGES_PATH + this.getCarId() + ImagesShortcutConstants.HALF_SIZE);
        this.playerCar.updatePosition(GameplayConstants.INITIAL_CAR_POSITION_X, GameplayConstants.INITIAL_CAR_POSITION_Y);
        this.playerCar.updateAmmunition(trackMode.getInitialAmmonition());
        this.player.updatePoints(GameplayConstants.INITIAL_STATS_VALUE);
        currentStats.addObserver(observer);

        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        //Update EndTruckTime
        RunTrack.currentStats.updateEndTruckTime(this.trackMode.getEndTruckTime());

        MusicPlayer.getInstance().play();
        MusicPlayer.getInstance().startStopPause();

        KeyFrame kf = new KeyFrame(
                Duration.seconds(this.currentFramesPerSecond),
                event -> {

                    //Check for pause
                    if (isPaused) {
                        PauseHandler pauseHandler = new PauseHandler(gameLoop, gc, background, this.y, this.player, this.obstacle.getObstacles(), this.collectible.getCollectibles());
                        pauseHandler.activatePause();
                    }

                    //Set movement params
                    y = Math.round(this.y + velocity);
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
                    cheat.useCheat(this.player);

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
                        shoot = false;
                    }
                    if (shoot) {
                        this.ammo.addAmmo(this.ammo.generateAmmo(this.player));
                        setShoot(false);
                    }

                    //Draw background and playerCar
                    gc.clearRect(0, 0, GeneralConstants.CANVAS_WIDTH, GeneralConstants.CANVAS_HEIGHT);
                    gc.drawImage(background, 0, y - GeneralConstants.CANVAS_HEIGHT);
                    gc.drawImage(background, 0, y);
                    this.playerCar.update();
                    this.playerCar.render(gc);

                    //Render items
                    this.ammo.visualizeAmmo(gc, this.obstacle.getObstacles(), player);
                    this.obstacle.visualizeObstacle(gc, velocity, player);

                    String action = this.collectible.visualizeCollectible(gc, velocity);
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

            boolean win = this.player.getHealthPoints() > 0 && currentStats.getDistance() >= this.trackMode.getFinalExpectedDistance();

            if (win) {
                this.player.setMaxLevelPassed(this.player.getMaxLevelPassed() + 1);
            }

            PlayerData.getInstance().updatePlayer(PlayerData.getInstance().getCurrentPlayer());

            clearObstaclesAndCollectibles();
            gameLoop.stop();
            MusicPlayer.getInstance().stop();
            Notification.hidePopupMessage();
            this.time = 0;
            velocity = GameplayConstants.START_GAME_VELOCITY;
            currentStats.updateDistance(0);

            Stage currentStage = (Stage) canvas.getScene().getWindow();
            root.getChildren().remove(canvas);
            StageManager manager = new StageManagerImpl();
            manager.loadSceneToStage(currentStage, win ? ViewsConstants.GAME_WIN_VIEW_PATH : ViewsConstants.GAME_OVER_VIEW_PATH);

            this.player.updateStatsAtEnd();
        }
    }

    private void updatePlayerStats() {
        currentStats.updateTime((long) (this.time * this.currentFramesPerSecond));
        currentStats.updateDistance(currentStats.getDistance() + (long) this.velocity / 2);
        this.player.addPoints(this.trackMode.getPointsPerDistance());
        currentStats.updatePoints(this.player.getPoints());
        currentStats.updateBullets(this.playerCar.getAmmunition());
        observer.update(currentStats, observer);

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
        this.collectible.getCollectibles().clear();
        this.obstacle.getObstacles().clear();
    }

    private void startArmageddonsPower() {
        for (Obstacle o : this.obstacle.getObstacles()) {
            o.handleImpactByCarPlayer(velocity);
        }
    }
}
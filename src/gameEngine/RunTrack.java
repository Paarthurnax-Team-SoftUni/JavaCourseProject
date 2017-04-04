package gameEngine;

import controllers.ChooseCarController;
import dataHandler.*;
import interfaces.Playable;
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
import models.*;
import music.MusicPlayer;
import stageHandler.StageManager;
import stageHandler.StageManagerImpl;
import constants.CarConstants;

import java.util.Observable;
import java.util.Observer;

public class RunTrack {

    private static long time;
    private static boolean isPaused;
    private static float velocity;
    private static boolean shoot;
    private static CurrentStats currentStats;
    private static Cheat cheat;
    private int frame;
    private int y;
    private float currentFramesPerSecond;
    private String carId;
    private Playable player;
    private CurrentHealth currentHealth;
    private ChooseCarController chooseCarController;
    private Collectible collectible;
    private Obstacle obstacle;
    private Ammo ammo;

    public static Cheat getCheat() {
        return cheat;
    }

    public RunTrack(Playable player, float velocityValue) {
        frame = 0;
        time = 0;
        isPaused = false;
        shoot = false;
        velocity = velocityValue;
        currentStats = new CurrentStats(0, 0, 0, 0);
        cheat = new Cheat();
        this.collectible = new Collectible(player);
        this.obstacle = new Obstacle();
        this.ammo = new Ammo();
        this.chooseCarController = new ChooseCarController();
        this.setCarId(chooseCarController.getCarId());
        this.setPlayer(player);
        this.setCurrentFramesPerSecond(CarConstants.FRAMES_PER_SECOND);
    }

    public void runGame(AnchorPane root, Image background, int drunkDrivers, int minLeftSide, int maxRightSide) {

        StageManager manager = new StageManagerImpl();
        Canvas canvas = new Canvas(CarConstants.CANVAS_WIDTH, CarConstants.CANVAS_HEIGHT);

        root.getChildren().add(canvas);
        root.getScene().setOnKeyPressed(new KeyHandlerOnPress(this.getPlayer(), minLeftSide, maxRightSide));
        root.getScene().setOnKeyReleased(new KeyHandlerOnRelease(this.getPlayer(), minLeftSide, maxRightSide));
        GraphicsContext gc = canvas.getGraphicsContext2D();

        String carImg = CarConstants.CAR_IMAGES_PATH + this.carId + CarConstants.HALF_SIZE;
        this.player.updateImage(carImg);
        this.player.setPosition(200, 430);
        this.player.updatePoints(0L);

        this.currentHealth = new CurrentHealth(this.player);
        currentStats.addObserver(observer);

        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        MusicPlayer.getInstance().play();
        MusicPlayer.getInstance().pause();
        KeyFrame kf = new KeyFrame(
                Duration.seconds(currentFramesPerSecond),
                event -> {

                    if (isPaused) {
                        PauseHandler pauseHandler = new PauseHandler(gameLoop, gc, background, y, player, obstacle.getObstacles(), collectible.getCollectibles());
                        pauseHandler.activatePause();
                    }

                    y = Math.round(y + velocity);
                    time++;
                    frame++;

                    //update immortality status if its activated
                    collectible.updateStatus();

                    currentStats.updateTime((long) (time * currentFramesPerSecond));
                    currentStats.updateDistance(currentStats.getDistance() + (long) velocity / 2);
                    player.addPoints(1);
                    currentStats.updatePoints(player.getPoints());
                    currentStats.updateBullets(player.getAmmunition());

                    observer.update(currentStats, observer);

                    if (Math.abs(y) >= CarConstants.CANVAS_HEIGHT) {
                        y = y - CarConstants.CANVAS_HEIGHT;
                        frame = 0;
                    }
                    this.player.setVelocity(0, 0);

                    cheat.useCheat(player);

                    //Generate obstacles
                    if (frame == 0) {
                        obstacle.addObstacle(obstacle.generateObstacle(drunkDrivers, minLeftSide, maxRightSide));
                    }

                    gc.clearRect(0, 0, CarConstants.CANVAS_WIDTH, CarConstants.CANVAS_HEIGHT);
                    gc.drawImage(background, 0, y - CarConstants.CANVAS_HEIGHT);
                    gc.drawImage(background, 0, y);
                    this.player.update();
                    this.player.render(gc);
                    currentHealth.update();

                    obstacle.manageObstacles(gc, collectible, player, obstacle.getObstacles(), velocity);

                    // Ammo logic
                    ammo.visualizeAmmo(gc, obstacle.getObstacles(), ammo.getAmmunition(),player);
                    if (shoot) {
                        ammo.addAmmo(ammo.generateAmmo(player));
                        setShoot(false);
                    }

                    Stage currentStage = (Stage) canvas.getScene().getWindow();

                    //CHECK FOR END && CHECK FOR LOSE
                    if (time >= CarConstants.TRACK_1_END_TIME || player.getHealthPoints() <= 0) {
                        boolean win = player.getHealthPoints() > 0 && currentStats.getDistance() >= CarConstants.TRACK_1_END_DISTANCE;
                        if (win) {
                            this.player.updateMaxLevel(this.player.getMaxLevelPassed() + 1);
                            PlayerData.getInstance().updatePlayer(PlayerData.getInstance().getCurrentPlayer());
                        }

                        PlayerData.getInstance().updatePlayer(PlayerData.getInstance().getCurrentPlayer());

                        clearObstaclesAndCollectibles();
                        gameLoop.stop();
                        MusicPlayer.getInstance().stop();
                        time = 0;
                        Notification.hidePopupMessage();
                        velocity = CarConstants.START_GAME_VELOCITY;
                        currentStats.updateDistance(0);
                        root.getChildren().remove(canvas);
                        player.updateAmmunition(CarConstants.START_GAME_BULLETS);

                        manager.loadSceneToStage(currentStage, win ? CarConstants.GAME_WIN_VIEW_PATH : CarConstants.GAME_OVER_VIEW_PATH);

                        this.player.updateStatsAtEnd();
                    }

                    if (frame % CarConstants.COLLECTIBLES_OFFSET == 0) {
                        collectible.addCollectible(Collectible.generateCollectible(minLeftSide, maxRightSide));
                    }
                    String action = collectible.visualizeCollectible(gc, velocity, currentStage);

                    if (action != null && action.equals(CarConstants.ARMAGEDDON_STRING)) {
                        startArmageddonsPower();
                    } else if (action != null && action.equals(CarConstants.FUEL_BOTTLE_STRING)) {
                        time -= CarConstants.FUEL_TANK_BONUS_TIME / CarConstants.FRAMES_PER_SECOND;
                    }
                });

        gameLoop.getKeyFrames().add(kf);
        gameLoop.playFromStart();
    }

    public Playable getPlayer() {
        return this.player;
    }

    public void setPlayer(Playable player) {
        this.player = player;
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
        return isPaused;
    }

    public static void setIsPaused(boolean newValue) {
        isPaused = newValue;
    }

    public static void setShoot(boolean newValue) {
        shoot = newValue;
    }

    private static Observer observer = new Observer() {
        @Override
        public void update(Observable o, Object arg) {
        }
    };

    private void setCurrentFramesPerSecond(float currentFramesPerSecond) {
        this.currentFramesPerSecond = currentFramesPerSecond;
    }

    private void setCarId(String carId) {
        this.carId = carId == null ? "car1" : carId;
    }

    private void clearObstaclesAndCollectibles() {
        collectible.getCollectibles().clear();
        obstacle.getObstacles().clear();
    }

    private void startArmageddonsPower() {
        for (Obstacle o : obstacle.getObstacles()) {
            o.handleImpactByCarPlayer(velocity);
        }
    }
}
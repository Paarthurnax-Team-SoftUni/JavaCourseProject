package gameEngine;

import constants.*;
import controllers.ChooseCarController;
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
import models.Cheat;
import models.Notification;
import models.Player;
import models.sprites.Ammo;
import models.sprites.Collectible;
import models.sprites.Obstacle;
import models.sprites.PlayerCar;
import music.MusicPlayer;
import stageHandler.StageManager;
import stageHandler.StageManagerImpl;

import java.util.Observable;
import java.util.Observer;

public class RunTrack {

    private static long time;
    private static boolean isPaused;
    private static float velocity;
    private static boolean shoot;
    private static CurrentStats currentStats;
    private static Cheat cheat;
    private static Observer observer = new Observer() {
        @Override
        public void update(Observable o, Object arg) {
        }
    };
    private int frame;
    private int y;
    private float currentFramesPerSecond;
    private String carId;
    private Player player;
    private CurrentHealth currentHealth;
    private ChooseCarController chooseCarController;
    private Collectible collectible;
    private Obstacle obstacle;
    private Ammo ammo;
    private PlayerCar playerCar;

    public RunTrack(Player player, float velocityValue) {
        frame = 0;
        time = 0;
        velocity = velocityValue;
        currentStats = new CurrentStats(0, 0, 0, 0);
        cheat = new Cheat();
        this.collectible = new Collectible(player);
        this.obstacle = new Obstacle();
        this.ammo = new Ammo();
        this.chooseCarController = new ChooseCarController();
        this.setCarId(chooseCarController.getCarId());
        this.setPlayer(player);
        this.playerCar = this.getPlayer().getCar();
        this.setCurrentFramesPerSecond(GeneralConstants.FRAMES_PER_SECOND);
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
        return isPaused;
    }

    public static void setIsPaused(boolean newValue) {
        isPaused = newValue;
    }

    public static void setShoot(boolean newValue) {
        shoot = newValue;
    }

    public void runGame(AnchorPane root, Image background, int drunkDrivers, int minLeftSide, int maxRightSide) {

        StageManager manager = new StageManagerImpl();
        Canvas canvas = new Canvas(GeneralConstants.CANVAS_WIDTH, GeneralConstants.CANVAS_HEIGHT);

        root.getChildren().add(canvas);
        root.getScene().setOnKeyPressed(new KeyHandlerOnPress(this.getPlayer(), minLeftSide, maxRightSide));
        root.getScene().setOnKeyReleased(new KeyHandlerOnRelease(this.getPlayer(), minLeftSide, maxRightSide));
        GraphicsContext gc = canvas.getGraphicsContext2D();

        String carImg = ResourcesConstants.CAR_IMAGES_PATH + this.carId + ImagesShortcutConstants.HALF_SIZE;
        this.playerCar.setImage(carImg);
        this.playerCar.setPosition(200, 430);
        this.player.setPoints(0L);

        this.currentHealth = new CurrentHealth(this.player);
        currentStats.addObserver(observer);

        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        MusicPlayer.getInstance().play();
        MusicPlayer.getInstance().startStopPause();

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
                    currentStats.updateBullets(this.playerCar.getAmmunition());

                    observer.update(currentStats, observer);

                    if (Math.abs(y) >= GeneralConstants.CANVAS_HEIGHT) {
                        y = y - GeneralConstants.CANVAS_HEIGHT;
                        frame = 0;
                    }
                    this.playerCar.setVelocity(0, 0);

                    cheat.useCheat(player);

                    //Generate obstacles
                    if (frame == 0) {
                        obstacle.addObstacle(obstacle.generateObstacle(drunkDrivers, minLeftSide, maxRightSide));
                    }

                    gc.clearRect(0, 0, GeneralConstants.CANVAS_WIDTH, GeneralConstants.CANVAS_HEIGHT);
                    gc.drawImage(background, 0, y - GeneralConstants.CANVAS_HEIGHT);
                    gc.drawImage(background, 0, y);
                    this.playerCar.update();
                    this.playerCar.render(gc);
                    try {
                        currentHealth.update();
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    obstacle.manageObstacles(gc, collectible, player, obstacle.getObstacles(), velocity);

                    // Ammo logic
                    ammo.visualizeAmmo(gc, obstacle.getObstacles(), ammo.getAmmunition(), player);
                    if (shoot) {
                        ammo.addAmmo(ammo.generateAmmo(player));
                        setShoot(false);
                    }

                    Stage currentStage = (Stage) canvas.getScene().getWindow();

                    //CHECK FOR END && CHECK FOR LOSE
                    if (time >= GameplayConstants.TRACK_1_END_TIME || player.getHealthPoints() <= 0) {
                        boolean win = player.getHealthPoints() > 0 && currentStats.getDistance() >= GameplayConstants.TRACK_1_END_DISTANCE;
                        if (win) {
                            this.player.setMaxLevelPassed(this.player.getMaxLevelPassed() + 1);
                            PlayerData.getInstance().updatePlayer(PlayerData.getInstance().getCurrentPlayer());
                        }

                        PlayerData.getInstance().updatePlayer(PlayerData.getInstance().getCurrentPlayer());

                        clearObstaclesAndCollectibles();
                        gameLoop.stop();
                        MusicPlayer.getInstance().stop();
                        time = 0;
                        Notification.hidePopupMessage();
                        velocity = GameplayConstants.START_GAME_VELOCITY;
                        currentStats.updateDistance(0);
                        root.getChildren().remove(canvas);
                        this.playerCar.setAmmunition(GameplayConstants.START_GAME_BULLETS);

                        manager.loadSceneToStage(currentStage, win ? ViewsConstants.GAME_WIN_VIEW_PATH : ViewsConstants.GAME_OVER_VIEW_PATH);

                        this.player.updateStatsAtEnd();
                    }

                    if (frame % GameplayConstants.COLLECTIBLES_OFFSET == 0) {
                        collectible.addCollectible(Collectible.generateCollectible(minLeftSide, maxRightSide));
                    }
                    String action = collectible.visualizeCollectible(gc, velocity, currentStage);

                    if (action != null && action.equals(CollectiblesAndObstaclesConstants.ARMAGEDDON_STRING)) {
                        startArmageddonsPower();
                    } else if (action != null && action.equals(CollectiblesAndObstaclesConstants.FUEL_BOTTLE_STRING)) {
                        time -= GameplayConstants.FUEL_TANK_BONUS_TIME / GeneralConstants.FRAMES_PER_SECOND;
                    }
                });

        gameLoop.getKeyFrames().add(kf);
        gameLoop.playFromStart();
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private void setCurrentFramesPerSecond(float currentFramesPerSecond) {
        this.currentFramesPerSecond = currentFramesPerSecond;
    }

    private void setCarId(String carId) {
        this.carId = carId == null ? "car1" : carId;
    }

    private void clearObstaclesAndCollectibles() {
        this.collectible.getCollectibles().clear();
        this.obstacle.getObstacles().clear();
    }

    private void startArmageddonsPower() {
        for (Obstacle o : obstacle.getObstacles()) {
            o.handleImpactByCarPlayer(velocity);
        }
    }
}
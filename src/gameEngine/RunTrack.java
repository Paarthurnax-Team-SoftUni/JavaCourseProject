package gameEngine;

import utils.constants.*;
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
import utils.notifications.Notification;
import models.Player;
import models.sprites.Ammo;
import models.sprites.Collectible;
import models.sprites.Obstacle;
import models.sprites.PlayerCar;
import utils.music.MusicPlayer;
import utils.stages.StageManager;
import utils.stages.StageManagerImpl;

import java.util.Observer;

public class RunTrack {

    private static long time;
    private static boolean isPaused;
    private static float velocity;
    private static boolean shoot;
    private static CurrentStats currentStats;
    private static Cheat cheat;
    private static Observer observer;
    private int frame;
    private int y;
    private float currentFramesPerSecond;
    private Player player;
    private CurrentHealth currentHealth;
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
        observer = (o, arg) -> {};
        this.collectible = new Collectible(player);
        this.obstacle = new Obstacle();
        this.ammo = new Ammo();
        this.setPlayer(player);
        this.playerCar = this.getPlayer().getCar();
        this.currentHealth = new CurrentHealth(this.getPlayer());
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

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void runGame(AnchorPane root, Image background, int drunkDrivers, int minLeftSide, int maxRightSide) {

        Canvas canvas = new Canvas(GeneralConstants.CANVAS_WIDTH, GeneralConstants.CANVAS_HEIGHT);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getScene().setOnKeyPressed(new KeyHandlerOnPress(this.getPlayer(), minLeftSide, maxRightSide));
        root.getScene().setOnKeyReleased(new KeyHandlerOnRelease(this.getPlayer(), minLeftSide, maxRightSide));

        this.playerCar.setImage(ResourcesConstants.CAR_IMAGES_PATH + this.getCarId() + ImagesShortcutConstants.HALF_SIZE);
        this.playerCar.updatePosition(200, 430);
        this.player.updatePoints(0L);
        currentStats.addObserver(observer);

        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);

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
                    y = Math.round(y + velocity);
                    time++;
                    frame++;
                    if (Math.abs(y) >= GeneralConstants.CANVAS_HEIGHT) {
                        y = y - GeneralConstants.CANVAS_HEIGHT;
                        frame = 0;
                    }

                    //Update stats
                    this.collectible.updateStatus();
                    this.updatePlayerStats();
                    this.playerCar.setVelocity(0, 0);
                    cheat.useCheat(this.player);

                    //Generate items
                    if (frame == 0) {
                        obstacle.addObstacle(obstacle.generateObstacle(drunkDrivers, minLeftSide, maxRightSide));
                        collectible.addCollectible(collectible.generateCollectible(minLeftSide, maxRightSide));
                    }
                    if (shoot) {
                        ammo.addAmmo(ammo.generateAmmo(player));
                        setShoot(false);
                    }

                    //Draw background and playerCar
                    gc.clearRect(0, 0, GeneralConstants.CANVAS_WIDTH, GeneralConstants.CANVAS_HEIGHT);
                    gc.drawImage(background, 0, y - GeneralConstants.CANVAS_HEIGHT);
                    gc.drawImage(background, 0, y);
                    this.playerCar.update();
                    this.playerCar.render(gc);

                    //Render items
                    ammo.visualizeAmmo(gc, obstacle.getObstacles(), player);
                    obstacle.visualizeObstacles(gc, velocity, collectible, player);
                    String action = collectible.visualizeCollectible(gc, velocity);

                    if (action != null && action.equals(CollectiblesAndObstaclesConstants.ARMAGEDDON_STRING)) {
                        startArmageddonsPower();
                    } else if (action != null && action.equals(CollectiblesAndObstaclesConstants.FUEL_BOTTLE_STRING)) {
                        time -= GameplayConstants.FUEL_TANK_BONUS_TIME / currentFramesPerSecond;
                    }

                    //Check for end game
                    this.checkForEndGame(root, canvas, gameLoop);
                });

        gameLoop.getKeyFrames().add(kf);
        gameLoop.playFromStart();
    }

    private void checkForEndGame(AnchorPane root, Canvas canvas, Timeline gameLoop) {
        if (time >= GameplayConstants.TRACK_1_END_TIME || player.getHealthPoints() <= 0) {

            boolean win = player.getHealthPoints() > 0 && currentStats.getDistance() >= GameplayConstants.TRACK_1_END_DISTANCE;

            if (win) {
                this.player.setMaxLevelPassed(this.player.getMaxLevelPassed() + 1);
            }

            PlayerData.getInstance().updatePlayer(PlayerData.getInstance().getCurrentPlayer());

            clearObstaclesAndCollectibles();
            gameLoop.stop();
            MusicPlayer.getInstance().stop();
            Notification.hidePopupMessage();
            time = 0;
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
        currentStats.updateTime((long) (time * currentFramesPerSecond));
        currentStats.updateDistance(currentStats.getDistance() + (long) velocity / 2);
        player.addPoints(1);
        currentStats.updatePoints(player.getPoints());
        currentStats.updateBullets(this.playerCar.getAmmunition());

        observer.update(currentStats, observer);

        try {
            currentHealth.update();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void setCurrentFramesPerSecond(float currentFramesPerSecond) {
        this.currentFramesPerSecond = currentFramesPerSecond;
    }

    private String getCarId() {
        ChooseCarController chooseCarController = new ChooseCarController();
        String carId = chooseCarController.getCarId();
        return carId == null ? "car1" : carId;
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
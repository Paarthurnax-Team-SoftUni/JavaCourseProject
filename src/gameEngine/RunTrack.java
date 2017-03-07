package gameEngine;

import controllers.ChooseCarController;
import dataHandler.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import keyHandler.KeyHandlerOnPress;
import keyHandler.KeyHandlerOnRelease;
import models.Ammo;
import models.Collectible;
import models.Obstacle;
import models.Player;
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
    private static CurrentPoints currentPoints;
    private static CurrentTime currentTime;
    private static CurrentDistance currentDistance;
    private static CurrentBullets currentBullets;
    private int frame;
    private int y;
    private float currentFramesPerSecond;
    private String carId;
    private Player player;
    private HealthBar currentHealth;
    private ChooseCarController chooseCarController;
    private Collectible collectible;
    private Obstacle obstacle;
    private int ammoCap;
    private Ammo ammo;
    private static Cheat cheat = new Cheat();

    public static Cheat getCheat() {
        return cheat;
    }

    public RunTrack(Player player, float velocity) {
        frame = 0;
        time = 0;
        isPaused = false;
        shoot = false;
        currentPoints = new CurrentPoints(0);
        currentDistance = new CurrentDistance(0);
        currentTime = new CurrentTime(0);
        currentBullets = new CurrentBullets(0);
        this.chooseCarController = new ChooseCarController();
        this.collectible = new Collectible(player);
        this.obstacle = new Obstacle();
        this.ammoCap = 0;
        this.ammo = new Ammo();
        this.setPlayer(player);
        this.setCurrentFramesPerSecond(Constants.FRAMES_PER_SECOND);
        RunTrack.velocity = velocity;
        //this.cheat=new Cheat();
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
        this.carId = carId;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void runGame(Image background, AnchorPane root, int drunkDrivers, int minLeftSide, int maxRightSide) {

        StageManager manager = new StageManagerImpl();

        Canvas canvas = new Canvas(Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT);

        root.getChildren().add(canvas);
        root.getScene().setOnKeyPressed(new KeyHandlerOnPress(this.getPlayer(), minLeftSide, maxRightSide));
        root.getScene().setOnKeyReleased(new KeyHandlerOnRelease(this.getPlayer(), minLeftSide, maxRightSide));
        GraphicsContext gc = canvas.getGraphicsContext2D();

        this.setCarId(chooseCarController.getCarId());
        carId = carId == null ? "car1" : carId;
        //String carImg = Constants.CAR_IMAGES_PATH + carId + ".png";
        String carImg = Constants.CAR_IMAGES_PATH + carId + Constants.HALF_SIZE;
        player.setImage(carImg);
        player.setPosition(200, 430);
        player.setPoints(0L);

        currentHealth = new HealthBar(player);
        currentPoints.addObserver(observer);
        currentTime.addObserver(observer);
        currentDistance.addObserver(observer);
        currentBullets.addObserver(observer);

        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        MusicPlayer.play();
        MusicPlayer.pause();
        KeyFrame kf = new KeyFrame(
                Duration.seconds(currentFramesPerSecond),
                event -> {
                    //Pause
                    if (isPaused) {
                        PauseHandler pauseHandler = new PauseHandler(gameLoop, gc, background, y, player, obstacle.getObstacles(), collectible.getCollectibles());
                        pauseHandler.activatePause();
                    }

                    y = Math.round(y + velocity);
                    time++;
                    frame++;

                    //update immortality status if its actuvated
                    collectible.updateStatus();

                    currentTime.setValue((long) (time * currentFramesPerSecond));
                    currentDistance.setValue(currentDistance.getValue() + (long) velocity / 2);
                    player.addPoints(1);
                    currentPoints.setValue(player.getPoints());
                    currentBullets.setValue(player.getAmmunition());

                    observer.update(currentPoints, observer);
                    observer.update(currentTime, observer);
                    observer.update(currentDistance, observer);

                    if (Math.abs(y) >= Constants.CANVAS_HEIGHT) {
                        y = y - Constants.CANVAS_HEIGHT;
                        frame = 0;
                    }
                    this.player.setVelocity(0, 0);

                    cheat.useCheat(player);


                    //Generate obstacles
                    if (frame == 0) {
                        obstacle.addObstacle(obstacle.generateObstacle(drunkDrivers, minLeftSide, maxRightSide));
                    }

                    gc.clearRect(0, 0, Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT);
                    gc.drawImage(background, 0, y - Constants.CANVAS_HEIGHT);
                    gc.drawImage(background, 0, y);
                    this.player.update();
                    this.player.render(gc);
                    currentHealth.update();
                    obstacle.manageObstacles(gc, collectible, player, obstacle.getObstacles(), velocity);

                    // Ammo logic
                    ammo.visualizeAmmo(gc, obstacle.getObstacles(), ammo.getAmmunition());
                    if (shoot) {
                        ammo.addAmmo(ammo.generateAmmo(player));
                        setShoot(false);
                    } else {
                        // ammo.getAmmunition().clear();
                    }

                    Stage currentStage = (Stage) canvas.getScene().getWindow();

                    //CHECK FOR END && CHECK FOR LOSE
                    if (time >= Constants.TRACK_1_END_TIME || player.getHealthPoints() <= 0) {
                        boolean win = player.getHealthPoints() > 0 && currentDistance.getValue() >= Constants.TRACK_1_END_DISTANCE;
                        if (win) {
                            this.player.setMaxLevelPassed(this.player.getMaxLevelPassed() + 1);
                        }

                        clearObstaclesAndCollectibles();
                        gameLoop.stop();
                        MusicPlayer.stop();
                        time = 0;
                        Notification.hidePopupMessage();
                        velocity = Constants.START_GAME_VELOCITY;
                        currentDistance.setValue(0);
                        root.getChildren().remove(canvas);

                        // Ternar operator If final time is achieved -> GAME_WIN_VIEW else Game Lose View;
                        FXMLLoader loader = manager.loadSceneToStage(currentStage, win ? Constants.GAME_WIN_VIEW_PATH : Constants.GAME_OVER_VIEW_PATH, null);

                        this.player.updateStatsAtEnd();
                    }

                    if (frame % Constants.COLLECTIBLES_OFFSET == 0) {
                        collectible.addCollectible(Collectible.generateCollectible(minLeftSide, maxRightSide));
                    }
                    String action = collectible.visualizeCollectible(gc, velocity, currentStage);

                    if (action != null && action.equals(Constants.ARMAGEDDON_STRING)) {
                        startArmageddonsPower();
                    } else if (action != null && action.equals(Constants.FUEL_BOTTLE_STRING)) {
                        time -= Constants.FUEL_TANK_BONUS_TIME / Constants.FRAMES_PER_SECOND;
                    }
                });

        gameLoop.getKeyFrames().add(kf);
        gameLoop.playFromStart();
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

    public static CurrentPoints getCurrentPoints() {
        return (currentPoints);
    }

    public static CurrentTime getCurrentTime() {
        return (currentTime);
    }

    public static CurrentDistance getCurrentDistance() {
        return (currentDistance);
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

    public static CurrentBullets getCurrentBullets() {
        return currentBullets;
    }
    public static boolean getShoot() {
        return shoot;
    }

    public static void setShoot(boolean newValue) {
        shoot = newValue;
    }
}
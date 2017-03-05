package GameEngine;

import controllers.ChooseCarController;
import dataHandler.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import keyHandler.KeyHandlerOnPress;
import keyHandler.KeyHandlerOnRelease;
import models.Collectible;
import models.Obstacle;
import models.Player;
import music.MusicPlayer;
import stageHandler.StageManager;
import stageHandler.StageManagerImpl;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class RunTrack {
    private int frame;
    private long time;
    private int y;
    private double immortalityTimer;
    private float currentFramesPerSecond;
    private static boolean isPaused;
    private static boolean isImmortable;
    private static float velocity;
    private String carId;
    private ArrayList<Obstacle> testObstacles;
    private ArrayList<Collectible> collectibles;
    private Player player;
    private static CurrentPoints currentPoints;
    private static CurrentTime currentTime;
    private static CurrentDistance currentDistance;
    private HealthBar currentHealth;
    private ChooseCarController chooseCarController;


    public RunTrack(Player player, float velocity) {
        setPlayer(player);
        this.testObstacles = new ArrayList<>();
        this.collectibles = new ArrayList<>();
        this.frame = 0;
        this.time = 0;
        this.setCurrentFramesPerSecond(Constants.FRAMES_PER_SECOND);
        isPaused = false;
        isImmortable = false;
        RunTrack.velocity = velocity;
        currentPoints = new CurrentPoints(0);
        currentDistance = new CurrentDistance(0);
        currentTime = new CurrentTime(0);
        chooseCarController = new ChooseCarController();
    }

    private static Observer observer = new Observer() {
        @Override
        public void update(Observable o, Object arg) {
        }
    };

    public void setCurrentFramesPerSecond(float currentFramesPerSecond) {
        this.currentFramesPerSecond = currentFramesPerSecond;
    }

    private void setCarId(String carId) {
        this.carId = carId;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private double getImmortalityTimer() {
        return immortalityTimer;
    }

    private void setImmortalityTimer(double immortalityTimer) {
        this.immortalityTimer = immortalityTimer;
    }

    public void runGame(Image background, AnchorPane root) {

        StageManager manager = new StageManagerImpl();

        Canvas canvas = new Canvas(Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT);

        root.getChildren().add(canvas);
        root.getScene().setOnKeyPressed(new KeyHandlerOnPress(this.getPlayer()));
        root.getScene().setOnKeyReleased(new KeyHandlerOnRelease(this.getPlayer()));
        GraphicsContext gc = canvas.getGraphicsContext2D();
        this.setCarId(chooseCarController.getCarId());
        carId = carId == null ? "car1" : carId;
        //String carImg = Constants.CAR_IMAGES_PATH + carId + ".png";
        String carImg = Constants.CAR_IMAGES_PATH + carId + "_half_size.png";
        player.setImage(carImg);
        player.setPosition(200, 430);
        player.setPoints(0L);
        currentHealth = new HealthBar(player);
        currentPoints.addObserver(observer);
        currentTime.addObserver(observer);
        currentDistance.addObserver(observer);

        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        MusicPlayer.play();
        MusicPlayer.pause();
        KeyFrame kf = new KeyFrame(
                Duration.seconds(currentFramesPerSecond),
                event -> {

                    //Pause
                    if (isPaused) {
                        PauseHandler pauseHandler = new PauseHandler(gameLoop, gc, background, y, player, testObstacles, collectibles);
                        pauseHandler.activatePause();
                    }

                    y = Math.round(y + velocity);

                    time++;
                    frame++;

                    //update immortality status if its actuvated
                    if (isImmortable) {
                        updateImmortalityStatus();
                    }

                    currentTime.setValue((long) (time * currentFramesPerSecond));
                    currentDistance.setValue(currentDistance.getValue() + (long) velocity / 2);
                    player.setPoints(player.getPoints() + 1);
                    currentPoints.setValue(player.getPoints());

                    observer.update(currentPoints, observer);
                    observer.update(currentTime, observer);
                    observer.update(currentDistance, observer);
                   
                    if (Math.abs(y) >= Constants.CANVAS_HEIGHT) {
                        y = y - Constants.CANVAS_HEIGHT;
                        frame = 0;
                    }
                    player.setVelocity(0, 0);

                    //Generate obstacles
                    if (frame == 0) {
                        testObstacles.add(Obstacle.generateObstacle());
                    }

                    gc.clearRect(0, 0, Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT);
                    gc.drawImage(background, 0, y - Constants.CANVAS_HEIGHT);
                    gc.drawImage(background, 0, y);
                    player.update();
                    player.render(gc);
                    currentHealth.update();
                    manageObstacles(gc);

                    //CHECK FOR END
                    if (currentDistance.getValue() >= 10000) {       //if(time >= Constants.TRACK_1_END_TIME){
                        clearObstaclesAndCollectibles();
                        gameLoop.stop();
                        MusicPlayer.stop();
                        time = 0;

                        player.updateStatsAtEnd();
                        velocity = Constants.START_GAME_VELOCITY;
                        currentDistance.setValue(0);

                        Stage currentStage = (Stage) canvas.getScene().getWindow();
                        root.getChildren().remove(canvas);
                        FXMLLoader loader = manager.loadSceneToStage(currentStage, Constants.GAME_WIN_VIEW_PATH,null);
                    }

                    //CHECK FOR LOSE
                    if (player.getHealthPoints() <= 0) {
                        clearObstaclesAndCollectibles();
                        gameLoop.stop();
                        MusicPlayer.stop();
                        time = 0;

                        player.updateStatsAtEnd();

                        // TODO: stop timers!
                        //NOt sure if timers stopeed ? as there are no timers now

                        velocity = 5;
                        currentDistance.setValue(0);

                        Stage currentStage = (Stage) canvas.getScene().getWindow();
                        root.getChildren().remove(canvas);
                        FXMLLoader loader = manager.loadSceneToStage(currentStage, Constants.GAME_OVER_VIEW_PATH,null);
                    }

                    if (frame % Constants.COLLECTIBLES_OFFSET == 0) {
                        collectibles.add(Collectible.generateCollectible());
                    }
                    visualizeCollectible(gc, velocity);
                });

        gameLoop.getKeyFrames().add(kf);
        gameLoop.playFromStart();
    }

    private void manageObstacles(GraphicsContext gc) {
        for (Obstacle testObst : testObstacles) {
            String obstacleType = testObst.getObstacleType();
            if (obstacleType.contains("player_car") && !testObst.isDestroyed()) {

                testObst.setVelocity(0, velocity / 3);
                if (testObst.getIsDrunk()) {
                    if (new Random().nextInt(100)> 90) {
                        if (new Random().nextInt(2) == 0) {
                            testObst.setTurnLeft(true);
                            testObst.setTurnRight(false);
                        } else {
                            testObst.setTurnRight(true);
                            testObst.setTurnLeft(false);
                        }
                    }
                }

            } else {
                testObst.setVelocity(0, velocity);
            }
            testObst.update();
            testObst.render(gc);

            if (testObst.getBoundary().intersects(player.getBoundary())) {
                if (isImmortable) {

                    player.setPoints(player.getPoints()+Constants.BONUS_POINTS_HIT_WITH_SHIELD);
                    testObst.setDestroyed(true);
                }
                if (!testObst.isDestroyed()) {
                    player.setHealthPoints(player.getHealthPoints() - Constants.OBSTACLE_DAMAGE);
                    testObst.setDestroyed(true);
                }
            }

        }
    }

    private void visualizeCollectible(GraphicsContext gc, double velocity) {
        for (Collectible collectible : collectibles) {

            collectible.setVelocity(0, velocity);
            collectible.update();
            collectible.render(gc);

            if (collectible.getBoundary().intersects(player.getBoundary())) {
                switch (collectible.getCollectibleType()) {
                    case "fuelBottle":
                        player.setPoints(player.getPoints() + Constants.FUEL_TANK_BONUS);
                        time -= Constants.FUEL_TANK_BONUS_TIME;
                        break;
                    case "health":
                        player.setPoints(player.getPoints() + Constants.HEALTH_PACK_BONUS_POINTS);
                        if (player.getHealthPoints() < Constants.HEALTH_BAR_MAX) {
                            player.setHealthPoints(Math.min(player.getHealthPoints() + Constants.HEALTH_BONUS, Constants.HEALTH_BAR_MAX));
                        }
                        break;
                    case "bonusPts":
                        player.setPoints(player.getPoints() + Constants.BONUS_POINTS);
                        break;
                    case "immortality":
                        player.setPoints(player.getPoints() + Constants.IMMORTALITY_BONUS);
                        if (!isImmortable) {
                            player.setPoints(player.getPoints() + Constants.ARMAGEDDONS_BONUS);
                            startImmortalityTimer();
                        }
                        break;
                    case "armageddonsPower":
                        player.setPoints(player.getPoints() + Constants.ARMAGEDDONS_BONUS);
                        startArmageddonsPower();
                        break;
                }
                collectible.setPosition(Constants.DESTROY_OBJECT_COORDINATES, Constants.DESTROY_OBJECT_COORDINATES);
            }
        }
    }

    private void clearObstaclesAndCollectibles() {
        collectibles.clear();
        testObstacles.clear();
    }

    private void startImmortalityTimer() {
        isImmortable = true;
        this.setImmortalityTimer(Constants.IMMORTALITY_DURATION / currentFramesPerSecond);
    }

    private void updateImmortalityStatus() {
        this.setImmortalityTimer(this.getImmortalityTimer() - 1);
        if (this.getImmortalityTimer() < 0) {
            isImmortable = false;
            System.out.println("immortality off");
            System.out.println(isImmortable);
        }
    }

    private void startArmageddonsPower() {
        for (Obstacle obstacle : testObstacles) {
            obstacle.setDestroyed(true);
        }
    }

    public static CurrentPoints getCurrentPoints() {
        System.out.println(currentPoints.getValue());
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

    public void pauseGame(ActionEvent actionEvent) {
        if (isPaused()) {
            setIsPaused(false);
        } else {
            setIsPaused(true);
        }
    }
}
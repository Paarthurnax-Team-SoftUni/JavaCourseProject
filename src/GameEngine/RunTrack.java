package GameEngine;

import controllers.ChooseCarController;
import controllers.ScreenController;
import dataHandler.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import music.MusicPlayer;
import stageHandler.StageManager;
import stageHandler.StageManagerImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class RunTrack{
    private int frame;
    private long time;
    private double y;
    private static boolean isPaused;
    private static float velocity;
    private String carId;
    private ArrayList<Sprite> testObstacles;
    private ArrayList<Sprite> collectibles;
    private Player player;
    private static CurrentPoints currentPoints = new CurrentPoints(0);
    private static CurrentTime currentTime = new CurrentTime(0);
    private static CurrentDistance currentDistance = new CurrentDistance(0);
    private HealthBar currentHealth;

    public RunTrack(Player player, float velocity) {
        setPlayer(PlayerData.getInstance().getCurrentPlayer());
        this.testObstacles=new ArrayList<>();
        this.collectibles=new ArrayList<>();
        this.frame = 0;
        this.time = 0;
        this.isPaused = false;
        this.velocity = velocity;
    }

    private static Observer observer = new Observer() {
        @Override
        public void update(Observable o, Object arg) {
        }
    };

    private void setCarId(String carId) {
        this.carId = carId;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void RunTrack(Image background) throws IOException {

        AnchorPane root = FXMLLoader.load(getClass().getResource(Constants.GAME_PLAY_VIEW_PATH));
        System.out.println(root);
     
        //Stage currentStage = (Stage) root..getWindow();
        StageManager manager = new StageManagerImpl();

        //AnchorPane root = ScreenController.getInstance().getRoot();


//        if (ScreenController.getInstance().getGamePlayStage() != null) {
//            Stage stage = (Stage) canvas.getScene().getWindow();
//            try {
//                ScreenController.getInstance().loadStage(stage, ScreenController.getInstance().getGamePlayStage(), Constants.GAME_OVER_VIEW_PATH);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        Canvas canvas = new Canvas(Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT);
        root.getChildren().add(canvas);
        root.setOnKeyPressed(new KeyHandlerOnPress(this.getPlayer()));
        root.setOnKeyReleased(new KeyHandlerOnRelease(this.getPlayer()));
        GraphicsContext gc = canvas.getGraphicsContext2D();

        this.setCarId(ChooseCarController.getInstance().getCarId());
        carId = carId == null ? "car1" : carId;
        String carImg = Constants.CAR_IMAGES_PATH + carId + ".png";

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
        KeyFrame kf = new KeyFrame(
                Duration.seconds(Constants.FRAMES_PER_SECOND),
                event -> {

                    //Pause

                    if (isPaused) {
                        PauseHandler pauseHandler = new PauseHandler(gameLoop, gc, background, y, player, testObstacles, collectibles);
                        pauseHandler.activatePause();
                    }

                    y = y + velocity;
                    time++;
                    frame++;

                    currentTime.setValue((long) (time * Constants.FRAMES_PER_SECOND));
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

                    System.out.println(y);
                    System.out.println(Constants.CANVAS_HEIGHT);
                    System.out.println("got here");

                    player.update();
                    player.render(gc);
                    currentHealth.render();
                    manageObstacles(gc);

                    if (time >= Constants.TRACK_1_END_TIME) {
                        clearObstaclesAndCollectibles();
                        gameLoop.stop();
                        MusicPlayer.stop();
                        time = 0;
                        player.setHealthPoints(Constants.HEALTH_BAR_MAX);
                        if (player.getHighScore() < player.getPoints()) {
                            player.setHighScore(player.getPoints());
                        }
                        player.setPoints(0L);
                        player.stopAccelerate();
                        velocity = Constants.START_GAME_VELOCITY;
                        currentDistance.setValue(0);

                        root.getChildren().remove(canvas);
                        //FXMLLoader loader = manager.loadSceneToStage(currentStage, Constants.GAME_WIN_VIEW_PATH,null);
                    }
                    if (player.getHealthPoints() <= 0) {
                        clearObstaclesAndCollectibles();
                        gameLoop.stop();
                        MusicPlayer.stop();
                        time = 0;
                        player.setHealthPoints(Constants.HEALTH_BAR_MAX);
                        if (player.getHighScore() < player.getPoints()) {
                            player.setHighScore(player.getPoints());
                        }
                        player.setPoints(0L);
                        player.stopAccelerate();
                        velocity = 5;
                        currentDistance.setValue(0);
                        root.getChildren().remove(canvas);
                        //FXMLLoader loader = manager.loadSceneToStage(currentStage, Constants.GAME_OVER_VIEW_PATH,null);
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
        for (Sprite testObst : testObstacles) {
            if (testObst.getName().substring(0, 6).equals("player") && !testObst.isDestroyed()) {
                testObst.setVelocity(0, velocity / 2);
            } else {
                testObst.setVelocity(0, velocity);
            }
            testObst.update();
            testObst.render(gc);

            if (testObst.getBoundary().intersects(player.getBoundary())) {
                if (!testObst.isDestroyed()) {
                    player.setHealthPoints(player.getHealthPoints() - Constants.OBSTACLE_DAMAGE);
                    testObst.setDestroyed(true);
                }
            }

        }
    }

    private void visualizeCollectible(GraphicsContext gc, double velocity) {
        for (Sprite collectible : collectibles) {
            collectible.setVelocity(0, velocity);
            collectible.update();
            collectible.render(gc);

            if (collectible.getBoundary().intersects(player.getBoundary())) {
                switch (collectible.getName()) {
                    case "1":      //Fuel Bottle/Pack
                        player.setPoints(player.getPoints() + Constants.FUEL_TANK_BONUS);
                        time -= 294;
                        break;
                    case "2":        //Health Pack
                        player.setPoints(player.getPoints() + Constants.HEALTH_PACK_BONUS_POINTS);
                        if (player.getHealthPoints() < Constants.HEALTH_BAR_MAX) {
                            player.setHealthPoints(Math.min(player.getHealthPoints() + Constants.HEALTH_BONUS, Constants.HEALTH_BAR_MAX));
                        }
                        break;
                    case "3":     //Bonus
                        player.setPoints(player.getPoints() + Constants.BONUS_POINTS);
                        break;
                }
                collectible.setPosition(Constants.DESTROY_OBJECT_COORDINATES, Constants.DESTROY_OBJECT_COORDINATES);
            }
        }
    }

    public void clearObstaclesAndCollectibles() {
        collectibles.clear();
        testObstacles.clear();
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

    public static boolean isIsPaused() {
        return isPaused;
    }

    public static void setIsPaused(boolean newValue) {
        isPaused = newValue;
    }

    public void pauseGame(ActionEvent actionEvent) {
        if (this.isIsPaused()) {
            this.setIsPaused(false);
        } else {
            this.setIsPaused(true);
        }
    }

    public void quitGame(ActionEvent actionEvent) {
        Platform.exit();
    }
}

package controllers;

import dataHandler.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import keyHandler.KeyHandlerOnPress;
import keyHandler.KeyHandlerOnRelease;
import music.MusicPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import static controllers.ScreenController.gameOverStage;
import static controllers.ScreenController.gameWinStage;
import static controllers.ScreenController.gamePlayStage;
import static controllers.ScreenController.loadStage;

public class GamePlayController implements Initializable {

    private static int frame = 0;
    private static long time = 0;
    private static double y;
    private static boolean isPaused = false;
    private static float velocity = Constants.START_GAME_VELOCITY;
    private static String carId;
    private static ArrayList<Sprite> testObstacles = new ArrayList<>();
    private static ArrayList<Sprite> collectibles = new ArrayList<>();
    private static Player player = LoginController.player;
    private static CurrentPoints currentPoints = new CurrentPoints(0);
    private static CurrentTime currentTime = new CurrentTime(0);
    private static CurrentDistance currentDistance = new CurrentDistance(0);
    private static HealthBar currentHealth;

    public static ImageView _health100;
    public static ImageView _health75;
    public static ImageView _health50;
    public static ImageView _health25;
    public static Rectangle _healthBar;

    @FXML
    public ImageView health100;
    @FXML
    public ImageView health75;
    @FXML
    public ImageView health50;
    @FXML
    public ImageView health25;
    @FXML
    public Rectangle healthBar;
    @FXML
    private Label timeInfo;
    @FXML
    public Label scorePoints;
    @FXML
    public Label distance;

    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        CurrentPoints currentPlayerPoints = getCurrentPoints();
        CurrentTime currentTime = getCurrentTime();
        CurrentDistance currentDistance = getCurrentDistance();
        scorePoints.textProperty().bind(Bindings.convert(currentPlayerPoints.valueProperty()));
        timeInfo.textProperty().bind(Bindings.convert(currentTime.valueProperty()));
        distance.textProperty().bind(Bindings.convert(currentDistance.valueProperty()));
        _health100 = health100;
        _health75 = health75;
        _health50 = health50;
        _health25 = health25;
        _healthBar = healthBar;
    }

    public static void RunTrack(Image background) {
        AnchorPane root = ScreenController.root;
        Canvas canvas = new Canvas(Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT);

        if (ScreenController.gamePlayStage != null) {
            Stage stage = (Stage) canvas.getScene().getWindow();
            try {
                loadStage(stage, gamePlayStage, Constants.GAME_OVER_VIEW_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        root.getChildren().add(canvas);
        root.getScene().setOnKeyPressed(new KeyHandlerOnPress(player));
        root.getScene().setOnKeyReleased(new KeyHandlerOnRelease(player));
        GraphicsContext gc = canvas.getGraphicsContext2D();
        carId = ChooseCarController.getCarId();
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
        MusicPlayer.PlayMusic();

        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.017),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        y = y + velocity ;
                        time++;
                        frame++;

                        currentTime.setValue((long) (time * 0.017));
                        currentDistance.setValue(currentDistance.getValue() + (long) velocity/2);
                        player.setPoints(player.getPoints()+1);
                        currentPoints.setValue(player.getPoints());

                        observer.update(currentPoints, observer);
                        observer.update(currentTime, observer);
                        observer.update(currentDistance, observer);

                        if (Math.abs(y) >= Constants.CANVAS_HEIGHT) {
                            y = y - Constants.CANVAS_HEIGHT;
                            frame = 0;
                        }
                        player.setVelocity(0, 0);

                        //Pause
                        if (isPaused) {
                            handleGamePause(gameLoop, gc, background);
                        }

                        //Generate obstacles
                        if (frame % 50000 == 0) {
                            testObstacles.add(Obstacle.generateObstacle());
                        }

                        gc.clearRect(0, 0, Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT);
                        gc.drawImage(background, 0, y - Constants.CANVAS_HEIGHT);
                        gc.drawImage(background, 0, y);
                        player.update();
                        player.render(gc);
                        currentHealth.render();
                        manageObstacles(gc);
                        if(currentDistance.getValue() >= 8000){
                            clearObstaclesAndCollectibles();
                            gameLoop.stop();
                            MusicPlayer.StopMusic();
                            time = 0;
                            player.setHealthPoints(100);
                            if (player.getHighScore() < player.getPoints()) {
                                player.setHighScore(player.getPoints());
                            }
                            player.setPoints(0L);
                            player.stopAccelerate();
                            velocity = 5;
                            currentDistance.setValue(0);
                            Stage stage = (Stage) canvas.getScene().getWindow();
                            root.getChildren().remove(canvas);
                            try {
                                loadStage(stage, gameWinStage, Constants.GAME_WIN_VIEW_PATH);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (player.getHealthPoints() <= 0) {
                            clearObstaclesAndCollectibles();
                            gameLoop.stop();
                            MusicPlayer.StopMusic();
                            time = 0;
                            player.setHealthPoints(100);
                            if (player.getHighScore() < player.getPoints()) {
                                player.setHighScore(player.getPoints());
                            }
                            player.setPoints(0L);
                            player.stopAccelerate();
                            velocity=5;
                            currentDistance.setValue(0);
                            Stage stage = (Stage) canvas.getScene().getWindow();
                            root.getChildren().remove(canvas);
                            try {
                                loadStage(stage, gameOverStage, Constants.GAME_OVER_VIEW_PATH);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        if (frame % Constants.COLLECTIBLES_OFFSET == 0) {
                            collectibles.add(Collectible.generateCollectible());
                        }
                        visualizeCollectible(gc, velocity);
                    }
                });

        gameLoop.getKeyFrames().add(kf);
        gameLoop.playFromStart();
    }

    private static void manageObstacles(GraphicsContext gc) {
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
                    player.setHealthPoints(player.getHealthPoints() - 25);
                    testObst.setDestroyed(true);
                }
            }

        }
    }

    private static void visualizeCollectible(GraphicsContext gc, double velocity) {
        for (Sprite collectible : collectibles) {
            collectible.setVelocity(0, velocity);
            collectible.update();
            collectible.render(gc);

            if (collectible.getBoundary().intersects(player.getBoundary())) {
                switch (collectible.getName()) {
                    case "1":      //Fuel Bottle/Pack
                        player.setPoints(player.getPoints() + Constants.FUEL_TANK_BONUS);
                        break;
                    case "2":        //Health Pack
                        player.setPoints(player.getPoints() + Constants.HEALTH_PACK_BONUS_POINTS);
                        if (player.getHealthPoints() < 100) {
                            player.setHealthPoints(player.getHealthPoints() + Constants.HEALTH_BONUS);
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

    public static void clearObstaclesAndCollectibles() {
        collectibles.clear();
        testObstacles.clear();
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

    public static void setVelocity(float velocity) {
        velocity = velocity;
    }

    public static void printHealthBar(Integer healthPoints) {

       // _healthBar.setWidth(healthPoints*1.56);  IF WE DECIDE TO SHOW IT BY PERCENTIGE

        if (healthPoints > 75 && healthPoints <= 100) {
            _health100.setVisible(true);
            _health75.setVisible(false);
            _health50.setVisible(false);
            _health25.setVisible(false);
        }
        else if (healthPoints <= 75 && healthPoints > 50) {
            _health100.setVisible(false);
            _health75.setVisible(true);
            _health50.setVisible(false);
            _health25.setVisible(false);
        }
        else if (healthPoints <= 50 && healthPoints > 25) {
            _health100.setVisible(false);
            _health75.setVisible(false);
            _health50.setVisible(true);
            _health25.setVisible(false);
        }
        else if (healthPoints <= 25) {
            _health100.setVisible(false);
            _health75.setVisible(false);
            _health50.setVisible(false);
            _health25.setVisible(true);
        }
    }

    private static Observer observer = new Observer() {
        @Override
        public void update(Observable o, Object arg) {
            
        }
    };

    private static void handleGamePause(final Timeline gameLoop, final GraphicsContext gc, final Image background) {
        isPaused = true;
        gameLoop.pause();
        if (isPaused) {
            MusicPlayer.Pause();

            Timeline pauseloop = new Timeline();
            pauseloop.setCycleCount(Timeline.INDEFINITE);

            KeyFrame keyFramePause = new KeyFrame(
                    Duration.seconds(0.017),
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (!isPaused) {
                                gameLoop.play();
                                MusicPlayer.Pause();
                                pauseloop.stop();
                            }

                            gc.clearRect(0, 0, Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT);
                            gc.drawImage(background, 0, y);
                            gc.drawImage(background, 0, y - Constants.CANVAS_HEIGHT);
                            player.render(gc);

                            for (Sprite collectible : collectibles) {
                                collectible.render(gc);
                            }
                            for (Sprite obs : testObstacles) {
                                obs.render(gc);
                            }
                        }
                    });
            pauseloop.getKeyFrames().add(keyFramePause);
            pauseloop.play();

        }
    }

    public static boolean isIsPaused() {
        return isPaused;
    }

    public static void setIsPaused(boolean newValue) {
        isPaused = newValue;
    }

    public void pauseGame(ActionEvent actionEvent) {
        if (isIsPaused()) {
            setIsPaused(false);
        } else {
            setIsPaused(true);
        }
    }

    public void quitGame(ActionEvent actionEvent) {
        Platform.exit();
    }
}

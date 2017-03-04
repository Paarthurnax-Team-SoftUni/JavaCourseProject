package GameEngine;

import controllers.ChooseCarController;
import controllers.ScreenController;
import dataHandler.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
import java.util.Observer;

public class GamePlayController implements Initializable {
    private static volatile GamePlayController instance = null;
    private int frame;
    private long time;
    private int y;
    private boolean isPaused;
    private float velocity;
    private String carId;
    private ArrayList<Sprite> testObstacles;
    private ArrayList<Sprite> collectibles;
    private Player player;
    private static CurrentPoints currentPoints = new CurrentPoints(0);
    private static CurrentTime currentTime = new CurrentTime(0);
    private static CurrentDistance currentDistance = new CurrentDistance(0);
    private HealthBar currentHealth;


    private static ImageView _health100;
    private static ImageView _health75;
    private static ImageView _health50;
    private static ImageView _health25;
    private static Rectangle _healthBar;


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

    public GamePlayController() {
    }

    ;

    private GamePlayController(int frame, long time, boolean isPaused, float velocity) {
        this.frame = frame;
        this.time = time;
        this.isPaused = isPaused;
        this.velocity = velocity;
        this.testObstacles = new ArrayList<>();
        this.collectibles = new ArrayList<>();
    }

    public static GamePlayController getInstance() {
        if (instance == null) {
            synchronized (GamePlayController.class) {
                if (instance == null) {
                    instance = new GamePlayController(0, 0, false, Constants.START_GAME_VELOCITY);
                }
            }
        }
        return instance;
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

    public void RunTrack(Image background) {
        AnchorPane root = ScreenController.getInstance().getRoot();
        Canvas canvas = new Canvas(Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT);

        if (ScreenController.getInstance().getGamePlayStage() != null) {
            Stage stage = (Stage) canvas.getScene().getWindow();
            try {
                ScreenController.getInstance().loadStage(stage, ScreenController.getInstance().getGamePlayStage(), Constants.GAME_OVER_VIEW_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        root.getChildren().add(canvas);
        root.getScene().setOnKeyPressed(new KeyHandlerOnPress(this.getPlayer()));
        root.getScene().setOnKeyReleased(new KeyHandlerOnRelease(this.getPlayer()));
        GraphicsContext gc = canvas.getGraphicsContext2D();
        this.setCarId(ChooseCarController.getInstance().getCarId());
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
        MusicPlayer.PlayMusic();
        MusicPlayer.Pause();

        KeyFrame kf = new KeyFrame(
                Duration.seconds(Constants.FRAMES_PER_SECOND),
                event -> {

                    //Pause

                    if (isPaused) {
                        PauseHandler pauseHandler = new PauseHandler(gameLoop, gc, background, y, player, testObstacles, collectibles);
                        pauseHandler.activatePause();
                    }

                    y = Math.round(y + velocity) ;
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
                    player.update();
                    player.render(gc);
                    currentHealth.render();
                    manageObstacles(gc);
                    if (time >= Constants.TRACK_1_END_TIME) {
                        clearObstaclesAndCollectibles();
                        gameLoop.stop();
                        MusicPlayer.StopMusic();
                        time = 0;
                        player.setHealthPoints(Constants.HEALTH_BAR_MAX);
                        if (player.getHighScore() < player.getPoints()) {
                            player.setHighScore(player.getPoints());
                        }
                        player.setPoints(0L);
                        player.stopAccelerate();
                        velocity = Constants.START_GAME_VELOCITY;
                        currentDistance.setValue(0);
                        Stage stage = (Stage) canvas.getScene().getWindow();
                        root.getChildren().remove(canvas);
                        try {
                            ScreenController.getInstance().loadStage(stage, ScreenController.getInstance().getGamePlayStage(), Constants.GAME_WIN_VIEW_PATH);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (player.getHealthPoints() <= 0) {
                        clearObstaclesAndCollectibles();
                        gameLoop.stop();
                        MusicPlayer.StopMusic();
                        time = 0;
                        player.setHealthPoints(Constants.HEALTH_BAR_MAX);
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
                            ScreenController.getInstance().loadStage(stage, ScreenController.getInstance().getGameOverStage(), Constants.GAME_OVER_VIEW_PATH);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
                    //player.setHealthPoints(player.getHealthPoints() - Constants.OBSTACLE_DAMAGE);
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

    public CurrentPoints getCurrentPoints() {
        System.out.println(currentPoints.getValue());
        return (currentPoints);
    }

    public CurrentTime getCurrentTime() {
        return (currentTime);
    }

    public CurrentDistance getCurrentDistance() {
        return (currentDistance);
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float v) {
        velocity = v;
    }

    public void printHealthBar(Integer healthPoints) {

        // _healthBar.setWidth(healthPoints*1.56);  IF WE DECIDE TO SHOW IT BY PERCENTIGE

        if (healthPoints > Constants.HEALTH_BAR_AVERAGE_HIGH && healthPoints <= Constants.HEALTH_BAR_MAX) {
            _health100.setVisible(true);
            _health75.setVisible(false);
            _health50.setVisible(false);
            _health25.setVisible(false);
        } else if (healthPoints <= Constants.HEALTH_BAR_AVERAGE_HIGH && healthPoints > Constants.HEALTH_BAR_AVERAGE_LOW) {
            _health100.setVisible(false);
            _health75.setVisible(true);
            _health50.setVisible(false);
            _health25.setVisible(false);
        } else if (healthPoints <= Constants.HEALTH_BAR_AVERAGE_LOW && healthPoints > Constants.HEALTH_BAR_MIN) {
            _health100.setVisible(false);
            _health75.setVisible(false);
            _health50.setVisible(true);
            _health25.setVisible(false);
        } else if (healthPoints <= Constants.HEALTH_BAR_MIN) {
            _health100.setVisible(false);
            _health75.setVisible(false);
            _health50.setVisible(false);
            _health25.setVisible(true);
        }
    }

    private static Observer observer = (o, arg) -> {
    };

    public boolean isIsPaused() {
        return isPaused;
    }

    public void setIsPaused(boolean newValue) {
        isPaused = newValue;
    }

    public void pauseGame(ActionEvent actionEvent) {

        System.out.println("clicked");

        if (this.getInstance().isIsPaused()) {
            this.getInstance().setIsPaused(false);
        } else {
            this.getInstance().setIsPaused(true);
        }
    }

    public void quitGame(ActionEvent actionEvent) {
        Platform.exit();
    }
}

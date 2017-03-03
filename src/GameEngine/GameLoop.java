//package GameEngine;
//
//import controllers.GamePlayController;
//import controllers.ScreenController;
//import dataHandler.*;
//import javafx.animation.KeyFrame;
//import javafx.animation.Timeline;
//import javafx.beans.binding.Bindings;
//import javafx.fxml.Initializable;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.image.Image;
//import javafx.scene.layout.AnchorPane;
//import javafx.stage.Stage;
//import javafx.util.Duration;
//import music.MusicPlayer;
//
//import java.io.IOException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Observer;
//import java.util.ResourceBundle;
//
///**
// * Under Construction
// */
//public class GameLoop implements Initializable {
//    private Timeline gameLoop;
//    private GraphicsContext gc;
//    private Image background;
//    private Player player;
//    private ArrayList<Sprite> testObstacles;
//    private ArrayList<Sprite> collectibles;
//    private Canvas canvas;
//    AnchorPane root;
//
//    private int frame;
//    private long time;
//    private double y;
//    private float velocity;
//    private static CurrentTime currentTime = new CurrentTime(0);
//    private static CurrentDistance currentDistance = new CurrentDistance(0);
//    private static CurrentPoints currentPoints = new CurrentPoints(0);
//    private HealthBar currentHealth;
//
//
//    public GameLoop(GraphicsContext gc, Image background, Player player,
//                    ArrayList<Sprite> testObstacles, ArrayList<Sprite> collectibles,
//                    Canvas canvas, AnchorPane root, int frame, long time, double y,
//                    float velocity, HealthBar currentHealth) {
//        this.gameLoop =new Timeline();
//        this.gameLoop.setCycleCount(Timeline.INDEFINITE);
//        this.gc = gc;
//        this.background = background;
//        this.player = player;
//        this.testObstacles = testObstacles;
//        this.collectibles = collectibles;
//        this.canvas = canvas;
//        this.root = root;
//        this.frame = frame;
//        this.time = time;
//        this.y = y;
//        this.velocity = velocity;
//        this.currentHealth = currentHealth;
//    }
//
//    public void runGame(){
//        MusicPlayer.PlayMusic();
//
//        KeyFrame kf = new KeyFrame(
//                Duration.seconds(Constants.FRAMES_PER_SECOND),
//                event -> {
//
//                    Observer observer = (o, arg) -> {
//                    };
//
//
//                    if (GamePlayController.getInstance().isIsPaused()) {
//                        PauseHandler pauseHandler = new PauseHandler(gameLoop, gc, background, y, player, testObstacles, collectibles);
//                        pauseHandler.activatePause();
//                    }
//
//                    y = y + velocity ;
//                    time++;
//                    frame++;
//
//                    currentTime.setValue((long) (time * Constants.FRAMES_PER_SECOND));
//                    currentDistance.setValue(currentDistance.getValue() + (long) velocity/2);
//                    player.setPoints(player.getPoints()+1);
//                    currentPoints.setValue(player.getPoints());
//
//                    observer.update(currentPoints, observer);
//                    observer.update(currentTime, observer);
//                    observer.update(currentDistance, observer);
//
//                    if (Math.abs(y) >= Constants.CANVAS_HEIGHT) {
//                        y = y - Constants.CANVAS_HEIGHT;
//                        frame = 0;
//                    }
//                    player.setVelocity(0, 0);
//
//                    //Pause
//
//                    //Generate obstacles
//                    if (frame == 0) {
//                        testObstacles.add(Obstacle.generateObstacle());
//                    }
//
//                    gc.clearRect(0, 0, Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT);
//                    gc.drawImage(background, 0, y - Constants.CANVAS_HEIGHT);
//                    gc.drawImage(background, 0, y);
//                    player.update();
//                    player.render(gc);
//                    currentHealth.render();
//                    manageObstacles(gc);
//                    if(time >= Constants.TRACK_1_END_TIME){
//                        clearObstaclesAndCollectibles();
//                        gameLoop.stop();
//                        MusicPlayer.StopMusic();
//                        time = 0;
//                        player.setHealthPoints(Constants.HEALTH_BAR_MAX);
//                        if (player.getHighScore() < player.getPoints()) {
//                            player.setHighScore(player.getPoints());
//                        }
//                        player.setPoints(0L);
//                        player.stopAccelerate();
//                        velocity = Constants.START_GAME_VELOCITY;
//                        currentDistance.setValue(0);
//                        Stage stage = (Stage) canvas.getScene().getWindow();
//                        root.getChildren().remove(canvas);
//                        try {
//                            ScreenController.getInstance().loadStage(stage, ScreenController.getInstance().getGamePlayStage(), Constants.GAME_WIN_VIEW_PATH);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (player.getHealthPoints() <= 0) {
//                        clearObstaclesAndCollectibles();
//                        gameLoop.stop();
//                        MusicPlayer.StopMusic();
//                        time = 0;
//                        player.setHealthPoints(Constants.HEALTH_BAR_MAX);
//                        if (player.getHighScore() < player.getPoints()) {
//                            player.setHighScore(player.getPoints());
//                        }
//                        player.setPoints(0L);
//                        player.stopAccelerate();
//                        velocity=5;
//                        currentDistance.setValue(0);
//                        Stage stage = (Stage) canvas.getScene().getWindow();
//                        root.getChildren().remove(canvas);
//                        try {
//                            ScreenController.getInstance().loadStage(stage, ScreenController.getInstance().getGameOverStage(), Constants.GAME_OVER_VIEW_PATH);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    if (frame % Constants.COLLECTIBLES_OFFSET == 0) {
//                        collectibles.add(Collectible.generateCollectible());
//                    }
//                    visualizeCollectible(gc, velocity);
//                });
//
//        gameLoop.getKeyFrames().add(kf);
//        gameLoop.playFromStart();
//    }
//
//
//    private void manageObstacles(GraphicsContext gc) {
//        for (Sprite testObst : testObstacles) {
//            if (testObst.getName().substring(0, 6).equals("player") && !testObst.isDestroyed()) {
//                testObst.setVelocity(0, velocity / 2);
//            } else {
//                testObst.setVelocity(0, velocity);
//            }
//            testObst.update();
//            testObst.render(gc);
//
//            if (testObst.getBoundary().intersects(player.getBoundary())) {
//                if (!testObst.isDestroyed()) {
//                    player.setHealthPoints(player.getHealthPoints() - Constants.OBSTACLE_DAMAGE);
//                    testObst.setDestroyed(true);
//                }
//            }
//
//        }
//    }
//
//    private void visualizeCollectible(GraphicsContext gc, double velocity) {
//        for (Sprite collectible : collectibles) {
//            collectible.setVelocity(0, velocity);
//            collectible.update();
//            collectible.render(gc);
//
//            if (collectible.getBoundary().intersects(player.getBoundary())) {
//                switch (collectible.getName()) {
//                    case "1":      //Fuel Bottle/Pack
//                        player.setPoints(player.getPoints() + Constants.FUEL_TANK_BONUS);
//                        time -= 294;
//                        break;
//                    case "2":        //Health Pack
//                        player.setPoints(player.getPoints() + Constants.HEALTH_PACK_BONUS_POINTS);
//                        if (player.getHealthPoints() < Constants.HEALTH_BAR_MAX) {
//                            player.setHealthPoints(Math.min(player.getHealthPoints() + Constants.HEALTH_BONUS, Constants.HEALTH_BAR_MAX));
//                        }
//                        break;
//                    case "3":     //Bonus
//                        player.setPoints(player.getPoints() + Constants.BONUS_POINTS);
//                        break;
//                }
//                collectible.setPosition(Constants.DESTROY_OBJECT_COORDINATES, Constants.DESTROY_OBJECT_COORDINATES);
//            }
//        }
//    }
//
//    public void clearObstaclesAndCollectibles() {
//        collectibles.clear();
//        testObstacles.clear();
//    }
//    public CurrentPoints getCurrentPoints() {
//        System.out.println(currentPoints.getValue());
//        return (currentPoints);
//    }
//
//    public CurrentTime getCurrentTime() {
//        return (currentTime);
//    }
//
//    public CurrentDistance getCurrentDistance() {
//        return (currentDistance);
//    }
//
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        CurrentPoints currentPlayerPoints = getCurrentPoints();
//        CurrentTime currentTime = getCurrentTime();
//        CurrentDistance currentDistance = getCurrentDistance();
//        GamePlayController.getInstance().scorePoints.textProperty().bind(Bindings.convert(currentPlayerPoints.valueProperty()));
//        GamePlayController.getInstance().timeInfo.textProperty().bind(Bindings.convert(currentTime.valueProperty()));
//        GamePlayController.getInstance().distance.textProperty().bind(Bindings.convert(currentDistance.valueProperty()));
//    }
//}

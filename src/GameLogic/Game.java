package GameLogic;

import Controllers.ChooseCarController;
import Controllers.LoginController;
import Controllers.ScreenController;
import DataHandler.*;
import KeyHandler.KeyHandlerOnPress;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;

import static Controllers.ScreenController.loadStage;
import static Controllers.ScreenController.startStage;

public class Game {
    public static int velocity = 5;
    public static CurrentDistance currentDistance =new CurrentDistance(0);
    private static AnchorPane root = ScreenController.root;
    private static int frame = 0;
    private static long time = 0;
    public static boolean isPaused = false;
    private static double y;
    private static ArrayList<Sprite> testObstacles = new ArrayList<>();
    private static ArrayList<Sprite> collectibles = new ArrayList<>();
    private static Player playerCar = LoginController.player;
    private static String carId = ChooseCarController.carId;
    private static CurrentPoints currentPoints = new CurrentPoints(0);
    private static CurrentTime currentTime = new CurrentTime(0);
    private static Timer timer = new Timer();
    private static HealthBar currentHealth;


    private static Observer observer = new Observer() {
        @Override
        public void update(Observable o, Object arg) {

        }
    };


    public static void RunTrack(Image background) {
        Canvas canvas = new Canvas(500, 600);
        if (ScreenController.startStage != null) {
            Stage stage = (Stage) canvas.getScene().getWindow();
            try {
                loadStage(stage, startStage, "../views/gameOver.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        root.getChildren().add(canvas);
        root.getScene().setOnKeyPressed(new KeyHandlerOnPress(playerCar));
        GraphicsContext gc = canvas.getGraphicsContext2D();
        carId = carId == null ? "car1" : carId;
        String carImg = "/resources/images/player_" + carId + ".png";
        playerCar.setImage(carImg);
        //playerCar.setImage("/resources/images/player_car3.png");  depending on level?
        playerCar.setPosition(200, 430);
        playerCar.setPoints(0L);
        currentHealth = new HealthBar(playerCar);
        currentPoints.addObserver(observer);
        currentTime.addObserver(observer);
        currentDistance.addObserver(observer);
        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.017),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        y = velocity * frame;
                        time++;
                        frame++;

                       // playerCar.setPoints(playerCar.getPoints() + 1);

                        currentTime.setValue((long) (time * 0.017));
                        currentDistance.setValue(currentDistance.getValue()+velocity);
                        currentPoints.setValue(playerCar.getPoints());
                        observer.update(currentPoints, observer);
                        observer.update(currentTime, observer);
                        observer.update(currentDistance, observer);


                        if (y >= 600) {
                            frame = 0;
                        }   if (y <= -600) {
                            frame = 0;
                        }
                        playerCar.setVelocity(0, 0);

                        //Pause Block
                        if (isPaused) {
                            handleGamePause(gameLoop, gc, background);
                        } //End of pause

                        if (frame % 50000 == 0) {
                            testObstacles.add(Obstacle.generateObstacle());
                            System.out.println(frame);
                        }
                        gc.clearRect(0, 0, 500, 600);
                        gc.drawImage(background, 0, y - 600);
                        gc.drawImage(background, 0, y);
                        playerCar.render(gc);
                        currentHealth.render(gc);
                        //observer.update(currentHealth, observer);
                        //currentHealth.render(gc);
                        manageObstacles(gc);
                        if (playerCar.getHealthPoints() <= 0) {
                            clearObstaclesAndCollectibles();
                            gameLoop.stop();
                            time = 0;
                            playerCar.setHealthPoints(100);
                            if (playerCar.getHighScore() < playerCar.getPoints()) {
                                playerCar.setHighScore(playerCar.getPoints());
                            }
                            playerCar.setPoints(0L);
                            root.getChildren().remove(canvas);
                            try {
                                loadStage(ScreenController.primaryStage, startStage, "../views/gameOver.fxml");

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                        if (frame % 50000 == 0) {
                            collectibles.add(Collectible.generateCollectible());
                        }
                        visualizeCollectible(gc);

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

            //
            if (testObst.getBoundary().intersects(playerCar.getBoundary())) {
                if (!testObst.isDestroyed()) {
                    playerCar.setHealthPoints(playerCar.getHealthPoints() - 10);
                    testObst.setDestroyed(true);
                }
            }
        }
    }

    private static void handleGamePause(final Timeline gameLoop, final GraphicsContext gc, final Image background) {
        isPaused = true;
        gameLoop.pause();
        if (isPaused) {

            System.out.println("Enter Pause");

            Timeline pauseloop = new Timeline();
            pauseloop.setCycleCount(Timeline.INDEFINITE);

            KeyFrame keyFramePause = new KeyFrame(
                    Duration.seconds(0.017),
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (!isPaused) {
                                System.out.println("Exit Pause");
                                gameLoop.play();
                                pauseloop.stop();
                            }

                            gc.clearRect(0, 0, 500, 600);
                            gc.drawImage(background, 0, y);
                            gc.drawImage(background, 0, y - 600);
                            playerCar.render(gc);

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


    private static void visualizeCollectible(GraphicsContext gc) {
        for (Sprite collectible : collectibles) {
            collectible.setVelocity(0, velocity);
            collectible.render(gc);
            collectible.update();

            if (collectible.getBoundary().intersects(playerCar.getBoundary())) {
                switch (collectible.getName()) {
                    case "1":      //Fuel Bottle/Pack
                        playerCar.setPoints(playerCar.getPoints() + 250);
                        break;
                    case "2":        //Health Pack
                        playerCar.setPoints(playerCar.getPoints() + 500);
                        if (playerCar.getHealthPoints() < 100) {
                            playerCar.setHealthPoints(playerCar.getHealthPoints() + 10);
                        }
                        break;
                    case "3":     //Bonus
                        playerCar.setPoints(playerCar.getPoints() + 1000);
                        break;
                }
                collectible.setPosition(800, 800);

                // System.out.println("Points: " + playerCar.getPoints());
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
    public static CurrentDistance getCurrentDstance() {
        return (currentDistance);
    }


}

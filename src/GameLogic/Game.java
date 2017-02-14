package GameLogic;

import Controllers.ChooseCarController;
import Controllers.GamePlayController;
import Controllers.LoginController;
import Controllers.ScreenController;
import DataHandler.*;
import KeyHandler.KeyHandlerOnPress;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.*;

import static Controllers.ScreenController.gamePlayStage;
import static Controllers.ScreenController.loadStage;
import static Controllers.ScreenController.startStage;
import static Controllers.ScreenController.gameOverStage;

public class Game {
    public static int velocity = 5;
    private static AnchorPane root = ScreenController.root;
    private static int frame = 0;
    private static long time = 0;
    private static boolean isPaused = false;
    private static double y;
    private static ArrayList<Sprite> testObstacles = new ArrayList<>();
    private static ArrayList<Sprite> collectibles = new ArrayList<>();
    private static Player player = LoginController.player;
    private static String carId = ChooseCarController.carId;
    private static CurrentPoints currentPoints = new CurrentPoints(0);
    private static CurrentTime currentTime = new CurrentTime(0);
    private static HealthBar currentHealth;

    private static Observer observer = new Observer() {
        @Override
        public void update(Observable o, Object arg) {
        }
    };


    public static void RunTrack(Image background) {
        AnchorPane root = ScreenController.root;
        Canvas canvas = new Canvas(500, 600);
        //EventHandler<? super KeyEvent> onKeyPressed = root.getOnKeyPressed();
        if (ScreenController.gamePlayStage != null) {
            Stage stage = (Stage) canvas.getScene().getWindow();
            try {
                loadStage(stage, gamePlayStage, "../views/gameOver.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        root.getChildren().add(canvas);
        root.getScene().setOnKeyPressed(new KeyHandlerOnPress(player));
        GraphicsContext gc = canvas.getGraphicsContext2D();
        carId = carId == null ? "car1" : carId;
        String carImg = "/resources/images/player_" + carId + ".png";
        player.setImage(carImg);
        //playerCar.setImage("/resources/images/player_car3.png");  depending on level?
        player.setPosition(200, 430);
        player.setPoints(0L);
        currentHealth = new HealthBar(player);

        currentPoints.addObserver(observer);
        currentTime.addObserver(observer);
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

                        currentTime.setValue((long) (time * 0.017));
                        currentPoints.setValue(player.getPoints());

                        observer.update(currentPoints, observer);
                        observer.update(currentTime, observer);

                        if (y >= 600) {
                            frame = 0;
                        }
                        if (y <= -600) {
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

                        gc.clearRect(0, 0, 500, 600);
                        gc.drawImage(background, 0, y - 600);
                        gc.drawImage(background, 0, y);
                        player.render(gc);
                        currentHealth.render(gc);
                        //observer.update(currentHealth, observer);
                        manageObstacles(gc);

                        if (player.getHealthPoints() <= 0) {
                            clearObstaclesAndCollectibles();
                            gameLoop.stop();
                            time = 0;
                            player.setHealthPoints(100);
                            if (player.getHighScore() < player.getPoints()) {
                                player.setHighScore(player.getPoints());
                            }
                            player.setPoints(0L);
                            Stage stage = (Stage) canvas.getScene().getWindow();
                            root.getChildren().remove(canvas);
                            try {
                                loadStage(stage, gameOverStage, "../views/gameOver.fxml");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        if (frame % 50000 == 0) {
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

            //
            if (testObst.getBoundary().intersects(player.getBoundary())) {
                if (!testObst.isDestroyed()) {
                    player.setHealthPoints(player.getHealthPoints() - 25);
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

    private static void visualizeCollectible(GraphicsContext gc, int velocity) {
        for (Sprite collectible : collectibles) {
            collectible.setVelocity(0, velocity);
            collectible.render(gc);
            collectible.update();

            if (collectible.getBoundary().intersects(player.getBoundary())) {
                switch (collectible.getName()) {
                    case "1":      //Fuel Bottle/Pack
                        player.setPoints(player.getPoints() + 250);
                        break;
                    case "2":        //Health Pack
                        player.setPoints(player.getPoints() + 500);
                        if (player.getHealthPoints() < 100) {
                            player.setHealthPoints(player.getHealthPoints() + 25);
                        }
                        break;
                    case "3":     //Bonus
                        player.setPoints(player.getPoints() + 1000);
                        break;
                }
                collectible.setPosition(800, 800);
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

    public static boolean isIsPaused() {
        return isPaused;
    }

    public static void setIsPaused(boolean isPaused) {
        Game.isPaused = isPaused;
    }
}

package GameLogic;

import Controllers.ChooseCarController;
import Controllers.LoginController;
import Controllers.ScreenController;
import DataHandler.CurrentTime;
import DataHandler.Player;
import DataHandler.Sprite;
import DataHandler.CurrentPoints;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Time;
import java.util.*;

import static Controllers.ScreenController.loadStage;
import static Controllers.ScreenController.startStage;

public class Game {
    private static AnchorPane root = ScreenController.root;
    private static int frame = 0;
    private static long time = 0;
    private static boolean isPaused = false;
    private static double y;
    private static ArrayList<Sprite> testObstacles = new ArrayList<>();
    private static ArrayList<Sprite> collectibles = new ArrayList<>();
    private static Player playerCar = LoginController.player;
    private static String carId = ChooseCarController.carId;
    private static CurrentPoints currentPoints = new CurrentPoints(0);
    private static CurrentTime  currentTime = new CurrentTime(0);
    private static Timer timer = new Timer();




    private static Observer observer = new Observer() {
        @Override
        public void update(Observable o, Object arg) {

        }
    };


    public static void RunTrack(Image background, int velocity) {
        Canvas canvas = new Canvas(500, 600);
        EventHandler<? super KeyEvent> onKeyPressed = root.getOnKeyPressed();
        if (ScreenController.startStage != null) {
            Stage stage = (Stage) canvas.getScene().getWindow();
            try {
                loadStage(stage, startStage, "../views/gameOver.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        root.getChildren().add(canvas);
        ArrayList<String> input = new ArrayList<>();

        root.getScene().setOnKeyPressed(event -> {
            String code = event.getCode().toString();
            if (!input.contains(code)) {
                if (code.equals("P") || code.equals("LEFT") || code.equals("RIGHT")) {
                    input.add(code);
                }
            }
        });

        GraphicsContext gc = canvas.getGraphicsContext2D();
        carId = carId == null ? "car1" : carId;
        String carImg = "/resources/images/player_" + carId + ".png";
        playerCar.setImage(carImg);
        //playerCar.setImage("/resources/images/player_car3.png");  depending on level?
        playerCar.setPosition(200, 430);


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
                        playerCar.setPoints(playerCar.getPoints() + 1);

                        currentTime.setValue((long)(time*0.017));


                        currentPoints.setValue(playerCar.getPoints());
                        observer.update(currentPoints, observer);
                        observer.update(currentTime, observer);


                        if (y == 600) {
                            frame = 0;
                        }
                        playerCar.setVelocity(0, 0);

                        //Pause Block
                        if (input.contains("P") && !isPaused) {
                            handleGamePause(input, gameLoop, gc, background);

                        } //End of pause


                        if (frame % 50000 == 0) {
                            testObstacles.add(generateObstacle());
                            System.out.println(frame);

                        }
                        if (input.contains("LEFT")) {
                            playerCar.addVelocity(-50, 0);
                            input.remove("LEFT");
                            playerCar.update();
                            playerCar.render(gc);
                        }
                        if (input.contains("RIGHT")) {
                            playerCar.addVelocity(50, 0);
                            input.remove("RIGHT");
                            playerCar.update();
                            playerCar.render(gc);
                        }
                        gc.clearRect(0, 0, 500, 600);
                        gc.drawImage(background, 0, y);
                        gc.drawImage(background, 0, y - 600);
                        playerCar.render(gc);


                        for (Sprite testObst : testObstacles) {
                            if (testObst.getName().substring(0, 6).equals("player") && !testObst.isDestroyed()) {
                                testObst.setVelocity(0, velocity / 2);
                            } else {
                                testObst.setVelocity(0, velocity);
                            }
                            testObst.render(gc);
                            testObst.update();

                            if (testObst.getBoundary().intersects(playerCar.getBoundary())) {
                                if (!testObst.isDestroyed()) {
                                    playerCar.setHealthPoints(playerCar.getHealthPoints() - 10);
                                    testObst.setDestroyed(true);
                                }
                                testObst.setVelocity(0, 0);
                                testObst.setImage("resources/images/flame.png");
                                if (playerCar.getHealthPoints() <= 0) {
                                    clearObstaclesAndCollectibles();
                                    gameLoop.stop();

                                    root.getChildren().remove(canvas);
                                    try {
                                        loadStage(ScreenController.primaryStage, startStage, "../views/gameOver.fxml");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        }

                        if (frame % 50000 == 0) {
                            collectibles.add(generateCollectible());
                        }
                        visualizeCollectible(gc, velocity);

                    }
                });

        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();

    }

    private static void handleGamePause(final ArrayList<String> input, final Timeline gameLoop, final GraphicsContext gc, final Image background) {
        isPaused = true;
        input.remove("P");
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
                            if (input.contains("P") && isPaused) {
                                System.out.println("Exit Pause");
                                isPaused = false;
                                gameLoop.play();
                                pauseloop.stop();
                                input.remove("P");
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

    private static Sprite generateObstacle() {
        String[] obstacles = {"obstacle1", "obstacle2", "obstacle3", "obstacle1", "obstacle2", "obstacle3", "player_car1", "player_car2", "player_car3", "player_car4", "player_car5", "player_car6"};
        String random = (obstacles[new Random().nextInt(obstacles.length)]);

        Random obstacleX = new Random();
//        Random obstacleY = new Random();
//        Random obstaclePic = new Random();
//        long numb = System.currentTimeMillis() % 3;

        String sd = "/resources/images/" + random + ".png";
        Sprite testObstacle = new Sprite();
        testObstacle.setImage(sd);

        testObstacle.setName(random);
        testObstacle.setPosition(50 + obstacleX.nextInt(300), -166);

        return testObstacle;
    }

    private static Sprite generateCollectible() {
        Random collectibleX = new Random();
        long numb = System.currentTimeMillis() % 3;
        //TODO: change stringDirectory to the correct images!
        String stringDirectory = "/resources/images/collectable" + (numb + 1) + ".png";

        Sprite collectible = new Sprite();
        collectible.setName(String.valueOf(numb + 1));
        collectible.setImage(stringDirectory);
        collectible.setPosition(50 + collectibleX.nextInt(300), -60);

        return collectible;
    }

    private static void visualizeCollectible(GraphicsContext gc, int velocity) {
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

    private static void clearObstaclesAndCollectibles() {
        collectibles = new ArrayList<>();
        testObstacles = new ArrayList<>();
    }

    public static void clearObs() {
        testObstacles.clear();
    }

    public static DataHandler.CurrentPoints getCurrentPoints() {
        return (currentPoints);
    }
}

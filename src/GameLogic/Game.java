package GameLogic;

import Controllers.ChooseCarController;
import Controllers.LoginController;
import Controllers.ScreenController;
import DataHandler.Player;
import DataHandler.Sprite;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static Controllers.ScreenController.loadStage;
import static Controllers.ScreenController.startStage;

public class Game {
    private static AnchorPane root = ScreenController.root;
    private static int seconds = 0;
    private static boolean isPaused = false;
    private static double y;
    private static ArrayList<Sprite> testObstacles = new ArrayList<>();
    private static ArrayList<Sprite> collectibles = new ArrayList<>();
    private static Player playerCar = LoginController.player;
    private static String carId = ChooseCarController.carId;

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

        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.017),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        y = velocity * seconds;
                        seconds++;
                        if (y == 600) {
                            seconds = 0;
                        }
                        playerCar.setVelocity(0, 0);

                        //Pause Block
                        if (input.contains("P") && !isPaused) {
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
                                                for (Sprite obs : testObstacles) {
                                                    obs.render(gc);
                                                }
                                            }
                                        });
                                pauseloop.getKeyFrames().add(keyFramePause);
                                pauseloop.play();

                            }

                        } //End of pause

                        if (seconds % 150 == 0) {
                            testObstacles.add(generateObstacle());
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
                            testObst.setVelocity(0, y / 47);
                            testObst.render(gc);
                            testObst.update();

                            if (testObst.getBoundary().intersects(playerCar.getBoundary())) {
                                gameLoop.stop();
                                Stage stage = ScreenController.startStage;
                                root.getChildren().remove(canvas);
                                try {
                                    loadStage(ScreenController.primaryStage, startStage, "../views/gameOver.fxml");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if (seconds % 300 == 0){
                            collectibles.add(generateCollectible());
                        }
                            visualizeCollectible(gc);



                    }
                });

        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();

    }

    private static Sprite generateObstacle() {
        String[] obstacles = {"obstacle1","obstacle2","obstacle3","player_car1","player_car2","player_car3","player_car4","player_car5","player_car6"};
        String random = (obstacles[new Random().nextInt(obstacles.length)]);

        Random obstacleX = new Random();
//        Random obstacleY = new Random();
//        Random obstaclePic = new Random();
//        long numb = System.currentTimeMillis() % 3;

        String sd = "/resources/images/"+ random +".png";
        Sprite testObstacle = new Sprite();
        testObstacle.setImage(sd);
        testObstacle.setPosition(50 + obstacleX.nextInt(300), 0);

        return testObstacle;
    }

    private static Sprite generateCollectible(){
        Random collectibleX = new Random();
        long numb = System.currentTimeMillis() % 3;
        //TODO: change stringDirectory to the correct images!
        String stringDirectory = "/resources/images/collectable" + (numb + 1) + ".png";

        Sprite collectible = new Sprite();
        collectible.setImage(stringDirectory);
        collectible.setPosition(50 + collectibleX.nextInt(300), 0);

        return collectible;
    }

    private static void visualizeCollectible(GraphicsContext gc){
        for (Sprite collectible : collectibles) {
            collectible.setVelocity(0, y / 47);
            collectible.render(gc);
            collectible.update();

            if (collectible.getBoundary().intersects(playerCar.getBoundary())){
                playerCar.setPoints(playerCar.getPoints() + 10);
                collectible.setPosition(800,800);

                System.out.println("Points: " + playerCar.getPoints());
            }
        }
    }

    public static void clearObs() {
        testObstacles.clear();
    }
}

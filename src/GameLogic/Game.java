package GameLogic;

import Controllers.ChooseCarController;
import Controllers.LoginController;
import Controllers.ScreenController;
import DataHandler.Player;
import KeyHandler.Sprite;
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
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    private static AnchorPane root = ScreenController.root;
    private static int seconds = 0;
    private static boolean isPaused = false;
    private static double y;
    private static Sprite testObstacle = generateObstacle();
    private static Player playerCar = LoginController.player;
    private static String carId = ChooseCarController.carId;

    public static void RunTrack(Image background, int velocity) {
        Canvas canvas = new Canvas(500, 600);

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

        root.getScene().addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if(t.getCode() == KeyCode.SPACE) {
                    t.consume();
                }
            }});

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
                                                testObstacle.render(gc);
                                            }
                                        });
                                pauseloop.getKeyFrames().add(keyFramePause);
                                pauseloop.play();

                            }

                        } //End of pause

                        if (seconds % 150 ==0){
                            testObstacle = generateObstacle();
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
                        testObstacle.setVelocity(0, y/47);
                        testObstacle.render(gc);
                        testObstacle.update();
                        playerCar.render(gc);

                        if (testObstacle.getBoundary().intersects(playerCar.getBoundary())){
                            gameLoop.stop();
                        }
                    }
                });

        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();

    }
    private static Sprite generateObstacle (){
        Random obstacleX = new Random();
        Random obstacleY = new Random();
        Random obstaclePic = new Random();
        long numb = System.currentTimeMillis()%3;

        String sd = "/resources/images/obstacle" + (numb+1) + ".png";
        Sprite testObstacle = new Sprite();
        testObstacle.setImage(sd);
        testObstacle.setPosition(50+obstacleX.nextInt(300), 0);

        return testObstacle;
    }
}

package GameLogic;

import DataHandler.Player;
import KeyHandler.Sprite;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.Main;

import java.util.ArrayList;
import java.util.Map;

public class Game {
    private static AnchorPane root = Main.windowPane;
    private static int seconds = 0;
    private static long lastNanoTime = System.nanoTime();
    private static Stage theStage = Main.primStage;

    public static void RunTrack(Image background) {
        Canvas canvas = new Canvas(500, 600);
        
        root.getChildren().add(canvas);
        ArrayList<String> input = new ArrayList<>();

        root.getScene().setOnKeyPressed(event -> {
            String code = event.getCode().toString();
            if (!input.contains(code))
            {
                input.add(code);
            }
        });

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Sprite playerCar = new Player("toBeDonloadedFromTheData", 0L, 0.0, 0L, 0L, 100);
        playerCar.setImage("/resources/images/player_car.png");
        playerCar.setPosition(200, 430);

        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(
            Duration.seconds(0.017),
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    seconds++;
                    double y = 5 * seconds;
                    if(y == 600) {
                        seconds=1;
                    }

                    playerCar.setVelocity(0, 0);
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
                    gc.drawImage(background, 0, y-610);
                    playerCar.render(gc);
                }
            });

        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();
    }
}

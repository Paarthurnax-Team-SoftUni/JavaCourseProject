package GameLogic;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;
import main.Main;

public class Game {

    public static void RunTrack(Image background) {
        Image car = new Image("/resources/images/player_car.png");
        Canvas canvas = new Canvas(500, 600);
        Main.windowPane.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        final long timeStart = System.currentTimeMillis();
        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.017),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        double t = (System.currentTimeMillis() - timeStart) / 1000.0;
                        double y = 100 * t;
                        gc.clearRect(0, 0, 500, 600);
                        gc.drawImage(background, 0, y);
                        gc.drawImage(car, 200, 430);
                    }

                });
        gameLoop.getKeyFrames().add(kf);

        gameLoop.play();
    }
}

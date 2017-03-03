package GameEngine;

import dataHandler.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;
import models.Collectible;
import models.Obstacle;
import models.Player;
import models.Sprite;
import music.MusicPlayer;

import java.util.ArrayList;

public class PauseHandler {
    private Timeline gameLoop;
    private GraphicsContext gc;
    private Image background;
    private double y;
    private Player player;
    private ArrayList<Obstacle> testObstacles;
    private ArrayList<Collectible> collectibles;

    public PauseHandler(Timeline gameLoop, GraphicsContext gc, Image background,
                        double y, Player player, ArrayList<Obstacle> testObstacles, ArrayList<Collectible> collectibles) {
        this.gameLoop = gameLoop;
        this.gc = gc;
        this.background = background;
        this.y = y;
        this.player = player;
        this.testObstacles = testObstacles;
        this.collectibles = collectibles;
    }

    public void activatePause() {
        GamePlayController.getInstance().setIsPaused(true);
        this.gameLoop.pause();
        if (GamePlayController.getInstance().isIsPaused()) {
            MusicPlayer.Pause();

            Timeline pauseloop = new Timeline();
            pauseloop.setCycleCount(Timeline.INDEFINITE);

            KeyFrame keyFramePause = new KeyFrame(
                    Duration.seconds(Constants.FRAMES_PER_SECOND),
                    event -> {
                        if (!GamePlayController.getInstance().isIsPaused()) {
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
                    });
            pauseloop.getKeyFrames().add(keyFramePause);
            pauseloop.play();

        }
    }
}

package GameEngine;

import dataHandler.Constants;
import dataHandler.Player;
import dataHandler.Sprite;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;
import music.MusicPlayer;

import java.util.ArrayList;

/**
 * Created by Todor Popov using Lenovo on 2.3.2017 г. at 4:51.
 */
public class PauseHandler {
    private Timeline gameLoop;
    private GraphicsContext gc;
    private Image background;
    private double y;
    private Player player;
    private ArrayList<Sprite> testObstacles;
    private ArrayList<Sprite> collectibles;

    public PauseHandler(Timeline gameLoop, GraphicsContext gc, Image background,
                        double y, Player player, ArrayList<Sprite> testObstacles, ArrayList<Sprite> collectibles) {
        this.gameLoop = gameLoop;
        this.gc = gc;
        this.background = background;
        this.y = y;
        this.player = player;
        this.testObstacles = testObstacles;
        this.collectibles = collectibles;
    }

    public void activatePause() {
        RunTrack.setIsPaused(true);
        this.gameLoop.pause();
        if (RunTrack.isIsPaused()) {
            MusicPlayer.pause();

            Timeline pauseloop = new Timeline();
            pauseloop.setCycleCount(Timeline.INDEFINITE);

            KeyFrame keyFramePause = new KeyFrame(
                    Duration.seconds(Constants.FRAMES_PER_SECOND),
                    event -> {
                        if (!RunTrack.isIsPaused()) {
                            gameLoop.play();
                            MusicPlayer.pause();
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

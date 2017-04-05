package music;

import constants.CarConstants;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class MusicPlayer {

    private static volatile MusicPlayer instance = null;
    private static Duration resumeTime;
    private static MediaPlayer mediaPlayer;

    private MusicPlayer() {
    }

    public static MusicPlayer getInstance() {
        if (instance == null) {
            synchronized (MusicPlayer.class) {
                if (instance == null) {
                    instance = new MusicPlayer();
                }
            }
        }
        return instance;
    }

    public void play() {
        Media media = new Media(new File(CarConstants.SONG_PATH).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public void startStopPause() {
        if (mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED)) {
            mediaPlayer.setStartTime(resumeTime);
            mediaPlayer.play();
        } else {
            mediaPlayer.pause();
            resumeTime = mediaPlayer.getCurrentTime();
        }
    }

    public void stop() {
        mediaPlayer.stop();
    }

}

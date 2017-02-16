package music;

import dataHandler.Constants;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class MusicPlayer {

    private static MediaPlayer mediaPlayer;
    private static Duration resumeTime;

    public static void PlayMusic() {
        Media media = new Media(new File(Constants.SONG_PATH).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public static void Pause() {
        if (mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED)) {
            mediaPlayer.setStartTime(resumeTime);
            mediaPlayer.play();
        } else {
            mediaPlayer.pause();
            resumeTime = mediaPlayer.getCurrentTime();
        }
    }
    public static void StopMusic() {
        mediaPlayer.stop();
    }

}

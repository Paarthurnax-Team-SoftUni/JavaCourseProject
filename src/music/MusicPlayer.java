package music;

import dataHandler.Constants;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class MusicPlayer {

    private static MediaPlayer mediaPlayer;
    private static Duration resumeTime;

    public static void play() {
        System.out.println("play");
        Media media = new Media(new File(Constants.SONG_PATH).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public static void pause() {
        System.out.println("pause");
        if (mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED)||
                mediaPlayer.getStatus().equals(MediaPlayer.Status.READY)) {
            mediaPlayer.setStartTime(resumeTime);
            mediaPlayer.play();
        } else {
            mediaPlayer.pause();
            resumeTime = mediaPlayer.getCurrentTime();
        }
    }

    public static void stop() {
        mediaPlayer.stop();
    }

}

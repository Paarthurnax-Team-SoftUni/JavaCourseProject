package Music;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class MusicPlayer {

    private static String projectPath = System.getProperty("user.dir") + "/src/resources/";
    private static MediaPlayer mediaPlayer;
    private static Duration resumeTime;

    public static void PlayMusic() {
        String path = projectPath + "music.wav";
        Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public static void Pause() {
        if (mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED)) {
            System.out.println("paused");
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

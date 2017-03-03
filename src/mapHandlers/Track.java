package mapHandlers;

import GameEngine.RunTrack;
import dataHandler.Constants;
import dataHandler.PlayerData;
import javafx.scene.image.Image;

import java.io.IOException;

public class Track {

    private static final Image backgroundLevel1 = new Image(Constants.TRACK_BACKGROUND_PATH);
    private static RunTrack runTrack = new RunTrack(PlayerData.getInstance().getCurrentPlayer(),Constants.START_GAME_VELOCITY);

    public Track(int level) throws IOException {
        initializeLevel(level);
    }

    public static void initializeLevel(int level) throws IOException {
        switch (level) {
            case 1: {
                createBackground();
                break;
            }
        }
    }

    private static void createBackground() {
            runTrack.RunTrack(backgroundLevel1);
        //  GamePlayController.getInstance().RunTrack(backgroundLevel1);
    }

    public static RunTrack getRunTrack() {
        return runTrack;
    }
}

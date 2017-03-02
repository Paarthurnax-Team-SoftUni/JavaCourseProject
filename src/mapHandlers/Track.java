package mapHandlers;

import GameEngine.GamePlayController;
import dataHandler.Constants;
import javafx.scene.image.Image;

import java.io.IOException;

public class Track {

    private static final Image backgroundLevel1 = new Image(Constants.TRACK_BACKGROUND_PATH);
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
        GamePlayController.getInstance().RunTrack(backgroundLevel1);
    }
}

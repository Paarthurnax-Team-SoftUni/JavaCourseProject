package MapHandlers;

import DataHandler.Constants;
import GameLogic.Game;
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
            }
        }
    }

    private static void createBackground() {
       Game.RunTrack(backgroundLevel1);
    }
}

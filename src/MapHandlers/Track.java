package MapHandlers;

import Controllers.GamePlayController;
import GameLogic.Game;
import javafx.scene.image.Image;
import resources.MyImage;

import java.io.IOException;
import java.util.ArrayList;

public class Track {

    private static final Image backgroundLevel1 = new Image("/resources/images/background2.jpg");
    private ArrayList<MyImage> images = new ArrayList<>();
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

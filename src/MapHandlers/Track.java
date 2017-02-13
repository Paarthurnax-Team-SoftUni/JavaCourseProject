package MapHandlers;

import Controllers.GamePlayController;
import GameLogic.Game;
import javafx.scene.image.Image;
import resources.MyImage;

import java.io.IOException;
import java.util.ArrayList;

public class Track {

    private static final Image backgroundLevel1 = new Image("/resources/images/background2.jpg");
    //private ArrayList<MyImage> images = new ArrayList<>();
    private static int velocity = 5;

    public Track(int level) throws IOException {
        initializeLevel(level);
    }

    public static void initializeLevel(int level) throws IOException {
        switch (level) {
            case 1: {
                createBackground(velocity);
            }
        }
    }

    private static void createBackground(int velocity) throws IOException {
       Game.RunTrack(backgroundLevel1, velocity);
    }
}

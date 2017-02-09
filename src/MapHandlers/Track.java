package MapHandlers;

import GameLogic.Game;
import javafx.scene.image.Image;
import resources.MyImage;

import java.util.ArrayList;

public class Track {
    private static final Image backgroundLevel1 = new Image("/resources/images/background2.jpg");
    private ArrayList<MyImage> images = new ArrayList<>();

    public Track(int level) {
        initializeLevel(level);
    }

    public static void initializeLevel(int level) {
        switch (level) {
            case 1: {
                createBackground(5);
            }
        }
    }

    private static void createBackground(int velocity) {
        Game.RunTrack(backgroundLevel1, velocity);
    }



}

package MapHandlers;

import GameLogic.Game;
import javafx.scene.image.Image;
import resources.MyImage;

import java.util.ArrayList;

public class Track {
    private static Image background;
    private ArrayList<MyImage> images = new ArrayList<>();

    public Track(int level) {
        initializeLevel(level);
    }

    private void initializeLevel(int level) {
        switch (level) {
            case 1: {
                createBackground();
            }
        }
    }

    public static void createBackground() {
        background = new Image("/resources/images/background2.jpg");
        Game.RunTrack(background);
    }



}

package MapHandlers;

import resources.MyImage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.Main;

import java.util.ArrayList;

public class Track {
    private static ImageView background;
    private ArrayList<MyImage> images = new ArrayList<>();
    private double backgroundImageStartingX;
    private double backgroundImageStartingY;
    private double squareSize;
    private int squaresLength = 20;

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
        background = new ImageView(new Image("/resources/images/background.jpg"));
        Main.windowPane.getChildren().add(background);
        background.toBack();
    }

    public void setBackgroundImageStartingX(double backgroundImageStartingX) {
        this.backgroundImageStartingX = backgroundImageStartingX;
    }

    public void setBackgroundImageStartingY(double backgroundImageStartingY) {
        this.backgroundImageStartingY = backgroundImageStartingY;
    }

    public double getBackgroundImageStartingX() {
        return backgroundImageStartingX;
    }

    public double getBackgroundImageStartingY() {
        return backgroundImageStartingY;
    }

    private double differenceBetweenBackgroundAndWindow(){
        return background.getBoundsInParent().getHeight() - Main.windowPane.getPrefHeight();
    }

}

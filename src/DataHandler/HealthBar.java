package DataHandler;


import Controllers.GamePlayController;
import javafx.scene.image.Image;

import java.util.Observable;


public class HealthBar extends Observable {

    private Image image;
    private double positionX;
    private double positionY;
    private Player player;


    public HealthBar(Player p) {
        this.positionX = 0;
        this.positionY = 0;
        this.player = p;
    }

    public void setImage(String filename) {
        Image i = new Image(filename);
        setImage(i);
    }

    public void setImage(Image i) {
        image = i;

    }

    public void setPosition(double x, double y) {
        positionX = x;
        positionY = y;
    }

    public void render() {
        this.update();

    }

    private void update() {
        int healthPoints = this.player.getHealthPoints();
        GamePlayController.printHealthBar(healthPoints);
    }
}


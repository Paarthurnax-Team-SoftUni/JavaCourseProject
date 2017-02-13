package DataHandler;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Observable;


public class CurrentHealth extends Observable {

    private Image image;
    private double positionX;
    private double positionY;
    private Player player;


    public CurrentHealth(Player p) {
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

    public void render(GraphicsContext gc) {
        this.update();
        gc.drawImage(image, positionX, positionY);
    }

    private void update() {
        if (this.player.getHealthPoints() >= 75 && this.player.getHealthPoints() <= 100) {
            this.setImage("/resources/images/health-100.png");
        }
            this.setImage("/resources/images/health-75.png");
        }
        }
        }

    }
}


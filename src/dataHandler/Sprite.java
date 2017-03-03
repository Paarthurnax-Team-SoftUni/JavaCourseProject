package dataHandler;

import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;

public class Sprite {
    private Image image;
    private String name;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;
    private boolean isDestroyed;

    public Sprite() {
        positionX = 0;
        positionY = 0;
        velocityX = 0;
        velocityY = 0;
        isDestroyed = false;
    }

    public void setName(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public void setImage(String filename) {
        Image i = new Image(filename);
        setImage(i);
    }

    public void setImage(Image i) {
        image = i;
        width = i.getWidth();
        height = i.getHeight();
    }


    public void setPosition(double x, double y) {
        positionX = x;
        positionY = y;
    }

    public void setVelocity(double x, double y) {
        velocityX = x;
        velocityY = y;
    }

    public void addVelocity(double x, double y) {
        if (x < 0) {
            if (positionX > 50) {
                velocityX += x;
            }
        } else if (x > 0) {
            if (positionX < 350) {
                velocityX += x;
            }
        }
        if(y < 0) {
            if(positionY > 300) {
                velocityY += y;
            }
        } else if (y > 0) {
            if (positionY < Constants.CANVAS_HEIGHT-this.height) {
                velocityY += y;
            }
        }
    }

    public void update() {
        positionX += velocityX;
        positionY += velocityY;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, positionX, positionY);
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }

    public boolean intersects(Sprite s) {
        return s.getBoundary().intersects(this.getBoundary());
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.setImage(Constants.FLAME_PATH);
        this.setVelocity(0, 0);
        isDestroyed = destroyed;
    }
}
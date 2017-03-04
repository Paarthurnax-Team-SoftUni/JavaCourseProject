package models;

import dataHandler.Constants;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Sprite {
    private Image image;
    private String name;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;
    protected boolean isDestroyed;

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public  Sprite() {

        positionX = 0;
        positionY = 0;
        velocityX = 0;
        velocityY = 0;
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

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image i) {
        this.image = i;
        this.width = i.getWidth();
        this.height = i.getHeight();
    }

    public double getPositionX() {
        return this.positionX;
    }

    public double getPositionY() {
        return this.positionY;
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
            if (positionX < 400) {
                velocityX += x;
            }
        }
        if(y < 0) {
            if(positionY > 300) {
                velocityY += y;
            }
        } else if (y > 0) {
            if (positionY < Constants.CANVAS_HEIGHT-this.height*2) {
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
        Rectangle2D rectangle2D = new Rectangle2D(positionX, positionY, width, height);
        return rectangle2D;
    }

    public boolean intersects(Sprite s) {
        return s.getBoundary().intersects(this.getBoundary());
    }


}
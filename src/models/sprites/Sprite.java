package models.sprites;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Sprite {

    private String name;
    private Image image;
    private double imageWidth;
    private double imageHeight;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private boolean isDestroyed;

    protected Sprite() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(String filename) {
        Image image = new Image(filename);
        this.image = image;
        this.imageWidth = image.getWidth();
        this.imageHeight = image.getHeight();
    }

    public double getImageHeight() {
        return this.imageHeight;
    }

    public double getImageWidth() {
        return this.imageWidth;
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

    public double getVelocityX() {
        return this.velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return this.velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public void setVelocity(double x, double y) {
        velocityX = x;
        velocityY = y;
    }

    public void update() {
        this.setPosition(this.getPositionX() + this.getVelocityX(), this.getPositionY() + this.getVelocityY());
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(this.getImage(), this.getPositionX(), this.getPositionY());
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(this.positionX, this.positionY, this.getImageWidth(), this.getImageHeight());
    }

    public boolean intersects(Sprite other) {
        return this.getBoundary().intersects(other.getBoundary());
    }

    public boolean isDestroyed() {
        return this.isDestroyed;
    }

    public void setDestroyed(boolean isDestroyed) {
        this.isDestroyed = isDestroyed;
    }

    public void removeWind() {
        this.setVelocity(0, 0);
    }
}

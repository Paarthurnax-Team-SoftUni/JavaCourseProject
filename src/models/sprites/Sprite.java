package models.sprites;

import gameEngine.RotatedImageInCanvas;
import gameEngine.RunTrack;
import constants.CarConstants;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Sprite {

    private String name;
    private Image image;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private double imageWidth;
    private double imageHeight;
    private boolean isDestroyed;
    private boolean turnRight;
    private boolean turnLeft;
    private double angle;
    private int minLeftSide;
    private int maxRightSide;

    protected Sprite() {}

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
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

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public void setVelocity(double x, double y) {
        velocityX = x;
        velocityY = y;
    }

    public double getImageHeight() {
        return this.imageHeight;
    }

    public boolean isDestroyed() {
        return this.isDestroyed;
    }

    public void setDestroyed(boolean isDestroyed) {
        this.isDestroyed = isDestroyed;
    }

    public double getAngle() {
        return this.angle;
    }

    public void setAngle(double angle) {
        if (angle < 43 && angle > -43) {
            this.angle = angle;
        }
    }

    private boolean getTurnRight() {
        return this.turnRight;
    }

    public void setTurnRight(boolean b) {
        this.turnRight = b;
    }

    private boolean getTurnLeft() {
        return this.turnLeft;
    }

    public void setTurnLeft(boolean b) {
        this.turnLeft = b;
    }

    public void addVelocity(double x, double y) {
        this.addVelocity(x, y, this.minLeftSide, this.maxRightSide);
    }

    public void update() {
        updateAngle();
        this.addVelocity(Math.tan(Math.toRadians(this.getAngle())) * RunTrack.getVelocity() / 3, 0);
        this.positionX += this.velocityX;
        this.positionY += this.velocityY;
    }

    public void update(int min, int max) {
        updateAngle();
        this.addVelocity(Math.tan(Math.toRadians(this.getAngle())) * RunTrack.getVelocity() / 3, 0, min, max);
        positionX += velocityX;
        positionY += velocityY;
    }

    public void removeWind(){
        this.setVelocity(0, 0);
        this.setAngle(0);
        this.setTurnRight(false);
        this.setTurnLeft(false);
    }

    public void render(GraphicsContext gc) {
        RotatedImageInCanvas.drawRotatedImage(gc, this.getImage(), this.getAngle(), this.getPositionX(), this.getPositionY());
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(this.positionX, this.positionY, this.imageWidth, this.imageHeight);
    }

    public boolean intersects(Sprite s) {
        return s.getBoundary().intersects(this.getBoundary());
    }

    private void addVelocity(double x, double y, int min, int max) {
        this.minLeftSide = min;
        this.maxRightSide = max;

        if (x < 0) {
            if (this.positionX > min) {
                this.velocityX += x;
            }
        } else if (x > 0) {
            if (this.positionX < max) {
                this.velocityX += x;
            }
        }
        if (y < 0) {
            if (this.positionY > 300) {
                this.velocityY += y;
            }
        } else if (y > 0) {
            if (this.positionY < CarConstants.CANVAS_HEIGHT - this.imageHeight * 2) {
                this.velocityY += y;
            }
        }
    }

    private void updateAngle() {
        if (this.getTurnLeft()) {
            this.setAngle(this.getAngle() - 4);
        }
        if (this.getTurnRight()) {
            this.setAngle(this.getAngle() + 4);
        }
    }
}
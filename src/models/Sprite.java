package models;

import gameEngine.RotatedImageInCanvas;
import gameEngine.RunTrack;
import dataHandler.Constants;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Sprite {
    private Image image;
    private String name;
    protected double positionX;
    protected double positionY;
    protected double velocityX;
    protected double velocityY;
    private double width;
    protected double height;
    protected boolean isDestroyed;
    private boolean turnRight;
    private boolean turnLeft;
    private double angle;

    public double getAngle() {
        return this.angle;
    }

    public void setAngle(double angle) {
        if (angle < 43 && angle > -43) {
            this.angle = angle;
        }
    }

    public boolean getTurnRight() {
        return this.turnRight;
    }

    public void setTurnRight(boolean b) {
        this.turnRight = b;

    }

    public boolean getTurnLeft() {
        return this.turnLeft;
    }

    public void setTurnLeft(boolean b) {
        this.turnLeft = b;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public Sprite() {
        positionX = 0;
        positionY = 0;
        velocityX = 0;
        velocityY = 0;
        this.angle = 0;
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
        if (y < 0) {
            if (positionY > 300) {
                velocityY += y;
            }
        } else if (y > 0) {
            if (positionY < Constants.CANVAS_HEIGHT - this.height * 2) {
                velocityY += y;
            }
        }
    }

    public void update() {
        if (this.getTurnLeft()) {
            setAngle(getAngle() - 4);
        }
        if (this.getTurnRight()) {
            setAngle(getAngle() + 4);
        }
        this.addVelocity(Math.tan(Math.toRadians(this.getAngle())) * RunTrack.getVelocity() / 3, 0);
        positionX += velocityX;
        positionY += velocityY;
    }

    public void removeWind(){
        this.velocityX=0;
        this.velocityY=0;
        this.setAngle(0);
        this.setTurnRight(false);
        this.setTurnLeft(false);
    }

    public void render(GraphicsContext gc) {
        RotatedImageInCanvas.drawRotatedImage(gc, this.getImage(), this.getAngle(), this.getPositionX(), this.getPositionY());
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }

    public boolean intersects(Sprite s) {
        return s.getBoundary().intersects(this.getBoundary());
    }
}
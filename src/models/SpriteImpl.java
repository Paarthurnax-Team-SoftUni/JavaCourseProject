package models;

import constants.CarConstants;
import gameEngine.RotatedImageInCanvas;
import gameEngine.RunTrack;
import interfaces.Sprite;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class SpriteImpl implements Sprite {

    private Image image;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private double width;
    private double height;
    private boolean isDestroyed;
    private boolean turnRight;
    private boolean turnLeft;
    private double angle;
    private int minLeftSide;
    private int maxRightSide;

    protected SpriteImpl() {
        this.positionX = 0;
        this.positionY = 0;
        this.velocityX = 0;
        this.velocityY = 0;
        this.angle = 0;
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

    public double getHeight() {
        return this.height;
    }

    public void updateImage(String filename) {
        Image i = new Image(filename);
        setImage(i);
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public final boolean isDestroyed() {
        return this.isDestroyed;
    }

    public final Image getImage() {
        return this.image;
    }

    public void setImage(Image i) {
        this.image = i;
        this.width = i.getWidth();
        this.height = i.getHeight();
    }

    public final double getPositionX() {
        return this.positionX;
    }

    public final double getPositionY() {
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
            if (positionX > minLeftSide) {
                velocityX += x;
            }
        } else if (x > 0) {
            if (positionX < maxRightSide) {
                velocityX += x;
            }
        }
        if (y < 0) {
            if (positionY > 300) {
                velocityY += y;
            }
        } else if (y > 0) {
            if (positionY < CarConstants.CANVAS_HEIGHT - this.height * 2) {
                velocityY += y;
            }
        }
    }

    public void removeWind(){
        this.velocityX=0;
        this.velocityY=0;
        this.setAngle(0);
        this.setTurnRight(false);
        this.setTurnLeft(false);
    }

    @Override
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

    @Override
    public void update(int min, int max) {
        if (this.getTurnLeft()) {
            setAngle(getAngle() - 4);
        }
        if (this.getTurnRight()) {
            setAngle(getAngle() + 4);
        }
        this.addVelocity(Math.tan(Math.toRadians(this.getAngle())) * RunTrack.getVelocity() / 3, 0, min, max);
        positionX += velocityX;
        positionY += velocityY;
    }

    @Override
    public void render(GraphicsContext gc) {
        RotatedImageInCanvas.drawRotatedImage(gc, this.getImage(), this.getAngle(), this.getPositionX(), this.getPositionY());
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(positionX, positionY, width, height);
    }

    public boolean intersects(SpriteImpl s) {
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
            if (this.positionY < CarConstants.CANVAS_HEIGHT - this.height * 2) {
                this.velocityY += y;
            }
        }
    }

}
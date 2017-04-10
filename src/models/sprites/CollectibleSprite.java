package models.sprites;

import constants.ErrorsConstants;
import constants.GameplayConstants;
import constants.GeneralConstants;
import gameEngine.RotatedImageInCanvas;
import gameEngine.RunTrack;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class CollectibleSprite {

    private String name;
    private Image image;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;
    private double imageWidth;
    private double imageHeight;
    private double angle;
    private int minLeftSide;
    private int maxRightSide;
    private boolean turnRight;
    private boolean turnLeft;

    protected CollectibleSprite() {
    }

    public void setTurnLeft(boolean isTurning) {
        this.turnLeft = isTurning;
    }

    public boolean intersects(CollectibleSprite other) {
        return this.getBoundary().intersects(other.getBoundary());
    }

    public void render(GraphicsContext gc) {
        RotatedImageInCanvas.drawRotatedImage(gc, this.getImage(), this.getAngle(), this.getPositionX(), this.getPositionY());
    }

    public void setVelocity(double x, double y) {
        this.setVelocityX(x);
        this.setVelocityY(y);
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(this.positionX, this.positionY, this.imageWidth, this.imageHeight);
    }

    public double getAngle() {
        return this.angle;
    }

    public void setAngle(double angle) {
        if (angle < GameplayConstants.TURNING_ANGLE && angle > -GameplayConstants.TURNING_ANGLE) {
            this.angle = angle;
        }
    }

    public void update() {
        updateAngle();
        this.addVelocity(Math.tan(Math.toRadians(this.getAngle())) * RunTrack.getVelocity() / GameplayConstants.UPDATE_DELIMITER, 0);
        this.positionX += this.velocityX;
        this.positionY += this.velocityY;
    }

    public void update(int min, int max) {
        updateAngle();
        this.addVelocity(Math.tan(Math.toRadians(this.getAngle())) * RunTrack.getVelocity() / GameplayConstants.UPDATE_DELIMITER, 0, min, max);
        this.positionX += this.velocityX;
        this.positionY += this.velocityY;
    }

    public void addVelocity(double x, double y) {
        this.addVelocity(x, y, this.minLeftSide, this.maxRightSide);
    }

    public void updateName(String name) {
        this.setName(name);
    }



    public final String getName() {
        return this.name;
    }

    public final Image getImage() {
        return this.image;
    }

    public void setImage(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException(ErrorsConstants.FILE_PATH_EXCEPTION);
        }
        Image image = new Image(filename);
        this.image = image;
        this.imageWidth = image.getWidth();
        this.imageHeight = image.getHeight();
    }

    public void updatePosition(double x, double y) {
        this.setPosition(x, y);
    }

    public final double getImageHeight() {
        return this.imageHeight;
    }

    public void updateVelocityX(double velocityX) {
        this.setVelocityX(velocityX);
    }


    public void updateVelocityY(double velocityY) {
        this.setVelocityY(velocityY);
    }

    public void setTurnRight(boolean isTurning) {
        this.turnRight = isTurning;
    }

    protected final double getPositionX() {
        return this.positionX;
    }

    protected final double getPositionY() {
        return this.positionY;
    }

    private void setPosition(double x, double y) {
        if (x < 0 || y < GameplayConstants.OBSTACLE_ANIMATION_Y_OFFSET) {
            throw new IllegalArgumentException(ErrorsConstants.POSITION_EXCEPTION);
        }
        this.positionX = x;
        this.positionY = y;
    }

    private void setVelocityY(double velocityY) {
        if (velocityY < -5) {
            throw new IllegalArgumentException(ErrorsConstants.VELOCITY_EXCEPTION);
        }
        this.velocityY = velocityY;
    }

    private void setVelocityX(double velocityX) {
        if (velocityX < GameplayConstants.X_TOP_RENDER_SPEED_DRUNK_DRIVERS) {
            throw new IllegalArgumentException(ErrorsConstants.VELOCITY_EXCEPTION);
        }
        this.velocityX = velocityX;
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
        if (y < GameplayConstants.CANVAS_BEGINNING) {
            if (this.positionY > GameplayConstants.CANVAS_Y_END) {
                this.velocityY += y;
            }
        } else if (y > GameplayConstants.CANVAS_BEGINNING) {
            if (this.positionY < GeneralConstants.CANVAS_HEIGHT - this.imageHeight * GameplayConstants.IMAGE_HEIGHT_OFFSET) {
                this.velocityY += y;
            }
        }
    }

    private void updateAngle() {
        if (this.getTurnLeft()) {
            this.setAngle(this.getAngle() - GameplayConstants.TURN_UPDATE_DEGREES);
        }
        if (this.getTurnRight()) {
            this.setAngle(this.getAngle() + GameplayConstants.TURN_UPDATE_DEGREES);
        }
    }

    private boolean getTurnRight() {
        return this.turnRight;
    }

    private boolean getTurnLeft() {
        return this.turnLeft;
    }

    private void setName(String name) {
        if(name == null) {
            throw new IllegalArgumentException(ErrorsConstants.NAME_EXCEPTION);
        }
        this.name = name;
    }
}


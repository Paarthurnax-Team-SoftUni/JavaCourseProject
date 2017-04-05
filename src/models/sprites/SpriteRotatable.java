package models.sprites;

import gameEngine.RunTrack;
import constants.CarConstants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

public abstract class SpriteRotatable extends Sprite {

    private double angle;
    private boolean turnRight;
    private boolean turnLeft;
    private int minLeftSide;
    private int maxRightSide;

    protected SpriteRotatable() {}

    public void addVelocity(double x, double y) {
        this.addVelocity(x, y, this.minLeftSide, this.maxRightSide);
    }

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

    @Override
    public void removeWind(){
        this.setVelocity(0, 0);
        this.setAngle(0);
        this.setTurnRight(false);
        this.setTurnLeft(false);
    }

    public void update(int min, int max) {
        this.updateAngle();
        this.addVelocity(Math.tan(Math.toRadians(this.getAngle())) * RunTrack.getVelocity() / 3, 0, min, max);
        this.setPosition(this.getPositionX() + this.getVelocityX(), this.getPositionY() + this.getVelocityY());
    }

    @Override
    public void update() {
        this.updateAngle();
        this.addVelocity(Math.tan(Math.toRadians(this.getAngle())) * RunTrack.getVelocity() / 3, 0);
        this.setPosition(this.getPositionX() + this.getVelocityX(), this.getPositionY() + this.getVelocityY());
    }

    @Override
    public void render(GraphicsContext gc) {
        this.drawRotatedImage(gc, this.getImage(), this.getAngle(), this.getPositionX(), this.getPositionY());
    }

    private void drawRotatedImage(GraphicsContext gc, Image image, double angle, double tlpx, double tlpy) {
        gc.save(); // saves the current state on stack, including the current transform
        Rotate r = new Rotate( angle, tlpx, tlpy);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());

        gc.drawImage(image, tlpx, tlpy);
        gc.restore(); // back to original state (before rotation)
    }

    private void addVelocity(double x, double y, int min, int max) {
        this.minLeftSide = min;
        this.maxRightSide = max;

        if (x < 0 && this.getPositionX() > min) {
            this.setVelocityX(this.getVelocityX() + x);
        } else if (x > 0 && this.getPositionX() < max) {
            this.setVelocityX(this.getVelocityX() + x);
        }

        if (y < 0 && this.getPositionY() > 300) {
            this.setVelocityY(this.getVelocityY() + y);
        } else if (y > 0 && this.getVelocityY() < CarConstants.CANVAS_HEIGHT - this.getImageHeight() * 2) {
            this.setVelocityY(this.getVelocityY() + y);
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
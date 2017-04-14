package models.sprites;

import constants.GameplayConstants;
import gameEngine.DrawImageInCanvas;
import gameEngine.RunTrack;
import interfaces.Rotatable;
import javafx.scene.canvas.GraphicsContext;

public abstract class DestroyableSprite extends Sprite implements Rotatable {

    private boolean isDestroyed;
    private boolean turningRight;
    private boolean turningLeft;
    private double angle;
    private boolean centerWheel;

    public boolean isCenterWheel() {
        return this.centerWheel;
    }

    protected DestroyableSprite() {
    }

    private void updateAngle() {
        if (this.getTurningLeft()) {
            this.setAngle(this.getAngle() - GameplayConstants.TURN_UPDATE_DEGREES);
        }
        if (this.getTurningRight()) {
            this.setAngle(this.getAngle() + GameplayConstants.TURN_UPDATE_DEGREES);
        }
    }

    public double getAngle() {
        return this.angle;
    }

    public void setAngle(double angle) {
        if (angle < GameplayConstants.TURNING_ANGLE && angle > -GameplayConstants.TURNING_ANGLE) {
            this.angle = angle;
        }
    }

    protected DestroyableSprite(boolean isDestroyed) {
        this.isDestroyed = isDestroyed;
    }

    public void removeWind() {
        super.setVelocity(GameplayConstants.CANVAS_BEGINNING, GameplayConstants.CANVAS_BEGINNING);
        this.setAngle(0);
        this.setTurningRight(false);
        this.setTurningLeft(false);
    }

    public boolean isDestroyed() {
        return this.isDestroyed;
    }

    public void setDestroyed(boolean isDestroyed) {
        this.isDestroyed = isDestroyed;
    }

    protected void setTurningLeft(boolean isTurning) {
        this.turningLeft = isTurning;
    }

    protected void setTurningRight(boolean isTurning) {
        this.turningRight = isTurning;
    }

    private boolean getTurningRight() {
        return this.turningRight;
    }

    private boolean getTurningLeft() {
        return this.turningLeft;
    }

    public void setCenterWheel(boolean isCentered) {
        this.centerWheel = isCentered;
    }

    @Override
    public void render(GraphicsContext gc) {
        DrawImageInCanvas.drawImage(gc, this.getImage(), this.getAngle(), this.getPositionX(), this.getPositionY());
    }

    @Override
    public void update() {
        updateAngle();
        this.addVelocity(Math.tan(Math.toRadians(this.getAngle())) * RunTrack.getVelocity() / GameplayConstants.UPDATE_DELIMITER, 0);
        super.update();
    }

    @Override
    public void addVelocity(double x, double y) {
        this.addVelocity(x, y, this.minLeftSide, this.maxRightSide);
    }


    public void updateWithVelocityAdd(int min, int max) {
        updateAngle();
        this.addVelocity(Math.tan(Math.toRadians(this.getAngle())) * RunTrack.getVelocity() / GameplayConstants.UPDATE_DELIMITER, 0, min, max);
        super.update();
    }

    @Override
    public void turnLeft() {
        this.setCenterWheel(false);
        this.setTurningLeft(true);
        // player.addVelocity(-player.getWidth() * 0.66667, 0);
    }

    @Override
    public void turnRight() {
        this.setCenterWheel(false);
        this.setTurningRight(true);
        //player.setAngle(player.getAngle() + 15);
        // player.addVelocity(player.getWidth() * 0.66667, 0);

    }

    @Override
    public void goCenter() {
        this.setTurningRight(false);
        this.setTurningLeft(false);
        this.setCenterWheel(true);
    }
}

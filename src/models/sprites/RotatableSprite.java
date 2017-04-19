package models.sprites;

import gameEngine.DrawImageInCanvas;
import gameEngine.RunTrack;
import interfaces.Rotatable;
import javafx.scene.canvas.GraphicsContext;
import utils.constants.GameplayConstants;

public abstract class RotatableSprite extends Sprite implements Rotatable {

    private double angle;
    private boolean centerWheel;
    private boolean turningRight;
    private boolean turningLeft;

    protected RotatableSprite() {
    }

    public double getAngle() {
        return this.angle;
    }

    private void setAngle(double angle) {
        this.angle = angle;
    }

    protected void updateAngle(double angle) {
        if (angle < GameplayConstants.TURNING_ANGLE && angle > -GameplayConstants.TURNING_ANGLE) {
            this.setAngle(angle);
        }
    }

    public boolean isCenterWheel() {
        return this.centerWheel;
    }

    private void setCenterWheel(boolean centerWheel) {
        this.centerWheel = centerWheel;
    }

    public void updateCenterWheel(boolean isCentered) {
        this.setCenterWheel(isCentered);
    }

    protected void setTurningLeft(boolean isTurning) {
        this.turningLeft = isTurning;
    }

    protected void setTurningRight(boolean isTurning) {
        this.turningRight = isTurning;
    }

    public void removeWind() {
        super.setVelocity(GameplayConstants.CANVAS_BEGINNING, GameplayConstants.CANVAS_BEGINNING);
        this.updateAngle(0);
        this.setTurningRight(false);
        this.setTurningLeft(false);
    }

    public void updateWithVelocityAdd(int min, int max) {
        updateAngle();
        this.addVelocity(Math.tan(Math.toRadians(this.getAngle())) * RunTrack.getVelocity() / GameplayConstants.UPDATE_DELIMITER, 0, min, max);
        super.update();
    }

    @Override
    public void update() {
        updateAngle();
        this.addVelocity(Math.tan(Math.toRadians(this.getAngle())) * RunTrack.getVelocity() / GameplayConstants.UPDATE_DELIMITER, 0);
        super.update();
    }

    @Override
    public void addVelocity(double x, double y) {
        this.addVelocity(x, y, this.getMinLeftSide(), this.getMaxRightSide());
    }

    @Override
    public void render(GraphicsContext gc) {
        DrawImageInCanvas.drawImage(gc, this.getImage(), this.getAngle(), this.getPositionX(), this.getPositionY());
    }

    @Override
    public void turnLeft() {
        this.updateCenterWheel(false);
        this.setTurningLeft(true);
        // player.addVelocity(-player.getWidth() * 0.66667, 0);
    }

    @Override
    public void turnRight() {
        this.updateCenterWheel(false);
        this.setTurningRight(true);
        //player.updateAngle(player.getAngle() + 15);
        // player.addVelocity(player.getWidth() * 0.66667, 0);

    }

    @Override
    public void goCenter() {
        this.setTurningRight(false);
        this.setTurningLeft(false);
        this.updateCenterWheel(true);
    }

    private void updateAngle() {
        if (this.turningLeft) {
            this.updateAngle(this.getAngle() - GameplayConstants.TURN_UPDATE_DEGREES);
        }
        if (this.turningRight) {
            this.updateAngle(this.getAngle() + GameplayConstants.TURN_UPDATE_DEGREES);
        }
    }
}

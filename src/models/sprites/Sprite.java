package models.sprites;

import constants.GameplayConstants;

public abstract class Sprite extends DestroyableSprite {
    private double angle;

    protected Sprite() {
    }

    public double getAngle() {
        return this.angle;
    }

    public void setAngle(double angle) {
        if (angle < GameplayConstants.TURNING_ANGLE && angle > -GameplayConstants.TURNING_ANGLE) {
            this.angle = angle;
        }
    }

}
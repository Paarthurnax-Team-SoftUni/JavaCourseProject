package models.sprites;

public abstract class Sprite extends DestroyableSprite {
    private double angle;

    protected Sprite() {
    }

    public double getAngle() {
        return this.angle;
    }

    public void setAngle(double angle) {
        if (angle < 43 && angle > -43) {
            this.angle = angle;
        }
    }

}
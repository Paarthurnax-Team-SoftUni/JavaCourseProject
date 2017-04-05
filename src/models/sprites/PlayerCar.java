package models.sprites;

import constants.CarConstants;
import gameEngine.RunTrack;

public class PlayerCar extends Sprite {
    private int ammunition;
    private boolean accelerating;
    private boolean centerWheel;
    private boolean turnRight;
    private boolean turnLeft;
    private double angle;

    public PlayerCar() {
        this.ammunition = CarConstants.START_GAME_BULLETS;
        this.angle = 0;
    }

    public void shot() {
        if (getAmmunition() > 0) {
            setAmmunition(getAmmunition() - 1);
            RunTrack.setShoot(true);
        }
    }

    public int getAmmunition() {
        return ammunition;
    }

    public void setAmmunition(Integer ammunition) {
        this.ammunition = ammunition;
    }

    public void setCenterWheel(boolean b) {
        this.centerWheel = b;
    }

    public void accelerate() {
        this.accelerating = true;
    }

    public void stopAccelerate() {
        this.accelerating = false;
    }

    @Override
    public void update() {
        if (this.centerWheel) {
            if (this.getAngle() < 0) {
                this.setAngle(this.getAngle() + 5);
            } else if (this.getAngle() > 0)
                this.setAngle(this.getAngle() - 5);
            else this.centerWheel = false;
        }
        if (accelerating) {
            this.addVelocity(0, -2);
            if (RunTrack.getVelocity() < 20) {
                RunTrack.setVelocity((float) (RunTrack.getVelocity() + 0.1));
            }
        } else {
            this.addVelocity(0, 1);
            if (RunTrack.getVelocity() > 5) {
                RunTrack.setVelocity((float) (RunTrack.getVelocity() - 0.1));
            }
        }

        super.update();
    }
}

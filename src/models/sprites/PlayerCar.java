package models.sprites;

import constants.GameplayConstants;
import gameEngine.RunTrack;
import interfaces.Shootable;

public class PlayerCar extends Sprite implements Shootable {
    private int ammunition;
    private boolean accelerating;
    private boolean centerWheel;

    public PlayerCar() {
        this.ammunition = GameplayConstants.START_GAME_BULLETS;
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
    public void shoot() {
        if (getAmmunition() > 0) {
            setAmmunition(getAmmunition() - 1);
            RunTrack.setShoot(true);
        }
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

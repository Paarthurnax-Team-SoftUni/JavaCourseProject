package models.sprites;

import constants.CarConstants;
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
        return this.ammunition;
    }

    public void setAmmunition(int ammunition) {
        this.ammunition = ammunition;
    }

    public void setCenterWheel(boolean isCentered) {
        this.centerWheel = isCentered;
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
                this.setAngle(this.getAngle() + GameplayConstants.START_GAME_VELOCITY);
            } else if (this.getAngle() > 0)
                this.setAngle(this.getAngle() - GameplayConstants.START_GAME_VELOCITY);
            else this.centerWheel = false;
        }
        if (this.accelerating) {
            this.addVelocity(0, -GameplayConstants.IMAGE_HEIGHT_OFFSET);
            if (RunTrack.getVelocity() < GameplayConstants.MAX_ACCELERATION_VELOCITY) {
                RunTrack.setVelocity((float) (RunTrack.getVelocity() + CarConstants.PLAYER_CAR_ACCELERATION_OFFSET));
            }
        } else {
            this.addVelocity(0, 1);
            if (RunTrack.getVelocity() > GameplayConstants.START_GAME_VELOCITY) {
                RunTrack.setVelocity((float) (RunTrack.getVelocity() - 0.1));
            }
        }

        super.update();
    }
}

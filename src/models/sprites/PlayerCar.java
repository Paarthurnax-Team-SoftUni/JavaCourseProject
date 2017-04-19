package models.sprites;

import utils.constants.CarConstants;
import utils.constants.GameplayConstants;
import gameEngine.RunTrack;
import interfaces.Shootable;

public class PlayerCar extends RotatableSprite implements Shootable {

    private int ammunition;
    private boolean accelerating;
    private String carId;
    private boolean isImmortal;

    public PlayerCar() {
        this.setAmmunition(GameplayConstants.START_GAME_BULLETS_NORMAL_MODE);
    }

    public String getCarId() {
        return this.carId;
    }

    public void updateCarID(String carId) {
        this.setCarId(carId);
    }

    private void setCarId(String carId) {
        this.carId = carId;
    }

    public int getAmmunition() {
        return this.ammunition;
    }

    public void updateAmmunition(int ammunition) {
        this.setAmmunition(ammunition);
    }

    public boolean isImmortal() {
        return this.isImmortal;
    }

    public void updateImmortal(boolean immortal) {
        this.setImmortal(immortal);
    }

    public void accelerate() {
        this.accelerating = true;
    }

    public void stopAccelerate() {
        this.accelerating = false;
    }

    private void setAmmunition(int ammunition) {
        this.ammunition = ammunition;
    }

    private void setImmortal(boolean immortal) {
        this.isImmortal = immortal;
    }

    @Override
    public void shoot() {
        if (this.getAmmunition() > 0) {
            RunTrack.setShoot(true);
            this.ammunition--;
        }
    }

    @Override
    public void update() {
        if (this.isCenterWheel()) {
            if (this.getAngle() < 0) {
                this.setAngle(this.getAngle() + GameplayConstants.START_GAME_VELOCITY);
            } else if (this.getAngle() > 0)
                this.setAngle(this.getAngle() - GameplayConstants.START_GAME_VELOCITY);
        }
        if (this.accelerating) {
            this.addVelocity(0, -GameplayConstants.IMAGE_HEIGHT_OFFSET);
            if (RunTrack.getVelocity() < GameplayConstants.MAX_ACCELERATION_VELOCITY) {
                RunTrack.setVelocity(RunTrack.getVelocity() + CarConstants.PLAYER_CAR_ACCELERATION_OFFSET);
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

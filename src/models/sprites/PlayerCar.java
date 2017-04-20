package models.sprites;

import dataHandler.TrackParams;
import interfaces.Shootable;
import utils.constants.CarConstants;
import utils.constants.GameplayConstants;

import java.util.Observer;

public abstract class PlayerCar extends RotatableSprite implements Shootable {

    private int ammunition;
    private boolean accelerating;
    private String carId;
    private boolean isImmortal;
    private TrackParams trackParams = TrackParams.getInstance();
    private Observer observer;
    private int bulletsBonus;
    private long immortalityBonus;
    private long doublePointsBonus;
    private int healthBonus;

    public PlayerCar(int bulletsBonus, long immortalityBonus, long doublePointsBonus, int healthBonus) {
        this.setAmmunition(GameplayConstants.START_GAME_BULLETS_NORMAL_MODE);
        this.observer=(o, arg) -> {
        };
        this.bulletsBonus = bulletsBonus;
        this.immortalityBonus = immortalityBonus;
        this.doublePointsBonus = doublePointsBonus;
        this.healthBonus = healthBonus;
    }

    public String getCarId() {
        return this.carId;
    }

    public int getBulletsBonus() {
        return this.bulletsBonus;
    }

    public long getImmortalityBonus() {
        return this.immortalityBonus;
    }

    public long getDoublePointsBonus() {
        return this.doublePointsBonus;
    }

    public int getHealthBonus() {
        return this.healthBonus;
    }

    private void setCarId(String carId) {
        this.carId = carId;
    }

    public void updateCarID(String carId) {
        this.setCarId(carId);
    }

    public int getAmmunition() {
        return this.ammunition;
    }

    private void setAmmunition(int ammunition) {
        this.ammunition = ammunition;
    }

    public void updateAmmunition(int ammunition) {
        this.setAmmunition(ammunition);
    }

    public boolean isImmortal() {
        return this.isImmortal;
    }

    private void setImmortal(boolean immortal) {
        this.isImmortal = immortal;
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

    @Override
    public void shoot() {
        if (this.getAmmunition() > 0) {
//            RunTrack.setShoot(true);
            this.trackParams.getInstance().updateShoot(true);
            this.observer.update(TrackParams.getInstance(), this.observer);
            this.ammunition--;
        }
    }

    @Override
    public void update() {
        if (this.isCenterWheel()) {
            if (this.getAngle() < 0) {
                this.updateAngle(this.getAngle() + GameplayConstants.START_GAME_VELOCITY);
            } else if (this.getAngle() > 0)
                this.updateAngle(this.getAngle() - GameplayConstants.START_GAME_VELOCITY);
        }
        if (this.accelerating) {
            this.addVelocity(0, -GameplayConstants.IMAGE_HEIGHT_OFFSET);
            if (this.trackParams.getVelocity() < GameplayConstants.MAX_ACCELERATION_VELOCITY) {
                this.trackParams.updateVelocity(this.trackParams.getVelocity() + CarConstants.PLAYER_CAR_ACCELERATION_OFFSET);
            }
        } else {
            this.addVelocity(0, 1);
            if (  this.trackParams.getVelocity() > GameplayConstants.START_GAME_VELOCITY) {
                this.trackParams.updateVelocity((float) (  this.trackParams.getVelocity() - 0.1));
            }
        }
        this.observer.update(TrackParams.getInstance(), this.observer);
        super.update();
    }
}

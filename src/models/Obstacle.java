package models;

import dataHandler.Constants;
import models.interfaces.ObstacleInterface;

import java.util.Random;

public class Obstacle extends Sprite implements ObstacleInterface {
    private boolean isDestroyed;
    private boolean isDrunk;


    public Obstacle() {
        setDestroyed(false);
        setIsDrunk(false);
    }

    public boolean getIsDrunk() {
        return this.isDrunk;
    }

    public void setIsDrunk(boolean b) {
        this.isDrunk = b;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.setImage(Constants.FLAME_PATH_SMALL);
        this.setVelocity(0, 0);
        isDestroyed = destroyed;
    }

    public void handleImpactByCarPlayer(){
        this.setDestroyed(true);
        this.setIsDrunk(false);
        this.setTurnLeft(false);
        this.setTurnRight(false);

    }

    public String getObstacleType() {
        return this.getName().substring(0, this.getName().length() - 1);
    }

}

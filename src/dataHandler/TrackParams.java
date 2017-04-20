package dataHandler;


import models.Cheat;
import java.util.Observable;

public class TrackParams extends Observable {
    private static volatile TrackParams instance = null;
    private boolean isPaused;
    private float velocity;
    private boolean shoot;
    private CurrentStats currentStats;
    private Cheat cheat;

    private TrackParams() {
    }

    public static TrackParams getInstance() {
        if (instance == null) {
            synchronized (TrackParams.class) {
                if (instance == null) {
                    instance = new TrackParams();
                }
            }
        }
        return instance;
    }


    public boolean isPaused() {
        return this.isPaused;
    }

    public void updatePaused(boolean paused) {
        this.setPaused(paused);
    }

    private void setPaused(boolean paused) {
        this.isPaused = paused;
    }

    public float getVelocity() {
        return this.velocity;
    }

    public void updateVelocity(float velocity) {
        this.setVelocity(velocity);
    }

    private void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public boolean isShoot() {
        return this.shoot;
    }

    public void updateShoot(boolean shoot) {
        this.setShoot(shoot);
    }

    private void setShoot(boolean shoot) {
        this.shoot = shoot;
    }

    public CurrentStats getCurrentStats() {
        return this.currentStats;
    }

    public void updateCurrentStats(CurrentStats currentStats) {
        this.setCurrentStats(currentStats);
    }

    private void setCurrentStats(CurrentStats currentStats) {
        this.currentStats = currentStats;
    }

    public Cheat getCheat() {
        return this.cheat;
    }

    public void updateCheat(Cheat cheat) {
        this.setCheat(cheat);
    }

    private void setCheat(Cheat cheat) {
        this.cheat = cheat;
    }
}

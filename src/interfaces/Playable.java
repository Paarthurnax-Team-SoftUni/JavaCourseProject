package interfaces;

public interface Playable extends Sprite, Savable, Shooter, Movable, Hittable, Turnable {

    void setCenterWheel(boolean isCentered);

    void accelerate();

    void updateStatsAtEnd();

    void stopAccelerate();

    void updatePoints(long l);
}

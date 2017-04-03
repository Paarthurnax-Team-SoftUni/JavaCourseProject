package models;

public interface Playable extends Sprite, Player {
    void setCenterWheel(boolean isCentered);
    void accelerate();
    void updateStatsAtEnd();
    void stopAccelerate();

}

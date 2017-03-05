package models.interfaces;

import models.Obstacle;

public interface ObstacleInterface {
    boolean getIsDrunk();
    void setIsDrunk(boolean b);
    boolean isDestroyed();
    String getObstacleType();
}

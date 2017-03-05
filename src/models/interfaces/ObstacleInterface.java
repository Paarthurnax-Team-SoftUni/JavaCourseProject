package models.interfaces;

import dataHandler.Constants;
import models.Obstacle;

import java.util.Random;

public interface ObstacleInterface {
    boolean getIsDrunk();
    void setIsDrunk(boolean b);
    boolean isDestroyed();
    String getObstacleType();
    
    static Obstacle generateObstacle() {

        String[] obstacles = Constants.OBSTACLES_LIST_SMALL;
        String random = (obstacles[new Random().nextInt(obstacles.length)]);

        Random obstacleX = new Random();

        String sd = Constants.IMAGES_PATH + random + ".png";
        Obstacle testObstacle = new Obstacle();
        testObstacle.setImage(sd);

        testObstacle.setName(random);
        testObstacle.setPosition(50 + obstacleX.nextInt(300), -166);

        if (random.contains("car")){
            if (new Random().nextInt(100)> 60){
                testObstacle.setIsDrunk(true);
            }
        }

        return testObstacle;
    }
}

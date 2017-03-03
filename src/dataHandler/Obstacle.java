package dataHandler;

import java.util.Random;

public class Obstacle extends Sprite{

    public  static Sprite generateObstacle(){

        String[] obstacles = Constants.OBSTACLES_LIST;
        String random = (obstacles[new Random().nextInt(obstacles.length)]);

        Random obstacleX = new Random();

        String sd = Constants.IMAGES_PATH + random + ".png";
        Sprite testObstacle = new Sprite();
        testObstacle.setImage(sd);

        testObstacle.setName(random);
        testObstacle.setPosition(50 + obstacleX.nextInt(300), -166);

        return testObstacle;
    }
}

package DataHandler;

import java.util.Random;

public class Obstacle extends Sprite{

    public  static Sprite generateObstacle(){

        String[] obstacles = {"obstacle1", "obstacle2", "obstacle3", "obstacle1", "obstacle2", "obstacle3", "player_car1", "player_car2", "player_car3", "player_car4", "player_car5", "player_car6"};
        String random = (obstacles[new Random().nextInt(obstacles.length)]);

        Random obstacleX = new Random();
//        Random obstacleY = new Random();
//        Random obstaclePic = new Random();
//        long numb = System.currentTimeMillis() % 3;

        String sd = "/resources/images/" + random + ".png";
        Sprite testObstacle = new Sprite();
        testObstacle.setImage(sd);

        testObstacle.setName(random);
        testObstacle.setPosition(50 + obstacleX.nextInt(300), -166);

        return testObstacle;
    }

}

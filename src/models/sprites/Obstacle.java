package models.sprites;

import utils.constants.*;
import javafx.scene.canvas.GraphicsContext;
import models.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Obstacle extends RotatableSprite {

    private boolean isDrunk;
    private boolean isDestroyed;
    private List<Obstacle> obstacles;

    public Obstacle() {
        this.obstacles = new ArrayList<>();
    }

    public void add(Obstacle obstacle){
        this.obstacles.add(obstacle);
    }

    public List<Obstacle> getObstacles(){
        return this.obstacles;
    }

    public boolean isDestroyed() {
        return this.isDestroyed;
    }

    public Obstacle generateObstacle(int drunkDrivers, int minLeftSide, int maxRightSide) {

        String[] obstacles = CollectiblesAndObstaclesConstants.OBSTACLES_LIST_SMALL;
        String random = (obstacles[new Random().nextInt(obstacles.length)]);
        String image = ResourcesConstants.IMAGES_PATH + random + ".png";

        Random obstacleX = new Random();
        Obstacle obstacle = new Obstacle();

        if (random.contains(CarConstants.CAR_STRING) && new Random().nextInt(100) > drunkDrivers) {
            obstacle = new EnemyCar();
            obstacle.setIsDrunk(true);
            obstacle.updatePosition(obstacleX.nextInt((maxRightSide - GameplayConstants.OBSTACLES_BOUNDS) -
                    (minLeftSide + GameplayConstants.OBSTACLES_BOUNDS)) + minLeftSide + GameplayConstants
                    .OBSTACLES_BOUNDS, GameplayConstants.OBSTACLE_ANIMATION_Y_OFFSET);
        }

        obstacle.updatePosition(obstacleX.nextInt(maxRightSide - minLeftSide) + minLeftSide, GameplayConstants
                .OBSTACLE_ANIMATION_Y_OFFSET);
        obstacle.setImage(image);
        obstacle.updateName(random);

        return obstacle;
    }

    public void visualizeObstacle(GraphicsContext gc, double velocity, Player player) {
        for (Obstacle obstacle : this.obstacles) {
            String obstacleType = obstacle.getObstacleType();
            if (obstacleType.contains(ImagesShortcutConstants.PLAYER_CAR) && !obstacle.isDestroyed()) {
                obstacle.setVelocity(0, velocity / 3);

                if (obstacle.isDrunk && new Random().nextInt(100) > 90) {
                    if (new Random().nextInt(2) == 0) {
                        obstacle.setTurningLeft(true);
                        obstacle.setTurningRight(false);
                    } else {
                        obstacle.setTurningRight(true);
                        obstacle.setTurningLeft(false);
                    }
                }
            } else {
                obstacle.setVelocity(0, velocity);
            }

            obstacle.update();
            obstacle.render(gc);

            if (obstacle.intersects(player.getCar())) {
                if (player.getCar().isImmortal()) {
                    player.addPoints(GameplayConstants.BONUS_POINTS_HIT_WITH_SHIELD);
                } else if (!obstacle.isDestroyed()) {
                    player.updateHealthPoints(player.getHealthPoints() - GameplayConstants.OBSTACLE_DAMAGE);
                }
                obstacle.handleImpactByCarPlayer(velocity);
            }
        }
    }

    public void handleImpactByAmmo(Player player) {
        player.addPoints(GameplayConstants.DESTROYED_OBJECT_BONUS);
        this.setDestroyed(true);
        this.setIsDrunk(false);
        this.removeWind();
    }

    public void handleImpactByCarPlayer(double velocity) {
        this.setDestroyed(true);
        this.setIsDrunk(false);
        this.removeWind();

        if (this.getObstacleType().contains(ImagesShortcutConstants.PLAYER_CAR)) {
            this.setVelocity(0, velocity);
        }
    }

    private void setDestroyed(boolean isDestroyed) {
        this.setImage(ResourcesConstants.FLAME_PATH_SMALL);
        this.setVelocity(0, 0);
        this.isDestroyed = isDestroyed;
    }

    public void setIsDrunk(boolean isDrunk) {
        this.isDrunk = isDrunk;
    }

    private String getObstacleType() {
        return this.getName().substring(0, this.getName().length() - 1);
    }
}

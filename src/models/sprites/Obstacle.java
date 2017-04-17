package models.sprites;

import utils.constants.*;
import javafx.scene.canvas.GraphicsContext;
import models.Player;

import java.util.Random;

public class Obstacle extends RotatableSprite {

    private boolean isDrunk;
    private boolean isDestroyed;

    public Obstacle() {}

    public boolean isDestroyed() {
        return this.isDestroyed;
    }

    public void visualizeObstacle(GraphicsContext gc, double velocity, Player player) {
        String obstacleType = this.getObstacleType();

        if (obstacleType.contains(ImagesShortcutConstants.PLAYER_CAR) && !this.isDestroyed()) {
            this.setVelocity(0, velocity / 3);

            if (this.isDrunk && new Random().nextInt(100) > 90) {
                if (new Random().nextInt(2) == 0) {
                    this.setTurningLeft(true);
                    this.setTurningRight(false);
                } else {
                    this.setTurningRight(true);
                    this.setTurningLeft(false);
                }
            }
        } else {
            this.setVelocity(0, velocity);
        }

        this.update();
        this.render(gc);

        if (this.intersects(player.getCar())) {
                if (player.getCar().isImmortal()) {
                    player.addPoints(GameplayConstants.BONUS_POINTS_HIT_WITH_SHIELD);
                } else if (!this.isDestroyed()) {
                    player.updateHealthPoints(player.getHealthPoints() - GameplayConstants.OBSTACLE_DAMAGE);
                }
            this.handleImpactByCarPlayer(velocity);
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

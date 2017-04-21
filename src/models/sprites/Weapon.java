package models.sprites;

import javafx.scene.canvas.GraphicsContext;
import models.Player;
import models.sprites.Obstacles.Obstacle;
import utils.constants.GameplayConstants;
import utils.constants.ResourcesConstants;

import java.util.ArrayList;
import java.util.List;

public class Weapon extends Sprite {

    private List<Weapon> ammunitions;

    public Weapon() {
        this.ammunitions = new ArrayList<>();
    }

    public void addAmmo(Weapon weapon) {
        this.ammunitions.add(weapon);
    }

    public Weapon generateAmmo(Player player) {
        Weapon weapon = new Weapon();
        weapon.updatePosition(player.getCar().getPositionX(), player.getCar().getPositionY() + GameplayConstants.BULLET_SPEED);
        weapon.updateImage(ResourcesConstants.AMMO_PATH);
        return weapon;
    }

    public void visualizeAmmo(GraphicsContext gc, Iterable<Obstacle> obstacles, Player player) {
        for (Weapon weapon : this.ammunitions) {
            weapon.setVelocity(0, -GameplayConstants.BULLET_SPEED);
            weapon.update();
            weapon.render(gc);
            for (Obstacle obstacle : obstacles) {
                if (weapon.intersects(obstacle)) {
                    if (!obstacle.isDestroyed()) {
                        weapon.updatePosition(GameplayConstants.DESTROY_OBJECT_COORDINATES, GameplayConstants.DESTROY_OBJECT_COORDINATES);
                        obstacle.handleImpactByAmmo(player);
                    }
                }
            }
        }
    }
}

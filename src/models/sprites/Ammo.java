package models.sprites;

import utils.constants.GameplayConstants;
import utils.constants.ResourcesConstants;
import javafx.scene.canvas.GraphicsContext;
import models.Player;

import java.util.ArrayList;
import java.util.List;

public class Ammo extends CollectibleSprite {

    private List<Ammo> ammunitions;

    public Ammo() {
        this.ammunitions = new ArrayList<>();
    }

    public void addAmmo(Ammo ammo) {
        this.ammunitions.add(ammo);
    }

    public Ammo generateAmmo(Player player) {
        Ammo ammo = new Ammo();
        ammo.updatePosition(player.getCar().getPositionX(), player.getCar().getPositionY() + GameplayConstants.BULLET_SPEED);
        ammo.setImage(ResourcesConstants.AMMO_PATH);
        return ammo;
    }

    public void visualizeAmmo(GraphicsContext gc, List<Obstacle> obstacles, Player player) {
        for (Ammo ammo : this.ammunitions) {
            ammo.setVelocity(0, -GameplayConstants.BULLET_SPEED);
            ammo.update();
            ammo.render(gc);
            for (Obstacle obstacle : obstacles) {
                if (ammo.intersects(obstacle)) {
                    if (!obstacle.isDestroyed()) {
                        ammo.updatePosition(GameplayConstants.DESTROY_OBJECT_COORDINATES, GameplayConstants.DESTROY_OBJECT_COORDINATES);
                        obstacle.handleImpactByAmmo(player);
                    }
                }
            }
        }
    }
}

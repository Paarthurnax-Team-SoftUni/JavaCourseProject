package models.sprites;

import constants.*;
import javafx.scene.canvas.GraphicsContext;
import models.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ammo extends Sprite {

    private List<Ammo> ammunition;

    public Ammo() {
        this.ammunition = new ArrayList<>();
    }

    public void addAmmo(Ammo ammo) {
        this.ammunition.add(ammo);
    }

    public List<Ammo> getAmmunition() {
        return Collections.unmodifiableList(this.ammunition);
    }

    public Ammo generateAmmo(Player player) {
        Ammo ammo = new Ammo();
        ammo.setPosition(player.getCar().getPositionX(), player.getCar().getPositionY() + 5);
        ammo.setImage(ResourcesConstants.AMMO_PATH);
        return ammo;
    }

    public void visualizeAmmo(GraphicsContext gc, List<Obstacle> obstacles, List<Ammo> ammunition, Player player) {
        for (Ammo ammo : ammunition) {
            ammo.setVelocity(0, -5);
            ammo.update();
            ammo.render(gc);
            for (Obstacle obstacle : obstacles) {
                if (ammo.intersects(obstacle)) {
                    if (!obstacle.isDestroyed()) {
                        ammo.setPosition(GameplayConstants.DESTROY_OBJECT_COORDINATES, GameplayConstants.DESTROY_OBJECT_COORDINATES);
                        obstacle.handleImpactByAmmo(player);
                    }
                }
            }
        }
    }
}

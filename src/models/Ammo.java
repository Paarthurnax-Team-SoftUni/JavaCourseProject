package models;

import dataHandler.Constants;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class Ammo extends Sprite{
    private List<Ammo> ammunition;

    public Ammo(){
        this.ammunition = new ArrayList<>();
    }

    public void addAmmo(Ammo ammo){
        this.ammunition.add(ammo);
    }

    public List<Ammo> getAmmunition() {
        return ammunition;
    }

    public Ammo generateAmmo(Player player){
        Ammo ammo = new Ammo();
        ammo.setPosition(player.getPositionX() ,player.getPositionY() + 5);
        ammo.setImage(Constants.AMMO_PATH);
        return ammo;
    }

    public void visualizeAmmo(GraphicsContext gc, List<Obstacle> obstacles, List<Ammo> ammunition) {
        for (Ammo ammo : ammunition) {
            ammo.setVelocity(0,-5);
            ammo.update();
            ammo.render(gc);
            for (Obstacle obstacle : obstacles) {
                if (ammo.getBoundary().intersects(obstacle.getBoundary())) {
                    obstacle.handleImpactByAmmo();
                }
            }
        }
        //ammunition.remove(ammunition.size()-1);
    }
}

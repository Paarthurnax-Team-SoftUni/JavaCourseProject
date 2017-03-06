package models;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Angel of the Day on 6.3.2017 г..
 */

public class Ammo extends Sprite{
    private List<Ammo> ammunition;
    public Ammo(){
        this.ammunition=new ArrayList<>();
    }
    public void addAmmo(Ammo ammo){
        this.ammunition.add(ammo);
    }

    public List<Ammo> getAmmunition() {
        return ammunition;
    }
    public Ammo generateAmmo(Player player){
        String sd = "/resources/images/missile.png";
        Ammo ammo = new Ammo();
        ammo.setPosition(player.getPositionX() ,player.getPositionY()+5);
        ammo.setImage(sd);
        return ammo;
    }
    public void manageObstacles(GraphicsContext gc, List<Obstacle> obstacles,List<Ammo> ammunition) {
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

    }
}

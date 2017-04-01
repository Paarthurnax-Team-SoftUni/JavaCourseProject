package dataHandler;

import javafx.scene.image.ImageView;
import models.Player;
import constants.Constants;

import java.util.Observable;

public class HealthBar extends Observable {

    private Player player;

    private static ImageView health100;
    private static ImageView health75;
    private static ImageView health50;
    private static ImageView health25;

    public HealthBar(Player p) {
        this.player = p;
    }

    public HealthBar(ImageView health100, ImageView health75, ImageView health50, ImageView health25) {
        HealthBar.health100 = health100;
        HealthBar.health75 = health75;
        HealthBar.health50 = health50;
        HealthBar.health25 = health25;
    }

    public void update() {
        int healthPoints = this.player.getHealthPoints();
        printHealthBar(healthPoints);
    }

    private static void printHealthBar(Integer healthPoints) {

        // _healthBar.setWidth(healthPoints*1.56);  IF WE DECIDE TO SHOW IT BY PERCENTIGE

        if (healthPoints > Constants.HEALTH_BAR_AVERAGE_HIGH && healthPoints <= Constants.HEALTH_BAR_MAX) {
            health100.setVisible(true);
            health75.setVisible(false);
            health50.setVisible(false);
            health25.setVisible(false);
        }
        else if (healthPoints <=  Constants.HEALTH_BAR_AVERAGE_HIGH && healthPoints > Constants.HEALTH_BAR_AVERAGE_LOW) {
            health100.setVisible(false);
            health75.setVisible(true);
            health50.setVisible(false);
            health25.setVisible(false);
        }
        else if (healthPoints <= Constants.HEALTH_BAR_AVERAGE_LOW && healthPoints > Constants.HEALTH_BAR_MIN) {
            health100.setVisible(false);
            health75.setVisible(false);
            health50.setVisible(true);
            health25.setVisible(false);
        }
        else if (healthPoints <= Constants.HEALTH_BAR_MIN) {
            health100.setVisible(false);
            health75.setVisible(false);
            health50.setVisible(false);
            health25.setVisible(true);
        }
    }
}


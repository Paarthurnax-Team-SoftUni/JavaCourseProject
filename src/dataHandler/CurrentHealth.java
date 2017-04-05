package dataHandler;

import constants.CarConstants;
import javafx.scene.image.ImageView;
import models.Player;

import java.util.Observable;

public class CurrentHealth extends Observable {

    private static ImageView health100;
    private static ImageView health75;
    private static ImageView health50;
    private static ImageView health25;
    private Player player;

    public CurrentHealth(Player p) {
        this.player = p;
    }

    public CurrentHealth(ImageView health_100, ImageView health_75, ImageView health_50, ImageView health_25) {
        health100 = health_100;
        health75 = health_75;
        health50 = health_50;
        health25 = health_25;
    }

    private static void printHealthBar(Integer healthPoints) {

        if (healthPoints > CarConstants.HEALTH_BAR_AVERAGE_HIGH && healthPoints <= CarConstants.HEALTH_BAR_MAX) {
            health100.setVisible(true);
            health75.setVisible(false);
            health50.setVisible(false);
            health25.setVisible(false);
        } else if (healthPoints <= CarConstants.HEALTH_BAR_AVERAGE_HIGH && healthPoints > CarConstants.HEALTH_BAR_AVERAGE_LOW) {
            health100.setVisible(false);
            health75.setVisible(true);
            health50.setVisible(false);
            health25.setVisible(false);
        } else if (healthPoints <= CarConstants.HEALTH_BAR_AVERAGE_LOW && healthPoints > CarConstants.HEALTH_BAR_MIN) {
            health100.setVisible(false);
            health75.setVisible(false);
            health50.setVisible(true);
            health25.setVisible(false);
        } else if (healthPoints <= CarConstants.HEALTH_BAR_MIN) {
            health100.setVisible(false);
            health75.setVisible(false);
            health50.setVisible(false);
            health25.setVisible(true);
        }
    }

    public void update() {
        int healthPoints = this.player.getHealthPoints();
        printHealthBar(healthPoints);
    }
}


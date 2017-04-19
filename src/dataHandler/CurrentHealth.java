package dataHandler;

import interfaces.Updateable;
import javafx.scene.image.ImageView;
import models.Player;

import java.lang.reflect.Field;
import java.util.Observable;

public class CurrentHealth extends Observable implements Updateable {

    private static ImageView health4;
    private static ImageView health3;
    private static ImageView health2;
    private static ImageView health1;
    private Player player;

    public CurrentHealth(Player player) {
        this.player = player;
    }

    public CurrentHealth(ImageView health_100, ImageView health_75, ImageView health_50, ImageView health_25) {
        health4 = health_100;
        health3 = health_75;
        health2 = health_50;
        health1 = health_25;
    }

    private void printHealthBar(int healthPoints) throws NoSuchFieldException, IllegalAccessException {
        Class<CurrentHealth> currentHealthClass = CurrentHealth.class;
        for (int i = 1; i <= 4; i++) {
            Field field = currentHealthClass.getDeclaredField("health" + i);
            ((ImageView) field.get(this)).setVisible(false);
        }
        Field field = currentHealthClass.getDeclaredField("health" + healthPoints / 25);
        ((ImageView) field.get(this)).setVisible(true);
    }

    public void update() throws NoSuchFieldException, IllegalAccessException {
        int healthPoints = this.player.getHealthPoints();
        printHealthBar(healthPoints);
    }
}


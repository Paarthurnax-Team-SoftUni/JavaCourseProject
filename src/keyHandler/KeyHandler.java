package keyHandler;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import models.Player;

public abstract class KeyHandler implements EventHandler<KeyEvent> {
    private Player player;
    private int minLeftSide;
    private int maxRightSide;

    protected KeyHandler(Player player, int minLeftSide, int maxRightSide) {
        this.player = player;
        this.minLeftSide = minLeftSide;
        this.maxRightSide = maxRightSide;
    }

    protected int getMinLeftSide() {
        return this.minLeftSide;
    }

    protected int getMaxRightSide() {
        return this.maxRightSide;
    }

    protected Player getPlayer() {
        return this.player;
    }
}
package keyHandler;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import models.Player;

public abstract class KeyHandler implements EventHandler<KeyEvent> {
    protected Player player;
    protected int minLeftSide;
    protected int maxRightSide;

    protected KeyHandler(Player p, int minLeftSide, int maxRightSide) {
        this.player = p;
        this.minLeftSide = minLeftSide;
        this.maxRightSide = maxRightSide;
    }
}
package keyHandler;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import models.Player;

public class KeyHandlerOnRelease implements EventHandler<KeyEvent> {
    private Player player;
    private int minLeftSide;
    private int maxRightSide;

    public KeyHandlerOnRelease(Player p, int minLeftSide, int maxRightSide) {
        this.player = p;
        this.minLeftSide = minLeftSide;
        this.maxRightSide = maxRightSide;
    }

    @Override
    public void handle(KeyEvent e) {
        KeyCode keyCode = e.getCode();

        switch (keyCode.getName()) {
            case "Up":
                this.player.stopAccelerate();
                break;
            case "Left":
                this.player.setTurnLeft(false);
                this.player.setCenterWheel(true);
                this.player.update(minLeftSide, maxRightSide);
                break;
            case "Right":
                this.player.setTurnRight(false);
                this.player.setCenterWheel(true);
                this.player.update(minLeftSide, maxRightSide);
                break;
        }
    }
}
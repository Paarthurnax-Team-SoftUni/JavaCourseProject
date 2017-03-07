package keyHandler;


import gameEngine.RunTrack;
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
        if ((keyCode.getName().equals("Up"))) {
        }
        switch (keyCode.getName()) {
            case "Up":
                this.player.stopAccelerate();
                break;
            case "Down":
                break;
            case "Left":
                player.setTurnLeft(false);
                player.setCenterWheel(true);
                player.update(minLeftSide, maxRightSide);
                break;
            case "Right":
                player.setTurnRight(false);
                player.setCenterWheel(true);
                player.update(minLeftSide, maxRightSide);
                break;
            case "S":
               // RunTrack.setShoot(false);
                break;
        }
    }
}
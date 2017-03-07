package keyHandler;


import gameEngine.RunTrack;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import models.Player;


public class KeyHandlerOnRelease implements EventHandler<KeyEvent> {
    private Player player;

    public KeyHandlerOnRelease(Player p) {
        this.player = p;
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
                player.update();
                break;
            case "Right":
                player.setTurnRight(false);
                player.setCenterWheel(true);
                player.update();
                break;
            case "Enter":
                RunTrack.setShoot(false);
                break;
        }
    }
}
package keyHandler;

import GameEngine.RunTrack;

import dataHandler.Player;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;



public class KeyHandlerOnPress implements EventHandler<KeyEvent> {
    private Player player;

    public KeyHandlerOnPress(Player p) {
        this.player = p;
    }

    @Override
    public void handle(KeyEvent e) {
        KeyCode keyCode = e.getCode();
        if (!RunTrack.isIsPaused()) {
            switch (keyCode.getName()) {
                case "Up":
                    player.accelerate();
                    player.update();
                    break;
                case "Down":
                    if (RunTrack.getVelocity() > 5) {
                        RunTrack.setVelocity(RunTrack.getVelocity() - 1);
                    }
                    player.addVelocity(0, 2);
                    player.update();
                    break;
                case "Left":
                    player.addVelocity(-50, 0);
                    player.update();
                    break;
                case "Right":
                    player.addVelocity(50, 0);
                    player.update();
                    break;
                case "P":
                    RunTrack.setIsPaused(true);
                    break;
                default:
                    break;
            }
        } else {
            if ((keyCode.getName().equals("P"))) {
                RunTrack.setIsPaused(false);
            }
        }
    }
}

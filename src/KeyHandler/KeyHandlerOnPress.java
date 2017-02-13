package KeyHandler;

import DataHandler.Player;
import GameLogic.Game;
import javafx.event.*;
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
        if ((keyCode.getName().equals("Up"))) {
//            Game.velocity++;
            player.addVelocity(0, -20);
            player.update();
        }
        if ((keyCode.getName().equals("Down"))) {
//            Game.velocity--;
            player.addVelocity(0, 20);
            player.update();
        }
        if ((keyCode.getName().equals("P"))) {
            if (Game.isPaused) Game.isPaused = false;
            else Game.isPaused = true;
        }
        if ((keyCode.getName().equals("Left"))) {
            player.addVelocity(-50, 0);
            player.update();
        }
        if ((keyCode.getName().equals("Right"))) {
            player.addVelocity(50, 0);
            player.update();
        }
    }
}

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

    private boolean[] keys;
    public boolean up, down, left, right, pause;


    public KeyHandlerOnPress() {
        keys = new boolean[256];
    }

    public void tick() {

    }

    @Override
    public void handle(KeyEvent e) {
        KeyCode keyCode = e.getCode();
        if ((keyCode.getName().equals("Up"))) {
            Game.velocity++;
        }
        if ((keyCode.getName().equals("Down"))) {
            Game.velocity--;
        }
        if ((keyCode.getName().equals("P"))) {
            if (Game.isPaused) Game.isPaused = false;
            else Game.isPaused = true;
        }
        if ((keyCode.getName().equals("Left"))) {
            player.addVelocity(-50, 0);
            player.update();

            System.out.println(player);
        }
        if ((keyCode.getName().equals("Right"))) {
            player.addVelocity(50, 0);
            player.update();
        }
    }
}

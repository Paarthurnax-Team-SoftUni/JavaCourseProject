package KeyHandler;

import DataHandler.Player;
import GameLogic.Game;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class KeyHandlerOnRelease implements EventHandler<KeyEvent> {
    private Player player;

    public KeyHandlerOnRelease(Player p) {
        this.player = p;
    }

    @Override
    public void handle(KeyEvent e) {
        KeyCode keyCode = e.getCode();
        if ((keyCode.getName().equals("Up"))) {
            this.player.stopAccelerate();
        }
    }
}

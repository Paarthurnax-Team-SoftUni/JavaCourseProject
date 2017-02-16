package keyHandler;

import controllers.GamePlayController;
import dataHandler.Player;
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
        if (!GamePlayController.isIsPaused()) {
            KeyCode keyCode = e.getCode();
            if ((keyCode.getName().equals("Up"))) {
//            Game.velocity++;

                player.accelerate();
                player.update();
            }
            if ((keyCode.getName().equals("Down"))) {
                if (GamePlayController.getVelocity() > 5) GamePlayController.setVelocity(GamePlayController.getVelocity()-1);
                player.addVelocity(0, 2);
                player.update();
            }
            if ((keyCode.getName().equals("P"))) {
                if (GamePlayController.isIsPaused()) GamePlayController.setIsPaused(false);
                else GamePlayController.setIsPaused(true);
            }
            if ((keyCode.getName().equals("Left"))) {
                player.addVelocity(-50, 0);
                player.update();
            }
            if ((keyCode.getName().equals("Right"))) {
                player.addVelocity(50, 0);
                player.update();
            }
        }else {
            KeyCode keyCode = e.getCode();
            if ((keyCode.getName().equals("P"))) {
                if (GamePlayController.isIsPaused()) GamePlayController.setIsPaused(false);
                else GamePlayController.setIsPaused(true);
            }
        }
    }
}

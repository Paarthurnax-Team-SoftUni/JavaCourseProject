package keyHandler;

import GameEngine.GamePlayController;
import models.Player;
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
        if (!GamePlayController.getInstance().isIsPaused()) {
            switch (keyCode.getName()) {
                case "Up":
                    player.accelerate();
                    player.update();
                    break;
                case "Down":
                    if (GamePlayController.getInstance().getVelocity() > 5) {
                        GamePlayController.getInstance().setVelocity(GamePlayController.getInstance().getVelocity() - 1);
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
                    GamePlayController.getInstance().setIsPaused(true);
                    break;
                default:
                    break;
            }
        } else {
            if ((keyCode.getName().equals("P"))) {
                GamePlayController.getInstance().setIsPaused(false);
            }
        }
    }
}

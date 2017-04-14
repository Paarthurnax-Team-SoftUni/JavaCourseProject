package keyHandler;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import models.Player;
import models.sprites.PlayerCar;

public class KeyHandlerOnRelease extends KeyHandler {

    public KeyHandlerOnRelease(Player p, int minLeftSide, int maxRightSide) {
        super(p,minLeftSide,maxRightSide);
    }

    @Override
    public void handle(KeyEvent e) {
        KeyCode keyCode = e.getCode();

        PlayerCar playerCar = this.player.getCar();

        switch (keyCode.getName()) {
            case "Up":
                playerCar.stopAccelerate();
                break;
            case "Left":
            case "Right":
                playerCar.goCenter();
                playerCar.updateWithVelocityAdd(minLeftSide, maxRightSide);
                break;
        }
    }
}
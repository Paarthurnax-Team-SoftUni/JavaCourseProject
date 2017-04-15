package keyHandler;

import utils.constants.KeyHandlersConstants;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import models.Player;
import models.sprites.PlayerCar;

public class KeyHandlerOnRelease extends KeyHandler {

    public KeyHandlerOnRelease(Player player, int minLeftSide, int maxRightSide) {
        super(player, minLeftSide, maxRightSide);
    }

    @Override
    public void handle(KeyEvent e) {
        KeyCode keyCode = e.getCode();

        PlayerCar playerCar = super.getPlayer().getCar();

        switch (keyCode.getName()) {
            case KeyHandlersConstants.UP_STRING:
                playerCar.stopAccelerate();
                break;
            case KeyHandlersConstants.LEFT_STRING:
            case KeyHandlersConstants.RIGHT_STRING:
                playerCar.goCenter();
                playerCar.updateWithVelocityAdd(super.getMinLeftSide(), super.getMaxRightSide());
                break;
        }
    }
}
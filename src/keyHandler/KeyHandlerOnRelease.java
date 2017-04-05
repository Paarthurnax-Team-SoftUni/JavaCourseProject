package keyHandler;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import models.Player;
import models.sprites.PlayerCar;

public class KeyHandlerOnRelease implements EventHandler<KeyEvent> {
    private Playable player;
    private int minLeftSide;
    private int maxRightSide;

    public KeyHandlerOnRelease(Playable p, int minLeftSide, int maxRightSide) {
        this.player = p;
        this.minLeftSide = minLeftSide;
        this.maxRightSide = maxRightSide;
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
                playerCar.setTurnLeft(false);
                playerCar.setCenterWheel(true);
                playerCar.update(minLeftSide, maxRightSide);
                break;
            case "Right":
                playerCar.setTurnRight(false);
                playerCar.setCenterWheel(true);
                playerCar.update(minLeftSide, maxRightSide);
                break;
        }
    }
}
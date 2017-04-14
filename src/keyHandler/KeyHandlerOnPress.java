package keyHandler;

import gameEngine.RunTrack;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import models.Player;
import models.sprites.PlayerCar;
import music.MusicPlayer;

public class KeyHandlerOnPress extends KeyHandler {

    public KeyHandlerOnPress(Player p, int minLeftSide, int maxRightSide) {
        super(p,minLeftSide,maxRightSide);
    }

    @Override
    public void handle(KeyEvent e) {
        KeyCode keyCode = e.getCode();

        PlayerCar playerCar = this.player.getCar();

        if (!RunTrack.isPaused()) {
            RunTrack.getCheat().add(keyCode.getName());
            switch (keyCode.getName()) {
                case "Up":
                    playerCar.accelerate();
                    playerCar.update();
                    break;
                case "Down":
                    if (RunTrack.getVelocity() > 5) {
                        RunTrack.setVelocity(RunTrack.getVelocity() - 1);
                    }
                    playerCar.setCenterWheel(false);
                    //player.addVelocity(0, 2);
                    playerCar.update();
                    break;
                case "Left":
                    playerCar.turnLeft();
                    playerCar.updateWithVelocityAdd(minLeftSide, maxRightSide);
                    break;
                case "Right":
                    playerCar.turnRight();
                    playerCar.updateWithVelocityAdd(minLeftSide, maxRightSide);
                    break;
                case "P":
                    RunTrack.setIsPaused(true);
                    break;
                case "M":
                    MusicPlayer.getInstance().play();
                    break;
                case "Q":
                    Platform.exit();
                    break;
                case "S":
                    playerCar.shoot();
                    break;
                default:
                    break;
            }
        } else {
            if(keyCode.getName().equals("P")){
                RunTrack.setIsPaused(false);
            }
        }
    }
}
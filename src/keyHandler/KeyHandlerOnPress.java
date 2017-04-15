package keyHandler;

import constants.GameplayConstants;
import constants.KeyHandlersConstants;
import gameEngine.RunTrack;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import models.Player;
import models.sprites.PlayerCar;
import music.MusicPlayer;

public class KeyHandlerOnPress extends KeyHandler {

    public KeyHandlerOnPress(Player player, int minLeftSide, int maxRightSide) {
        super(player, minLeftSide, maxRightSide);
    }

    @Override
    public void handle(KeyEvent e) {
        KeyCode keyCode = e.getCode();

        PlayerCar playerCar = super.getPlayer().getCar();

        if (!RunTrack.isPaused()) {
            RunTrack.getCheat().add(keyCode.getName());
            switch (keyCode.getName()) {
                case KeyHandlersConstants.UP_STRING:
                    playerCar.accelerate();
                    playerCar.update();
                    break;
                case KeyHandlersConstants.DOWN_STRING:
                    if (RunTrack.getVelocity() > GameplayConstants.START_GAME_VELOCITY) {
                        RunTrack.setVelocity(RunTrack.getVelocity() - GameplayConstants.BRAKES_STRENGTH);
                    }
                    playerCar.setCenterWheel(false);
                    //player.addVelocity(0, 2);
                    playerCar.update();
                    break;
                case KeyHandlersConstants.LEFT_STRING:
                    playerCar.turnLeft();
                    playerCar.updateWithVelocityAdd(super.getMinLeftSide(), super.getMaxRightSide());
                    break;
                case KeyHandlersConstants.RIGHT_STRING :
                    playerCar.turnRight();
                    playerCar.updateWithVelocityAdd(super.getMinLeftSide(), super.getMaxRightSide());
                    break;
                case KeyHandlersConstants.PAUSE_STRING:
                    RunTrack.setIsPaused(true);
                    break;
                case KeyHandlersConstants.MUSIC_PLAYER_STRING:
                    MusicPlayer.getInstance().play();
                    break;
                case KeyHandlersConstants.QUIT_STRING:
                    Platform.exit();
                    break;
                case KeyHandlersConstants.SHOOT_STRING:
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
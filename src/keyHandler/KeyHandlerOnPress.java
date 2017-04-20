package keyHandler;

import dataHandler.TrackParams;
import gameEngine.RunTrack;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import models.Player;
import models.sprites.PlayerCar;
import utils.constants.GameplayConstants;
import utils.constants.KeyHandlersConstants;
import utils.music.MusicPlayer;

import java.util.Observer;

public class KeyHandlerOnPress extends KeyHandler {
    private Observer observer;
    private TrackParams trackParams=TrackParams.getInstance();

    public KeyHandlerOnPress(Player player, int minLeftSide, int maxRightSide) {
        super(player, minLeftSide, maxRightSide);
        this.observer=this.observer = (o, arg) -> {
        };
    }

    @Override
    public void handle(KeyEvent e) {
        KeyCode keyCode = e.getCode();

        PlayerCar playerCar = super.getPlayer().getCar();

        if (!this.trackParams.isPaused()) {
            this.trackParams.getCheat().add(keyCode.getName());
            switch (keyCode.getName()) {
                case KeyHandlersConstants.UP_STRING:
                    playerCar.accelerate();
                    playerCar.update();
                    break;
                case KeyHandlersConstants.DOWN_STRING:
                    if (this.trackParams.getVelocity() > GameplayConstants.START_GAME_VELOCITY) {
                        this.trackParams.updateVelocity(this.trackParams.getVelocity() - GameplayConstants.BRAKES_STRENGTH);
                    }
                    playerCar.updateCenterWheel(false);
                    //player.addVelocity(0, 2);
                    playerCar.update();
                    break;
                case KeyHandlersConstants.LEFT_STRING:
                    playerCar.turnLeft();
                    playerCar.updateWithVelocityAdd(super.getMinLeftSide(), super.getMaxRightSide());
                    break;
                case KeyHandlersConstants.RIGHT_STRING:
                    playerCar.turnRight();
                    playerCar.updateWithVelocityAdd(super.getMinLeftSide(), super.getMaxRightSide());
                    break;
                case KeyHandlersConstants.PAUSE_STRING:
                    this.trackParams.updatePaused(true);
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
            if (keyCode.getName().equals("P")) {
                this.trackParams.updatePaused(false);
            }
        }
        this.observer.update(this.trackParams,this.observer);
    }
}
package keyHandler;

import gameEngine.RunTrack;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import models.sprites.PlayerCar;
import music.MusicPlayer;

public class KeyHandlerOnPress implements EventHandler<KeyEvent> {
    //public static boolean[] pressedKeys = new boolean[256];
    private Playable player;
    private int minLeftSide;
    private int maxRightSide;

    public KeyHandlerOnPress(Playable p, int minLeftSide, int maxRightSide) {
        this.player = p;
        this.minLeftSide = minLeftSide;
        this.maxRightSide = maxRightSide;
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
                    playerCar.setCenterWheel(false);
                    playerCar.setTurnLeft(true);
                    // player.addVelocity(-player.getWidth() * 0.66667, 0);
                    playerCar.update(minLeftSide, maxRightSide);
                    break;
                case "Right":
                    playerCar.setCenterWheel(false);
                    playerCar.setTurnRight(true);
                    //player.setAngle(player.getAngle() + 15);
                    // player.addVelocity(player.getWidth() * 0.66667, 0);
                    playerCar.update(minLeftSide, maxRightSide);
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
                    playerCar.shot();
                    break;
                default:
                    break;
            }
        } else {
            switch (keyCode.getName()) {
                case "P":
                    RunTrack.setIsPaused(false);
                    break;
                case "M":
                    MusicPlayer.getInstance().play();
                    break;
            }
//            if(keyCode.getName().equals("P")){
//                RunTrack.setIsPaused(false);
//            }
        }
    }
}
package keyHandler;

import gameEngine.RunTrack;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import music.MusicPlayer;
import models.Player;


public class KeyHandlerOnPress implements EventHandler<KeyEvent> {
    public static boolean[] pressedKeys = new boolean[256];
    private Player player;
    private int minLeftSide;
    private int maxRightSide;

    public KeyHandlerOnPress(Player p, int minLeftSide, int maxRightSide) {
        this.player = p;
        this.minLeftSide = minLeftSide;
        this.maxRightSide = maxRightSide;
    }

    @Override
    public void handle(KeyEvent e) {
        KeyCode keyCode = e.getCode();
        if (!RunTrack.isPaused()) {
            RunTrack.getCheat().add(keyCode.getName());
            switch (keyCode.getName()) {
                case "Up":
                    player.accelerate();
                    player.update();
                    break;
                case "Down":
                    if (RunTrack.getVelocity() > 5) {
                        RunTrack.setVelocity(RunTrack.getVelocity() - 1);
                    }
                    player.setCenterWheel(false);
                    //player.addVelocity(0, 2);
                    player.update();
                    break;
                case "Left":
                    player.setCenterWheel(false);
                    player.setTurnLeft(true);
                    // player.addVelocity(-player.getWidth() * 0.66667, 0);
                    player.update(minLeftSide, maxRightSide);
                    break;
                case "Right":
                    player.setCenterWheel(false);
                    player.setTurnRight(true);
                    //player.setAngle(player.getAngle() + 15);
                    // player.addVelocity(player.getWidth() * 0.66667, 0);
                    player.update(minLeftSide, maxRightSide);
                    break;
                case "P":
                    RunTrack.setIsPaused(true);
                    break;
                case "M":
                    MusicPlayer.play();
                    break;
                case "Q":
                    Platform.exit();
                    break;
                case "S":
                    player.shot();
                    //RunTrack.setShoot(true);
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
                    MusicPlayer.play();
                    break;
            }
//            if(keyCode.getName().equals("P")){
//                RunTrack.setIsPaused(false);
//            }
        }
    }
}
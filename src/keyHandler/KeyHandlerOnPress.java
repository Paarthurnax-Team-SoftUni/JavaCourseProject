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

    public KeyHandlerOnPress(Player p) {
        this.player = p;
    }

    @Override
    public void handle(KeyEvent e) {
        KeyCode keyCode = e.getCode();
        if (!RunTrack.isPaused()) {
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
                    player.addVelocity(0, 2);
                    player.update();
                    break;
                case "Left":
                    player.setCenterWheel(false);
                    player.setTurnLeft(true);
                    // player.addVelocity(-player.getWidth() * 0.66667, 0);
                    player.update();
                    break;
                case "Right":
                    player.setCenterWheel(false);
                    player.setTurnRight(true);
                    //player.setAngle(player.getAngle() + 15);
                    // player.addVelocity(player.getWidth() * 0.66667, 0);
                    player.update();
                    break;
                case "P":
                    RunTrack.setIsPaused(true);
                    break;
                case "M":
                    System.out.println("clicked");
                    MusicPlayer.play();
                    break;
                case "Q":
                    Platform.exit();
                    break;
                case "Enter":
                    break;
                default:
                    break;
            }
        } else {
            switch (keyCode.getName()){
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
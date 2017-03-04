package GameEngine;

/**
 * Under Construction

 public class GameLoop {
 private Timeline gameloop;
 private
 private

 public GameLoop(Timeline gameloop) {
 this.gameloop = gameloop;
 this.gameloop.setCycleCount(Timeline.INDEFINITE);
 }

 public void runGame(){
 MusicPlayer.PlayMusic();

 KeyFrame kf = new KeyFrame(
 Duration.seconds(Constants.FRAMES_PER_SECOND),
 event -> {

 if (GamePlayController.getInstance().isIsPaused()) {
 handleGamePause(this.gameloop, gc, background);
 }

 y = y + velocity ;
 time++;
 frame++;

 currentTime.setValue((long) (time * Constants.FRAMES_PER_SECOND));
 currentDistance.setValue(currentDistance.getValue() + (long) velocity/2);
 player.setPoints(player.getPoints()+1);
 currentPoints.setValue(player.getPoints());

 observer.update(currentPoints, observer);
 observer.update(currentTime, observer);
 observer.update(currentDistance, observer);

 if (Math.abs(y) >= Constants.CANVAS_HEIGHT) {
 y = y - Constants.CANVAS_HEIGHT;
 frame = 0;
 }
 player.setVelocity(0, 0);

 //Pause

 //Generate obstacles
 if (frame == 0) {
 testObstacles.add(Obstacle.generateObstacle());
 }

 gc.clearRect(0, 0, Constants.CANVAS_WIDTH, Constants.CANVAS_HEIGHT);
 gc.drawImage(background, 0, y - Constants.CANVAS_HEIGHT);
 gc.drawImage(background, 0, y);
 player.update();
 player.render(gc);
 currentHealth.render();
 manageObstacles(gc);
 if(time >= Constants.TRACK_1_END_TIME){
 clearObstaclesAndCollectibles();
 gameLoop.stop();
 MusicPlayer.StopMusic();
 time = 0;
 player.setHealthPoints(Constants.HEALTH_BAR_MAX);
 if (player.getHighScore() < player.getPoints()) {
 player.setHighScore(player.getPoints());
 }
 player.setPoints(0L);
 player.stopAccelerate();
 velocity = Constants.START_GAME_VELOCITY;
 currentDistance.setValue(0);
 Stage stage = (Stage) canvas.getScene().getWindow();
 root.getChildren().remove(canvas);
 try {
 ScreenController.getInstance().loadStage(stage, ScreenController.getInstance().getGamePlayStage(), Constants.GAME_WIN_VIEW_PATH);
 } catch (IOException e) {
 e.printStackTrace();
 }
 }
 if (player.getHealthPoints() <= 0) {
 clearObstaclesAndCollectibles();
 gameLoop.stop();
 MusicPlayer.StopMusic();
 time = 0;
 player.setHealthPoints(Constants.HEALTH_BAR_MAX);
 if (player.getHighScore() < player.getPoints()) {
 player.setHighScore(player.getPoints());
 }
 player.setPoints(0L);
 player.stopAccelerate();
 velocity=5;
 currentDistance.setValue(0);
 Stage stage = (Stage) canvas.getScene().getWindow();
 root.getChildren().remove(canvas);
 try {
 ScreenController.getInstance().loadStage(stage, ScreenController.getInstance().getGameOverStage(), Constants.GAME_OVER_VIEW_PATH);
 } catch (IOException e) {
 e.printStackTrace();
 }
 }

 if (frame % Constants.COLLECTIBLES_OFFSET == 0) {
 collectibles.add(Collectible.generateCollectible());
 }
 visualizeCollectible(gc, velocity);
 });

 gameLoop.getKeyFrames().add(kf);
 gameLoop.playFromStart();
 }

 }

 }
 */
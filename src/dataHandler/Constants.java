package dataHandler;

public class Constants {

    public static final String START_FXML_PATH = "/views/start.fxml";
    public static final String LOGO_PATH = "/resources/images/logo.png";
    public static final String LOGIN_VIEW_PATH = "/views/login.fxml";
    public static final String CHOOSE_CAR_VIEW_PATH = "/views/chooseCar.fxml";
    public static final String GAME_PLAY_VIEW_PATH = "/views/gamePlay.fxml";
    public static final String HIGH_SCORES_DIALOG = "/views/highScoresDialog.fxml";
    public static final String HIGH_SCORE_DIALOG_TITLE = "Best Slav Ranking";
    public static final String DIALOG_MESSAGE = "Best Slav Ranking";
    public static final String COLLECTIBLES_PATH = "/resources/images/collectable";
    public static final String[] COLLECTABLE_LIST = {"collectable1", "collectable2","collectable3"};  //to be used to refractor gameTokens
    public static final String[] OBSTACLES_LIST = {"obstacle1", "obstacle2", "obstacle3", "obstacle1", "obstacle2", "obstacle3", "player_car1", "player_car2", "player_car3", "player_car4", "player_car5", "player_car6"};
    public static final String IMAGES_PATH = "/resources/images/";
    public static final String HIGH_SCORES_FILE_NAME = System.getProperty("user.dir") + "/src/resources/playerList.txt";
    public static final String FLAME_PATH ="resources/images/flame.png";
    public static final String GAME_TITLE = "SoftUni Rush";
    public static final String TRACK_BACKGROUND_PATH = "/resources/images/background2.jpg";
    public static final String SONG_PATH = System.getProperty("user.dir") + "/src/resources/music.wav";
    public static final String GAME_OVER_VIEW_PATH = "/views/gameOver.fxml";
    public static final String GAME_WIN_VIEW_PATH = "/views/gameWin.fxml";
    public static final String CAR_IMAGES_PATH = "/resources/images/player_";
    public static final long TRACK_1_END_TIME = 3533; //Key Time in Frames;
    public static final int CANVAS_WIDTH = 500;
    public static final int CANVAS_HEIGHT = 600;
    public static final int COLLECTIBLES_OFFSET = 50000;
    public static final int FUEL_TANK_BONUS = 250;
    public static final int HEALTH_BONUS = 25;
    public static final int HEALTH_PACK_BONUS_POINTS = 500;
    public static final int BONUS_POINTS = 1000;
    public static final int DESTROY_OBJECT_COORDINATES = 800;
    public static final int START_GAME_VELOCITY = 5;
    public static final int HEALTH_BAR_MIN = 25;
    public static final int HEALTH_BAR_MAX = 100;
    public static final int HEALTH_BAR_AVERAGE_HIGH = 75;
    public static final int HEALTH_BAR_AVERAGE_LOW = 50;
    public static final int OBSTACLE_DAMAGE = 25;
    public static final float FRAMES_PER_SECOND = 0.017f;   //Frames in seconds;

}

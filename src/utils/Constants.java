package utils;

public class Constants {

    public static final String GAME_TITLE = "SoftUni Rush";
    public static final int CANVAS_WIDTH = 850;
    public static final int CANVAS_HEIGHT = 650;
    public static final float FRAMES_PER_SECOND = 0.017f;   //Seconds per frame [ 1 Frame == the given time in seconds];

    //views
    public static final String LOGIN_VIEW_PATH = "/views/login.fxml";
    public static final String START_FXML_PATH = "/views/start.fxml";
    public static final String CHOOSE_CAR_VIEW_PATH = "/views/chooseCar.fxml";
    public static final String CHOOSE_LEVEL_VIEW_PATH = "/views/chooseLevel.fxml";
    public static final String GAME_PLAY_VIEW_PATH = "/views/gamePlay.fxml";
    public static final String GAME_OVER_VIEW_PATH = "/views/gameOver.fxml";
    public static final String GAME_WIN_VIEW_PATH = "/views/gameWin.fxml";
    public static final String HIGH_SCORES_DIALOG = "/views/highScoresDialog.fxml";

    //resources
    public static final String IMAGES_PATH = "resources/images/";
    public static final String HIGH_SCORES_FILE_NAME = System.getProperty("user.dir") + "/src/resources/playerList.txt";
    public static final String LOGO_PATH = "/resources/images/logo.png";
    public static final String FLAME_PATH = "resources/images/flame.png";
    public static final String FLAME_PATH_SMALL = "resources/images/flame_half_size.png";
    public static final String SECOND_TRACK_BACKGROUND_PATH  = "resources/images/backgrounds/backgroundLevel2.jpg";
    public static final String TRACK_BACKGROUND_PATH = "resources/images/backgrounds/backgroundLevel1.jpg";
    public static final String SONG_PATH = System.getProperty("user.dir") + "/src/resources/music/music.wav";
    public static final String CAR_IMAGES_PATH = "/resources/images/cars/player_";
    public static final String AMMO_PATH = "/resources/images/collectibles/collectible6.png";

    //login strings
    public static final String ERROR_USERNAME_TITLE = "You have to fill in the username field!";
    public static final String ERROR_USERNAME_CONTENT = "Please click on OK to retry!";
    public static final String ERROR_USERNAME_HEADER = "You have to fill in the username field!";
    public static final String LOGIN_USER_TITLE = "Login with this username";
    public static final String LOGIN_USER_HEADER = "Login as user: ";
    public static final String LOGIN_USER_CONTENT = "Are you sure? Press OK to continue.";
    public static final String CREATE_USER_TITLE = "Create new user";
    public static final String CREATE_USER_CONTENT = "Are you sure? Press OK to continue.";
    public static final String CREATE_USER_HEADER = "Create new user: ";

    public static final String HIGH_SCORE_DIALOG_TITLE = "Best Slav Ranking";

    //collctibles and obstacles
    public static final String FUEL_BOTTLE_STRING = "fuelBottle";
    public static final String HEALTH_STRING = "health";
    public static final String DOUBLE_POINTS_STRING = "doublePts";
    public static final String IMMORTALITY_STRING = "immortality";
    public static final String ARMAGEDDON_STRING = "armageddonsPower";
    public static final String BONUS_POINTS_STRING = "bonusPts";
    public static final String AMMO_STRING = "ammunitions";
    public static final String[] COLLECTIBLE_LIST = {"collectible1", "collectible2", "collectible3", "collectible4", "collectible5","collectible6"}; //to be used to refractor gameTokens
    public static final String[] OBSTACLES_LIST = {"obstacles/obstacle1", "obstacles/obstacle2", "obstacles/obstacle3",
            "cars/player_car1", "cars/player_car2", "cars/player_car3", "cars/player_car4", "cars/player_car5", "cars/player_car6"};
    public static final String COLLECTIBLE_PATH = "resources/images/collectibles/";
    public static final String OBSTACLES_PATH = "resources/images/obstacles/";
    public static final String[] COLLECTIBLE_LIST_SMALL = {"collectible1_half_size", "collectible2_half_size", "collectible3_half_size", "collectible4_half_size", "collectible5_half_size", "collectible6_half_size"}; //to be used to refractor gameTokens
    public static final String[] OBSTACLES_LIST_SMALL = {"obstacles/obstacle1_half_size", "obstacles/obstacle2_half_size", "obstacles/obstacle3_half_size",
            "cars/player_car1_half_size", "cars/player_car2_half_size", "cars/player_car3_half_size", "cars/player_car4_half_size", "cars/player_car5_half_size", "cars/player_car6_half_size"};

    //gameplay constants
    public static final int BONUS_POINTS_HIT_WITH_SHIELD = 10;
    public static final long TRACK_1_END_TIME = 1500;    //Key Time in Frames
    public static final long TRACK_1_END_DISTANCE = 5000;
    public static final int COLLECTIBLES_OFFSET = 50000;
    public static final int FUEL_TANK_BONUS = 250;
    public static final int FUEL_TANK_BONUS_TIME = 5;
    public static final int HEALTH_BONUS = 25;
    public static final int HEALTH_PACK_BONUS_POINTS = 500;
    public static final int IMMORTALITY_BONUS = 500;
    public static final long IMMORTALITY_DURATION = 5;    //data in Seconds
    public static final int ARMAGEDDONS_BONUS = 500;
    public static final int AMMO_BONUS = 500;
    public static final int DOUBLE_BONUS_POINTS = 1000;
    public static final long DOUBLE_PTS_DURATION = 5;
    public static final int DESTROY_OBJECT_COORDINATES = 1000;
    public static final int DESTROYED_OBJECT_BONUS = 800;
    public static final int START_GAME_VELOCITY = 5;
    public static final int START_GAME_BULLETS = 5;
    public static final int HEALTH_BAR_MIN = 25;
    public static final int HEALTH_BAR_MAX = 100;
    public static final int HEALTH_BAR_AVERAGE_HIGH = 75;
    public static final int HEALTH_BAR_AVERAGE_LOW = 50;
    public static final int OBSTACLE_DAMAGE = 25;

    //notifications
    public static final String FUEL_NOTIFICATION_MESSAGE = "Extra fuel! +5 seconds";
    public static final String HEALTH_NOTIFICATION_MESSAGE = "Health! Restore your health";
    public static final String DOUBLE_PTS_NOTIFICATION_MESSAGE = "Bonus! Double points in the next 5 seconds";
    public static final String IMMORTALITY_NOTIFICATION_MESSAGE = "Immortality! You are invincible for the next 5 seconds";
    public static final String ARMAGEDDONS_NOTIFICATION_MESSAGE = "Armageddons Power! Nothing can get on your way now";
    public static final String AMMO_NOTIFICATION_MESSAGE = "Ammunition! You can shoot once";


    //table columns
    public static final String TABLE_COLUMN_NAME = "Player Name";
    public static final String TABLE_COLUMN_MONEY = "Kinti Earned";
    public static final String TABLE_COLUMN_SCORE = "High Score";
    public static final String CAR_STRING = "car";

    //images shortcuts
    public static final String HALF_SIZE_NAME = "_half_size";
    public static final String COLLECTIBLE = "collectible";
    public static final String HALF_SIZE = "_half_size.png";
    public static final java.lang.String VALUE_STRING = "value";

    //styles
    public static final String RED_COLOUR = "-fx-fill: rgba(255,0,0, 0.55);";
    public static final String GREY_COLOUR = "-fx-fill: rgba(95, 88, 93, 0.7);";

    //map constants
    public static final int FIRST_LEVEL_DRUNK_DRIVERS = 100;
    public static final int FIRST_LEVEL_MIN_X = 120;
    public static final int FIRST_LEVEL_MAX_X = 320;
    public static final int SECOND_LEVEL_DRUNK_DRIVERS = 60;
    public static final int SECOND_LEVEL_MIN_X = 50;
    public static final int SECOND_LEVEL_MAX_X = 400;

    //DB Errors
    public static final String DB_INIT_ERROR_TITLE = "Error in the connection to the DB";
    public static final String DB_INIT_ERROR_CONTENT = "Please relaunch the application";
}

package utils.constants;

public class ErrorConstants {

    public static final String BULLETS_EXCEPTION = "The bullets can not be negative number";
    public static final String POINTS_EXCEPTION = "The points can not be negative number";
    public static final String DISTANCE_EXCEPTION = "The distance can not be negative number";
    public static final String ID_EXCEPTION = "The ID can not be negative number";
    public static final String NAME_EXCEPTION = "The name can not be null";
    public static final String HIGHSCORE_ERROR = "The Highscore can not be negative";
    public static final String MONEY_EXCEPTION = "The money value can not be negative";
    public static final String HEALTH_POINTS_EXCEPTION = "The health points can not be negative";
    public static final String FILE_PATH_EXCEPTION = "The path to the file can not be null";
    public static final String POSITION_EXCEPTION = "The position's coordinates can not be negative";
    public static final String VELOCITY_EXCEPTION = "The velocity change value can no be outside bounds";
    public static final String PLAYER_MESSAGE = "Current PlayerImpl can not be null";
    public static final String DB_INIT_ERROR_TITLE = "Error in the connection to the DB";
    public static final String DB_INIT_ERROR_CONTENT = "Please relaunch the application";
    public static final String SQL_EXCEPTION_ERROR_MESSAGE = "Couldn't close connection: ";
    public static final String SQL_CONNECTION_ERROR_MESSAGE = "Error in the connection to the database: ";
    public static final String CAR_ERROR_MESSAGE = "The car value can not be null";

    protected ErrorConstants() {
    }
}

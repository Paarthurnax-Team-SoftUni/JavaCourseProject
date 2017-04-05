package constants;

public class SQLConstants {

    public static final java.lang.String PROPERTY_VALUE = "os.name";
    public static final int INDEX_COLUMN_ID = 1;
    public static final int INDEX_COLUMN_NAME = 2;
    public static final int INDEX_COLUMN_HIGHSCORE = 3;
    public static final int INDEX_COLUMN_MONEY = 4;
    public static final int INDEX_COLUMN_HEALTH = 5;
    private static final String TABLE_NAME = "players";
    public static final String QUERY_PLAYERS = "SELECT * FROM " + TABLE_NAME;
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_HIGHSCORE = "highScore";
    public static final String UPDATE_PLAYER_SCORE = "UPDATE " +
            TABLE_NAME + " SET " +
            COLUMN_HIGHSCORE + " = ? WHERE " +
            COLUMN_NAME + " = ?";
    private static final String COLUMN_MONEY = "money";
    private static final String COLUMN_HEALTH = "health";
    public static final String CREATE_TABLE_COMMAND = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" +
            COLUMN_ID + "  INTEGER PRIMARY KEY, " +
            COLUMN_NAME + " TEXT UNIQUE, " +
            COLUMN_HIGHSCORE + " INTEGER, " +
            COLUMN_MONEY + " DOUBLE, " +
            COLUMN_HEALTH + " INTEGER)";
    public static final String INSERT_PLAYER = "INSERT INTO " +
            TABLE_NAME + "(" +
            COLUMN_NAME + ", " +
            COLUMN_HIGHSCORE + ", " +
            COLUMN_MONEY + ", " +
            COLUMN_HEALTH + ") VALUES(?, ?, ?, ?)";
    private static final String CONNECTION_STRING = "jdbc:sqlite:src\\resources\\database\\players.db";
    private static final String LINUX_CONNECTION_STRING = "jdbc:sqlite:src/resources/database/players.db";

    public static String returnPath(String OSName) {
        if (OSName.contains("win")) {
            return CONNECTION_STRING;
        } else {
            return LINUX_CONNECTION_STRING;
        }
    }
}
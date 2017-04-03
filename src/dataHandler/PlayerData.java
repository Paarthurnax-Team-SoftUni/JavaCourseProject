package dataHandler;

import constants.DBErrorConstants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.PlayerImlp;
import constants.SQLConstants;

import java.sql.*;

public class PlayerData {

    private static volatile PlayerData instance = null;
    private ObservableList<PlayerImlp> playersList;
    private PlayerImlp currentPlayer;
    private Connection conn;

    private PreparedStatement insertPlayer;
    private PreparedStatement queryPlayers;
    private PreparedStatement updatePlayer;

    private PlayerData() {}

    public static PlayerData getInstance() {
        if(instance == null) {
            synchronized (PlayerData.class) {
                if(instance == null) {
                    instance = new PlayerData();
                }
            }
        }
        return instance;
    }

    public boolean open() {
        try {
            this.conn = DriverManager.getConnection(SQLConstants.returnPath(System.getProperty(SQLConstants.PROPERTY_VALUE).toLowerCase()));
            this.insertPlayer = this.conn.prepareStatement(SQLConstants.INSERT_PLAYER);
            this.queryPlayers = this.conn.prepareStatement(SQLConstants.QUERY_PLAYERS);
            this.updatePlayer = this.conn.prepareStatement(SQLConstants.UPDATE_PLAYER_SCORE);
            return true;
        } catch (SQLException e) {
            System.out.println(DBErrorConstants.SQL_CONNECTION_ERROR_MESSAGE + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {

            if(this.insertPlayer != null) {
                this.insertPlayer.close();
            }

            if(this.queryPlayers != null) {
                this.queryPlayers.close();
            }

            if(this.updatePlayer != null) {
                this.updatePlayer.close();
            }

            if (this.conn != null) {
                this.conn.close();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(DBErrorConstants.SQL_EXCEPTION_ERROR_MESSAGE + e.getMessage());
        }
    }

    public void createDb() {
        try( Connection conn = DriverManager.getConnection(SQLConstants.returnPath(System.getProperty(SQLConstants.PROPERTY_VALUE).toLowerCase()));
             Statement statement = conn.createStatement()) {
            statement.execute(SQLConstants.CREATE_TABLE_COMMAND);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PlayerImlp getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void registerPlayer(PlayerImlp player) {
        setCurrentPlayer(player);
    }

    public ObservableList<PlayerImlp> getPlayersList() {
        return this.playersList;
    }

    public void addPlayer(PlayerImlp player) {
        this.playersList.add(player);
    }

    public ObservableList loadPlayersData() {
        this.playersList = FXCollections.observableArrayList();
        try  {
            ResultSet results = queryPlayers.executeQuery();
            while (results.next()) {
                PlayerImlp player = new PlayerImlp();
                player.updateId(results.getInt(SQLConstants.INDEX_COLUMN_ID));
                player.updateName(results.getString(SQLConstants.INDEX_COLUMN_NAME));
                player.updateHighScore(results.getLong(SQLConstants.INDEX_COLUMN_HIGHSCORE));
                player.updateMoney(results.getDouble(SQLConstants.INDEX_COLUMN_MONEY));
                player.updateHealthPoints(results.getInt(SQLConstants.INDEX_COLUMN_HEALTH));
                this.playersList.add(player);
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return this.playersList;
    }

    public void storePlayersData(PlayerImlp player) throws SQLException {
        this.insertPlayer.setString(1, player.getName());
        this.insertPlayer.setLong(2, player.getPoints());
        this.insertPlayer.setDouble(3, player.getMoney());
        this.insertPlayer.setInt(4, player.getHealthPoints());
        this.insertPlayer.executeUpdate();
    }

    public boolean checkForPlayer(String player) {
        for (PlayerImlp savedPlayer : this.playersList) {
            if (savedPlayer.getName().equals(player)) {
                return false;
            }
        }
        return true;
    }

    public PlayerImlp returnPlayer(String player) {
        for (PlayerImlp savedPlayer : this.playersList) {
            if (savedPlayer.getName().equals(player)) {
                return savedPlayer;
            }
        }
        return null;
    }

    public void updatePlayer(PlayerImlp currentPlayer) {
        if (currentPlayer.getPoints() > currentPlayer.getHighScore()) {
            currentPlayer.updateHighScore(currentPlayer.getPoints());
            try {
                this.updatePlayer.setLong(1, currentPlayer.getHighScore());
                this.updatePlayer.setString(2, currentPlayer.getName());
                this.updatePlayer.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String getHighscores() {
        String highscore = this.playersList.sorted((p1, p2) -> p2.getHighScore().compareTo(p1.getHighScore())).get(0).getHighScore().toString();

        return highscore;
    }

    private void setCurrentPlayer(PlayerImlp currentPlayer) {
        if (currentPlayer == null) {
            throw new IllegalArgumentException("Current PlayerImlp can not be null");
        }
        this.currentPlayer = currentPlayer;
    }
}
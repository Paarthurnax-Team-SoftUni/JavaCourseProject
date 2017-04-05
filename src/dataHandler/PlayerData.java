package dataHandler;

import constants.DBErrorConstants;
import constants.SQLConstants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Player;

import java.sql.*;

public class PlayerData {

    private static volatile PlayerData instance = null;
    private ObservableList<Player> playersList;
    private Player currentPlayer;
    private Connection conn;

    private PreparedStatement insertPlayer;
    private PreparedStatement queryPlayers;
    private PreparedStatement updatePlayer;

    private PlayerData() {
    }

    public static PlayerData getInstance() {
        if (instance == null) {
            synchronized (PlayerData.class) {
                if (instance == null) {
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

            if (this.insertPlayer != null) {
                this.insertPlayer.close();
            }

            if (this.queryPlayers != null) {
                this.queryPlayers.close();
            }

            if (this.updatePlayer != null) {
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
        try (Connection conn = DriverManager.getConnection(SQLConstants.returnPath(System.getProperty(SQLConstants.PROPERTY_VALUE).toLowerCase()));
             Statement statement = conn.createStatement()) {
            statement.execute(SQLConstants.CREATE_TABLE_COMMAND);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    private void setCurrentPlayer(Player currentPlayer) {
        if (currentPlayer == null) {
            throw new IllegalArgumentException("Current PlayerImpl can not be null");
        }
        this.currentPlayer = currentPlayer;
    }

    public void registerPlayer(Player player) {
        setCurrentPlayer(player);
    }

    public ObservableList<Player> getPlayersList() {
        return this.playersList;
    }

    public void addPlayer(Player player) {
        this.playersList.add(player);
    }

    public ObservableList loadPlayersData() {
        this.playersList = FXCollections.observableArrayList();
        try {
            ResultSet results = queryPlayers.executeQuery();
            while (results.next()) {
                Player player = new Player();
                player.setId(results.getInt(SQLConstants.INDEX_COLUMN_ID));
                player.setName(results.getString(SQLConstants.INDEX_COLUMN_NAME));
                player.setHighScore(results.getLong(SQLConstants.INDEX_COLUMN_HIGHSCORE));
                player.setMoney(results.getDouble(SQLConstants.INDEX_COLUMN_MONEY));
                player.setHealthPoints(results.getInt(SQLConstants.INDEX_COLUMN_HEALTH));
                this.playersList.add(player);
            }

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
        }
        return this.playersList;
    }

    public void storePlayersData(Player player) throws SQLException {
        this.insertPlayer.setString(1, player.getName());
        this.insertPlayer.setLong(2, player.getPoints());
        this.insertPlayer.setDouble(3, player.getMoney());
        this.insertPlayer.setInt(4, player.getHealthPoints());
        this.insertPlayer.executeUpdate();
    }

    public boolean checkForPlayer(String player) {
        for (Player savedPlayer : this.playersList) {
            if (savedPlayer.getName().equals(player)) {
                return false;
            }
        }
        return true;
    }

    public Player returnPlayer(String player) {
        for (Player savedPlayer : this.playersList) {
            if (savedPlayer.getName().equals(player)) {
                return savedPlayer;
            }
        }
        return null;
    }

    public void updatePlayer(Player currentPlayer) {
        if (currentPlayer.getPoints() > currentPlayer.getHighScore()) {
            currentPlayer.setHighScore(currentPlayer.getPoints());
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
}
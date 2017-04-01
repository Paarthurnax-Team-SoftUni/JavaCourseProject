package dataHandler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Player;
import constants.SQLConstants;

import java.sql.*;

public class PlayerData {

    private static volatile PlayerData instance = null;
    private ObservableList<Player> playersList;
    private Player currentPlayer;
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
            conn = DriverManager.getConnection(SQLConstants.returnPath(System.getProperty(SQLConstants.PROPERTY_VALUE).toLowerCase()));
            insertPlayer = conn.prepareStatement(SQLConstants.INSERT_PLAYER);
            queryPlayers = conn.prepareStatement(SQLConstants.QUERY_PLAYERS);
            updatePlayer = conn.prepareStatement(SQLConstants.UPDATE_PLAYER_SCORE);
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {

            if(insertPlayer != null) {
                insertPlayer.close();
            }

            if(queryPlayers != null) {
                queryPlayers.close();
            }

            if(updatePlayer != null) {
                updatePlayer.close();
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
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

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public ObservableList<Player> getPlayersList() {
        return this.playersList;
    }

    public void addPlayer(Player player) {
        this.playersList.add(player);
    }

    public ObservableList loadPlayersData() {
        this.playersList = FXCollections.observableArrayList();
        try  {
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
        insertPlayer.setString(1, player.getName());
        insertPlayer.setLong(2, player.getPoints());
        insertPlayer.setDouble(3, player.getMoney());
        insertPlayer.setInt(4, player.getHealthPoints());
        insertPlayer.executeUpdate();
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
                updatePlayer.setLong(1, currentPlayer.getHighScore());
                updatePlayer.setString(2, currentPlayer.getName());
                updatePlayer.executeUpdate();
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
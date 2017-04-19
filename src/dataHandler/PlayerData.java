package dataHandler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Player;
import models.sprites.PlayerCar;
import utils.constants.ErrorConstants;
import utils.constants.SQLConstants;

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
            System.out.println(ErrorConstants.SQL_CONNECTION_ERROR_MESSAGE + e.getMessage());
            return false;
        }
    }

    public void updateCarId(String carId) {
        this.currentPlayer.updateCarId(carId);
    }

    public String getCarId() {
        return this.currentPlayer.getCarId();
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
            throw new IllegalStateException(ErrorConstants.SQL_EXCEPTION_ERROR_MESSAGE + e.getMessage());
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
            throw new IllegalArgumentException(ErrorConstants.PLAYER_MESSAGE);
        }
        this.currentPlayer = currentPlayer;
    }

    public void registerPlayer(Player player) {
        setCurrentPlayer(player);
    }

    public ObservableList<Player> getPlayersList() {
        return FXCollections.unmodifiableObservableList(this.playersList);
    }

    public void addPlayer(Player player) {
        this.playersList.add(player);
    }

    public ObservableList loadPlayersData() {
        this.playersList = FXCollections.observableArrayList();
        try {
            ResultSet results = this.queryPlayers.executeQuery();
            while (results.next()) {
                PlayerCar playerCar = new PlayerCar();
                Player player = new Player(playerCar);
                player.updateId(results.getInt(SQLConstants.INDEX_COLUMN_ID));
                player.updateName(results.getString(SQLConstants.INDEX_COLUMN_NAME));
                player.updateHighScore(results.getLong(SQLConstants.INDEX_COLUMN_HIGHSCORE));
                player.updateMoney(results.getDouble(SQLConstants.INDEX_COLUMN_MONEY));
                player.updateHealthPoints(results.getInt(SQLConstants.INDEX_COLUMN_HEALTH));
                this.playersList.add(player);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return this.playersList;
    }

    public void storePlayersData(Player player) throws SQLException {
        this.insertPlayer.setString(SQLConstants.INDEX_COLUMN_NAME_TABLE_QUERY, player.getName());
        this.insertPlayer.setLong(SQLConstants.INDEX_COLUMN_HIGHSCORE_TABLE_QUERY, player.getPoints());
        this.insertPlayer.setDouble(SQLConstants.INDEX_COLUMN_MONEY_TABLE_QUERY, player.getMoney());
        this.insertPlayer.setInt(SQLConstants.INDEX_COLUMN_HEALTH_TABLE_QUERY, player.getHealthPoints());
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
            currentPlayer.updateHighScore(currentPlayer.getPoints());
            try {
                this.updatePlayer.setLong(SQLConstants.INDEX_COLUMN_NAME_TABLE_QUERY, currentPlayer.getHighScore());
                this.updatePlayer.setString(SQLConstants.INDEX_COLUMN_HIGHSCORE_TABLE_QUERY, currentPlayer.getName());
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
package dataHandler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Player;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;


public class PlayerData {

    private static volatile PlayerData instance = null;
    private ObservableList<Player> playersList;
    private Player currentPlayer;

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

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public ObservableList<Player> getPlayersList() {
        return this.playersList;
    }

    public String getHighscores() {
        String highscore = this.playersList.sorted((p1, p2) -> p2.getHighScore().compareTo(p1.getHighScore())).get(0).getHighScore().toString();

        return highscore;
    }

    public void addPlayer(Player player) {
        this.playersList.add(player);
    }

    public void loadPlayersData() {
        this.playersList = FXCollections.observableArrayList();
        Path path = Paths.get(Constants.HIGH_SCORES_FILE_NAME);

        String input;

        try (BufferedReader br = Files.newBufferedReader(path)) {
            while ((input = br.readLine()) != null) {
                String[] itemPieces = input.split("\t");
                String name = itemPieces[0];
                long highScore = Long.parseLong(itemPieces[1]);
                double money = Double.parseDouble(itemPieces[2]);
                long points = Long.parseLong(itemPieces[3]);
                long experience = Long.parseLong(itemPieces[4]);
                int healthPoints = Integer.parseInt(itemPieces[5]);
                Player player = new Player(name, highScore, money, points, experience, healthPoints);
                this.playersList.add(player);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void storePlayersData() {
        Path path = Paths.get(Constants.HIGH_SCORES_FILE_NAME);
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            Iterator<Player> iter = this.playersList.iterator();
            while (iter.hasNext()) {
                Player player = iter.next();
                bw.write(String.format("%s\t%s\t%s\t%s\t%s\t%s%n",
                        player.getName(),
                        player.getHighScore(),
                        player.getMoney(),
                        player.getPoints(),
                        player.getExperience(),
                        player.getHealthPoints()
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void deletePlayer(Player player) {
        playersList.remove(player);
    }
}
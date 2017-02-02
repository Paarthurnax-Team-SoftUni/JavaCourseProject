package DataHandler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class PlayerData {
    private static PlayerData instance = new PlayerData();
    private static String fileName = "PlayerList.txt";

    private ObservableSet<Player> playersList;

    public static PlayerData getInstance() {
        return instance;
    }

    public ObservableSet<Player> getPlayersList() {
        return playersList;
    }

    public void addPlayer(Player player) {
        playersList.add(player);
    }

    public void loadPlayersData() {
        playersList = FXCollections.observableSet();
        Path path = Paths.get(fileName);

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
                playersList.add(player);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void storePlayersData() {
        Path path = Paths.get(fileName);
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            Iterator<Player> iter = playersList.iterator();
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

    public boolean checkForPlayer(Player player) {
        for (Player savedPlayer : playersList) {
            if (savedPlayer.getName().equals(player.getName())) {
                return false;
            }
        }
        return true;
    }

    public void deletePlayer(Player player) {
        playersList.remove(player);
    }
}

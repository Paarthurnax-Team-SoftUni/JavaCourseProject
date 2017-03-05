package controllers;

import dataHandler.PlayerData;
import javafx.fxml.FXML;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Player;

import java.util.Comparator;
import java.util.function.Predicate;

public class HighScoreController {

    @FXML
    private TableView<Player> highScoresListView;

    public void initialize() {

        Predicate<Player> wantAllItems = player -> true;
        FilteredList<Player> filteredList = new FilteredList<Player>(PlayerData.getInstance().getPlayersList(), wantAllItems);

        SortedList<Player> sortedList = new SortedList<Player>(filteredList, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return o2.getHighScore().compareTo(o1.getHighScore());
            }
        });

        TableColumn name = new TableColumn("Player Name");
        name.setMinWidth(100);
        name.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));

        TableColumn money = new TableColumn("Kinti Earned");
        money.setMinWidth(100);
        money.setCellValueFactory(new PropertyValueFactory<Player, String>("money"));

        TableColumn highScore = new TableColumn("High Score");
        highScore.setMinWidth(100);
        highScore.setCellValueFactory(new PropertyValueFactory<Player, String>("highScore"));

        highScoresListView.setItems(sortedList);
        highScoresListView.getColumns().addAll(name, money, highScore);
    }
}

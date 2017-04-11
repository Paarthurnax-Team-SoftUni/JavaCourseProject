package controllers;

import dataHandler.PlayerData;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import models.Player;

import java.util.function.Predicate;

public class HighScoreController {

    @FXML
    private TableView<Player> highScoresListView;

    public void initialize() {
        Predicate<Player> wantAllItems = player -> true;
        FilteredList<Player> filteredList = new FilteredList<Player>(PlayerData.getInstance().getPlayersList(), wantAllItems);
        SortedList<Player> sortedList = new SortedList<Player>(filteredList, (o1, o2) ->
                Long.compare(o2.getHighScore(),
                o1.getHighScore()));

        highScoresListView.setItems(sortedList);
    }
}

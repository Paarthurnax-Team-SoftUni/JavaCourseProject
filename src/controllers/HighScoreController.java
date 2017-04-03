package controllers;

import dataHandler.PlayerData;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import models.PlayerImlp;

import java.util.function.Predicate;

public class HighScoreController {

    @FXML
    private TableView<PlayerImlp> highScoresListView;

    public void initialize() {
        Predicate<PlayerImlp> wantAllItems = player -> true;
        FilteredList<PlayerImlp> filteredList = new FilteredList<PlayerImlp>(PlayerData.getInstance().getPlayersList(), wantAllItems);
        SortedList<PlayerImlp> sortedList = new SortedList<PlayerImlp>(filteredList, (o1, o2) -> o2.getHighScore().compareTo(o1.getHighScore()));

        highScoresListView.setItems(sortedList);
    }
}

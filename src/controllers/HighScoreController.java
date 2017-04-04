package controllers;

import dataHandler.PlayerData;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import interfaces.Playable;

import java.util.function.Predicate;

public class HighScoreController {

    @FXML
    private TableView<Playable> highScoresListView;

    public void initialize() {
        Predicate<Playable> wantAllItems = player -> true;
        FilteredList<Playable> filteredList = new FilteredList<Playable>(PlayerData.getInstance().getPlayersList(), wantAllItems);
        SortedList<Playable> sortedList = new SortedList<Playable>(filteredList, (o1, o2) -> o2.getHighScore().compareTo(o1.getHighScore()));

        highScoresListView.setItems(sortedList);
    }
}

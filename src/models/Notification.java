package models;

import javafx.scene.control.Label;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class Notification {
    private static Popup popup = new Popup();

    private static Popup createPopup(final String type, final String message) {
        popup.setAutoFix(true);
        popup.setHideOnEscape(true);
        Label label = new Label(message);
        label.getStylesheets().add("/resources/styles/styles.css");
        label.getStyleClass().add("popup");
        label.getStyleClass().add(type);
        popup.getContent().add(label);
        return popup;
    }

    public static void showPopupMessage(final String type, final String message, final Stage stage) {
        popup = createPopup(type, message);
        popup.setOnShown(e -> {
            popup.setX(stage.getX() + 20);
            popup.setY(stage.getY() + stage.getHeight() - popup.getHeight()- 20);
        });
        popup.show(stage);
    }

    public static void hidePopupMessage(){
        popup.hide();
    }


}

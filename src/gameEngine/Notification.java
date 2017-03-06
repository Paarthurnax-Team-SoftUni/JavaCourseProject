package gameEngine;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Notification {
    private static Popup popup = new Popup();

    private static Popup createPopup(final String type, final String message) {
        popup.setAutoFix(true);
        popup.setAutoHide(true);
        popup.setHideOnEscape(true);
        Label label = new Label(message);
        label.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                popup.hide();
            }
        });
        label.getStylesheets().add("/resources/styles/styles.css");
        label.getStyleClass().add("popup");
        label.getStyleClass().add(type);
        popup.getContent().add(label);
        return popup;
    }

    public static void showPopupMessage(final String type, final String message, final Stage stage) {
        popup = createPopup(type, message);
        popup.setOnShown(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                popup.setX(stage.getX() + 20);
                popup.setY(stage.getY() + stage.getHeight() - popup.getHeight()- 20);
            }
        });
        popup.show(stage);
    }


}

package utils.notifications;

import utils.constants.StylesConstants;
import javafx.scene.control.Label;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class Notification {

    private static Popup popup = new Popup();

    private static Popup createPopup(final String type, final String message) {
        popup.setAutoFix(true);
        popup.setHideOnEscape(true);
        Label label = new Label(message);
        label.getStylesheets().add(StylesConstants.STYLES_PATH);
        label.getStyleClass().add(StylesConstants.STYLES_TYPE);
        label.getStyleClass().add(type);
        popup.getContent().add(label);
        return popup;
    }

    public static void showPopupMessage(final String type, final String message, final Stage stage) {
        popup = createPopup(type, message);
        popup.setOnShown(e -> {
            popup.setX(stage.getX() + StylesConstants.WINDOW_OFFSET);
            popup.setY(stage.getY() + stage.getHeight() - popup.getHeight() - StylesConstants.WINDOW_OFFSET);
        });
        popup.show(stage);
    }

    public static void hidePopupMessage() {
        popup.hide();
    }
}

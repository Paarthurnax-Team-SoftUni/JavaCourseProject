package utils.stages;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import utils.constants.LoginStringConstants;

import java.io.IOException;
import java.util.Optional;

public class DialogBox {

    public static void loadErrorBox() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(LoginStringConstants.ERROR_USERNAME_TITLE);
        alert.setContentText(LoginStringConstants.ERROR_USERNAME_CONTENT);
        alert.setHeaderText(LoginStringConstants.ERROR_USERNAME_HEADER);
        alert.showAndWait();
    }

    public static boolean loadConfirmBox(String name) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(LoginStringConstants.LOGIN_USER_TITLE);
        alert.setHeaderText(LoginStringConstants.LOGIN_USER_HEADER + name);
        alert.setContentText(LoginStringConstants.LOGIN_USER_CONTENT);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            return true;
        }

        return false;
    }

    public static void loadTableViewBox(FXMLLoader fxmlLoader) {
        Dialog<ButtonType> dialog = new Dialog<>();


        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.showAndWait();
    }
}

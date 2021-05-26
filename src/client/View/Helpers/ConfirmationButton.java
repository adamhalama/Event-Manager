package client.View.Helpers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ConfirmationButton
{
    public ConfirmationButton()
    {

    }

    public static boolean confirmationView(String text)
    {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setHeaderText(text);

        Optional<ButtonType> result = a.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            return true;
        }
        else
            return false;
    }
}



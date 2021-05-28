package client.ViewModel;

import client.Model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MainMenuViewModel
{
    private StringProperty errorLabel;
    private Model model;

    public MainMenuViewModel(Model model)
    {
        this.model = model;
        this.errorLabel = new SimpleStringProperty();
    }

    public void reset()
    {
        errorLabel.set("");
    }

    public StringProperty getErrorLabelProperty()
    {
        return errorLabel;
    }

    public boolean myAccountButton()
    {
        if (model.getLoggedClientID() == 0)
        {
            errorLabel.set("Client not logged in");
            return false;
        }
        return true;
    }
}

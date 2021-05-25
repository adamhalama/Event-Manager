package client.ViewModel;

import client.Model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoginViewModel {
    private StringProperty usernameProperty;
    private StringProperty passwordProperty;

    public LoginViewModel(){
        this.usernameProperty = new SimpleStringProperty();
        this.passwordProperty = new SimpleStringProperty();
    }

    public StringProperty getUsernameProperty() {
        return usernameProperty;
    }

    public StringProperty getPasswordProperty() {
        return passwordProperty;
    }
}

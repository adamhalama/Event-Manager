package client.ViewModel;

import Shared.API;
import client.Model.Model;
import client.RmiClient;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LoginViewModel {
    private StringProperty usernameProperty;
    private StringProperty passwordProperty;
    private RmiClient modelAPI;

    public LoginViewModel(RmiClient api) {
        this.usernameProperty = new SimpleStringProperty();
        this.passwordProperty = new SimpleStringProperty();
        this.modelAPI = api;
    }

    public StringProperty getUsernameProperty() {
        return usernameProperty;
    }

    public StringProperty getPasswordProperty() {
        return passwordProperty;
    }

    public void login(String username, String password) {
        try {
            //modelAPI.loginEmployee(username, password);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}

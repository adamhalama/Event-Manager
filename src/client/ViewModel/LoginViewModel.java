package client.ViewModel;

import Shared.API;
import client.Model.Model;
import client.RmiClient;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;

public class LoginViewModel {
    private Model model;
    private StringProperty usernameProperty;
    private StringProperty passwordProperty;
//    private RmiClient modelAPI;

    public LoginViewModel(/*RmiClient api*/Model model)
    {
        this.model = model;
        this.usernameProperty = new SimpleStringProperty();
        this.passwordProperty = new SimpleStringProperty();
//        this.modelAPI = api;
    }

    public StringProperty getUsernameProperty() {
        return usernameProperty;
    }

    public StringProperty getPasswordProperty() {
        return passwordProperty;
    }

    public void login(String username, String password) {

        try
        {
            model.login(username, password);
        } catch (SQLException throwables)
        {
            throwables.printStackTrace();
        } catch (GeneralSecurityException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        //TODO add different warnings on different exceptions?
    }
}

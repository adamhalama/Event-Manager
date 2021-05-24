package client.View.Login;

import client.View.ViewHandler;
import client.ViewModel.LoginViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

public class LoginViewController {
    @FXML
    private TextField userTextField;
    @FXML
    private PasswordField passField;

    private ViewHandler viewHandler;
    private LoginViewModel viewModel;
    private Region root;

    public LoginViewController(){}

    public void init(ViewHandler viewHandler, LoginViewModel viewModel, Region root){
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;

        this.userTextField.textProperty().bindBidirectional(viewModel.getUsernameProperty());
        this.passField.textProperty().bindBidirectional(viewModel.getPasswordProperty());
    }
}

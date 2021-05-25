package client.View.Login;

import client.View.ViewHandler;
import client.ViewModel.LoginViewModel;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class LoginViewController {
    @FXML
    private TextField userTextField;
    @FXML
    private PasswordField passField;
    @FXML
    private Button showButton;
    @FXML
    private HBox rootVBox;

    private ViewHandler viewHandler;
    private LoginViewModel viewModel;

    private Region root;

    public LoginViewController() {
    }

    public void init(ViewHandler viewHandler, LoginViewModel viewModel, Region root) {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;
        this.userTextField.textProperty().bindBidirectional(viewModel.getUsernameProperty());
        this.passField.textProperty().bindBidirectional(viewModel.getPasswordProperty());

    }

    @FXML
    void onEnter() {
        loginPress();
    }

    @FXML
    void showPress() {
        // show the password button
        // the step: remove the passwordField first and add a textField

    }

    @FXML
    void forgetPassword() {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText("Forget password?");
        a.setContentText("Please contact administrator for help.");
        Optional<ButtonType> result = a.showAndWait();
        if (result.get() == ButtonType.APPLY) {
            a.close();
        }
    }

    @FXML
    void loginPress() {
        try {
            viewModel.login(userTextField.getText(), passField.getText());
            viewHandler.openView("MainMenu");
        } catch (Exception e) {
            Alert b = new Alert(Alert.AlertType.ERROR);
            b.setHeaderText("Wrong username or password");
            Optional<ButtonType> result = b.showAndWait();
            if (result.get() == ButtonType.APPLY) {
                b.close();
            }
        }
    }

    public Region getRoot() {
        return root;
    }

    private void initTextField(TextField field) {
        field.setLayoutX(333);
        field.setLayoutY(48);
    }
}

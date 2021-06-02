package client.View.MainMenu;

import client.View.Helpers.ConfirmationButton;
import client.View.ViewHandler;
import client.ViewModel.MainMenuViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class MainMenuViewController
{
    @FXML
    private Button roomsButton, eventsButton, employeesButton, chatButton, myAccountButton, logoutButton;
    @FXML
    private Label errorLabel;

    private ViewHandler viewHandler;
    private MainMenuViewModel viewModel;
    private Region root;

    public MainMenuViewController()
    {
    }

    public void init(ViewHandler viewHandler, MainMenuViewModel viewModel, Region root)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;

        errorLabel.textProperty().bind(viewModel.getErrorLabelProperty());
    }

    @FXML
    private void myAccountButton()
    {
        if (viewModel.myAccountButton())
            viewHandler.openView("MyAccount");
    }

    @FXML
    private void logoutButton()
    {
        if (ConfirmationButton.confirmationView("Are you sure you want to log out?"))
        {
            if(viewModel.logoutButton())
                viewHandler.openView("Login");
        }
    }

    @FXML
    private void roomsButton(){
        viewHandler.openView("RoomList");
    }

    @FXML
    private void eventsButton()
    {
        viewHandler.openView("EventList");
    }

    @FXML
    private void employeesButton(){
        viewHandler.openView("EmployeeList");
    }

    @FXML
    private void chatButton(){
        viewHandler.openView("MessageRoomList");
    }

    public Region getRoot()
    {
        return root;
    }
}

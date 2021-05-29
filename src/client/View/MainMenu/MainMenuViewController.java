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
    private void myAccountButton() throws SQLException, RemoteException {
        if (viewModel.myAccountButton())
            viewHandler.openView("MyAccount");
    }

    @FXML
    private void logoutButton()
    {
        ConfirmationButton.confirmationView("Are you sure you want to log out?");
        //todo log out
    }

    @FXML
    private void roomsButton() throws SQLException, RemoteException {
        viewHandler.openView("RoomList");
    }

    @FXML
    private void eventsButton() throws SQLException, RemoteException {
        viewHandler.openView("EventList");
    }

    @FXML
    private void employeesButton() throws SQLException, RemoteException {
        viewHandler.openView("EmployeeList");
    }

    @FXML
    private void chatButton() throws SQLException, RemoteException {
        viewHandler.openView("MessageRoomList");
    }

    public Region getRoot()
    {
        return root;
    }
}

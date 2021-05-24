package client.View.MainMenu;

import client.View.ViewHandler;
import client.ViewModel.MainMenuViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

public class MainMenuViewController
{
    @FXML
    private Button roomsButton, eventsButton, employeesButton, chatButton, myAccountButton, logoutButton;

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
    }

    @FXML
    private void myAccountButton()
    {
        //todo open a specific employee
    }

    @FXML
    private void logoutButton()
    {
        //todo log out
    }

    @FXML
    private void roomsButton()
    {
        viewHandler.openView("RoomList");
    }

    @FXML
    private void eventsButton()
    {
        viewHandler.openView("EventList");
    }

    @FXML
    private void employeesButton()
    {
        viewHandler.openView("EmployeeList");
    }

    @FXML
    private void chatButton()
    {
        //todo open chat rooms
    }

    public Region getRoot()
    {
        return root;
    }
}

package client.View.MainMenu;

import client.View.ViewHandler;
import client.ViewModel.MainMenuViewModel;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;

public class MainMenuViewController
{
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
        //todo open roomList
    }

    @FXML
    private void eventsButton()
    {
        System.out.println("test: eventsButton");
        viewHandler.openView("EventList");
    }

    @FXML
    private void employeesButton()
    {
        //todo open eployeeList
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

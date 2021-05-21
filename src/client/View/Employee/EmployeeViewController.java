package client.View.Employee;

import client.View.StringIntegerConverter;
import client.View.ViewHandler;
import client.ViewModel.CreateRoomViewModel;
import client.ViewModel.EmployeeViewModel;
import client.ViewModel.PermissionViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;


public class EmployeeViewController
{
    @FXML
    private Label topLabel, employeeIDTextLabel;
    @FXML
    private Label passwordLabel, repeatPasswordLabel;
    @FXML
    private TextField username, password, repeatPassword, name, surname, role;
    @FXML
    private ListView<String> eventsList, messageRoomList;

    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private Button addButton, removeButton;
    @FXML
    private TableView<PermissionViewModel> permissionTable;
    @FXML
    private TableColumn<PermissionViewModel, String> permissionColumn;

    @FXML
    private Button confirmEditButton;


    private ViewHandler viewHandler;
    private EmployeeViewModel viewModel;
    private Region root;

    private boolean editing;
    private boolean viewing;


    public void init(ViewHandler viewHandler, EmployeeViewModel viewModel, Region root)
    {
        StringIntegerConverter converter = new StringIntegerConverter(0);

        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;

        name.textProperty().bindBidirectional(viewModel.getNameProperty());
        surname.textProperty().bindBidirectional(viewModel.getSurnameProperty());
        role.textProperty().bindBidirectional(viewModel.getRoleProperty());

        username.textProperty().bindBidirectional(viewModel.getUsernameProperty());

    }


    @FXML
    private void passwordTyped()
    {

    }

    @FXML
    private void repeatPasswordTyped()
    {

    }

    @FXML
    private void addButton()
    {

    }

    @FXML
    private void removeSelectedButton()
    {

    }

    @FXML
    private void confirmButton()
    {

    }

    @FXML
    private void backButton()
    {

    }

}
